package com.zklink.lightingsystem.control;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zklink.lightingsystem.R;


/**
 * Created by Administrator on 2016/5/17.
 */
public class Control_lampactivity extends Activity {
    private ImageView switchimage;
    private boolean switchcontrol_flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);
        switchimage = (ImageView) findViewById(R.id.lamp_onoff_btn);
        switchimage.setOnClickListener(SwitchClickListenerClient);
        TextView tv1 = (TextView) findViewById(R.id.volttv);
        TextView tv2 = (TextView) findViewById(R.id.currenttv);
        TextView tv3 = (TextView) findViewById(R.id.activepowertv);
        TextView tv4 = (TextView) findViewById(R.id.temptv);
        TextView tv5 = (TextView) findViewById(R.id.dimmingvaluetv);
        String voltvalue ="220V";
        String currentvalue = "1.00A";
        String activepowervalue ="220W";
        String tempvalue = "35度";
        String dimmingvaluevalue = "100%";
        tv1.setText(voltvalue);
        tv2.setText(currentvalue);
        tv3.setText(activepowervalue);
        tv4.setText(tempvalue);
        tv5.setText(dimmingvaluevalue);


    }

    private final View.OnClickListener SwitchClickListenerClient = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // TODO Auto-generated method stub
            if (switchcontrol_flag) {
                switchcontrol_flag = false;
                switchimage.setImageDrawable(getResources().getDrawable(R.drawable.switch_on));
            } else {
                switchcontrol_flag = true;
                switchimage.setImageDrawable(getResources().getDrawable(R.drawable.switch_off));
            }
        }

    };
}