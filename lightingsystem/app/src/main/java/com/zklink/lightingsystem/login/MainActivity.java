package com.zklink.lightingsystem.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zklink.lightingsystem.R;
import com.zklink.lightingsystem.control.Controlactivity;
import com.zklink.lightingsystem.parameter.ParameterActivity;
import com.zklink.lightingsystem.scene.SceneActivity;
import com.zklink.lightingsystem.timetable.TimetableActivity;
import com.zklink.lightingsystem.location.LocationActivity;
import com.zklink.lightingsystem.info.Infoactivity;
import com.zklink.lightingsystem.equipment.EquipmentActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private Context mContext;

    private HorizontalScrollView mImageContainer;
    private GridViewAdapter mGridViewAdapter;
    private GridView mGridView;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = (GridView) findViewById(R.id.image_grid_view);
        mImageContainer = (HorizontalScrollView) findViewById(R.id.image_grid_view_container);

        mContext = getApplicationContext();
        mGridViewAdapter = new GridViewAdapter();
        mGridView.setAdapter(mGridViewAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

               switch (position) {
                    case 0://进入监控界面
                        startActivity(new Intent(MainActivity.this, Controlactivity.class));
                        //finish();
                        break;

                    case 1://进入位置设置
                        startActivity(new Intent(MainActivity.this, LocationActivity.class));
                        //finish();
                        break;

                    case 2://进入时间表设置界面
                        startActivity(new Intent(MainActivity.this, TimetableActivity.class));
                        //finish();
                        break;

                   case 3://进入场景界面
                       startActivity(new Intent(MainActivity.this, SceneActivity.class));
                       //finish();
                       break;

                   case 4://总控器设备统计页面
                       startActivity(new Intent(MainActivity.this, Infoactivity.class));
                       //finish();
                       break;

                   case 5://进入总控器设置界面
                       startActivity(new Intent(MainActivity.this,ParameterActivity.class));
                       //finish();
                       break;

                   case 6://产品介绍
                       startActivity(new Intent(MainActivity.this, Controlactivity.class));
                       //finish();
                       break;

                   case 7://进入帮组界面
                       startActivity(new Intent(MainActivity.this, Controlactivity.class));
                       //finish();
                       break;
                }
            }
        });

        refreshImageGridview();
    }

    private List<Bitmap> getEditingBitmaps() {
        List<Bitmap> images = new ArrayList<Bitmap>();
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.m1));//监控
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.m2));//区域
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.m3));//时间
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.m4));//场景
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.m5));//设备统计
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.m6));//总控器设置
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.m7));//产品介绍
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.m8));//帮助
        return images;
    }

    private void refreshImageGridview() {
        mImageContainer.setVisibility(View.VISIBLE);
        List<Bitmap> images = getEditingBitmaps();
        int imageSize =  images.size();
        mGridViewAdapter.setBitmaps(images);

        int rowNum = 4; // set how many row by yourself
        int gridItemWidth = getResources().getDimensionPixelSize(R.dimen.image_gridview_width);
        int gridItemHeight = getResources().getDimensionPixelSize(R.dimen.image_gridview_width);
        int gap = getResources().getDimensionPixelSize(R.dimen.image_gridview_gap);

        // get how many columns
        int columnNum = imageSize / rowNum;
        //if (columnNum % rowNum != 0) {
        //    columnNum++;
        //}

        // get GridView's height and width
        int gridViewWidth = (gridItemWidth + gap) * columnNum;
        int gridViewHeight = (gridItemHeight + gap) * rowNum;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridViewWidth, gridViewHeight);
        mGridView.setLayoutParams(params);
        mGridView.setColumnWidth(gridItemWidth);
        mGridView.setHorizontalSpacing(gap);
        mGridView.setVerticalSpacing(gap);
        mGridView.setStretchMode(GridView.NO_STRETCH);
        mGridView.setNumColumns(columnNum);
    }

    private class GridViewAdapter extends BaseAdapter {
        private List<Bitmap> mBitmaps = new ArrayList<Bitmap>();
        private ViewHolder mViewHolder;

        public void setBitmaps(List<Bitmap> images){
            mBitmaps = images;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mBitmaps != null ? mBitmaps.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return mBitmaps != null ? mBitmaps.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, parent, false);
                mViewHolder = new ViewHolder();
                mViewHolder.mImageView = (ImageView) convertView.findViewById(R.id.image_view);
                convertView.setTag(mViewHolder);
            }else{
                mViewHolder = (ViewHolder) convertView.getTag();
            }

            mViewHolder.mImageView.setImageBitmap(mBitmaps.get(position));
            return convertView;
        }



        public class ViewHolder{
            public ImageView mImageView;
        }
    }
}
