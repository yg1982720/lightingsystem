package com.zklink.lightingsystem.wight;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.zklink.lightingsystem.R;
import com.zklink.lightingsystem.control.Bean;
import com.zklink.lightingsystem.control.Control_lampactivity;
import com.zklink.lightingsystem.view.MipcaActivityCapture;

public class PopMenu {
	private ArrayList<String> itemList;
	private Context context;
	private PopupWindow popupWindow ;
	private ListView listView;
	//private OnItemClickListener listener;
	

	public PopMenu(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;

		//itemList = new ArrayList<String>(2);

		View view = LayoutInflater.from(context).inflate(R.layout.popmenu1, null);

		//listView = (ListView)view.findViewById(R.id.listView);
		//listView.setAdapter(new PopAdapter());
		//listView.setFocusableInTouchMode(true);
		//listView.setFocusable(true);

        popupWindow = new PopupWindow(view, 120, LayoutParams.WRAP_CONTENT);
        popupWindow = new PopupWindow(view,
        		context.getResources().getDimensionPixelSize(R.dimen.popmenu_width),
        		LayoutParams.WRAP_CONTENT);


		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	//public void setOnItemClickListener(OnItemClickListener listener) {
	//	listView.setOnItemClickListener(listener);
	//}


	public void addItems(String[] items) {
		for (String s : items)
			itemList.add(s);
	}
}
