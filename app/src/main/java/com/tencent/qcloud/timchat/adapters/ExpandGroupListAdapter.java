package com.tencent.qcloud.timchat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.TIMFriendGroup;
import com.tencent.TIMUserProfile;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.model.ProfileSummary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分组列表Adapters
 */
public class ExpandGroupListAdapter extends BaseExpandableListAdapter {
    private List<TIMFriendGroup> mGroups;
    private Context mContext;
    private int resourceId = -1;
    private List<TIMUserProfile> mChooseMembers;

    private List<String> groups;
    private Map<String, List<ProfileSummary>> mMembers;




    public ExpandGroupListAdapter(Context context, List<String> groups, Map<String, List<ProfileSummary>> members){
        mContext = context;
        this.groups = groups;
        mMembers = members;
    }

    public ExpandGroupListAdapter(Context context, List<TIMFriendGroup> groups, List<List<TIMUserProfile>> allgroupMembers, int resource) {
        mContext = context;
        mGroups = groups;
//        mAllGroupMembers = allgroupMembers;
        resourceId = resource;
        mChooseMembers = new ArrayList<TIMUserProfile>();
        mChooseMembers.clear();

    }



    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mMembers.get(groups.get(i)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mMembers.get(groups.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
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
        GroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group, null);
            groupHolder = new GroupHolder();
            groupHolder.groupname = (TextView) convertView.findViewById(R.id.groupName);
            groupHolder.tag = (ImageView) convertView.findViewById(R.id.groupTag);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        if (isExpanded) {
            groupHolder.tag.setBackgroundResource(R.drawable.open);
        } else {
            groupHolder.tag.setBackgroundResource(R.drawable.close);
        }
        if (groups.get(groupPosition).equals("")) {
            groupHolder.groupname.setText(mContext.getResources().getString(R.string.default_group_name));
        } else {
            groupHolder.groupname.setText(groups.get(groupPosition));
        }
        return convertView;
    }

    /**
     * 群组成员
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param viewGroup
     * @return
     */
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        ChildrenHolder itemHolder;
        if (convertView == null) {
            itemHolder = new ChildrenHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_childmember, null);
            itemHolder.tag = (ImageView) convertView.findViewById(R.id.chooseTag);
            final ChildrenHolder finalItemHolder = itemHolder;
//            itemHolder.tag.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (!mChooseMembers.contains(mGroups.get(groupPosition).getProfiles().get(childPosition))) {
//                        finalItemHolder.tag.setBackgroundResource(R.drawable.selected);
//                        mChooseMembers.add(mGroups.get(groupPosition).getProfiles().get(childPosition));
//                    } else {
//                        finalItemHolder.tag.setBackgroundResource(R.drawable.unselected);
//                        mChooseMembers.remove(mGroups.get(groupPosition).getProfiles().get(childPosition));
//                    }
//                }
//            });

            itemHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ChildrenHolder) convertView.getTag();
        }
        ProfileSummary data = (ProfileSummary) getChild(groupPosition,childPosition);
        itemHolder.name.setText(data.getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


    class GroupHolder {
        public TextView groupname;
        public ImageView tag;
    }

    class ChildrenHolder {
        public TextView name;
        public ImageView avatar;
        public ImageView tag;
    }

    public List<TIMUserProfile> getChooseList() {
        return mChooseMembers;
    }


}
