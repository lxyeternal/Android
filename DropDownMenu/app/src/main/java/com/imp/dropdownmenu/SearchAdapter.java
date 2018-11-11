package com.imp.dropdownmenu;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by import on 2017/12/28.
 */

public class SearchAdapter extends Madapter {

    private Context context;
    private int selectColor = Color.GRAY;
    private LayoutInflater inflater;
    private List<Dic> items;
    private int selectedPosition = -1;

    public List<Dic> getItems() {
        return items;
    }

    @Override
    public String getShowKey(int position , String key){
        if (key.equals("name")){
            return  items.get(position).getName();
        }else {
            return  items.get(position).getCode();
        }

    }





    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }


    public SearchAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }



    public void setItems(List<Dic> items) {
        this.items = items;
    }



    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        SearchAdapter.ViewHolder holder;
        if(convertView == null){

            holder = new SearchAdapter.ViewHolder();
            convertView = (View)inflater.inflate(R.layout.item_listview, parent , false);
            holder.name =(TextView) convertView.findViewById(R.id.name);
            holder.code = (TextView)convertView.findViewById(R.id.code);
            holder.employeesquery = (LinearLayout)convertView.findViewById(R.id.employeesquery);
            convertView.setTag(holder);
        }else{
            holder = (SearchAdapter.ViewHolder)convertView.getTag();
        }

        /**
         * 该项被选中时改变背景色
         */
        if(selectedPosition == position){
            holder.employeesquery.setBackgroundColor(selectColor);
        }else{
            holder.employeesquery.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.name.setText(items.get(position).getName());
        holder.code.setText(items.get(position).getCode());  //也可在ITTM里去掉这一项，写在Tag里


        return convertView;
    }

    class ViewHolder{
        TextView name;
        TextView code;
        LinearLayout employeesquery;
    }

    @Override
    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
    }
}
