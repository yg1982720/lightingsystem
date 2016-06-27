package com.zklink.lightingsystem.timetable;

import java.util.ArrayList;
import java.util.Calendar;

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

/**
 * 日期选择对话框
 * 
 * @author ywl
 *
 */
public class ChangeTimeDialog extends Dialog implements android.view.View.OnClickListener {

	private Context context;
	private WheelView wvHour;
	private WheelView wvMine;
	private WheelView wvDimmer;

	private View vChangeBirth;
	private View vChangeBirthChild;
	private TextView btnSure;
	private TextView btnCancel;

	private ArrayList<String> arry_hours = new ArrayList<String>();
	private ArrayList<String> arry_mines = new ArrayList<String>();
	private ArrayList<String> arry_dimmers = new ArrayList<String>();
	private CalendarTextAdapter mHourAdapter;
	private CalendarTextAdapter mMineAdapter;
	private CalendarTextAdapter mDimmerAdapter;

	private int maxTextSize = 24;
	private int minTextSize = 14;

	private String selectHour = "0";
	private String selectMinu = "0";
	private String selectDimmer = "0";

	private OnTimeListener onTimeListener;

	public ChangeTimeDialog(Context context) {
		super(context, R.style.ShareDialog);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_timetable_info);
		wvHour = (WheelView) findViewById(R.id.wv_hour);
		wvMine = (WheelView) findViewById(R.id.wv_mine);
		wvDimmer = (WheelView) findViewById(R.id.wv_dimmer);

		vChangeBirth = findViewById(R.id.ly_myinfo_changebirth);
		vChangeBirthChild = findViewById(R.id.ly_myinfo_changebirth_child);
		btnSure = (TextView) findViewById(R.id.btn_myinfo_sure);
		btnCancel = (TextView) findViewById(R.id.btn_myinfo_cancel);

		vChangeBirth.setOnClickListener(this);
		vChangeBirthChild.setOnClickListener(this);
		btnSure.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		
		
		initHours();
		mHourAdapter = new CalendarTextAdapter(context, arry_hours, getHour(selectHour), maxTextSize, minTextSize);
		wvHour.setVisibleItems(5);
		wvHour.setViewAdapter(mHourAdapter);
		wvHour.setCurrentItem(getHour(selectHour));

		initMinus();
		mMineAdapter = new CalendarTextAdapter(context, arry_mines,getMinu(selectMinu), maxTextSize, minTextSize);
		wvMine.setVisibleItems(5);
		wvMine.setViewAdapter(mMineAdapter);
		wvMine.setCurrentItem(getMinu(selectMinu));


		initDimmers();
		mDimmerAdapter = new CalendarTextAdapter(context, arry_dimmers,getDimmer(selectDimmer), maxTextSize, minTextSize);
		wvDimmer.setVisibleItems(5);
		wvDimmer.setViewAdapter(mDimmerAdapter);
		wvDimmer.setCurrentItem(getDimmer(selectMinu));

		wvHour.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				String currentText = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
				selectHour = currentText;
				setTextviewSize(currentText, mHourAdapter);
			}
		});

		wvHour.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				String currentText = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, mHourAdapter);
			}
		});

		wvMine.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				String currentText = (String) mMineAdapter.getItemText(wheel.getCurrentItem());
				selectMinu = currentText;
				setTextviewSize(currentText, mMineAdapter);
			}
		});

		wvMine.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				String currentText = (String) mMineAdapter.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, mMineAdapter);
			}
		});

		wvDimmer.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				String currentText = (String) mDimmerAdapter.getItemText(wheel.getCurrentItem());
				selectDimmer = currentText;
				setTextviewSize(currentText, mDimmerAdapter);
			}
		});

		wvDimmer.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				String currentText = (String) mDimmerAdapter.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, mDimmerAdapter);
			}
		});

	}
	/**
	 * 设置当前时间
	 * @param hour
	 * @param minu
	 */
	public void setTimes(String hour, String minu,String dimmer)
	{
		this.selectHour = hour;
		this.selectMinu = minu;
		this.selectDimmer = dimmer;
	}
	
	/**
	 * 初始化小时
	 */
	public void initHours()
	{
		for(int i = 0; i < 24; i++)
		{
			if(i < 10)
			{
				arry_hours.add("0" + i);
			}
			else
			{
				arry_hours.add(i + "");
			}
		}
	}
	
	/**
	 * 获取小时的索引
	 * @param hour
	 * @return
	 */
	public int getHour(String hour)
	{
		int h = Integer.parseInt(hour);
		for(int i = 0; i < 24; i++)
		{
			if(h == i)
				return i;
		}
		return 0;
	}
	
	/**
	 * 初始和分钟
	 */
	public void initMinus()
	{
		for(int i = 0; i < 60; i++)
		{
			if(i < 10)
			{
				arry_mines.add("0" + i);
			}
			else
			{
				arry_mines.add(i + "");
			}
		}
	}
	
	/**
	 * 获取分钟索引
	 * @param minu
	 * @return
	 */
	public int getMinu(String minu)
	{
		int m = Integer.parseInt(minu);
		for(int i = 0; i < 60; i ++)
		{
			if(i == m)
				return m;
		}
		return 0;
	}

	/**
	 * 初始化小时
	 */
	public void initDimmers()
	{
		for(int i = 0; i < 101; i++)
		{
			arry_dimmers.add(i + "");
		}
	}

	/**
	 * 获取小时的索引
	 * @param dimmer
	 * @return
	 */
	public int getDimmer(String dimmer)
	{
		int h = Integer.parseInt(dimmer);
		for(int i = 0; i < 101; i++)
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

	public void setTimeListener(OnTimeListener onTimeListener) {
		this.onTimeListener = onTimeListener;
	}

	@Override
	public void onClick(View v) {

		if (v == btnSure) {
			if (onTimeListener != null) {
				onTimeListener.onClick(selectHour, selectMinu,selectDimmer);
			}
		} else if (v == btnSure) {

		} else if (v == vChangeBirthChild) {
			return;
		} else {
			dismiss();
		}
		dismiss();

	}

	public interface OnTimeListener {
		public void onClick(String hour, String minu,String dimmer);
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