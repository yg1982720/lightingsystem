package  com.zklink.lightingsystem.my_protocol;

/**
 * Created by Administrator on 2016/3/11 0011.
 */
public interface Address {
    public byte[] getAddress();

    public void setAddress(String addr);

    public void setAddress(byte[] address);

    public boolean isBroadcast();

}
