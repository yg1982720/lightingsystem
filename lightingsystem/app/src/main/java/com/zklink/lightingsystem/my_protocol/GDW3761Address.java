package  com.zklink.lightingsystem.my_protocol;

import android.util.Log;

import java.util.Arrays;

/*
       GDW3761Address类用于设置集中器的地址域A，共五个字节;
       你可以他，先，通过调用本类的setAddress(String address)或者setAddress(byte[] address) 两个方法设置地址域
       然后，外部可以通过调用本类的getAddress()方法，获取地址域
 */

public class GDW3761Address implements Address{

    private byte[] address = new byte[5];

    /**
     * 集中器地址域，格式类似于16010001共八个数字，代表四个字节，如果格式不对，那么生成的地址将是全0
     *
     * @param addr 集中器地址
     */
    public GDW3761Address(String addr) {//你可以在建立类对象的时候，直接传入集中器地址
        setAddress(addr);
    }

    /**
     * 集中器地址域，5个字节:两个字节的区划码，两个字节的集中器地址，一个字节的主站编号和组地址标志位。假设集中器地址为5301
     * 50250(C44A), 主站地址编号为2，那么address[0]=01，address[1]=53，address[2]=4A，address
     * [3]=C4，address[4]=04。 传入参数允许不等于5个字节，少于字节部分补0,多于字节自动截断，忽略数组索引大于4的部分。
     *
     * @param address 集中器地址域
     */
    public GDW3761Address(byte[] address) throws IllegalArgumentException {
        setAddress(address);
    }

    /**
     * 获得默认全0的一个地址，按照376.1协议定义，全0的终端地址为无效
     */
    public GDW3761Address() {

    }

    @Override
    public byte[] getAddress() {
        return address;//   private byte[] address = new byte[5];
    }

    /**
     * 集中器地址域，格式类似于16010001共八个数字，代表四个字节，如果格式不对，那么生成的地址将是全0
     *
     * @param addr 集中器地址
     */
    @Override
    public void setAddress(String addr) {
        if (addr == null) {
            addr = "";
        }
        addr = addr.trim();
        byte[] setaddress = new byte[5];
        try {
            if (addr.length() == 8) {
//                System.arraycopy(Utils.fromHexNoSpace(addr.substring(0, 4)), 0, address, 0, 2);
                Log.d("TAG", "addr.length() == 8");
                setaddress[0] = (byte) Integer.parseInt(addr.substring(2, 4),16);
                setaddress[1] = (byte) Integer.parseInt(addr.substring(0, 2),16);
                setaddress[2] = (byte) Integer.parseInt(addr.substring(6, 8),16);
                setaddress[3] = (byte) Integer.parseInt(addr.substring(4, 6),16);
                setaddress[4] = 0;

                System.out.println(setaddress[0]);
                System.out.println(setaddress[1]);
                System.out.println(setaddress[2]);
                System.out.println(setaddress[3]);
                System.out.println(setaddress[4]);
                setAddress(setaddress);
                return;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            setAddress((byte[]) null);
        }
    }

    /**
     * 集中器地址域，5个字节:两个字节的区划码，两个字节的集中器地址，一个字节的主站编号和组地址标志位。假设集中器地址为5301
     * 50250(C44A), 主站地址编号为2，那么address[0]=01，address[1]=53，address[2]=4A，address
     * [3]=C4，address[4]=04。 传入参数允许不等于5个字节，少于字节部分补0,多于字节自动截断，忽略数组索引大于4的部分。
     *
     * @param setaddress 集中器地址域
     */
    @Override
    public void setAddress(byte[] setaddress) {    //private byte[] address = new byte[5] = this.address
        if (setaddress == null) {
            Log.d("TAG", "address = null.......");
            Arrays.fill(this.address, (byte) 0);//如果传入的地址域为空   则设置集中器地址域为0
        } else {
            if (setaddress.length < 5) {
                Log.d("TAG", "address.length < 5");
                System.arraycopy(setaddress, 0, this.address, 0, setaddress.length);
                Arrays.fill(this.address, setaddress.length, 5, (byte) 0);//如果格式不对，那么生成的地址将是全0
            } else {
                Log.d("TAG", "正常");
                System.arraycopy(setaddress, 0, this.address, 0, 5);//如果传入的地址域是有效的 ，则赋值给本类的address，让其保存，
                                                                    // 外界就可以通过本类的getAddress()方法获取到地址域的值了
                System.out.println(this.address[1]);
                for (int i=0; i<this.address.length; i++) {
                    System.out.print(Integer.toHexString(this.address[i])+" ");
                }
            }
        }
    }

    @Override
    public boolean isBroadcast() {
        return (address[2] & 0xff) == 0xff && (address[3] & 0xff) == 0xff && (address[4] & 0x01) == 0x01;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%02x%02x%05d", address[1], address[0], (address[2] & 0xff)
                + ((0xff & address[3]) << 8));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(address);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof GDW3761Address))
            return false;
        GDW3761Address other = (GDW3761Address) obj;
        for (int i = 0; i < 4; i++) {
            if (address[i] != other.address[i]) {
                return false;
            }
        }
        return true;
    }

}
