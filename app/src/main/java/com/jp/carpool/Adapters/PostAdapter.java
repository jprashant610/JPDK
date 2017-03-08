package com.jp.carpool.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
            holder.outerLayout = (LinearLayout) convertView.findViewById(R.id.lstlinearLayout);

            holder.idCarName = (TextView) convertView.findViewById(R.id.lstvtextCarNameNo);
            holder.idFullName = (TextView) convertView.findViewById(R.id.lstvtextfullName);
            holder.idSeat = (TextView) convertView.findViewById(R.id.lstvtextSeatNo);
            holder.idDate = (TextView) convertView.findViewById(R.id.lstvtextDate);
            holder.idTime = (TextView) convertView.findViewById(R.id.lstvtextTime);
            holder.idFrom = (TextView) convertView.findViewById(R.id.lstvtextFrom);
            holder.idTo = (TextView) convertView.findViewById(R.id.lstvtextTo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.idCarName.setText(pojo.getCarName());
        holder.idFullName.setText(pojo.getFullName());
        holder.idSeat.setText(pojo.getNumberSeat());
        holder.idDate.setText(pojo.getDate());
        holder.idTime.setText(pojo.getTime());
        holder.idFrom.setText(pojo.getFrom());
        holder.idTo.setText(pojo.getTo());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {

        }
        else
        {
            if(true == user.getUid().toString().equals(pojo.getUserId())) {
                holder.outerLayout.setBackgroundColor(Color.parseColor("#ffc000"));
             //   Log.d("PostAdapter","Match car name--->"+pojo.getCarName());
                Log.d("PostAdapter", String.valueOf(position)+"Match Uid name--->"+pojo.getUserId());
            }
            else {
                holder.outerLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                Log.d("PostAdapter", String.valueOf(position) + "Not Match Uid name--->" + pojo.getUserId());
            }
        }
        return convertView;
    }

    public class ViewHolder {
        TextView idCarName;
        TextView idFullName;
        TextView idSeat;
        TextView idDate;
        TextView idTime;
        TextView idFrom;
        TextView idTo;
        LinearLayout outerLayout;
    }


}