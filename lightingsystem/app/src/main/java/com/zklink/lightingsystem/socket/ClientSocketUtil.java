package  com.zklink.lightingsystem.socket;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *ClientSocketUtil类是一个专门用于客户端与服务器建立Socket连接的类。
 * 该类提供四个连接服务器时，你可能会用到的参数，ip地址，端口，连接超时，读取数据超时，该类为这四个参数的设置了默认值，
 * 你也可以在建立类对象的时候传入新的值，该类也提供相应参数的set和get方法，用于设置四个参数的值。
 * 在使用时，你可以根据构造方法的带参个数，建立不同的ClientSocketUtil对象。
 *
 * 该类提供writeByteData()和readByteData()两个方法，用于读写字节数据，对于其他类型的数据，你可以自行扩展。
 *
 */
public class ClientSocketUtil {
    private String ip = "127.0.0.1";
    private int port = 10000;
    private int connectTime = 10000;//如果超时期已满，connect()还没连接上服务器, connect()将引发java.net.SocketTimeoutException
    private int timeOut = 3000;//在与此 Socket 关联的 InputStream 上调用read()将只阻塞此时间长度,如果超过设定值，read()将引发java.net.SocketTimeoutException
    private Socket socket = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    public int writesign = 0;
    public int readsign = 0;
    public int readsuccess=0;

    /*
        通过系统默认类型的 SocketImpl 创建未连接套接字。
     */
    public ClientSocketUtil(){}
    /*
         创建一个流套接字并将其连接到指定主机上的指定端口号。
     */
    public ClientSocketUtil(String ip , int port){
        this.ip = ip;
        this.port = port;
        socketConnected(this.ip, this.port);
    }
    /*
        将此套接字连接到具有指定read()阻塞超时值的服务器。
     */
    public ClientSocketUtil( String ip, int port,int timeOut){
        this.ip = ip;
        this.port = port;
        this.timeOut = timeOut;
        socketConnected( this.ip, this.port, this.timeOut);
    }
    /*
        将此套接字连接到具有指定connect()连接超时值 和 read()阻塞超时值的服务器
     */
    public ClientSocketUtil(String ip , int port , int connectTime , int timeOut){
        this.ip = ip;
        this.port = port;
        this.connectTime = connectTime;
        this.timeOut = timeOut;
        socketConnected(this.ip, this.port, this.connectTime, this.timeOut);
    }

    /**
     * you can set ip,port,connectTime,timeOut by set***() method
     */
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(int connectTime) {
        this.connectTime = connectTime;
    }

    public int getReadsuccess(){
        return readsuccess;
    }
    public void setReadsuccess(int b){
        readsuccess =b;
    }
    /*
        According to the IP and port connection
    */
    public void socketConnected(String ip, int port){
        socketConnected(ip, port, this.connectTime, this.timeOut);
    }
    /*
        According to the IP、port and timeOut connection
     */
    public void socketConnected(String ip, int port, int timeOut){
        socketConnected(ip, port, this.connectTime, timeOut);
    }
    /*
        According to the IP、port、connectTime and timeOut connection
     */
    public void socketConnected(String ip, int port, int connectTime, int timeOut){
        try{
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), connectTime);
            if (!isConnected()){
                Log.d("Tag", "socketConnected failure");
                return;
            }
            socket.setKeepAlive(true);//开启保持活动状态的套接字
            socket.setSoTimeout(timeOut);

