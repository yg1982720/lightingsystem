package com.zklink.lightingsystem.login;

/**
 * Created by Administrator on 2016/4/14.
 */
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import com.zklink.lightingsystem.R;

import java.util.Timer;
import java.util.TimerTask;
public class ActMenu extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_welscreen);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                Intent intent = new Intent(ActMenu.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(timerTask, 1000*2);
    }

}