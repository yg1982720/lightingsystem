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
public class FloorIconDialog extends Dialog implements android.view.View.OnClickListener {

    private Context context;
    private WheelView wvflooricon;


    private View vChangeBirth;
    private View vChangeBirthChild;
    private TextView btnOk;
    private TextView btnCancel;

    private ArrayList<String> arry_flooricon = new ArrayList<String>();

    private CalendarTextAdapter mfloorAdapter;


    private int maxTextSize =30;
    private int minTextSize =15;

    private String flooricon;
    private int flooricon_num;
    private String[] flooricon_info = {"大厅", "卧室", "厨房"};

    private OnFlooriconListener onFlooriconListener;

    public FloorIconDialog(Context context) {
        super(context, R.style.ShareDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_flooricon_info);
        wvflooricon = (WheelView) findViewById(R.id.wv_flooricon);


        vChangeBirth = findViewById(R.id.ly_flooricon_changebirth);
        vChangeBirthChild = findViewById(R.id.ly_flooricon_changebirth_child);
        btnOk = (TextView) findViewById(R.id.btn_flooricon_ok);
        btnCancel = (TextView) findViewById(R.id.btn_flooricon_cancel);

        vChangeBirth.setOnClickListener(this);
        vChangeBirthChild.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


        initFloors();
        mfloorAdapter = new CalendarTextAdapter(context, arry_flooricon, getFloor(flooricon), maxTextSize, minTextSize);
        wvflooricon.setVisibleItems(3);
        wvflooricon.setViewAdapter(mfloorAdapter);
        wvflooricon.setCurrentItem(getFloor(flooricon));



        wvflooricon.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mfloorAdapter.getItemText(wheel.getCurrentItem());
                flooricon = currentText;
                setTextviewSize(currentText, mfloorAdapter);
            }
        });

        wvflooricon.addScrollingListener(new OnWheelScrollListener() {

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
    public void setFlooricon(int floor)
    {
        this.flooricon_num = floor;
    }

    /**
     * 初始化楼层
     */
    public void initFloors()
    {
        for(int i = 0; i < 3; i++)
        {
            arry_flooricon.add(flooricon_info[i]);
        }
    }

    /**
     * 获取楼层的索引
     * @param floor
     * @return
     */
    public int getFloor(String floor)
    {
        for(int i = 0; i < 3; i++)
        {
            if(floor==null)
                return 0;
                else {
                if (floor.equals(flooricon_info[i]))
                    return i;
            }
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

    public void setFlooriconListener(OnFlooriconListener onFlooriconListener) {
        this.onFlooriconListener = onFlooriconListener;
    }

    @Override
    public void onClick(View v) {

        if (v == btnOk) {
            if (onFlooriconListener != null) {
                onFlooriconListener.onClick(getFloor(flooricon));
            }
        } else if (v == btnOk) {

        } else if (v == vChangeBirthChild) {
            return;
        } else {
            dismiss();
        }
        dismiss();

    }

    public interface OnFlooriconListener {
        public void onClick(int floor);
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