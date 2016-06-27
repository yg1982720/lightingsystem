package  com.zklink.lightingsystem.my_protocol;


public class StringFormater {
    public static String tokVAhString(byte[] data) {
        return String.format("%02x%02x%02x.%02x kVAh", data[3], data[2], data[1], data[0]);
    }

    public static String tokWhString(byte data[]) {
        return String.format("%02x%02x%02x.%02x kWh", data[3], data[2], data[1], data[0]);
    }

    public static String tokvarhString(byte data[]) {
        return String.format("%02x%02x%02x.%02x kvarh", data[3], data[2], data[1], data[0]);
    }

    public static String tokWString(byte data[]) {
        return String.format("%02x.%02x%02x kW", data[2], data[1], data[0]);
    }

    public static String tokvarString(byte data[]) {
        return String.format("%02x.%02x%02x kvar", data[2], data[1], data[0]);
    }

    public static String toMMDDHHmmString(byte data[]) {
        return String.format("%02x月%02x日 %02x:%02x", data[3], data[2], data[1], data[0]);
    }

    public static String toYYMMDDWWString(byte data[]) {
        return String.format("%02x年%02x月%02x日 %02x周", data[3], data[2], data[1], data[0]);
    }

    public static String toYYMMDDhhmmString(byte data[]) {
        return String.format("%02x年%02x月%02x日 %02x:%02x", data[4], data[3], data[2], data[1], data[0]);
    }

    public static String tossmmhhDDMMYYString(byte data[]) {
        return String.format("%02x年%02x月%02x日 %02x:%02x:%02x", data[0], data[1], data[2], data[3], data[4],
                data[5]);
    }

    public static String tossmmhhDDString(byte data[]) {
        return String.format("%02x:%02x:%02x %02x日", data[2], data[1], data[0], data[3]);
    }
}
