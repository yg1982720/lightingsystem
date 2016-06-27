package com.zklink.lightingsystem.location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zklink.lightingsystem.R;
import com.zklink.lightingsystem.db.DBManager;
import com.zklink.lightingsystem.equipment.EquipmentActivity;
import com.zklink.lightingsystem.gumei.ui.ListViewCompat;
import com.zklink.lightingsystem.location.FloorSelectDialog.OnFloorListener;
import com.zklink.lightingsystem.db.GroupInfo;
import com.zklink.lightingsystem.login.MainActivity;
import com.zklink.lightingsystem.timetable.ChangeTimeDialog;
import com.zklink.lightingsystem.wight.PopMenu;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/14.
 */
public class AddLocationActivity extends Activity {
    private Context context = AddLocationActivity.this;
    private DBManager dbManager;
    private String name = "组";
    private String timetable = null;
    private String scene = null;
    private int installnum=0;
    private int mGridCount;
    private ArrayAdapter<String> mAdapter;
    private ImageView ivflooricon;
    private EditText etfloorname;
    private TextView tvfloorselectlog;
    public int group_iconid;
    int[] imageId = new int[] { R.mipmap.floor1, R.mipmap.floor2,R.mipmap.floor3 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlocation);
        dbManager = new DBManager(this);
        Button btnCancel = (Button) findViewById(R.id.btn_title_back);
        btnCancel.setText(R.string.cancel);

        btnCancel.setOnClickListener(onViewClick);

        TextView tvTitle = (TextView) findViewById(R.id.tv_headerTitle);
        tvTitle.setText(R.string.floor);

        Button btnOk = (Button) findViewById(R.id.btn_title_ok);

        btnOk.setOnClickListener(onViewClick);

        ivflooricon = (ImageView) findViewById(R.id.iv_flooricon);
        ivflooricon.setOnClickListener(ivflooriconClick);
        ivflooricon.setImageDrawable(getResources().getDrawable(R.mipmap.floor1));
        etfloorname=(EditText)findViewById(R.id.et_floorname);
        etfloorname.setText("默认");
        tvfloorselectlog = (TextView) findViewById(R.id.tv_floorselectlog);
        tvfloorselectlog.setText("1");
        tvfloorselectlog.setOnClickListener(tvfloorselectlogClick);
    }

    View.OnClickListener onViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_title_back) {
                startActivity(new Intent(AddLocationActivity.this, LocationActivity.class));
                finish();
            } else if (v.getId() == R.id.btn_title_ok) {
                mGridCount=Integer.parseInt(tvfloorselectlog.getText().toString());

                if(dbManager.searchRepeatGroup(mGridCount)==true) //无重复组号，写入数据库Group表
                {
                    if((mGridCount<=18)&&mGridCount!=0)
                    {
                        //mAdapter.add("Grid" + mGridCount);
                        ArrayList<GroupInfo> infoList = new ArrayList<GroupInfo>();
                        GroupInfo m = new GroupInfo();
                        m.group_id = mGridCount;
                        m.group_name = etfloorname.getText().toString();
                        m.group_timetable = timetable;
                        m.group_scene = scene;
                        m.group_lampinstallnum = installnum;
                        m.group_iconid = group_iconid;
                        infoList.add(m);
                        dbManager.addgroup(infoList);

                        Intent resultIntent = new Intent();
                        resultIntent.setClass(AddLocationActivity.this, LocationActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putCharSequence("group_id", Integer.toString(mGridCount));
                        bundle.putCharSequence("group_name", etfloorname.getText().toString());
                        resultIntent.putExtras(bundle);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                        //Intent intent=new Intent(AddLocationActivity.this, LocationActivity.class);
                        //Bundle bundle=new Bundle();
                        //bundle.putCharSequence("group_id", Integer.toString(mGridCount));
                        //bundle.putCharSequence("group_name", etfloorname.getText().toString());
                        //intent.putExtras(bundle);
                        //startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"组号异常", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(AddLocationActivity.this, LocationActivity.class);
                        startActivity(intent);
                    }
                }
                else//数据库已经存在该组号
                {
                    Toast.makeText(getApplicationContext(),"重复组号", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AddLocationActivity.this, LocationActivity.class);
                    startActivity(intent);
                }
            }
        }
    };

    View.OnClickListener ivflooriconClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FloorIconDialog floorIconDialog = new FloorIconDialog(AddLocationActivity.this);
            Window window = floorIconDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
            window.setWindowAnimations(R.style.mystyle);  //添加动画

            floorIconDialog.setFlooricon(0);
            floorIconDialog.show();
            floorIconDialog.setFlooriconListener(new FloorIconDialog.OnFlooriconListener() {

                @Override
                public void onClick(int floor) {
                    ivflooricon.setImageDrawable(getResources().getDrawable(imageId[floor]));
                    group_iconid=floor;
                }
            });
        }
    };

    View.OnClickListener tvfloorselectlogClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FloorSelectDialog floorSelectDialog = new FloorSelectDialog(AddLocationActivity.this);
            Window window = floorSelectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
            window.setWindowAnimations(R.style.mystyle);  //添加动画

            floorSelectDialog.setFloor("1");
            floorSelectDialog.show();
            floorSelectDialog.setFloorListener(new OnFloorListener() {

                @Override
                public void onClick(String floor) {
                    tvfloorselectlog.setTextSize(20);
                    tvfloorselectlog.setText(floor);
                }
            });
        }
    };
}
