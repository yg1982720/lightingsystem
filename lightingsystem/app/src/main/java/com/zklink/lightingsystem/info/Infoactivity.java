package com.zklink.lightingsystem.info;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zklink.lightingsystem.R;
import com.zklink.lightingsystem.db.DBManager;
import com.zklink.lightingsystem.db.LampInfo;
import com.zklink.lightingsystem.gumei.ui.ListViewCompat;
import com.zklink.lightingsystem.gumei.ui.ListViewCompat.OnLoadListener;
import com.zklink.lightingsystem.gumei.ui.ListViewCompat.OnRefreshListener;
import com.zklink.lightingsystem.gumei.ui.SlideView;
import com.zklink.lightingsystem.gumei.ui.SlideView.OnSlideListener;
import com.zklink.lightingsystem.gumei.ui.MessageItem;

@SuppressWarnings("rawtypes")
public class Infoactivity extends Activity implements OnItemClickListener, OnClickListener,
        OnSlideListener, OnRefreshListener,OnLoadListener {

    private ListViewCompat mListView;
    private List<MessageItem> mMessageItems = new ArrayList<MessageItem>();
    private SlideView mLastSlideViewWithStatusOn;
    private SlideAdapter adapter;
    private int allCount = 400;
    private DBManager dbManager;
    private int lamp_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        dbManager = new DBManager(this);
        setContentView(R.layout.activity_info);
        initView();
        loadData(ListViewCompat.REFRESH);
    }


    public void initView() {
        mListView = (ListViewCompat) findViewById(R.id.classeslist);
        adapter = new SlideAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnRefreshListener(this);
        mListView.setOnLoadListener(this);

    }

    private void loadData(final int what) {
        // 这里模拟从服务器获取数据
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = handler.obtainMessage();
                msg.what = what;
                msg.obj = getData();
                handler.sendMessage(msg);
            }
        }).start();
    }

    // 测试数据
    public List<MessageItem> getData() {
        List<MessageItem> result = new ArrayList<MessageItem>();
        for (int i = 0; i < 3; i++) {
            MessageItem item = new MessageItem();
            if (i == 0)
            {
                ArrayList<LampInfo> infoList = new ArrayList<LampInfo>();
                infoList = dbManager.searchAllLampData();
                lamp_num=0;
                for (LampInfo info : infoList)
                {
                    lamp_num++;
                }
                item.iconRes = R.drawable.lampon;
                item.title = "灯控器";
                item.msg = "控制灯具亮度";
                item.time = lamp_num+"/"+lamp_num+" >";
            } else if(i==1){
                item.iconRes = R.drawable.line_switch;
                item.title = "线路开关";
                item.msg = "控制线路断合";
                item.time = "0/0 >";
            }
            else
            {
                item.iconRes = R.drawable.control;
                item.title = "总控器";
                item.msg = "控制线路断合";
                item.time = "0/0 >";
            }
            result.add(item);
        }
        return result;
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            List<MessageItem> result = (List<MessageItem>) msg.obj;
            switch (msg.what) {
                case ListViewCompat.REFRESH:
                    mListView.onRefreshComplete();
                    mMessageItems.clear();
                    mMessageItems.addAll(result);
                    break;
                case ListViewCompat.LOAD:
                    mListView.onLoadComplete();
                    mMessageItems.addAll(result);
                    break;
                case 2:
                    mListView.onLoadComplete();
                    //Toast.makeText(Infoactivity.this, "已加载全部！", Toast.LENGTH_SHORT).show();
                    break;
            }
            mListView.setResultSize(result.size());
            adapter.notifyDataSetChanged();
        };
    };
    private class SlideAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.item_listview_delete, null);

                slideView = new SlideView(Infoactivity.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(Infoactivity.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            MessageItem item = mMessageItems.get(position);
            item.slideView = slideView;
            item.slideView.shrink();

            holder.icon.setImageResource(item.iconRes);
            holder.title.setText(item.title);
            holder.msg.setText(item.msg);
            holder.time.setText(item.time);
            holder.deleteHolder.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mMessageItems.remove(position);
                    adapter.notifyDataSetChanged();
                    //Toast.makeText(Infoactivity.this, "删除" + position+"个条", 0).show();
                }
            });
            holder.edit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    adapter.notifyDataSetChanged();
                   // Toast.makeText(Infoactivity.this, "编辑" + position+"个条", 0).show();
                }
            });

            return slideView;
        }

    }

    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView msg;
        public TextView time;
        public TextView deleteHolder;
        public TextView edit;

        ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);
            msg = (TextView) view.findViewById(R.id.msg);
            time = (TextView) view.findViewById(R.id.time);
            deleteHolder = (TextView) view.findViewById(R.id.delete);
            edit = (TextView) view.findViewById(R.id.edit);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast.makeText(this, "onItemClick position=" + position, 0).show();

    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.holder:

                break;

            default:
                break;
        }
    }
    @Override
    public void onLoad() {
        // TODO Auto-generated method stub
        if(adapter.getCount()<allCount){
            loadData(ListViewCompat.LOAD);
        }else{
            Message msg = handler.obtainMessage();
            msg.what = 2;
            msg.obj = mMessageItems;
            handler.sendMessage(msg);

        }
    }
    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        loadData(ListViewCompat.REFRESH);
    }


}
