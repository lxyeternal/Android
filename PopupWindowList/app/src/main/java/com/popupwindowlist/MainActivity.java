package com.popupwindowlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private TextView tvItem1, tvItem2, tvItem3;
    private List<String> list;
    private ListPopu listPopu;
    private View line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initData();
        tvItem1 = (TextView) findViewById(R.id.tv_item1);
        tvItem2 = (TextView) findViewById(R.id.tv_item2);
        tvItem3 = (TextView) findViewById(R.id.tv_item3);
        line = findViewById(R.id.line);


        tvItem1.setOnClickListener(this);
        tvItem2.setOnClickListener(this);
        tvItem3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (listPopu != null) {
            listPopu.dismiss();
            listPopu = null;
        } else {
            if(id ==R.id.tv_item2){
                setTextDrawable(R.mipmap.up_icon,tvItem2);
            }else if(id ==R.id.tv_item3){
                setTextDrawable(R.mipmap.up_icon,tvItem3);
            }else if(id ==R.id.tv_item1){
                setTextDrawable(R.mipmap.up_icon,tvItem1);
            }
            getList();
        }
    }

    private void getList() {
        listPopu = new ListPopu(this, list, R.layout.spiner_window_layout_item);
        //设置PopWindow显示的位置
        listPopu.showPopupWindow(line);
        //条目点击监听
        listPopu.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(Object obj, int position) {
                Toast.makeText(MainActivity.this, list.get(position), Toast.LENGTH_LONG).show();
                listPopu.dismiss();
                listPopu = null;
            }
        });
        //监听PopupWindow消失
        listPopu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setTextDrawable(R.mipmap.dow_icon,tvItem1);
                setTextDrawable(R.mipmap.dow_icon,tvItem2);
                setTextDrawable(R.mipmap.dow_icon,tvItem3);
            }
        });
    }

    class ListPopu extends SpinerPopWindow<String> {


        public ListPopu(Context context, List<String> list, int resId) {
            super(context, list, resId);
        }

        @Override
        public void setData(SpinerAdapter.SpinerHolder holder, int position) {
            TextView item = holder.getView(R.id.item);
            String s = list.get(position);
            item.setText(s);

        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("item:" + i);
        }
    }

    /**
     * 设置textview右边显示图片
     */
    private void setTextDrawable(int imageid, TextView tv) {
        Drawable drawable = getResources().getDrawable(imageid);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 监听物理返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (listPopu != null) {
                listPopu.dismiss();
                listPopu = null;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
