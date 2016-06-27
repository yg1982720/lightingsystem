package com.zklink.lightingsystem.location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
//import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

import com.zklink.lightingsystem.control.Controlactivity;
import com.zklink.lightingsystem.db.DBManager;
import com.zklink.lightingsystem.db.GroupInfo;
import com.zklink.lightingsystem.R;
import com.zklink.lightingsystem.db.LampInfo;
import com.zklink.lightingsystem.equipment.EquipmentActivity;
import com.zklink.lightingsystem.gumei.ui.ListViewCompat;
import com.zklink.lightingsystem.location.model.DargChildInfo;
import com.zklink.lightingsystem.location.model.DragIconInfo;
import com.zklink.lightingsystem.location.view.CustomAboveView;
import com.zklink.lightingsystem.location.view.CustomGroup;
import com.zklink.lightingsystem.login.MainActivity;
import com.zklink.lightingsystem.view.MipcaActivityCapture;
import com.zklink.lightingsystem.wight.PopMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/14.
 */
public class LocationActivity extends Activity {

    private static final String TAG = "LocationActivity";

    private CustomGroup mCustomGroup;
    private DBManager dbManager;
    private String name = "组";
    private String timetable = null;
    private String scene = null;
    private int installnum=0;


    private ArrayAdapter<String> mAdapter;
    private PopMenu popMenu;
    private int mGridCount;
    private Context context = LocationActivity.this;

    @Override
    protected void onResume() {
        super.onResume();
        //setContentView(R.layout.activity_location);
        Button btnExit = (Button) findViewById(R.id.btn_title_back);
        btnExit.setText(R.string.exit);

        btnExit.setOnClickListener(onViewClick);

        TextView tvTitle = (TextView) findViewById(R.id.tv_headerTitle);
        tvTitle.setText(R.string.floorset);

        ImageButton imageButton = (ImageButton) findViewById(R.id.btn_title_popmenu);
        imageButton.setOnClickListener(onViewClick);
        popMenu = new PopMenu(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        // 初始化DBManager
        dbManager = new DBManager(this);

        setContentView(R.layout.activity_location);

        mCustomGroup = (CustomGroup) findViewById(R.id.customgroup_grid_view_pager);
        mCustomGroup.setgroupinfodb(dbManager);

        Button btnExit = (Button) findViewById(R.id.btn_title_back);
        btnExit.setText(R.string.exit);

        btnExit.setOnClickListener(onViewClick);

        TextView tvTitle = (TextView) findViewById(R.id.tv_headerTitle);
        tvTitle.setText(R.string.floorset);

        ImageButton imageButton = (ImageButton) findViewById(R.id.btn_title_popmenu);
        imageButton.setOnClickListener(onViewClick);
        popMenu = new PopMenu(context);

        mCustomGroup.setCustomViewClickListener(new CustomAboveView.CustomAboveViewClickListener() {
            @Override
            public void onSingleClicked(DragIconInfo iconInfo) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();//生成一个Intent对象
                intent.setClass(LocationActivity.this, EquipmentActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("group_id",iconInfo.id );
                intent.putExtras(bundle);
                startActivity(intent);//启动跳转
                //startActivity(new Intent(LocationActivity.this, EquipmentActivity.class));
                finish();
            }
        });

    //    mDraggableGridViewPager.setOnRearrangeListener(new OnRearrangeListener() {
    //        @Override
    //        public void onRearrange(int oldIndex, int newIndex) {
    //            Log.i(TAG, "OnRearrangeListener.onRearrange from=" + oldIndex + ", to=" + newIndex);
    //            String item = mAdapter.getItem(oldIndex);
    //            mAdapter.setNotifyOnChange(false);
    //            mAdapter.remove(item);
    //            mAdapter.insert(item, newIndex);
    //            mAdapter.notifyDataSetChanged();
    //        }
    //    });
    }

    View.OnClickListener onViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_title_back) {
                startActivity(new Intent(LocationActivity.this, MainActivity.class));
                finish();
            } else if (v.getId() == R.id.btn_title_popmenu) {
                Intent intent = new Intent(context, AddLocationActivity.class);
                startActivityForResult(intent, 0);
                //startActivity(new Intent(LocationActivity.this, AddLocationActivity.class));
                //finish();
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1){
            Bundle bundle = data.getExtras();
            //ArrayList<LampInfo> infoList = new ArrayList<LampInfo>();
            //GroupInfo m = new GroupInfo();

            if (Integer.parseInt(bundle.getString("group_id")) >= 0) {
                //mAdapter.add("Grid" + mGridCount);
                mCustomGroup.setgroupinfodb(dbManager);
                mCustomGroup.initData();
            }
        }
    }
}