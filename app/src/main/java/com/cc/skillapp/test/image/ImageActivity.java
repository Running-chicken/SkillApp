package com.cc.skillapp.test.image;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;

import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityImageBinding;
import com.example.library_base.util.Utils;

public class ImageActivity extends AppCompatActivity {

    private LruCache<String, Bitmap> mMemoryCache;
    ActivityImageBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_image);


        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1000);
        Utils.log(getClass(),"最大内存:"+maxMemory);
        int cacheSize = maxMemory/8;

        mMemoryCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量
                return value.getByteCount()/1024;
            }
        };

        loadImage();

        mBinding.tvShowLru.setOnClickListener(view -> {
            leastRecentlyUsedImage();
        });
    }

    private void leastRecentlyUsedImage() {


        if(mMemoryCache.get("imageShoes")==null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap  = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_nike_shoes_two);
                    mMemoryCache.put("imageShoes",bitmap);
                    Message message = new Message();
                    message.obj = bitmap;
                    message.what=2;
                    mHandler.sendMessage(message);
                }
            }).start();
        }else{
            mBinding.ivTestTwo.setImageBitmap(mMemoryCache.get("imageShoes"));
        }


    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.what==1){
                mBinding.ivTest.setImageBitmap((Bitmap) message.obj);
            }else{
                mBinding.ivTestTwo.setImageBitmap((Bitmap) message.obj);
            }
            return false;
        }
    });

    //压缩图片
    private void loadImage() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = decodeSampleSize(getResources(),R.mipmap.icon_nike_shoes,300,500);
                Message message = new Message();
                message.obj = bitmap;
                message.what=1;
                mHandler.sendMessage(message);
            }
        }).start();

    }

    public int calculateInSampleSize(BitmapFactory.Options options,int requestWidth,int requestHeight){
        int h = options.outHeight;
        int w = options.outWidth;
        int inSampleSize = 1;
        if(h>requestHeight || w>requestWidth){
            int hSize = h/requestHeight;
            int wSize = w/requestWidth;
            inSampleSize = hSize<wSize ? hSize : wSize;
        }
        return inSampleSize;
    }

    public Bitmap decodeSampleSize(Resources resources,int resId,
                                   int reqWidth,int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources,resId,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources,resId,options);

    }

    //都存入Lru
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }
    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }


}