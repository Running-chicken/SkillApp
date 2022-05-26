package com.cc.skillapp.view;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import androidx.annotation.Nullable;

public class SlidingLayout extends LinearLayout implements View.OnTouchListener {

    /**
     * 滚动显示和隐藏左侧布局时，手指滑动需要达到的速度。
     */
    public static final int SNAP_VELOCITY = 200;

    private int screenWidth;

    //左侧布局最多可以滑动到的左边缘
    private int leftEdge;

    private int rightEdge = 0;

    //左侧布局完全显示时，留给右侧布局的宽度值
    private int leftLayoutPadding = 200;

    private float xDown;
    private float xMove;
    private float xUp;

    //左侧布局当前是显示还是隐藏 只有完全显示或隐藏时才会更改此值，滑动过程中此值无效
    private boolean isLeftLayoutVisible;
    private View leftLayout;
    private View rightLayout;

    private View mBindView;
    private MarginLayoutParams leftLayoutParams;
    private MarginLayoutParams rightLayoutParams;
    //手滑速度追踪
    private VelocityTracker mVelocityTracker;



    public SlidingLayout(Context context) {
        super(context);
    }

    public SlidingLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();
    }

    //绑定滑动的控件
    public void setScrollEvent(View bindView){
        mBindView = bindView;
        bindView.setOnTouchListener(this);
    }

    public void scrollToLeftLayout(){
        new ScrollTask().execute(30);
    }

    public void scrollToRightLayout(){
        new ScrollTask().execute(-30);
    }

    public boolean isLeftLayoutVisible(){
        return isLeftLayoutVisible;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            leftLayout = getChildAt(0);
            leftLayoutParams = (MarginLayoutParams) leftLayout.getLayoutParams();
            leftLayoutParams.width = screenWidth-leftLayoutPadding;
            leftEdge = -leftLayoutParams.width;
            leftLayoutParams.leftMargin = leftEdge;
            leftLayout.setLayoutParams(leftLayoutParams);


            rightLayout = getChildAt(1);
            rightLayoutParams = (MarginLayoutParams) rightLayout.getLayoutParams();
            rightLayoutParams.width = screenWidth;
            rightLayout.setLayoutParams(rightLayoutParams);
        }
    }




    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        createVelocityTracker(motionEvent);
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown = motionEvent.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = motionEvent.getRawX();
                int distanceX = (int) (xMove-xDown);
                if(isLeftLayoutVisible){
                    leftLayoutParams.leftMargin = distanceX;
                }else{
                    leftLayoutParams.leftMargin = leftEdge+distanceX;
                }

                if(leftLayoutParams.leftMargin<leftEdge){
                    leftLayoutParams.leftMargin = leftEdge;
                }else if(leftLayoutParams.leftMargin>rightEdge){
                    leftLayoutParams.leftMargin = rightEdge;
                }
                leftLayout.setLayoutParams(leftLayoutParams);
                break;
            case MotionEvent.ACTION_UP:
                xUp = motionEvent.getRawX();
                if (wantToShowLeftLayout()) {
                    if (shouldScrollToLeftLayout()) {
                        scrollToLeftLayout();
                    } else {
                        scrollToRightLayout();
                    }
                } else if (wantToShowRightLayout()) {
                    if (shouldScrollToContent()) {
                        scrollToRightLayout();
                    } else {
                        scrollToLeftLayout();
                    }
                }
                recycleVelocityTracker();

        }

        return isBindBasicLayout();
    }

    public boolean wantToShowRightLayout(){
        return xUp-xDown<0 && isLeftLayoutVisible;
    }

    public boolean wantToShowLeftLayout(){
        return xUp-xDown>0 && !isLeftLayoutVisible;
    }

    private boolean shouldScrollToLeftLayout() {
        return xUp - xDown > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    private boolean shouldScrollToContent() {
        return xDown - xUp + leftLayoutPadding > screenWidth / 2
                || getScrollVelocity() > SNAP_VELOCITY;
    }

    private boolean isBindBasicLayout() {
        if (mBindView == null) {
            return false;
        }
        String viewName = mBindView.getClass().getName();
        return viewName.equals(LinearLayout.class.getName())
                || viewName.equals(RelativeLayout.class.getName())
                || viewName.equals(FrameLayout.class.getName())
                || viewName.equals(TableLayout.class.getName());
    }

    private void createVelocityTracker(MotionEvent motionEvent) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(motionEvent);
    }

    //获取每秒速度
    public int getScrollVelocity(){
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    private void recycleVelocityTracker(){
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }




    class ScrollTask extends AsyncTask<Integer,Integer,Integer>{

        @Override
        protected Integer doInBackground(Integer... integers) {
            int leftMargin = leftLayoutParams.leftMargin;
            // 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
            while (true) {
                leftMargin = leftMargin + integers[0];
                if (leftMargin > rightEdge) {
                    leftMargin = rightEdge;
                    break;
                }
                if (leftMargin < leftEdge) {
                    leftMargin = leftEdge;
                    break;
                }
                publishProgress(leftMargin);
                // 为了要有滚动效果产生，每次循环使线程睡眠20毫秒，这样肉眼才能够看到滚动动画。
                sleep(20);
            }
            if (integers[0] > 0) {
                isLeftLayoutVisible = true;
            } else {
                isLeftLayoutVisible = false;
            }
            return leftMargin;

        }

        @Override
        protected void onProgressUpdate(Integer... leftMargin) {
            leftLayoutParams.leftMargin = leftMargin[0];
            leftLayout.setLayoutParams(leftLayoutParams);
        }

        @Override
        protected void onPostExecute(Integer leftMargin) {
            leftLayoutParams.leftMargin = leftMargin;
            leftLayout.setLayoutParams(leftLayoutParams);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
