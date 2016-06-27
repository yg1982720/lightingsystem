package com.zklink.lightingsystem.equipment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zklink.lightingsystem.R;
import com.zklink.lightingsystem.control.Bean;
import com.zklink.lightingsystem.control.Control_lampactivity;
import com.zklink.lightingsystem.db.DBManager;
import com.zklink.lightingsystem.db.GroupInfo;
import com.zklink.lightingsystem.db.LampInfo;

import com.zklink.lightingsystem.location.LocationActivity;
import com.zklink.lightingsystem.login.MainActivity;
import com.zklink.lightingsystem.view.MipcaActivityCapture;
import com.zklink.lightingsystem.wight.PopMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.zklink.lightingsystem.gumei.ui.ListViewCompat;
import com.zklink.lightingsystem.gumei.ui.ListViewCompat.OnLoadListener;
import com.zklink.lightingsystem.gumei.ui.ListViewCompat.OnRefreshListener;
import com.zklink.lightingsystem.gumei.ui.SlideView;
import com.zklink.lightingsystem.gumei.ui.SlideView.OnSlideListener;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;
/**
 * Created by Administrator on 2016/6/8.
 */
public class EquipmentActivity extends Activity implements OnItemClickListener, OnClickListener,
        OnSlideListener, OnRefreshListener,OnLoadListener {
    private PopMenu popMenu;

    private DBManager dbManager;
    private Context context = EquipmentActivity.this;
    private final static int SCANNINLAMP_GREQUEST_CODE = 1;//灯控器
    private final static int SCANNINLINE_GREQUEST_CODE = 2;//线路控制器
    private final static int SCANNINCON_GREQUEST_CODE=3;//集中器

    private ListViewCompat mListView;
    private List<MessageItem> mMessageItems = new ArrayList<EquipmentActivity.MessageItem>();
    private SlideView mLastSlideViewWithStatusOn;
    private SlideAdapter adapter;
    private int allCount = 400;
    private String[] lampid;
    public int group_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);

        dbManager = new DBManager(this);


        Button btnExit = (Button) findViewById(R.id.btn_title_back);
        btnExit.setText(R.string.exit);

        btnExit.setOnClickListener(onViewClick);

        TextView tvTitle = (TextView) findViewById(R.id.tv_headerTitle);
        Bundle bundle=this.getIntent().getExtras();
        if(bundle!=null) {
            group_id = bundle.getInt("group_id");
            tvTitle.setText("第"+group_id+"组设备添加");
        }
        else {
            tvTitle.setText("设备添加");
        }

        ImageButton imageButton = (ImageButton) findViewById(R.id.btn_title_popmenu);
        imageButton.setOnClickListener(onViewClick);

        popMenu = new PopMenu(context);
        initView();
        loadData(ListViewCompat.REFRESH);
        //popMenu.addItems(new String[]{getString(R.string.floorset), getString(R.string.newdevice)});
        //popMenu.getlistview().setOnItemClickListener(popmenuItemClickListener);
        //mlistView=popMenu.getlistview();
        //mlistView.setOnItemClickListener(new Click());
        //mlistView.setAdapter(popMenu.getadapter());

    }

    View.OnClickListener onViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_title_back) {
                startActivity(new Intent(EquipmentActivity.this, LocationActivity.class));
                finish();
            } else if (v.getId() == R.id.btn_title_popmenu) {
                //popMenu.showAsDropDown(v);
                Intent intent = new Intent(context, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNINLAMP_GREQUEST_CODE);
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNINLAMP_GREQUEST_CODE:
                if(resultCode == -1){
                    Bundle bundle = data.getExtras();
                    ArrayList<LampInfo> infoList = new ArrayList<LampInfo>();
                    LampInfo m = new LampInfo();
                    m.lamp_id =bundle.getString("result");
                    m.lamp_name = "LED灯";
                    m.lamp_devicenum = 1;
                    m.lamp_scalepoint = 1;
                    m.lamp_group =group_id;
                    m.lamp_workmode=0;
                    m.lamp_usestatus=0;
                    if(dbManager.searchRepeatLamp(m.lamp_id)==true) //无重复灯号，写入数据库LAMP表
                    {
                        infoList.add(m);
                        dbManager.addlamp(infoList);
                        Toast.makeText(getApplicationContext(),"安装成功", Toast.LENGTH_SHORT).show();
                        initView();
                        loadData(ListViewCompat.REFRESH);
                    }
                    else//数据库已经存在该灯号
                    {
                        Toast.makeText(getApplicationContext(),"重复灯号", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case SCANNINLINE_GREQUEST_CODE:
                if(resultCode == -1){
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                }
                break;

            case SCANNINCON_GREQUEST_CODE:
                if(resultCode == -1){
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                }
                break;
        }
    }

    public void initView() {
        mListView = (ListViewCompat) findViewById(R.id.classeslist);
        adapter = new SlideAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnRefreshListener(this);
        mListView.setOnLoadListener(this);

    }

    private void loadData(final int what) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = handler.obtainMessage();
                msg.what = what;
                msg.obj = getData();
                handler.sendMessage(msg);
            }
        }).start();
    }

    // 测试数据
    public List<MessageItem> getData() {
        List<MessageItem> result = new ArrayList<MessageItem>();
        ArrayList<LampInfo> infoList = new ArrayList<LampInfo>();
        infoList = dbManager.searchLampData(group_id);

            for (LampInfo info : infoList) {
                MessageItem item = new MessageItem();
                item.iconRes = R.drawable.lampon;
                item.title = "灯控器ID:"+info.lamp_id;
                if(info.lamp_workmode==0)
                {
                    item.msg = "时间表";
                }
                else
                {
                    item.msg = "经纬度";
                }

                item.time = Integer.toString(info.lamp_group)+"组";
                result.add(item);
            }

        return result;
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            List<MessageItem> result = (List<MessageItem>) msg.obj;
            switch (msg.what) {
                case ListViewCompat.REFRESH:
                    mListView.onRefreshComplete();
                    mMessageItems.clear();
                    mMessageItems.addAll(result);
                    break;
                case ListViewCompat.LOAD:
                    mListView.onLoadComplete();
                    mMessageItems.addAll(result);
                    break;
                case 2:
                    mListView.onLoadComplete();
                    //Toast.makeText(Infoactivity.this, "已加载全部！", Toast.LENGTH_SHORT).show();
                    break;
            }
            mListView.setResultSize(result.size());
            adapter.notifyDataSetChanged();
        };
    };

    private class SlideAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.item_listview_delete, null);

                slideView = new SlideView(EquipmentActivity.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(EquipmentActivity.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            MessageItem item = mMessageItems.get(position);
            item.slideView = slideView;
            item.slideView.shrink();

            holder.icon.setImageResource(item.iconRes);
            holder.title.setText(item.title);
            holder.msg.setText(item.msg);
            holder.time.setText(item.time);
            holder.deleteHolder.setOnClickListener(new View.OnClickListener() {
                MessageItem item = mMessageItems.get(position);
                @Override
                public void onClick(View v) {
                    lampid=item.title.toString().split("[:]");
                    dbManager.delLampData(lampid[1].trim());
                    mMessageItems.remove(position);
                    adapter.notifyDataSetChanged();
                    //Toast.makeText(Infoactivity.this, "删除" + position+"个条", 0).show();
                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    adapter.notifyDataSetChanged();
                    // Toast.makeText(Infoactivity.this, "编辑" + position+"个条", 0).show();
                }
            });

            return slideView;
        }

    }

    public class MessageItem {
        public int iconRes;
        public String title;
        public String msg;
        public String time;
        public SlideView slideView;
    }

    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView msg;
        public TextView time;
        public TextView deleteHolder;
        public TextView edit;

        ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);
            msg = (TextView) view.findViewById(R.id.msg);
            time = (TextView) view.findViewById(R.id.time);
            deleteHolder = (TextView) view.findViewById(R.id.delete);
            edit = (TextView) view.findViewById(R.id.edit);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast.makeText(this, "onItemClick position=" + position, 0).show();

    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.holder:

                break;

            default:
                break;
        }
    }

    @Override
    public void onLoad() {
        // TODO Auto-generated method stub
        if(adapter.getCount()<allCount){
            loadData(ListViewCompat.LOAD);
        }else{
            Message msg = handler.obtainMessage();
            msg.what = 2;
            msg.obj = mMessageItems;
            handler.sendMessage(msg);

        }
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        loadData(ListViewCompat.REFRESH);
    }
}


