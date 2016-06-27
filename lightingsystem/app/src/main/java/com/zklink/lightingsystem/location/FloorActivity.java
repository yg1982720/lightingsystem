package com.zklink.lightingsystem.location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zklink.lightingsystem.login.MainActivity;
import com.zklink.lightingsystem.R;

/**
 * Created by Administrator on 2016/5/14.
 */
public class FloorActivity extends Activity {
    private Button floor_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);
        floor_btn=(Button)findViewById(R.id.floor_return);
        floor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FloorActivity.this, LocationActivity.class));
                finish();
            }
        });

    }
}
