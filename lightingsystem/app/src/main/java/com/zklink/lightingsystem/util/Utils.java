/**
 * copyright© www.pemt.com.cn
 * create time: 2013-7-4 下午2:15:04
 */
package com.zklink.lightingsystem.util;

import com.zklink.lightingsystem.my_protocol.StringFormater;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 工具类
 *
 * @author hocking
 */
public class Utils {

    /**
     * 计算cs
     *
     * @param data   输入数据
     * @param offset 数据起点
     * @param len    数据长度
     * @return cs
     */
    public static int checksum(byte data[], int offset, int len) {
        int cs = 0;
        if (len + offset > data.length) {
            len = data.length - offset;
        }
        for (int i = offset; i < offset + len; i++) {
            cs += data[i];
        }
        return cs & 0xff;
    }

    /**
     * CRC16校验和算法
     *
     * @param data
     * @param offset
     * @param len
     * @return
     */
    public static int crc16(byte data[], int offset, int len) {
        int crc = 0xffff;
        for (int i = 0; i < len; i++) {
            byte databyte = data[offset + i];
            for (int j = 0; j < 8; j++) {
                int crcbit = (crc & 0x8000) != 0 ? 1 : 0;
                int databit = (databyte & 0x80) != 0 ? 1 : 0;
                crc = crc << 1;
                if (crcbit != databit) {
                    //Generator Polynomial =0x1021 ( the CCITT CRC-16 standard : X^16 + X^12 + X^5 + 1 = 2^16 + 2^12 + 2^5 + 1 = 0x11021
                    crc = crc ^ 0x1021;
                }
                databyte = (byte) (databyte << 1);
            }
        }
        return (crc ^ 0xffff) & 0xffff;
    }

    /**
     * 将二进制数组打印成字符串,带空格分割
     *
     * @param data
     * @param offset
     * @param len
     * @return
     */
    public static String toHex(byte data[], int offset, int len) {
        StringBuilder sb = new StringBuilder();
        if (len + offset > data.length) {
            len = data.length - offset;
        }
        for (int i = offset; i < len + offset; i++) {
            sb.append(String.format("%02x", data[i] & 0xff)).append(" ");
        }
        return sb.toString();
    }

    /**
     * 将二进制数组打印成字符串,带空格分割
     *
     * @param data
     * @return
     */
    public static String toHex(byte data[]) {
        return toHex(data, 0, data.length);
    }

    /**
     * 将带空格分割的16进制表示的字符串转换为二进制数组
     *
     * @param hexString
     * @return
     * @throws NumberFormatException
     */
    public static byte[] fromHex(String hexString) throws NumberFormatException {
        String s[] = hexString.split(" ");//删除空格
        byte ret[] = new byte[s.length];
        for (int i = 0; i < s.length; i++) {
            ret[i] = (byte) Integer.parseInt(s[i], 16);
        }
        return ret;
    }

    /**
     * 将二进制数组打印成字符串,不带空格分割
     *
     * @param data
     * @param offset
     * @param len
     * @return
     */
    public static String toHexNoSpace(byte data[], int offset, int len) {
        StringBuilder sb = new StringBuilder();
        if (len + offset > data.length) {
            len = data.length - offset;
        }
        for (int i = offset; i < len + offset; i++) {
            sb.append(String.format("%02x", data[i] & 0xff));
        }
        return sb.toString();
    }

    /**
     * 将二进制数组打印成字符串,补带空格分割
     *
     * @param data
     * @return
     */
    public static String toHexNoSpace(byte data[]) {
        return toHexNoSpace(data, 0, data.length);
    }

    /**
     * 将不带空格分割的16进制表示的字符串转换为二进制数组
     *
     * @param hexString
     * @return
     * @throws NumberFormatException
     */
    public static byte[] fromHexNoSpace(String hexString) throws NumberFormatException {
        byte ret[] = new byte[(hexString.length() + 1) / 2];
        for (int i = hexString.length(); i > 0; i -= 2) {
            ret[(i - 1) / 2] = (byte) Integer.parseInt(
                    hexString.substring((i - 2) >= 0 ? (i - 2) : i - 1, i), 16);
        }
        return ret;
    }

