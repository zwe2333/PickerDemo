package com.example.pickerdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.ImageConfig;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_CODE =1001 ;
    private static final int REQUEST_PREVIEW_CODE = 1002;
    private ArrayList<String> images=new ArrayList<String>();
    private MyAdapter mMyAdapter;
    private ImageView mImageView;
    private GridView mGridView;
    private ArrayList<String> tmp=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView= (ImageView) findViewById(R.id.btn);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSelectPhotos();
            }
        });
        mGridView= (GridView) findViewById(R.id.gridview);
        mMyAdapter=new MyAdapter(MainActivity.this,images);
        mGridView.setAdapter(mMyAdapter);
    }

    private void goToSelectPhotos() {
        ImageConfig config = new ImageConfig();
        config.minHeight = 400;
        config.minWidth = 400;
        config.mimeType = new String[]{"image/jpeg", "image/png"}; // 图片类型 image/gif ...
        config.minSize = 1 * 1024; // 图片大小
        PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
        intent.setSelectModel(SelectModel.MULTI);
        intent.setShowCarema(true); // 是否显示拍照， 默认false
        intent.setMaxTotal(9); // 最多选择照片数量，默认为9
        intent.setSelectedPaths(tmp); // 已选中的照片地址， 用于回显选中状态
        intent.setImageConfig(config);
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    refreshAdpater(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    break;
                // 拍照
                // 预览
                case REQUEST_PREVIEW_CODE:
                    refreshAdpater(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
                    break;
            }
        }
    }

    private void refreshAdpater(ArrayList<String> paths){
        // 处理返回照片地址
        tmp=paths;
        images.clear();
        images.addAll(paths);
        mMyAdapter.notifyDataSetChanged();
    }
}
