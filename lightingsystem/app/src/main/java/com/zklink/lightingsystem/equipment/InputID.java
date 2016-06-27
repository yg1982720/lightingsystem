package com.zklink.lightingsystem.equipment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zklink.lightingsystem.R;
import com.zklink.lightingsystem.login.MainActivity;
import com.zklink.lightingsystem.view.MipcaActivityCapture;
import com.zklink.lightingsystem.wight.PopMenu;

/**
 * Created by Administrator on 2016/6/17.
 */
public class InputID extends Activity {
    private PopMenu popMenu;
    private Context context = InputID.this;
    private Button btn_input_ok;
    private Button btn_input_cancel;
    private EditText et_input_lampid;
    public static final int RESULT_OK           = -1;
    private Paint paint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indeviceid);
        Button btnExit = (Button) findViewById(R.id.btn_title_back);
        btnExit.setText(R.string.back);

        btnExit.setOnClickListener(onViewClick);

        TextView tvTitle = (TextView) findViewById(R.id.tv_headerTitle);
        tvTitle.setText(R.string.handinput);

        ImageButton imageButton = (ImageButton) findViewById(R.id.btn_title_popmenu);
        imageButton.setVisibility(View.INVISIBLE);

        popMenu = new PopMenu(context);

        btn_input_cancel=(Button)findViewById(R.id.btn_input_cancel);
        btn_input_cancel.setOnClickListener(btncancelClick);
        btn_input_ok=(Button)findViewById(R.id.btn_input_ok);
        btn_input_ok.setOnClickListener(btnokClick);
        et_input_lampid=(EditText)findViewById(R.id.et_lampid);
    }

    View.OnClickListener onViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_title_back) {
                startActivity(new Intent(InputID.this, MipcaActivityCapture.class));
                finish();
            } else if (v.getId() == R.id.btn_title_popmenu) {

            }
        }
    };

    View.OnClickListener btncancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(InputID.this, MipcaActivityCapture.class));
            finish();
        }
    };

    View.OnClickListener btnokClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String lampid = et_input_lampid.getText().toString();
            if(lampid.equals("")) {
                Toast.makeText(InputID.this, "请输入正确的ID", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent resultIntent = new Intent();
                resultIntent.setClass(InputID.this, EquipmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("result", lampid);
                resultIntent.putExtras(bundle);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }
    };
}