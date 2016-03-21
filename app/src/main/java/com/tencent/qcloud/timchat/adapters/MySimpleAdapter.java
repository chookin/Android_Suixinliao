package com.tencent.qcloud.timchat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.model.ItemTIMProfile;

import java.util.ArrayList;

/**
 * 搜索结果
 */
public class MySimpleAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ItemTIMProfile> mSearchResult;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public MySimpleAdapter(Context context, ArrayList<ItemTIMProfile> searchResult) {
        mContext = context;
        mSearchResult = searchResult;
    }


    @Override
    public int getCount() {
        return mSearchResult.size();
    }

    @Override
    public Object getItem(int i) {
        return mSearchResult.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int groupPosition, View convertView, ViewGroup viewGroup) {
        ItemHolder Holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_searchresult, null);
            Holder = new ItemHolder();
            Holder.groupname = (TextView) convertView.findViewById(R.id.person_name);
//            Holder.img = (ImageView) convertView.findViewById(R.id.person_avatar);
            convertView.setTag(Holder);
        } else {
            Holder = (ItemHolder) convertView.getTag();
        }
        Holder.groupname.setText(mSearchResult.get(groupPosition).getName());
//        Holder.img.setText(mSearchResult.get(groupPosition).getName());
//        if (imageLoader != null)
//            imageLoader.displayImage(mSearchResult.get(groupPosition).ge, Holder.img);
        return convertView;
    }

    class ItemHolder {
        public TextView groupname;
        public ImageView img;
    }

}
