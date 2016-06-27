package  com.zklink.lightingsystem.my_protocol;

/**
 * 376.1协议控制域
 *
 * @author hocking
 */
public class GDW3761Control {

    private boolean fromMaster = true;
    private boolean startFrame = true;
    private boolean fcb_acd = false;
    private boolean fcv = false;
    private int fn = 0;

    /**
     * 默认fromMaster = true，startFrame = true，fcb_acd = false，fcv = false，fn = 0
     */
    public GDW3761Control() {
    }

    GDW3761Control(int controlByte) {
        setByte(controlByte);
    }

    public boolean isFromMaster() {
        return fromMaster;
    }

    public void setFromMaster(boolean fromMaster) {
        this.fromMaster = fromMaster;
    }

    public boolean isStartFrame() {
        return startFrame;
    }

    public void setStartFrame(boolean startFrame) {
        this.startFrame = startFrame;
    }

    public boolean isFcb_acd() {
        return fcb_acd;
    }

    public void setFcb_acd(boolean fcb_acd) {
        this.fcb_acd = fcb_acd;
    }

    public boolean isFcv() {
        return fcv;
    }

    public void setFcv(boolean fcv) {
        this.fcv = fcv;
    }

    public int getFn() {
        return fn;
    }

    public void setFn(int fn) {
        this.fn = fn;
    }

    /**
     * 获得当前对象代表的控制域字节
     *
     * @return
     */
    public byte getByte() {
        byte controlByte = 0;
        if (!isFromMaster()) {
            controlByte |= 0x80;
        }
        if (isStartFrame()) {
            controlByte |= 0x40;
        }
        if (isFcb_acd()) {
            controlByte |= 0x20;
        }
        if (isFcv()) {
            controlByte |= 0x10;
        }
        controlByte |= (fn & 0x0f);
        return controlByte;
    }

    /**
     * 根据指定的控制域字节设置当前对象的属性
     *
     * @param controlByte
     */
    public void setByte(int controlByte) {
        if ((controlByte & 0x80) == 0x80) {
            setFromMaster(false);
        } else {
            setFromMaster(true);
        }
        if ((controlByte & 0x40) == 0x40) {
            setStartFrame(true);
        } else {
            setStartFrame(false);
        }
        if ((controlByte & 0x20) == 0x20) {
            setFcb_acd(true);
        } else {
            setFcb_acd(false);
        }
        if ((controlByte & 0x10) == 0x10) {
            setFcv(true);
        } else {
            setFcv(false);
        }
        fn = (controlByte & 0x0f);
    }

    @Override
    public String toString() {
        return "GDW3761Control{" +
                "fromMaster=" + fromMaster +
                ", startFrame=" + startFrame +
                ", fcb_acd=" + fcb_acd +
                ", fcv=" + fcv +
                ", fn=" + fn +
                '}';
    }
}
