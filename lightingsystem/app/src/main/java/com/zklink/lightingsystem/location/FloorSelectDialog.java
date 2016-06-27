package com.zklink.lightingsystem.location;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zklink.lightingsystem.R;
import com.zklink.lightingsystem.timetable.adapters.AbstractWheelTextAdapter;
import com.zklink.lightingsystem.timetable.views.OnWheelChangedListener;
import com.zklink.lightingsystem.timetable.views.OnWheelScrollListener;
import com.zklink.lightingsystem.timetable.views.WheelView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/16.
 */
public class FloorSelectDialog extends Dialog implements android.view.View.OnClickListener {

    private Context context;
    private WheelView wvfloor;


    private View vChangeBirth;
    private View vChangeBirthChild;
    private TextView btnOk;
    private TextView btnCancel;

    private ArrayList<String> arry_floor = new ArrayList<String>();

    private CalendarTextAdapter mfloorAdapter;


    private int maxTextSize =30;
    private int minTextSize =15;

    private String selectfloor = "1";

    private OnFloorListener onFloorListener;

    public FloorSelectDialog(Context context) {
        super(context, R.style.ShareDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_floorselect_info);
        wvfloor = (WheelView) findViewById(R.id.wv_floor);


        vChangeBirth = findViewById(R.id.ly_floorselect_changebirth);
        vChangeBirthChild = findViewById(R.id.ly_floorselect_changebirth_child);
        btnOk = (TextView) findViewById(R.id.btn_floorselect_ok);
        btnCancel = (TextView) findViewById(R.id.btn_floorselect_cancel);

        vChangeBirth.setOnClickListener(this);
        vChangeBirthChild.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


        initFloors();
        mfloorAdapter = new CalendarTextAdapter(context, arry_floor, getFloor(selectfloor), maxTextSize, minTextSize);
        wvfloor.setVisibleItems(3);
        wvfloor.setViewAdapter(mfloorAdapter);
        wvfloor.setCurrentItem(getFloor(selectfloor));



        wvfloor.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mfloorAdapter.getItemText(wheel.getCurrentItem());
                selectfloor = currentText;
                setTextviewSize(currentText, mfloorAdapter);
            }
        });

        wvfloor.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mfloorAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mfloorAdapter);
            }
        });

    }
    /**
     * 设置当前楼层
     * @param floor
     */
    public void setFloor(String floor)
    {
        this.selectfloor = floor;
    }

    /**
     * 初始化楼层
     */
    public void initFloors()
    {
        for(int i = 1; i < 19; i++)
        {
            if(i < 10)
            {
                arry_floor.add("0" + i);
            }
            else
            {
                arry_floor.add(i + "");
            }
        }
    }

    /**
     * 获取楼层的索引
     * @param floor
     * @return
     */
    public int getFloor(String floor)
    {
        int h = Integer.parseInt(floor);
        for(int i = 1; i < 18; i++)
        {
            if(h == i)
                return i;
        }
        return 0;
    }

    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    public void setFloorListener(OnFloorListener onFloorListener) {
        this.onFloorListener = onFloorListener;
    }

    @Override
    public void onClick(View v) {

        if (v == btnOk) {
            if (onFloorListener != null) {
                onFloorListener.onClick(selectfloor);
            }
        } else if (v == btnOk) {

        } else if (v == vChangeBirthChild) {
            return;
        } else {
            dismiss();
        }
        dismiss();

    }

    public interface OnFloorListener {
        public void onClick(String floor);
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
            }
        }
    }

}