package  com.zklink.lightingsystem.socket;

import com.zklink.lightingsystem.my_protocol.Address;
import  com.zklink.lightingsystem.my_protocol.GDW3761Frame;

public class SendByteData {
    public void sendData(ClientSocketUtil cs,int afn,int seq,int pn,int fn,String addr){
        GDW3761Frame gdw3761Frame = new GDW3761Frame();
        gdw3761Frame.getControl().setByte(4);//设置控制域字节
        gdw3761Frame.setAfn(afn);//设置AFN = AC
        gdw3761Frame.getSeq().setByte(seq);//设置SEQ 113=0x71
        gdw3761Frame.setPn(pn);
        gdw3761Frame.setFn(fn);
        gdw3761Frame.getAddress().setAddress(addr);//设置地址域
        System.out.println(gdw3761Frame.getAddress().toString());
        byte[] message = gdw3761Frame.toBytes();//封装报文数据
        cs.writeByteData(message);//发送报文
    }
}
