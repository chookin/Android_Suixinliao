package com.tencent.qcloud.timchat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.qcloud.timchat.R;

import java.util.List;

/**
 * 分组信息Adapters
 */
public class GroupListAdapter extends BaseExpandableListAdapter {
    private List<String> mGroupList;
    //测试数据
    private String[] generalsTypes = new String[]{"魏", "蜀", "吴"};
    //测试数据
    private String[][] generals = new String[][]{
            {"夏侯惇", "甄姬", "许褚", "郭嘉", "司马懿", "杨修"},
            {"马超", "张飞", "刘备", "诸葛亮", "黄月英", "赵云"},
            {"吕蒙", "陆逊", "孙权", "周瑜", "孙尚香"}};

    private ChildItem[][] mChildItems;

    private Context mContext;


    public GroupListAdapter(Context context, List<String> groupList, ChildItem[][] childItems) {
        mContext = context;
//        mGroupList = groupList;
//        mChildItems = childItems;
    }


    public GroupListAdapter(Context context) {
        mContext = context;

    }

    @Override
    public int getGroupCount() {
        return generalsTypes.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return  generals[i].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return  generalsTypes[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return generals[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 群组
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param viewGroup
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group, null);
            groupHolder = new GroupHolder();
            groupHolder.groupname = (TextView) convertView.findViewById(R.id.group_name);
            // groupHolder.img = (ImageView) convertView
            // .findViewById(R.id.img);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.groupname.setText(generalsTypes[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_childmember, null);
            itemHolder = new ItemHolder();
            itemHolder.itemname = (TextView) convertView.findViewById(R.id.group_member_name);
//            itemHolder.img = (ImageView) convertView
//             .findViewById(R.id.img);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        itemHolder.itemname.setText(generals[groupPosition][childPosition]);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }


    class GroupHolder {
        public TextView groupname;
        public ImageView img;
    }

    class ItemHolder {
        public TextView itemname;
        public ImageView img;
    }

    class ChildItem {
        public String name;
        public String img;
    }
}
