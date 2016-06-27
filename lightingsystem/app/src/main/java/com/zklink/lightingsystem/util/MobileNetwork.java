package  com.zklink.lightingsystem.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class MobileNetwork {

    /**
     * 判断手机网络连接状态
     * @return
     */
    public static Boolean isNetworking(Context context){
        boolean wifinetwork = isWifi(context);
        boolean mobilework = isMobile(context);
        if (!wifinetwork && !mobilework){
            Log.d("TAG", "无网络连接");
            return false;
        }
        if(wifinetwork==true && mobilework==false){
            Log.d("TAG","连接的wifi");
            return true;
        }else{
            Log.d("TAG", "连接的手机网络");
            return true;
        }
    }

    /**
     * 判断当前网络是否是wifi局域网
     * @param context
     * @return
     */
    public static Boolean isWifi(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info != null) {
            return info.isConnected(); // 返回网络连接状态
        }
        return false;
    }

    /**
     * 判断当前网络是否是手机网络
     * @param context
     * @return
     */
    public static Boolean isMobile(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (info != null) {
            return info.isConnected(); // 返回网络连接状态
        }
        return false;
    }
}
