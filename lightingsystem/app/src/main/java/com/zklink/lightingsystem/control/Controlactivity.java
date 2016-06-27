package com.zklink.lightingsystem.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.zklink.lightingsystem.control.ui.HorizontalListView;
import com.zklink.lightingsystem.control.ui.HorizontalListViewAdapter;
import com.zklink.lightingsystem.db.DBManager;
import com.zklink.lightingsystem.db.GroupInfo;
import com.zklink.lightingsystem.location.model.DargChildInfo;
import com.zklink.lightingsystem.location.model.DragIconInfo;
import com.zklink.lightingsystem.login.MainActivity;
import com.zklink.lightingsystem.R;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/5/13.
 */
public class Controlactivity extends FragmentActivity {
    HorizontalListView hListView;
    HorizontalListViewAdapter hListViewAdapter;
    private DBManager dbManager;
    int[] imageId = new int[] { R.drawable.floor1, R.drawable.floor2,R.drawable.floor3 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        dbManager = new DBManager(this);
        FragmentManager fm = getSupportFragmentManager();
        ControllistViewFragment fragment = (ControllistViewFragment) fm.findFragmentById(R.id.lampListView);
        if(fragment == null)
        {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.lampListView, new ControllistViewFragment());
            ft.commit();
        }
        //ControlImageViewFragment ifragment =(ControlImageViewFragment) fm.findFragmentById(R.id.floorView);

        initUI();
        //if(ifragment == null)
        //{
        //    FragmentTransaction ft = fm.beginTransaction();
        //    ft.add(R.id.floorView, new ControlImageViewFragment());
        //    ft.commit();
        //}
    }

    public void initUI(){
        hListView = (HorizontalListView)findViewById(R.id.floorView);
       // previewImg = (ImageView)findViewById(R.id.floor);
       // String titles[]=new String[18];
        //int ids[] =new int[18];
        Integer i=0;
        ArrayList<GroupInfo> infoList = new ArrayList<GroupInfo>();
        if(dbManager!=null) {
            infoList = dbManager.searchAllGroupData();
            String titles[]=new String[infoList.size()];
            int ids[] =new int[infoList.size()];
            for (GroupInfo info : infoList) {
                titles[i]=info.group_name;
                ids[i]=imageId[info.group_iconid];
                i++;
            }
            hListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(),titles,ids);
            hListView.setAdapter(hListViewAdapter);
        }

        hListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private FragmentManager manager;
            private FragmentTransaction transaction;
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                hListViewAdapter.setSelectIndex(position);
                hListViewAdapter.notifyDataSetChanged();

                manager = getSupportFragmentManager();
                transaction = manager.beginTransaction();

                ControllistViewFragment fragment1 = new ControllistViewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("group_id", "1");
                fragment1.setArguments(bundle);

                transaction.commit();
            }
        });

    }
}
