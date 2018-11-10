package myaddressselector.myaddressselector.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import myaddressselector.myaddressselector.R;


/**
 * Created by Army on 17/2/24.
 */
public class SettingItemView extends LinearLayout {

    private RelativeLayout settingItem;
    private ImageView ivArrow;
    private ImageView ivIcon;
    private TextView tvText, tvTip;

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public SettingItemView(Context context) {

        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_setting, this);

        settingItem = (RelativeLayout)view.findViewById(R.id.settingItem);

        ivArrow = (ImageView)view.findViewById(R.id.ivArrow);
        ivIcon = (ImageView)view.findViewById(R.id.ivIcon);

        tvText = (TextView)view.findViewById(R.id.tvSettingText);
        tvTip = (TextView)view.findViewById(R.id.tvTip);
    }

    public void setOnItemClickListener(OnClickListener listener) {
        settingItem.setOnClickListener(listener);
    }

    public void setItemTip(int resId) {
        if(resId == R.string.command_value_hidden) {
            tvTip.setVisibility(View.INVISIBLE);
            return;
        }

        tvTip.setVisibility(View.VISIBLE);
        tvTip.setText(resId);
    }

    public void setItemTip(String tip) {
        tvTip.setVisibility(View.VISIBLE);
        tvTip.setText(tip);
    }

    public void setItemTipColor(int color) {
        tvTip.setVisibility(View.VISIBLE);
        tvTip.setTextColor(getResources().getColor(color));
    }

    public void hideItemTip() {
        tvTip.setVisibility(View.INVISIBLE);
    }

    public void setItemText(int resId) {
        if(resId == R.string.command_value_hidden) {
            tvText.setVisibility(View.INVISIBLE);
            return;
        }

        tvText.setVisibility(View.VISIBLE);
        tvText.setText(resId);
    }

    public void setItemText(String text) {
        tvText.setVisibility(View.VISIBLE);
        tvText.setText(text);
    }

    public void hideItemText() {
        tvText.setVisibility(View.GONE);
    }


    public void setItemArrow() {
        ivArrow.setVisibility(View.INVISIBLE);

    }

    public void setIvIcon(int resId){
        ivIcon.setVisibility(VISIBLE);
        ivIcon.setImageResource(resId);
    }


}
