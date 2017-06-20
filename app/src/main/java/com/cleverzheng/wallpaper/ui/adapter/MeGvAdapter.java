package com.cleverzheng.wallpaper.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cleverzheng.wallpaper.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wangzai on 2017/5/20.
 */
public class MeGvAdapter extends BaseAdapter {
    private static final String ITEM_ICON = "ITEM_ICON";
    private static final String ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
    private Context context;
    List<HashMap<String, Object>> mAdapterData;

    public MeGvAdapter(Context context, List<HashMap<String, Object>> mAdapterData) {
        this.context = context;
        this.mAdapterData = mAdapterData;
    }

    @Override
    public int getCount() {
        return mAdapterData.size();
    }

    @Override
    public Object getItem(int position) {
        return mAdapterData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.gvitem_me_menu, parent, false);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, Object> data = mAdapterData.get(position);

        holder.ivIcon.setImageResource((Integer) data.get(ITEM_ICON));
        holder.tvDescription.setText(data.get(ITEM_DESCRIPTION).toString());
        return convertView;
    }

    class ViewHolder {
        private ImageView ivIcon;
        private TextView tvDescription;
    }
}
