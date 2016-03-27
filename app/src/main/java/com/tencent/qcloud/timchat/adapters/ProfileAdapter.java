package com.tencent.qcloud.timchat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.model.ProfileItem;

import java.util.List;

/**
 * 人或群资料的adapter
 */
public class ProfileAdapter extends BaseAdapter {
    private Context mContext;
    private List<ProfileItem> itemList;

    public ProfileAdapter(Context context, List<ProfileItem> searchResult) {
        mContext = context;
        itemList = searchResult;
    }


    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ItemHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_searchresult, null);
            holder = new ItemHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.name);
            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.tvDes = (TextView) convertView.findViewById(R.id.description);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder) convertView.getTag();
        }
        holder.tvName.setText(itemList.get(position).getName());
        if (itemList.get(position).getAvatarRes() != 0){
            holder.avatar.setImageResource(itemList.get(position).getAvatarRes());
        }
        holder.tvDes.setText(itemList.get(position).getDescription());
        return convertView;
    }

    class ItemHolder {
        public ImageView avatar;
        public TextView tvName;
        public TextView tvDes;
    }

}