            this.ip = ip;
            this.port = port;
            this.connectTime = connectTime;
            this.timeOut = timeOut;
        }catch (SocketTimeoutException e){  //如果超时期已满，connect()还没连接上服务器, connect()将引发SocketTimeoutException
            Log.d("TAG", "Connection timeout");
        }catch (Exception e){ //Exception - 如果在连接期间发生错误
            Log.d("TAG", "Connection exception");
        }
    }
    /*
        Reconnect the server
     */

    public Socket getSocket() {
        return socket;
    }

    public void socketReconnected() {
        socketConnected(ip, port, connectTime, timeOut);
    }
    /**
     * The following is write data
     */
    //创建OutputStream, 用于写字节数据
    public Boolean getOutputStream() {//get the OutputStream to read data from this socket
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            Log.d("TAG", "Failed to get the outputStream");
            return false;
        }
        return true;
    }

    /*
        This method is specially used for writing data
     */
    private void writeData(byte[] bye){
        try{
            outputStream.write(bye);
            System.out.println("数据写出去了");
            writesign = 0;  //write data successful
        }catch (IOException e){
            Log.d("TAG", "Failed to write bufData");
            writesign = 2;//writeByteData Execption
            return;
        }
    }
    /*
         Judge whether the conditions of the write data is established
     */
    private Boolean isCanWrite(){
        if (!testConnected()){
            writesign = 1;  //connected failure
            return false;
        }

        if (!getOutputStream()){
            writesign = 3;  //getOutputStream failure
            return false;
        }
        return true;
    }
    /*
        writeByteData()方法：
            向服务器发送数据，设置writesign标志位，通过它的值，你可以判定发送数据过程中是否有问题;
            readsign = 0 ; 成功发送数据
            readsign = 1 ; 连接服务器失败
            readsign = 2 ; 发送数据的过程中，出现IOExecption
            readsign = 3 ; 没有获取到OutputStream
     */
    public void writeByteData(byte[] bye) {
        System.out.println("准备获取outputStream");
        if (isCanWrite()){
            writeData(bye);
            return;
        }

        closeAll();

        if (isCanWrite()){
            writeData(bye);
            return;
        }
        closeAll();
        writesign = 2;//writeByteData Execption
    }

    /**
     * The following is read data
     */

    public Boolean getInputStream(){//get the InputStream to read data from this socket
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            Log.d("TAG", "Failed to get the inputStream");
            return false;
        }
        return true;
    }
    /*
    This method is specially used for read data
 */
    private byte[] readData(){
        byte[] read_byteData = new byte[2048];
        int couter=0;
        try {
            Log.d("TAG", "get InputStream");
            couter=inputStream.read(read_byteData);
            if(couter>0)
            {
                readsign = 0;   //read data successful
                readsuccess=1;
            }
            else
            {
                readsign = 4;//getInputStream failure
                return null;
            }
        } catch (SocketTimeoutException e){
            readsign = 2;//read data outTime
            return null;
        } catch (IOException e) {
            readsign = 3;//read data execption
            return null;
        }
        return read_byteData;
    }
    /*
         Judge whether the conditions of the read data is established
     */
    private Boolean isCanRead(){
        if (!testConnected()){
            readsign = 1;  //connected failure
            return false;
        }

        if (!getInputStream()){
            readsign = 4;//getInputStream failure
            return false;
        }
        return true;
    }
    /*
        readByteData()方法：
            读取服务器反馈的数据，设置readsign标志位，通过它的值，你可以判定读取数据过程中是否有问题;
            readsign = 0 ; 成功读取数据
            readsign = 1 ; 连接服务器失败
            readsign = 2 ; read方法阻塞超时，读取数据失败
            readsign = 3 ; 读取数据的过程中，出现IOExecption
            readsign = 4 ; 没有获取到InputStream
     */
    public byte[] readByteData() {
        System.out.println("准备获取outputStream");
        if (isCanRead()){
            return readData();
        }
        else
        {
            return null;
        }
    }

    /*
        test whether success to connect to the server
     */
    private Boolean testConnected(){
        if (isConnected()) {
            Log.d("TAG", "connect faiure");
            return true;
        }
        socketReconnected();
        if (isConnected()) {
            return true;
        }else {
            Log.d("TAG", "socketReconnected faiure");
            return false;
        }
    }

    /*Determine if connected to the server
        isClosed();  //If the connect is closed, closed, return true;If not close, returns false
        isConnected(); //If the socket to connect to the server successfully, return true.
     */
    public Boolean isConnected(){
        return socket.isConnected() && !socket.isClosed();//If connection, return true
    }

    /*
           Close the IO stream and the Socket
     */
    public void closeAll(){

        if (outputStream!=null){
            try {
                outputStream.close();
                outputStream = null;
            } catch (IOException e) {
                Log.d("TAG", "outputStream closed abnoramally");
            }
        }
        if (inputStream!=null){
            try {
                inputStream.close();
                inputStream = null;
            } catch (IOException e) {
                Log.d("TAG", "inputStream closed abnoramally");
            }
        }
        if (socket!=null){
            try {
                socket.close();
                socket = null;
            } catch (IOException e) {
                Log.d("TAG", "socket closed abnormally");
            }
        }
    }
}
