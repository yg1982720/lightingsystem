package com.zklink.lightingsystem.scene;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zklink.lightingsystem.login.MainActivity;
import com.zklink.lightingsystem.R;

/**
 * Created by Administrator on 2016/5/13.
 */
public class SceneActivity extends Activity {
    private Button scene_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);
        scene_btn=(Button)findViewById(R.id.scene_return);
        scene_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SceneActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}