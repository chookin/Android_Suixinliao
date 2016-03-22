package com.tencent.qcloud.timchat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.model.CategoryItem;

import java.util.List;

/**
 * 选择类别界面的adapter
 */
public class CategoryAdapter extends ArrayAdapter<CategoryItem> {

    private int resourceId;
    private View view;
    private ViewHolder viewHolder;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public CategoryAdapter(Context context, int resource, List<CategoryItem> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null){
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }else{
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) view.findViewById(R.id.name);
            view.setTag(viewHolder);
        }
        CategoryItem data = getItem(position);
        viewHolder.tvName.setText(data.getName());
        if (data.isSelected()){
            viewHolder.tvName.setTextColor(getContext().getResources().getColor(R.color.text_blue1));
        }
        return view;
    }


    public class ViewHolder{
        public TextView tvName;
    }
}
