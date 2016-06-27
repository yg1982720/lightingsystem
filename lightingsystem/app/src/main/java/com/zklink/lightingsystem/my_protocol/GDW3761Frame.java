package  com.zklink.lightingsystem.my_protocol;


import  com.zklink.lightingsystem.util.Utils;

/**
 * Created by Administrator on 2016/3/11 0011.
 */
public class GDW3761Frame {
    // PW
    protected byte[] pw;
    protected Address address;

    private GDW3761Control control;
    private int afn;
    private GDW3761Seq seq;
    private int fn ;
    private int pn ;



    private int delay;
    private byte[] data;

    // EC
    private int ec1;
    private int ec2;
    // TP
    private int pfc;
    private byte[] a16 = new byte[4];



    public GDW3761Control getControl() {
        return control;
    }

    public void setControl(GDW3761Control control) {
        this.control = control;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getAfn() {
        return afn;
    }

    public void setAfn(int afn) {
        this.afn = afn;
    }

    public GDW3761Seq getSeq() {
        return seq;
    }

    public void setSeq(GDW3761Seq seq) {
        this.seq = seq;
    }

    /**
     *
     * @param ret
     * @param offset
     */
    private void setFnByte(byte[] ret,int offset) {
        if (fn==0) {
            ret[offset] = 0;
            ret[offset + 1] = 0;
            return;
        }
        int i = fn;
        int j = (i - 1) % 8;
        i = (i - 1) / 8;
        j = 1 << j;

        ret[offset] = (byte)j;
        ret[offset + 1] = (byte)i;
    }

    /**
     *
     * @param fn
     */
    public void setFn( int fn) {
        if (fn<0)
            this.fn = 0;
        else
            this.fn = fn;
    }

    /**
     *
     * @param ret
     * @param offset
     */
    private void setPnByte(byte[] ret,int offset) {
        if (pn==0) {
            ret[offset] = 0;
            ret[offset + 1] = 0;
            return;
        }
        int i = pn;
        int j = (i % 8) - 1;
        i = (i - 1)  / 8;
        i++;
        j = 1 << j;

        ret[offset] = (byte)j;
        ret[offset + 1] = (byte)i;

    }

    /**
     *
     * @param pn
     */
    public void setPn(int pn) {
        if (pn<0)
            this.pn = 0;
        else
            this.pn = pn;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public GDW3761Frame() {
        address = new GDW3761Address();
        control = new GDW3761Control();
        seq = new GDW3761Seq();
        pw = new byte[16];
    }

    /**
     *
     * @return
     */
    public byte[] toBytes() {   //建立一个数组，用于存储数据报文中的所有字节
        if (data == null) { //private byte[] data;
            data = new byte[0];
        }
        /**
         * 6个字节的0x68 len len 0x68
         * 1个字节控制域
         * 地址域 5字节 = address.getAddress().length
         * 2个字节的AFN+SEQ
         * 2个字节 CS 0x16
         * 4个字节 数据单元标识 pn fn
         */

        // len变量用于记录数据报文包含的字节总数

        //address = new GDW3761Address();
        //address.getAddress()得到一个字节数组 ， 该数组包含集中器地址域

        int len = 6+1+ address.getAddress().length + 2 + 2 + +4+ data.length;
        System.out.println("计算报文长度="+len);
        if (needPw()) {
            len += pw.length;
        }
        if (!control.isFromMaster() && control.isFcb_acd()) {
            len += 2;
        }
        if (seq.isTpv()) {//如果Tpv = 1 ,那么表示在附加信息域中带有时间标签Tp(6字节)
            len += 6;
        }
        byte ret[] = new byte[len];//新建一个数组  用于存储一条完整的报文数据
        ret[0] = 0x68;
        ret[1] = ret[3] = (byte) ((2 + ((len - 8) << 2)) & 0xff);
        ret[2] = ret[4] = (byte) (((len - 8) >> 6) & 0xff);
        ret[5] = 0x68;
        ret[6] = control.getByte();//设置控制域C
        //根据address = new GDW3761Address(); 设置地址域
        //外部会调用GDW3761Address类里面有setAddress()方法，用于设置地址域
        System.arraycopy(address.getAddress(), 0, ret, 7, address.getAddress().length);
//        ret[7] = address.getAddress()[0];
        for(int i=7; i<12; i++){
            System.out.print(ret[i]+" ");
        }
        System.out.println();
        int index = 7 + address.getAddress().length;
        ret[index++] = (byte) afn;//设置AFN
        ret[index++] = seq.getByte();//设置SEQ
        //设置数据单元标识
        //ret[index++] =
        //ret[index++] = (byte) getFn();
        setPnByte(ret,index++);
        index ++;
        setFnByte(ret,index++);
        index ++;
        //是否设置了单元数据
        if (data!=null){
            System.arraycopy(data, 0, ret, index, data.length);
            index += data.length;
        }

        //是否设置了PW
        if (needPw()) {
            System.arraycopy(pw, 0, ret, index, pw.length);
            index += pw.length;
        }
        if (!control.isFromMaster() && control.isFcb_acd()) {
            ret[index++] = (byte) ec1;
            ret[index++] = (byte) ec2;
        }
        if (seq.isTpv()) {
            ret[index++] = (byte) (pfc & 0xff);
            System.arraycopy(a16, 0, ret, index, a16.length);
            index += a16.length;
            ret[index++] = (byte) (delay & 0xff);
        }
        ret[index++] = (byte) Utils.checksum(ret, 6, len - 8);//计算校验和CS
        ret[index++] = 0x16;//设置数据报文以0x16 结尾
//        String str = new String(ret);
//        System.out.println(str);
        System.out.println("打印报文数据");
        String str;
        for(int i=0; i<ret.length; i++){
            str = Integer.toHexString(ret[i] & 0xFF);
            if (str.length()<2)
                str ="0" + str;
            System.out.print(str + " ");
        }
        System.out.println("");
        return ret; //返回一条完整的报文数据
    }
    public Address getAddress() {
        return address;//address = new GDW3761Address();
    }
    protected boolean needPw() {
        if (control.isFromMaster()) {
            switch (afn) {
                case 0x01:
                case 0x04:
                case 0x05:
                case 0x06:
                case 0x0f:
                case 0x10:
                    return true;
            }
        }
        return false;
    }
}
