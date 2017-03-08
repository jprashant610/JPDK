package com.jp.carpool.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jp.carpool.Data.postData;
import com.jp.carpool.R;

import java.util.ArrayList;

/**
 * Created by dk on 3/7/17.
 */

public class PostAdapter extends BaseAdapter{

    Context var;
    ArrayList<postData> postDatas = new ArrayList<postData>();

    public PostAdapter(Context var, ArrayList<postData> postDatas) {
        this.var = var;
        this.postDatas = postDatas;
    }

    @Override
    public int getCount() {
        return postDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return postDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return postDatas.indexOf(postDatas.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        postData pojo = postDatas.get(position);
        LayoutInflater inflater = (LayoutInflater) var.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.post_list_item, null);


            holder = new ViewHolder();

            holder.idCarName = (TextView) convertView.findViewById(R.id.idCarName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.idCarName.setText(pojo.getCarName());
        Log.d("PostAdapter","car name--->"+pojo.getCarName());
        Log.d("PostAdapter","Uid name--->"+pojo.getUserId());
        return convertView;
    }

    public class ViewHolder {
        TextView idCarName;
    }


}