package com.zklink.lightingsystem.timetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zklink.lightingsystem.login.MainActivity;
import com.zklink.lightingsystem.R;

import com.zklink.lightingsystem.timetable.ChangeTimeDialog.OnTimeListener;
/**
 * Created by Administrator on 2016/5/13.
 */
public class TimetableActivity extends Activity {
    private TextView mTimes;
    private TextView mTimevalue1,mTimevalue2,mTimevalue3,mTimevalue4,mTimevalue5,mTimevalue6;
    private TextView mDimmer1,mDimmer2,mDimmer3,mDimmer4,mDimmer5,mDimmer6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        mTimes = (TextView) findViewById(R.id.tv_time1);
        mTimevalue1 = (TextView) findViewById(R.id.tv_timevalue1);
        mDimmer1 = (TextView) findViewById(R.id.tv_dimmer1);
        mTimes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ChangeTimeDialog changeTimeDialog = new ChangeTimeDialog(TimetableActivity.this);
                changeTimeDialog.setTimes("00", "00", "0");
                changeTimeDialog.show();
                changeTimeDialog.setTimeListener(new OnTimeListener() {

                    @Override
                    public void onClick(String hour, String minu, String dimmer) {
                        mTimevalue1.setText(hour + ":" + minu);
                        mDimmer1.setText(dimmer+"%");
                    }
                });
            }
        });

        mTimes  = (TextView) findViewById(R.id.tv_time2);
        mTimevalue2 = (TextView) findViewById(R.id.tv_timevalue2);
        mDimmer2 = (TextView) findViewById(R.id.tv_dimmer2);
        mTimes .setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ChangeTimeDialog changeTimeDialog = new ChangeTimeDialog(TimetableActivity.this);
                changeTimeDialog.setTimes("00", "00", "0");
                changeTimeDialog.show();
                changeTimeDialog.setTimeListener(new OnTimeListener() {

                    @Override
                    public void onClick(String hour, String minu, String dimmer) {
                        mTimevalue2.setText(hour + ":" + minu);
                        mDimmer2.setText(dimmer+"%");
                    }
                });
            }
        });

        mTimes  = (TextView) findViewById(R.id.tv_time3);
        mTimevalue3 = (TextView) findViewById(R.id.tv_timevalue3);
        mDimmer3 = (TextView) findViewById(R.id.tv_dimmer3);
        mTimes .setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ChangeTimeDialog changeTimeDialog = new ChangeTimeDialog(TimetableActivity.this);
                changeTimeDialog.setTimes("00", "00", "0");
                changeTimeDialog.show();
                changeTimeDialog.setTimeListener(new OnTimeListener() {

                    @Override
                    public void onClick(String hour, String minu, String dimmer) {
                        mTimevalue3.setText(hour + ":" + minu);
                        mDimmer3.setText(dimmer+"%");
                    }
                });
            }
        });

        mTimes  = (TextView) findViewById(R.id.tv_time4);
        mTimevalue4 = (TextView) findViewById(R.id.tv_timevalue4);
        mDimmer4 = (TextView) findViewById(R.id.tv_dimmer4);
        mTimes .setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ChangeTimeDialog changeTimeDialog = new ChangeTimeDialog(TimetableActivity.this);
                changeTimeDialog.setTimes("00", "00", "0");
                changeTimeDialog.show();
                changeTimeDialog.setTimeListener(new OnTimeListener() {

                    @Override
                    public void onClick(String hour, String minu, String dimmer) {
                        mTimevalue4.setText(hour + ":" + minu);
                        mDimmer4.setText(dimmer+"%");
                    }
                });
            }
        });

        mTimes  = (TextView) findViewById(R.id.tv_time5);
        mTimevalue5 = (TextView) findViewById(R.id.tv_timevalue5);
        mDimmer5 = (TextView) findViewById(R.id.tv_dimmer5);
        mTimes .setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ChangeTimeDialog changeTimeDialog = new ChangeTimeDialog(TimetableActivity.this);
                changeTimeDialog.setTimes("00", "00", "0");
                changeTimeDialog.show();
                changeTimeDialog.setTimeListener(new OnTimeListener() {

                    @Override
                    public void onClick(String hour, String minu, String dimmer) {
                        mTimevalue5.setText(hour + ":" + minu);
                        mDimmer5.setText(dimmer+"%");
                    }
                });
            }
        });

        mTimes  = (TextView) findViewById(R.id.tv_time6);
        mTimevalue6 = (TextView) findViewById(R.id.tv_timevalue6);
        mDimmer6 = (TextView) findViewById(R.id.tv_dimmer6);
        mTimes .setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ChangeTimeDialog changeTimeDialog = new ChangeTimeDialog(TimetableActivity.this);
                changeTimeDialog.setTimes("00", "00", "0");
                changeTimeDialog.show();
                changeTimeDialog.setTimeListener(new OnTimeListener() {

                    @Override
                    public void onClick(String hour, String minu, String dimmer) {
                        mTimevalue6.setText(hour + ":" + minu);
                        mDimmer6.setText(dimmer+"%");
                    }
                });
            }
        });
    }
}
