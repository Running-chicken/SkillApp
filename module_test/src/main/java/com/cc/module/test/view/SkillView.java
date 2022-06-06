package com.cc.module.test.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ComposeShader;
import android.graphics.DashPathEffect;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.cc.module.test.R;

public class SkillView extends View {


    public SkillView(Context context) {
        this(context,null);
    }

    public SkillView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();

    }

    Paint mPaint;
    Path path = new Path();
    Canvas mCanvas;

    float[] points = {0,0,50,50,100,100,200,200};
    float[] lines = {0,0,100,0,100,0,200,200,200,200,300,300};

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;

//        canvas.drawColor(Color.WHITE);
//        testView(canvas);
//        testPath(canvas);
//        testText(canvas);
//        drawCircle(canvas);
//        testShader(canvas);
//        testColorFilter(canvas);

//        testXfermode(canvas);

//        testLine(canvas);
//        testDither(canvas);
//        testPathEffect();
//        setShadowLayer();
        setMaskFilter();
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

//        mPaint.setAntiAlias(true); 同上句
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    //测试线条
    private void testLine(Canvas canvas){
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(50);
        mPaint.setStrokeCap(Paint.Cap.BUTT); //线头
        mPaint.setStrokeJoin(Paint.Join.MITER); //拐角形状
//        canvas.drawLine(0,0,200,200,mPaint);
        path = new Path();
        path.moveTo(100,100);
        path.rLineTo(200,0);
        path.rLineTo(-100,200);
        canvas.drawPath(path,mPaint);

    }

    //抖动
    private void testDither(Canvas canvas){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_nike_shoes);
        mPaint.setDither(true); //抖动 设置抖动来优化色彩深度降低时的绘制效果
        mPaint.setFilterBitmap(true); //双线性过滤
        canvas.drawBitmap(bitmap,0,0,mPaint);  //设置双线性过滤来优化 Bitmap 放大绘制的效果。
    }


    //设置附加效果
    private void testPathEffect( ){

        //虚线效果  数组 实线-空白    phase:偏移量

        PathEffect effect = new DashPathEffect(new float[]{10,5},100);
        mPaint.setPathEffect(effect);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mCanvas.drawCircle(300,300,200,mPaint);
    }


    //加阴影
    private void setShadowLayer(){
        mPaint.setShadowLayer(20,20,20,Color.RED);
        mPaint.setTextSize(100);
        mCanvas.drawText("我是崔粲",100,100,mPaint);
    }

    //加模糊
    private void setMaskFilter(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_nike_shoes);
//        NORMAL: 内外都模糊绘制
//        SOLID: 内部正常绘制，外部模糊
//        INNER: 内部模糊，外部不绘制
//        OUTER: 内部不绘制，外部模糊
        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL));
        mCanvas.drawBitmap(bitmap,0,0,mPaint);


    }

    private void testView(Canvas canvas){
        //画圆
//        canvas.drawCircle(100,100,100,mPaint);
        //画矩形
//        canvas.drawRect(0,200,200,400,mPaint);
        //画点
//        canvas.drawPoint(10,410,mPaint);
        //画 点集合
//        canvas.drawPoints(points,mPaint);
        //画椭圆
//        canvas.drawOval(50,50,200,100,mPaint);
        //画直线
//        canvas.drawLine(0,0,100,300,mPaint);
        //画 直线集合
//        canvas.drawLines(lines,mPaint);
        //画圆角矩形
//        canvas.drawRoundRect(0,0,200,300,50,60,mPaint);
        //画扇形
//        canvas.drawArc(0,100,200,200,45,45,false,mPaint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_nike_shoes);
        canvas.drawBitmap(bitmap,0,0,mPaint);

    }

    private void testPath(Canvas canvas){
        mPaint.setStyle(Paint.Style.STROKE);
        path.lineTo(100,100);
        path.rLineTo(200,0);
        path.rLineTo(100,100);
        path.close();
        canvas.drawPath(path,mPaint);
    }

    private void testText(Canvas canvas){
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(50);
        canvas.drawText("我是崔粲",100,100,mPaint);
    }


    //作业 画圆
    private void drawCircle(Canvas canvas){
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(100,100,100,mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(320,100,100,mPaint);


        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(100,320,100,mPaint);



        path.setFillType(Path.FillType.EVEN_ODD); //设置交叉属性
        mPaint.setColor(Color.WHITE);
        path.addCircle(320,320,50, Path.Direction.CCW);
        canvas.drawPath(path,mPaint);
        path.addCircle(320,320,100, Path.Direction.CCW);
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(path,mPaint);


    }


    //技能 渐变
    private void testShader(Canvas canvas){
        //线性，两端点坐标及颜色
//        Shader shader = new LinearGradient(100,100,500,500,Color.parseColor("#E91163")
//                ,Color.parseColor("#2196f3"),Shader.TileMode.CLAMP);


        //辐射渐变 设置中心颜色和边缘颜色
//        Shader shader = new RadialGradient(300,300,200,Color.RED,Color.BLACK,Shader.TileMode.CLAMP);


        //扫描渐变
//        Shader shader = new SweepGradient(300,300,Color.RED,Color.BLUE);


        //组合
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.home_product_ad);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_test_ii);
        Shader shader1 = new BitmapShader(bitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        Shader shader2 = new BitmapShader(bitmap1,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        Shader shader = new ComposeShader(shader1,shader2, PorterDuff.Mode.DST_OUT);
        mPaint.setShader(shader);
        canvas.drawRect(0,0,300,300,mPaint);
    }


    private void testColorFilter(Canvas canvas){
        ColorFilter colorFilter = new LightingColorFilter(0x00ffff, 0x000000);
        mPaint.setColorFilter(colorFilter);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_nike_shoes);
        canvas.drawBitmap(bitmap,0,0,mPaint);


    }

    private void testXfermode(Canvas canvas){

//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.icon_nike_shoes),0,0,mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawRect(0,0,300,300,mPaint);

        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mPaint.setXfermode(xfermode);
        mPaint.setColor(Color.BLUE);
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.home_product_ad),0,0,mPaint);

        canvas.drawCircle(300,300,200,mPaint);
        mPaint.setXfermode(null);
    }
}
