package com.cc.skillapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cc.skillapp.R;
import com.cc.skillapp.view.SDAvatarListLayout;

import java.util.ArrayList;
import java.util.List;

public class ImageAvatarActivity extends AppCompatActivity {

    private SDAvatarListLayout sdAvatarListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_avatar);

        sdAvatarListLayout = findViewById(R.id.sd_image_avatar);

        final List<Integer> imageDatas = new ArrayList<>();
        imageDatas.add(R.drawable.ic_default_portrait_circle);
        imageDatas.add(R.drawable.home_product_ad);
        imageDatas.add(R.drawable.home_product_hdyxgj);
        //加载本地资源
        sdAvatarListLayout.setAvatarListListener(imageDatas);
//        //顺序加载图片使用任意框架加载
//        avatarLayout2.setAvatarListListener(new SDAvatarListLayout.ShowAvatarListener() {
//            @Override
//            public void showImageView(List<SDCircleImageView> imageViewList) {
//                //创建的ImageView的数量
//                int imageSize = imageViewList.size();
//                //实际需要显示的图片的数量
//                int realDataSize = imageDatas.size();
//                int mul = imageSize - realDataSize;
//                for (int i = 0; i < imageSize; i++) {
//                    if (i >= mul) {//6
//                        //可以替换为网络图片处理
//                        imageViewList.get(i).setImageResource(imageDatas.get(realDataSize - (i - mul) - 1));
//                        imageViewList.get(i).setVisibility(View.VISIBLE);
//                    } else {
//                        imageViewList.get(i).setVisibility(View.GONE);
//                    }
//                }
//            }
//        });
//        //逆序加载图片使用任意框架加载
//        avatarLayout3.setAvatarListListener(new SDAvatarListLayout.ShowAvatarListener() {
//            @Override
//            public void showImageView(List<SDCircleImageView> imageViewList) {
//                //创建的ImageView的数量
//                int imageSize = imageViewList.size();
//                //实际需要显示的图片的数量
//                int realDataSize = imageDatas.size();
//                int mul = imageSize - realDataSize;
//                for (int i = 0; i < imageSize; i++) {
//                    if (i >= mul) {//6
//                        //可以替换为网络图片处理
//                        imageViewList.get(i).setImageResource(imageDatas.get(i - mul));
//                        imageViewList.get(i).setVisibility(View.VISIBLE);
//                    } else {
//                        imageViewList.get(i).setVisibility(View.GONE);
//                    }
//                }
//            }
//        });


    }
}
