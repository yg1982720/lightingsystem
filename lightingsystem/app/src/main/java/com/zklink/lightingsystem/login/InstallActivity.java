package com.zklink.lightingsystem.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.zklink.lightingsystem.R;
import com.zklink.lightingsystem.control.Controlactivity;
import com.zklink.lightingsystem.info.Infoactivity;
import com.zklink.lightingsystem.location.LocationActivity;
import com.zklink.lightingsystem.parameter.ParameterActivity;
import com.zklink.lightingsystem.scene.SceneActivity;
import com.zklink.lightingsystem.timetable.TimetableActivity;

/**
 * Created by Administrator on 2016/6/29.
 */
public class InstallActivity extends Activity{
    private ImageView installimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_install);
        installimage = (ImageView) findViewById(R.id.install_btn);
        installimage.setOnClickListener(InstallClickListenerClient);

    }

    private final View.OnClickListener InstallClickListenerClient = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(InstallActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    };
}
