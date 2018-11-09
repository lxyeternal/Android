package com.popupwindowlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miao on 2017/9/29.
 * 自定义PopupWindow 实现下拉列表效果
 */

public abstract class SpinerPopWindow<T> extends PopupWindow{
    private List<T> datas;
    private Context mContext;
    private LayoutInflater mInflater;
    private RecyclerView recyclerView;
    private SpinerAdapter spinerAdapter;
    private Activity mActivity;
    private int itemResId;
    public SpinerPopWindow(Context context,List<T> list,int resId){
        this.mContext=context;
        this.datas = list;
        mInflater = LayoutInflater.from(mContext);
        this.itemResId=resId;
        mActivity= (Activity) context;
        initPopup();
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopup(){
        View view = mInflater.inflate(R.layout.spiner_window_layout, null);
        setContentView(view);
        //设置PopupWindow宽高
        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        setWidth(display.getWidth());
        setHeight(display.getHeight());
        //设置背景
        ColorDrawable dw = new ColorDrawable(0x60000000);
        setBackgroundDrawable(dw);
        setOutsideTouchable(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //设置adapter
        spinerAdapter=new SpinerAdapter(mContext,datas,itemResId);
        recyclerView.setAdapter(spinerAdapter);


    }
    class SpinerAdapter extends RecyclerView.Adapter<SpinerAdapter.SpinerHolder>{
        private List<T> spinerList;
        private Context spinerContext;
        private LayoutInflater spinerInflater;
        private int mResId;
        public SpinerAdapter(Context context,List<T> list,int resId){
            this.spinerContext=context;
            this.spinerList = new ArrayList<>();
            this.mResId=resId;
            if (spinerList != null) {
                this.spinerList.addAll(list);
            }
            spinerInflater = LayoutInflater.from(spinerContext);
        }
        public void nodfiyData(List<T> list) {
            if (list != null) {
                this.spinerList.clear();
                this.spinerList.addAll(list);
            }
            notifyDataSetChanged();
        }
        @Override
        public SpinerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = spinerInflater.inflate(mResId, parent, false);
            SpinerHolder holder=new SpinerHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(SpinerHolder holder, final int position) {
            setData(holder,position);

            //条目点击事件
            if (mItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClick(spinerList.get(position), position);
                    }
                });
            }
            //长按点击事件
            if (mItemLoogClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return mItemLoogClickListener.onItemLongClick(spinerList.get(position), position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return spinerList.size();
        }

        class SpinerHolder extends RecyclerView.ViewHolder{
            public SpinerHolder(View itemView) {
                super(itemView);
            }
            public <T extends View> T getView(int id) {
                return (T) itemView.findViewById(id);
            }
        }
        //使用接口回调点击事件
        private ItemClickListener mItemClickListener;

        public void setOnItemClickListener(ItemClickListener itemClickListener) {
            this.mItemClickListener = itemClickListener;
        }

        //使用接口回调点击事件
        private ItemLongClickListener mItemLoogClickListener;

        public void setOnItemLongClickListener(ItemLongClickListener itemLongClickListener) {
            this.mItemLoogClickListener = itemLongClickListener;
        }
    }

    /**
     * 给布局设置值
     * @param holder
     * @param position  对应的位置
     */
    public abstract void setData(SpinerAdapter.SpinerHolder holder, int position);

    /**
     * 刷新数据
     * @param list
     */
    public void nodfiyData(List<T> list){
        if(spinerAdapter!=null){
            spinerAdapter.nodfiyData(list);
        }
    }

    /**
     * 点击事件
     * @param itemClickListener
     */
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        if(spinerAdapter!=null){
            spinerAdapter.setOnItemClickListener(itemClickListener);
        }
    }

    /**
     * 长按事件
     * @param itemLongClickListener
     */
    public void setOnItemLongClickListener(ItemLongClickListener itemLongClickListener){
        if(spinerAdapter!=null){
            spinerAdapter.setOnItemLongClickListener(itemLongClickListener);
        }
    }
    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if(Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }
    /**
     * 设置PopupWindow的位置
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, 0, 2);
        } else {
            this.dismiss();
        }
    }
}
