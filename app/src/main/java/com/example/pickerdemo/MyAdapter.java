package com.example.pickerdemo;

/**
 * Created by Asus on 2017/3/13.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


public class MyAdapter extends BaseAdapter{

    private LayoutInflater layoutInflater;
    private ArrayList<String> images;
    public MyAdapter(Context context,ArrayList<String> images){
        this.images = images;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return images.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return images.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {
        View v = layoutInflater.inflate(R.layout.item,null);
        ImageView iv = (ImageView) v.findViewById(R.id.iv_gridView_item);
        String path=images.get(position);
        File file=new File(path);
        Bitmap bitmap = null;
        if (file.exists()){
            bitmap= BitmapFactory.decodeFile(path);
        }
        iv.setImageBitmap(ThumbnailUtils.extractThumbnail(bitmap, 65, 65));
        return v;
    }

}
