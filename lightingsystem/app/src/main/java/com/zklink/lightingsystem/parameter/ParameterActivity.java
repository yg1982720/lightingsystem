package com.zklink.lightingsystem.parameter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zklink.lightingsystem.R;
import com.zklink.lightingsystem.login.Socketapp;
import com.zklink.lightingsystem.socket.ClientSocketUtil;
import com.zklink.lightingsystem.socket.SendByteData;
import com.zklink.lightingsystem.view.MipcaActivityCapture;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2016/6/22.
 */
public class ParameterActivity extends Activity{
    private Context context = ParameterActivity.this;
    private boolean clientconnectstate = true; //服务器网络接情况
    private boolean connect_touch_state = false;
    private boolean shakehand_ok = false; //握手成功
    private boolean connect_ok = false; //连接从标志位
    private Button connect_button;
    private EditText ipordnseditText;
    private EditText inputportText;
    private EditText inputjzqidText;
    private EditText jzqid;
    private TextView jzqtime;
    private Button jzqidscan,readjzqtime;
    InetAddress address;
    private Socket SocketClient = null; //客户端socket
    private Thread ClientThread = null; //客户端线程
    static BufferedReader mBufferedReaderClient = null;
    static PrintWriter mPrintWriterClient = null;
    String str1="zk-link.f3322.net";
    String str2="2030";
    String stradd="16010001";
    private final static int SCANNINJZQ_GREQUEST_CODE = 1;
    private View rootView;//缓存Fragment view
    byte[] buf = new byte[1024];
    ClientSocketUtil cs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter);

        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        //ViewGroup parent = (ViewGroup) rootView.getParent();
        //if (parent != null) {
        //    parent.removeView(rootView);
        //}
        jzqid=(EditText)findViewById(R.id.inputjzqid);
        jzqidscan=(Button)findViewById(R.id.jzqidscanbutton);
        readjzqtime=(Button)findViewById(R.id.readtimebtn);
        jzqtime=(TextView)findViewById(R.id.timetextView);
        connect_button = (Button) findViewById(R.id.Connectjzq);
        //connect_button.setOnClickListener(ConnectClickListenerClient);
        ipordnseditText =(EditText)findViewById(R.id.inputipordns);
        inputportText =(EditText)findViewById(R.id.inputport);
        inputjzqidText =(EditText)findViewById(R.id.inputjzqid);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        // FragmentManager Fragment = getFragmentManager();

//建立handler对象
        Handler handler_main = new Handler() {
            public void handleMessage(Message msg){
                switch (msg.what){  //根据what字段的值， 判断Socket连接状态和数据读取状态
                    case 1 :
                    {
                        String data_message = (String) msg.obj;
                        jzqtime.setText(data_message);
                    }break;

                }
                //服务器返回的数据在界面上显示出来
                //System.out.println("打印msg.obj=" + msg.obj);
                //String data_message = (String) msg.obj;
                //System.out.println("服务器返回的数据: "+data_message);
                //jzqtime.setText(data_message);
            }
        };
        clickListener(handler_main);//监听事件

        jzqidscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(context, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNINJZQ_GREQUEST_CODE);
            }
        });

        readjzqtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Socketapp app= (Socketapp) ParameterActivity.this.getApplication();
                SocketClient = app.getSocket();
                stradd=app.getjzqid().toString();
                if(cs!=null) {
                    if (cs.isConnected()) {
                        SendByteData sbd = new SendByteData();
                        sbd.sendData(cs,0xac,113,00,01,stradd);
                    }
                    else
                    {
                        Toast.makeText(context, "网络断开，请重新连接", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "网络未连接，请先连接服务器", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clickListener(Handler handler) {
        ConnectClickListenerClient(handler);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNINJZQ_GREQUEST_CODE:
                if(resultCode == -1){
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    jzqid.setText(bundle.getString("result"));
                }
                break;
        }
    }
    /************************
     * 连接按钮
     *************************/
    private void  ConnectClickListenerClient(final Handler handler) {
        connect_button.setOnClickListener(new View.OnClickListener() {
            // protected final View.OnClickListener ConnectClickListenerClient = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                if (clientconnectstate) {

                    clientconnectstate = false;
                    connect_touch_state = true;

                    // Socketapp app = (Socketapp) getActivity().getApplication();
                    str1 = ipordnseditText.getText().toString();
                    str2 = inputportText.getText().toString();
                    stradd = inputjzqidText.getText().toString();
                    if (str1.isEmpty() || str2.isEmpty()||stradd.isEmpty()) {
                        connect_ok = false;
                        Toast.makeText(context, "请输入集中器ID和服务器地址及端口", Toast.LENGTH_SHORT).show();
                    } else {
                        connect_button.setText("连接中......");
                        Socketapp app = (Socketapp) ParameterActivity.this.getApplication();
                        cs = new ClientSocketUtil(str1, Integer.parseInt(str2));
                        app.setcs(cs);
                        app.setjzqid(stradd);
                        app.setSocket(cs.getSocket());
                        if (cs.getSocket().isConnected() != false) {
                            connect_ok = true;
                            MyThread myThread = new MyThread(handler);
                            new Thread(myThread).start();
                            ChangeButtonView("连接成功");
                        } else {
                            connect_ok = false;
                            // app.setBoolean(connect_ok);
                            Toast.makeText(context, "网络连接出错", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                } else {
                    clientconnectstate = true;
                    shakehand_ok = false;
                    connect_ok = false;
                    connect_touch_state = false;
                    if (cs != null) {
                        if (cs.getSocket() != null) {
                            if (cs.isConnected()) {
                                cs.closeAll();
                            }
                        }
                    }
                    connect_button.setText("连接集中器");
                }
            }
        });
    }

    class MyThread implements Runnable{
        Handler handler;
        //public MyThread(){ }
        public MyThread(Handler handler){
            this.handler = handler;
        }
        @Override
        public void run(){
            Message msg = new Message();
            int couter=0;
            while(connect_ok==true)
            {
                byte[] read_Bytedata = cs.readByteData();
                //Socketapp app= (Socketapp) getActivity().getApplication();
                //SocketClient= app.getSocket();

                //try
                //{
                // if((couter = SocketClient.getInputStream().read(read_Bytedata))>0); //接收到有效帧
                if(cs.getReadsuccess()==1)//接收数据
                {
                    //Message msg = new Message();
                    cs.setReadsuccess(0);
                    String str;
                    StringBuffer c = new StringBuffer();
                    for (int i = 0; i < read_Bytedata.length; i++) {
                        str = Integer.toHexString(read_Bytedata[i] & 0xFF);
                        if (str.length() < 2)
                            str = "0" + str;
                        c.append(str);
                        //System.out.print(str + " ");
                    }
                    msg.what = 1;
                    msg.obj = c.substring(36,48);
                    handler.sendMessage(msg);//通过handler把数据发给处理界面的代码
                    //return;
                }
                // }
                // catch( Exception e )
                // {
                // msg.obj = "FALSE";
                //handler.sendMessage(msg);//通过handler把数据发给处理界面的代码

                //return;
                // }

            }
        }

    }


    private void ChangeButtonView(final String str) {
        connect_button.post(new Runnable() {
            @Override
            public void run() {
                connect_button.setText(str);
            }
        });
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
}

