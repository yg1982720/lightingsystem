package com.zklink.lightingsystem.control;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.zklink.lightingsystem.R;
import com.zklink.lightingsystem.db.DBManager;
import com.zklink.lightingsystem.db.GroupInfo;
import com.zklink.lightingsystem.db.LampInfo;
import com.zklink.lightingsystem.location.model.DargChildInfo;
import com.zklink.lightingsystem.location.model.DragIconInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class ControllistViewFragment extends Fragment{
    private ListView mListView;
    private List<Bean> mDatas;
    private MyAdapter adapter;
    private DBManager dbManager;
    private int group=0;
    //public Context context = this.getActivity().getApplicationContext();
    public static ControllistViewFragment newInstance(int index) {
        ControllistViewFragment f = new ControllistViewFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.activity_control_listviewfragment, container, false);
        dbManager = new DBManager(this.getActivity().getApplicationContext());
        Bundle bundle = getArguments();
        int i=0;

        if(bundle!=null)
        {
            i=bundle.getInt("index");

            if(dbManager!=null) {
                initDatas(i);
                group=i;
                mListView=(ListView) view.findViewById(R.id.id_listview);
                mListView.setOnItemClickListener(new Click()) ;

                mListView.setAdapter(adapter);
            }
        }



        return view;

    }

    private class Click implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            Bean bean =mDatas.get(position);
            String title = bean.getName();
            String miaoShu = bean.getJieShao();
            Intent i = new Intent(getActivity(),Control_lampactivity.class);
            i.putExtra("id",title);
            i.putExtra("group",miaoShu);
            startActivity(i);
        }

    }

    private void initDatas(int group){
        mDatas = new ArrayList<Bean>();
        ArrayList<LampInfo> infoList = new ArrayList<LampInfo>();
        infoList = dbManager.searchLampData(group);
        for (LampInfo info : infoList) {
            mDatas.add(new Bean("ID："+info.lamp_id,info.lamp_name,R.drawable.lampon,R.drawable.activebtn));
        }
        adapter = new MyAdapter(getActivity(), mDatas);
    }

    public class MyAdapter extends BaseAdapter {
        // contextinflater
        private LayoutInflater mInflater;
        private List<Bean> mDatas;
        private ImageView switchimage;
        private boolean[] switchcontrol_flag=new boolean[400];
        //for(int num = 0; num< 400; num++)
        //{
        //    switchcontrol_flag[num] = false;
        //}

        public MyAdapter(Context context, List<Bean> datas) {
            mInflater = LayoutInflater.from(context);
            this.mDatas = datas;
        }


        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.listview_adapter, parent, false);
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.miaoshu = (TextView) convertView.findViewById(R.id.mytext);
                holder.image = (ImageView) convertView.findViewById(R.id.drawable);
                holder.lampbtn = (ImageView)convertView.findViewById(R.id.lamponoffbtn);
                convertView.setTag(holder);
            }else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            Bean bean = mDatas.get(position);
            holder.title.setText(bean.getName());
            holder.miaoshu.setText(bean.getJieShao());
            holder.image.setBackgroundResource(bean.getPicture1());
            holder.lampbtn.setBackgroundResource(bean.getPicture2());

            holder.lampbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (switchcontrol_flag[position]) {
                        switchcontrol_flag[position] = false;
                        ArrayList<LampInfo> infoList = new ArrayList<LampInfo>();
                        infoList = dbManager.searchLampData(group);
                        int i=0;
                        String Lamp_id[]=new String[infoList.size()];
                        String Lamp_name[]=new String[infoList.size()];
                        for (LampInfo info : infoList) {
                            Lamp_id[i]= info.lamp_id;
                            Lamp_name[i]=info.lamp_name;
                            i++;
                        }
                        Bean lamp1 = new Bean("ID："+Lamp_id[position],Lamp_name[position],R.drawable.lampon,R.drawable.activebtn);
                        SetData(position, lamp1);

                        //adapter = new MyAdapter(ControllistViewFragment.this, mDatas);
                        //notifyDataSetChanged();

                    } else {
                        switchcontrol_flag[position] = true;
                        ArrayList<LampInfo> infoList = new ArrayList<LampInfo>();
                        infoList = dbManager.searchLampData(group);
                        int i=0;
                        String Lamp_id[]=new String[infoList.size()];
                        String Lamp_name[]=new String[infoList.size()];
                        for (LampInfo info : infoList) {
                            Lamp_id[i]= info.lamp_id;
                            Lamp_name[i]=info.lamp_name;
                            i++;
                        }
                        Bean lamp1 = new Bean("ID："+Lamp_id[position],Lamp_name[position],R.drawable.lampon,R.drawable.hoverbtn);
                        SetData(position, lamp1);
                        //adapter = new MyAdapter(getActivity(), mDatas);
                        //notifyDataSetChanged();
                    }
                }
            });
            return convertView;
        }

        public final class ViewHolder {
            TextView title;
            TextView miaoshu;
            ImageView image;
            ImageView lampbtn;
        }

        public void AddData(Bean s){
            mDatas.add(s);
            adapter.notifyDataSetChanged();
        }

        public void SetData(final int position,Bean s){
            mDatas.set(position, s);
            adapter.notifyDataSetChanged();
        }
    }
}