    /**
     * 反转数组
     *
     * @param array
     * @return 被反转后的临时数组
     */
    public static byte[] reverse(byte[] array) {
        byte tmp[] = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            tmp[array.length - 1 - i] = array[i];
        }
        return tmp;
    }

    /**
     * 反转数组
     *
     * @param array
     * @return 被反转后的原数组
     */
    public static <T> T[] reverse(T[] array) {
        for (int i = 0; i < array.length; i++) {
            array[array.length - 1 - i] = array[i];
        }
        return array;
    }

    /**
     * 为数组的每个元素自加0x33
     *
     * @param data
     * @param offset
     * @param length
     */
    public static void add(byte data[], int value, int offset, int length) {
        for (int i = offset; i < offset + length; i++) {
            data[i] += value;
        }
    }

    /**
     * 根据DA1，DA2返回所有的Pn值，以整数方式返回
     *
     * @param DA1
     * @param DA2
     * @return
     */
    public static List<Integer> getPns(byte DA1, byte DA2) {
        List<Integer> result = new ArrayList<Integer>();
        if (DA1 == 0 && DA2 == 0) {
            result.add(0);
        } else {
            for (int i = 0; i < 8; i++) {
                if (((1 << i) & DA1) == (1 << i)) {// 该位被设置了
                    if (DA2 == 0x00) {
                        for (int j = 0; j < 255; j++) {
                            result.add(j * 8 + i + 1);
                        }
                    } else {
                        result.add(((DA2 & 0xff) - 1) * 8 + i + 1);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 把pn转换为DA1，DA2，DA1存放在返回数组的0索引，DA2存放在返回数组的1索引
     *
     * @return
     */
    public static byte[] setPn(int pn) {
        byte[] result = new byte[2];
        if (pn == 0) {
            result[0] = 0;
            result[1] = 0;
        } else {
            result[0] = (byte) (1 << ((pn - 1) % 8));
            result[1] = (byte) ((pn - 1) / 8 + 1);
        }
        return result;
    }

    /**
     * 把pn转换为DA1，DA2，DA1存放在返回数组的0索引，DA2存放在返回数组的1索引
     *
     * @return
     */
    public static void setPn(int pn, byte data[], int offset) {
        if (pn == 0) {
            data[offset] = 0;
            data[offset + 1] = 0;
        } else {
            data[offset] = (byte) (1 << ((pn - 1) % 8));
            data[offset + 1] = (byte) ((pn - 1) / 8 + 1);
        }
    }

    /**
     * 根据DT1，DT2返回所有的Fn值，以整数方式返回
     *
     * @param DT1
     * @param DT2
     * @return
     */
    public static List<Integer> getFns(byte DT1, byte DT2) {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < 8; i++) {
            if (((1 << i) & DT1) == (1 << i)) {// 该位被设置了
                if (DT2 == 0xff) {
                    for (int j = 0; j < 31; j++) {
                        result.add(j * 8 + i + 1);
                    }
                } else {
                    result.add((DT2 & 0xff) * 8 + i + 1);
                }
            }
        }
        return result;
    }

    /**
     * 把pn转换为DT1，DT2，DT1存放在返回数组的0索引，DT2存放在返回数组的1索引
     *
     * @return
     */
    public static byte[] setFn(int fn) {
        byte[] result = new byte[2];
        if (fn == 0) {
            result[0] = 0;
            result[1] = 0;
        } else {
            result[0] = (byte) (1 << ((fn - 1) % 8));
            result[1] = (byte) ((fn - 1) / 8);
        }
        return result;
    }

    /**
     * 把pn转换为DT1，DT2，DT1存放在返回数组的offset索引，DT2存放在返回数组的offset+1索引
     *
     * @return
     */
    public static void setFn(int fn, byte data[], int offset) {
        if (fn == 0) {
            data[offset] = 0;
            data[offset + 1] = 0;
        } else {
            data[offset] = (byte) (1 << ((fn - 1) % 8));
            data[offset + 1] = (byte) ((fn - 1) / 8);
        }
    }

    public static String A1(byte data[]) {
        String weekname[] = {"X", "一", "二", "三", "四", "五", "六", "日"};
        return String.format("%02x-%02x-%02x %02x:%02x:%02x 周%s", data[5], data[4] & 0x1f, data[3], data[2],
                data[1], data[0], weekname[(data[4] >> 5) & 0x07]);
    }

    public static String A12(byte data[]) {
        return String.format("%02x%02x%02x%02x%02x%02x", data[5] & 0xff, data[4] & 0xff, data[3] & 0xff,
                data[2] & 0xff, data[1] & 0xff, data[0] & 0xff);
    }

    public static String A14(byte data[]) {
        return String.format("%02x%02x%02x.%02x%02xkWh", data[4], data[3], data[2], data[1], data[0]);
    }

    public static String A15(byte data[]) {
        return StringFormater.toYYMMDDhhmmString(data);
    }

    public static String A16(byte data[]) {
        return StringFormater.tossmmhhDDString(data);
    }

    public static String A20(byte data[]) {
        return String.format("%02x年%02x月%02x日", data[2], data[1], data[0]);
    }

    /**
     * 二进制转换成16进制
     *
     * @param bString
     * @return
     */
    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    /**
     * 十六进制转换成二进制
     */
    public static String binaryString(byte data) {
        String s = "";
        if (Integer.toBinaryString(byte2ten(data)).length() < 8) {
            for (int i = 1; i <= 8 - Integer.toBinaryString(byte2ten(data)).length(); i++) {
                s += "0";
            }
        }
        String ss = s + Integer.toBinaryString(byte2ten(data));
        return ss;
    }

    /**
     * 二进制转换成十进制
     */
    public static String binaryString2ten(String data) {
        BigInteger src = new BigInteger(data, 2);// 转换为BigInteger类型
        return src.toString();
    }

    /**
     * byte转换成10进制
     */
    public static int byte2ten(byte data) {
        return data & 0xff;
    }

    /**
     * 字符串反转
     */
    public static String strReverse(String str) {
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 获取异常的描述字符串，即{@link Throwable#getMessage()}。如果为null，那么就从
     * {@link Throwable#getCause()}中获取，以此类推，往上5层终止。
     *
     * @param t
     * @return
     */
    public static String getExceptionMsg(Throwable t) {
        String msg = t.getClass().getName();
        for (int i = 0; i < 5; i++) {
            if (t.getMessage() == null) {
                if (t.getCause() != null) {
                    t = t.getCause();
                } else {
                    break;
                }
            } else {
                msg = t.getMessage();
            }
        }
        return msg;
    }

    /**
     * @param str
     * @param strLength
     * @return str
     */
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append("0").append(str);// 左(前)补0
            // sb.append(str).append("0");//右(后)补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    /**
     * 字符串后面加0
     */
    public static String addZero(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            // sb.append("0").append(str);// 左(前)补0
            sb.append(str).append("0");// 右(后)补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    /**
     * 低位在前，高位在后，将两个字节数据value添加到数组data的offset索引处
     *
     * @param data
     * @param offset
     * @param value
     */
    public static void setShort(byte data[], int offset, int value) {
        data[offset] = (byte) (value & 0xff);
        data[offset + 1] = (byte) ((value >> 8) & 0xff);
    }

    /**
     * 低位在前，高位在后，将两个字节数据value从数组data的offset索引处读取出来
     *
     * @param data
     * @param offset
     * @return
     */
    public static int getShort(byte data[], int offset) {
        return (data[offset] & 0xff) | ((data[offset + 1] & 0xff) << 8);
    }

    /**
     * 低位在前，高位在后，将4个字节数据value添加到数组data的offset索引处
     *
     * @param data
     * @param offset
     * @param value
     */
    public static void setInt(byte data[], int offset, int value) {
        data[offset] = (byte) (value & 0xff);
        data[offset + 1] = (byte) ((value >> 8) & 0xff);
        data[offset + 2] = (byte) ((value >> 16) & 0xff);
        data[offset + 3] = (byte) ((value >> 24) & 0xff);
    }

    /**
     * 低位在前，高位在后，将4个字节数据value从数组data的offset索引处读取出来
     *
     * @param data
     * @param offset
     * @return
     */
    public static int getInt(byte data[], int offset) {
        return (data[offset] & 0xff) | ((data[offset + 1] & 0xff) << 8) | ((data[offset + 2] & 0xff) << 16)
                | ((data[offset + 3] & 0xff) << 24);
    }

    /**
     * 低位在前，高位在后，将3个字节数据value添加到数组data的offset索引处
     *
     * @param data
     * @param offset
     * @param value
     */
    public static void set3byte(byte data[], int offset, int value) {
        data[offset] = (byte) (value & 0xff);
        data[offset + 1] = (byte) ((value >> 8) & 0xff);
        data[offset + 2] = (byte) ((value >> 16) & 0xff);
    }

    /**
     * 低位在前，高位在后，将3个字节数据value从数组data的offset索引处读取出来
     *
     * @param data
     * @param offset
     * @return
     */
    public static int get3byte(byte data[], int offset) {
        return (data[offset] & 0xff) | ((data[offset + 1] & 0xff) << 8) | ((data[offset + 2] & 0xff) << 16);
    }

    /**
     * 将bcd码表示的数值（十六进制）转换为十进制，比如bcd码为0x33的数表示的是十进制的33，那么传入的参数bcd为49(0x33)，输出为33
     *
     * @param bcd 传入的bcd码，函数内会搽除该值的高24位，只保留最低的8位（BCD码只有一个字节）
     * @return 十进制结果
     */
    public static int bcd2int(int bcd) {
        bcd &= 0xff;
        return bcd - (bcd >> 4) * 6;
    }

    /**
     * 将十进制数value转换为bcd码形式，比如说十进制数18,转换为bcd码应当为24（0x18）
     *
     * @param value 输入数据
     * @return 返回的BCD码
     */
    public static int int2bcd(int value) {
        return (value / 10) * 6 + value;
    }

    /**
     * 将byte数组转换成int数组，调用者需要确保source不为null
     *
     * @param source 输入数组
     * @return 输出数组
     */
    public static int[] bytes2ints(byte[] source) {
        int ret[] = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            ret[i] = source[i] & 0xff;
        }
        return ret;
    }

    /**
     * 将int数组转换成byte数组，调用者需要确保source不为null
     *
     * @param source 输入数组
     * @return 输出数组
     */
    public static byte[] ints2bytes(int[] source) {
        byte ret[] = new byte[source.length];
        for (int i = 0; i < source.length; i++) {
            ret[i] = (byte) source[i];
        }
        return ret;
    }

    /**
     * 判定是否字符串为全0
     *
     * @param str 输入字符串
     * @return 如果为全0则返回true
     */
    public static boolean isAllZero(String str) {
        return str.matches("^0+$");
    }
}
