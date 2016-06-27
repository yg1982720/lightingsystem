package com.zklink.lightingsystem.login;

import android.app.Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import com.zklink.lightingsystem.socket.ClientSocketUtil;

/**
 * Created by Administrator on 2016/4/11.
 */
public class Socketapp extends Application {

    private Socket socket;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    private String str,jzqid="16010001";
    private int Boo;
    private ClientSocketUtil cs;
    InetAddress address;
    byte[] buf = new byte[1024];
    int len = 0;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ClientSocketUtil getcs() {
        return cs;
    }

    public void setcs(ClientSocketUtil cs) {
        this.cs = cs;
    }
    public void setjzqid(String id) {
        jzqid = id;
    }
    public String getjzqid() {
        return jzqid;
    }
    public DataOutputStream getOut() {
        return out;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public String getString(){
        return str;
    }
    public void setString(String s){
        str =s;
    }
    public int getBoolean(){
        return Boo;
    }
    public void setBoolean(int b){
        Boo =b;
    }
}
