package com.cc.skillapp.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.cc.skillapp.adapter.AllApplicationAdapter;
import com.cc.skillapp.adapter.AllTitleAdapter;
import com.cc.skillapp.adapter.ToolAddedAdapter;
import com.cc.skillapp.entity.AllApplicationEntity;
import com.cc.skillapp.entity.BaseRootEntity;
import com.cc.skillapp.entity.MenuListEntity;
import com.cc.skillapp.manager.AllTitleLayoutManager;
import com.cc.skillapp.utils.MenuIntentHandleUtil;
import com.cc.skillapp.utils.StringUtils;
import com.cc.skillapp.utils.Utils;
import com.cc.skillapp.view.DragedRecycleView.HomeToolTouchHelperCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * AllApplicationActivity class
 *
 * @author
 * @date
 */
public class AllApplicationActivity extends BaseActivity implements ToolAddedAdapter.OnAddedItemListener, AllTitleAdapter.OnTitleItemListener, AllApplicationAdapter.OnAllApplicationItemClickListener, ToolAddedAdapter.OnAddedItemLongListener {

    //<editor-fold desc="变量">
    private LinearLayout mLlAdded;
    private RecyclerView mRvAdded;
    private RecyclerView mRvAllTitle;
    private RecyclerView mRvAllApplication;


    /**
     * 我的应用数据
     */
    private List<AllApplicationEntity.Submenu> mAddedList = new ArrayList<>();
    private ToolAddedAdapter mToolAddedAdapter;
    /**
     * 全部应用的标签数据
     */
    private List<AllApplicationEntity.AllAppMenu> mAllList = new ArrayList<>();
    private AllTitleAdapter mAllTitleAdapter;
    private AllApplicationAdapter mAllApplicationAdapter;

    private Dialog mProcessDialog;
    private Dialog mGetDialog;

    /**
     * 目标项是否在最后一个可见项之后
     */
    private boolean mShouldScroll;
    /**
     * 记录目标项位置
     */
    private int mToPosition;

    /**
     * 是否触发了横向item的点击事件
     */
    private boolean mIsHCheck;
    private int mLastPosition;

    /**
     * 是否是编辑状态
     */
    private boolean mIsEditing;

    private ItemTouchHelper mItemTouchHelper;

    /**
     * 可添加最大数
     */
    private int mAddedMaxNum = 7;

    private String mResultCode = "1";
    //</editor-fold>

