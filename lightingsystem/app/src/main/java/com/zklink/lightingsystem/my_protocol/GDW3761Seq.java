package com.zklink.lightingsystem.my_protocol;


public class GDW3761Seq {
    private boolean tpv = false;
    private boolean fir = true;
    private boolean fin = true;
    private boolean con = false;
    private int rpseq = 0;

    /**
     * tpv = false,fir = true,fin = true,con = false,rpseq = 0
     */
    public GDW3761Seq() {
    }

    public GDW3761Seq(int seq) {
        setByte(seq);
    }

    public boolean isTpv() {
        return tpv;
    }

    public void setTpv(boolean tpv) {
        this.tpv = tpv;
    }

    public boolean isFir() {
        return fir;
    }

    public void setFir(boolean fir) {
        this.fir = fir;
    }

    public boolean isFin() {
        return fin;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public boolean isCon() {
        return con;
    }

    public void setCon(boolean con) {
        this.con = con;
    }

    public int getRpseq() {
        return rpseq;
    }

    public void setRpseq(int rpseq) {
        this.rpseq = rpseq;
    }

    /**
     * 获得当前对象代表的seq字节
     *
     * @return
     */
    public byte getByte() {
        byte seq = 0;
        if (isTpv()) {
            seq |= 0x80;
        }
        if (isFir()) {
            seq |= 0x40;
        }
        if (isFin()) {
            seq |= 0x20;
        }
        if (isCon()) {
            seq |= 0x10;
        }
        seq |= (rpseq & 0x0f);
        return seq;
    }

    /**
     * 根据指定的seq字节设置当前对象的属性
     *
     * @param seq seq字段
     */
    public void setByte(int seq) {
        if ((seq & 0x80) == 0x80) {
            setTpv(true);
        } else {
            setTpv(false);
        }
        if ((seq & 0x40) == 0x40) {
            setFir(true);
        } else {
            setFir(false);
        }
        if ((seq & 0x20) == 0x20) {
            setFin(true);
        } else {
            setFin(false);
        }
        if ((seq & 0x10) == 0x10) {
            setCon(true);
        } else {
            setCon(false);
        }
        rpseq = (seq & 0x0f);
    }

    @Override
    public String toString() {
        return "GDW3761Seq{" +
                "tpv=" + tpv +
                ", fir=" + fir +
                ", fin=" + fin +
                ", con=" + con +
                ", rpseq=" + rpseq +
                '}';
    }
}