    private TextView tvTitle;
    private TextView tvRight;
    private ImageView ivLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_application);
        initView();
        initData();
        setRight1("编辑");
        registerListener();
        setData();
    }

    private void setData(){

        String selectedMenu = Utils.readLocalText("selectedmenu.txt");
        if(!StringUtils.isNullOrEmpty(selectedMenu)){
            CommitEntity entity = new Gson().fromJson(selectedMenu,CommitEntity.class);
            mAddedList.clear();
            mAddedList.addAll(entity.menuvos);
        }else{
            AllApplicationEntity.Submenu bean = new AllApplicationEntity.Submenu();
            bean.functionid = "2";
            bean.reponame = "任务";
            bean.logo = "ic_menu_renwu";
            mAddedList.clear();
            mAddedList.add(bean);
        }
        addEmptyData();
        mToolAddedAdapter.notifyDataSetChanged();

        String jsonStr = Utils.getJson("json.txt",mContext);
        Log.i("cuican",jsonStr);

        Gson gson = new Gson();
        Type type = new TypeToken<BaseRootEntity<AllApplicationEntity>>() {
        }.getType();
        BaseRootEntity<AllApplicationEntity> result = gson.fromJson(jsonStr, type);
        setAllDataRefresh(result.data);
    }

    private void registerListener() {
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRvAllApplication.scrollToPosition(0);
                if (mIsEditing){
                    //调取提交接口
                    getCommitJsonStr();
                }else {
                    mIsEditing = true;
                    setRight1("完成");
                    mLlAdded.setVisibility(View.VISIBLE);
                    if (mAllApplicationAdapter != null){
                        mAllApplicationAdapter.setIsEditing(mIsEditing);
                    }
                }
            }
        });

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsEditing){
                    mRvAllApplication.scrollToPosition(0);
                    mIsEditing = false;
                    setRight1("编辑");
                    mLlAdded.setVisibility(View.GONE);
                    if (mAllApplicationAdapter != null){
                        mAllApplicationAdapter.setIsEditing(mIsEditing);
                    }
                }else {
                    finish();
                }
            }
        });
    }

    private void setRight1(String right){
        tvRight.setText(right);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGetDialog != null && mGetDialog.isShowing()) {
            mGetDialog.dismiss();
        }
        if (mProcessDialog != null && mProcessDialog.isShowing()) {
            mProcessDialog.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsEditing){
                mRvAllApplication.scrollToPosition(0);
                mIsEditing = false;
                setRight1("编辑");
                mLlAdded.setVisibility(View.GONE);
                if (mAllApplicationAdapter != null){
                    mAllApplicationAdapter.setIsEditing(mIsEditing);
                }
            }else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //<editor-fold desc="初始化">
    private void initView() {
        mLlAdded = findViewById(R.id.ll_added);
        mRvAdded = findViewById(R.id.rv_added);
        mRvAllTitle = findViewById(R.id.rv_all_title);
        mRvAllApplication = findViewById(R.id.rv_all_application);
        tvRight = findViewById(R.id.tv_right);
        ivLeft = findViewById(R.id.iv_menu_left);
    }

    private void initData() {
        initAddedData();
        initAllTitleData();
        initAllApplicationData();
        setData();
    }

    /**
     * 初始化我的应用
     */
    private void initAddedData(){
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,4);
        mToolAddedAdapter = new ToolAddedAdapter(mContext,mAddedList);
        mRvAdded.setLayoutManager(layoutManager);
        mRvAdded.setAdapter(mToolAddedAdapter);
        ItemTouchHelper.Callback callback = new HomeToolTouchHelperCallback(mToolAddedAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRvAdded);
        mToolAddedAdapter.setAddedLongListener(this);
        mToolAddedAdapter.setAddedListener(this);
    }

    @Override
    public void onDragStarted(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    /**
     * 初始化横向标题
     */
    private void initAllTitleData(){
        AllTitleLayoutManager layoutManager = new AllTitleLayoutManager(mContext);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mAllTitleAdapter = new AllTitleAdapter(mContext,mAllList);
        mRvAllTitle.setLayoutManager(layoutManager);
        mRvAllTitle.setAdapter(mAllTitleAdapter);
        mAllTitleAdapter.setOnTitleItemListener(this);
        mRvAllTitle.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //更新选中状态，避免纵向列表快速滑动时，横向列表选中状态没有及时更新问题
                if (RecyclerView.SCROLL_STATE_IDLE == newState){
                    for (int i = 0;i < mAllList.size();i++){
                        if (mAllList.get(i).isCheck){
                            mAllTitleAdapter.notifyItemChanged(i,"unchecked");
                        }
                    }
                    mAllTitleAdapter.notifyItemChanged(mLastPosition,"checked");
                }
            }

        });
    }

    /**
     * 初始化全部应用
     */
    private void initAllApplicationData(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mAllApplicationAdapter = new AllApplicationAdapter(mContext,mAllList,mIsEditing);
        mRvAllApplication.setLayoutManager(layoutManager);
        mRvAllApplication.setAdapter(mAllApplicationAdapter);
        mAllApplicationAdapter.setOnAllApplicationItemClickListener(this);
        mRvAllApplication.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll && RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mShouldScroll = false;
                    smoothMoveToPosition(mRvAllApplication, mToPosition);
                }
                if (RecyclerView.SCROLL_STATE_IDLE == newState){
                    mIsHCheck = false;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager){
                    int position = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    for (int i = 0;i < mAllList.size();i++){
                        if (mAllList.get(i).isCheck){
                            mAllTitleAdapter.notifyItemChanged(i,"unchecked");
                        }
                    }
                    mAllTitleAdapter.notifyItemChanged(position,"checked");
                    if (!mIsHCheck){
                        //添加判断当横向列表点击item时不调用这个方法，避免横向列表回弹动作
                        mRvAllTitle.smoothScrollToPosition(position);
                    }
                    mLastPosition = position;
                }
            }

        });
    }
    //</editor-fold>

    @Override
    public void onAddedItemListener(AllApplicationEntity.Submenu bean) {
        //我的应用点击事件
        removeApplicationToAdded(bean);
        setItemApplicationEditState(bean);
    }

    @Override
    public void onTitleItemListener(int position) {
        //分类标签点击事件  全部应用列表滑动到指定位置
        mIsHCheck = true;
        mLastPosition = position;
        smoothMoveToPosition(mRvAllApplication, position);
    }

    @Override
    public void onAllApplicationItemListener(AllApplicationEntity.Submenu bean, int position, int index, View view) {
        if (mIsEditing){
            if("1".equals(mAllList.get(position).submenu.get(index).iscanedit)){
                //全部应用的点击事件
                if (mAllList.get(position).submenu.get(index).isAddOrRemove){
                    //将item从被选中列表中移除
                    mAllList.get(position).submenu.get(index).isAddOrRemove = false;
                    mAllApplicationAdapter.notifyItemChanged(position,index);
                    removeApplicationToAdded(bean);
                }else {
                    //将item添加到被选中列表
                    if (getAddedDataSize() < mAddedMaxNum){
                        mAllList.get(position).submenu.get(index).isAddOrRemove = true;
                        mAllApplicationAdapter.notifyItemChanged(position,index);
                        addApplicationToAdded(bean);
                    }else {
                        Utils.toast(mContext,"最多选择7个");
                    }
                }
            }
        }else {
            MenuListEntity menuBean = new MenuListEntity();
            menuBean.functionid = mAllList.get(position).submenu.get(index).functionid;
            menuBean.logincheck = mAllList.get(position).submenu.get(index).logincheck;
            menuBean.authcheck = mAllList.get(position).submenu.get(index).authcheck;
            menuBean.drivertype = mAllList.get(position).submenu.get(index).drivertype;
            menuBean.wapurl = mAllList.get(position).submenu.get(index).wapurl;
            MenuIntentHandleUtil.handleMenuFunctionIntent(AllApplicationActivity.this,menuBean);
        }
    }

    /**
     * 刷新所有数据
     */
    private void setAllDataRefresh(AllApplicationEntity bean){
        if (bean.allAppMenu != null){
            mAllList.clear();
            mAllList.addAll(bean.allAppMenu);
            setApplicationEditState();
            mAllTitleAdapter.notifyDataSetChanged();
            mAllApplicationAdapter.setIsEditing(mIsEditing);
            if (mAllList.size() > 0){
                mRvAllTitle.setVisibility(View.VISIBLE);
            }else {
                mRvAllTitle.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置全部应用按钮的编辑状态
     */
    private void setApplicationEditState(){
        HashMap<String,Integer> mAddedMap = new HashMap<>(16);
        for (int i = 0;i < mAddedList.size();i++){
            mAddedMap.put(mAddedList.get(i).functionid,i);
        }
        for (int i = 0;i < mAllList.size();i++){
            for (int j = 0;j < mAllList.get(i).submenu.size();j++){
                if (mAddedMap.containsKey(mAllList.get(i).submenu.get(j).functionid)){
                    mAllList.get(i).submenu.get(j).isAddOrRemove = true;
                }else {
                    mAllList.get(i).submenu.get(j).isAddOrRemove = false;
                }
            }
        }
    }

    /**
     * 设置全部应用中单条按钮的编辑状态
     */
    private void setItemApplicationEditState(AllApplicationEntity.Submenu bean){
        for (int i = 0;i < mAllList.size();i++){
            for (int j = 0;j < mAllList.get(i).submenu.size();j++){
                if (bean.functionid.equals(mAllList.get(i).submenu.get(j).functionid)){
                    mAllList.get(i).submenu.get(j).isAddOrRemove = false;
                    mAllApplicationAdapter.notifyItemChanged(i,j);
                    break;
                }
            }
        }
    }

    /**
     * 将选项添加到被选中列表中
     */
    private void addApplicationToAdded(AllApplicationEntity.Submenu bean){
        List<AllApplicationEntity.Submenu> tempList = new ArrayList<>();
        for (int i = 0;i < mAddedList.size();i++){
            if (!StringUtils.isNullOrEmpty(mAddedList.get(i).functionid)){
                tempList.add(mAddedList.get(i));
            }
        }
        mAddedList.clear();
        mAddedList.addAll(tempList);
        mAddedList.add(bean);
        addEmptyData();
        mToolAddedAdapter.notifyDataSetChanged();
    }

    /**
     * 将选项从被选中列表中移除
     */
    private void removeApplicationToAdded(AllApplicationEntity.Submenu bean){
        for (int i = 0;i < mAddedList.size();i++){
            if (bean.functionid.equals(mAddedList.get(i).functionid)){
                mAddedList.remove(i);
                i--;
            }
        }
        addEmptyData();
        mToolAddedAdapter.notifyDataSetChanged();
    }

    /**
     * 被选中列表添加占位数据
     */
    private void addEmptyData() {
        if (mAddedList.size() < mAddedMaxNum) {
            int emptyCount = 7 - mAddedList.size();
            for (int i = 0; i < emptyCount; i++) {
                AllApplicationEntity.Submenu addedBean = new AllApplicationEntity.Submenu();
                mAddedList.add(addedBean);
            }
        }
    }

    /**
     * 获取我的应用真实个数（默认添加了一切空数据，用于显示）
     */
    private int getAddedDataSize(){
        int count = 0;
        for (int i = 0;i < mAddedList.size();i++){
            if (!StringUtils.isNullOrEmpty(mAddedList.get(i).functionid)){
                count++;
            }
        }
        return count;
    }

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前，使用smoothScrollToPosition
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后，最后一个可见项之前
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }

    /**
     * 获取提交的保存信息
     */
    private void getCommitJsonStr() {
        String jsonStr = "";
        CommitEntity entity = new CommitEntity();
        List<AllApplicationEntity.Submenu> commitList = new ArrayList<>();
        for (int i = 0;i < mAddedList.size();i++){
            if (!StringUtils.isNullOrEmpty(mAddedList.get(i).functionid)){
                commitList.add(mAddedList.get(i));
            }
        }
        entity.menuvos = commitList;
        jsonStr = new Gson().toJson(entity);
        Utils.saveToSDCard(AllApplicationActivity.this,"selectedmenu.txt",jsonStr);
    }

    private class CommitEntity implements Serializable {
        private List<AllApplicationEntity.Submenu> menuvos;
    }

}
