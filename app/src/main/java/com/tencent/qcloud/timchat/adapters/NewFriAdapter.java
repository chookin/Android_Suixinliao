package com.tencent.qcloud.timchat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.TIMFriendFutureItem;
import com.tencent.TIMFutureFriendType;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.ui.NewFriendActivity;

import java.util.List;

/**
 * 搜索结果
 */
public class NewFriAdapter extends BaseAdapter {
    private Context mContext;
    private List<TIMFriendFutureItem> mNewFriList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private NewFriendActivity mParent;
    public NewFriAdapter(Context context, List<TIMFriendFutureItem> newfriendlist,NewFriendActivity activity) {
        mContext = context;
        mNewFriList = newfriendlist;
        mParent = activity;
    }


    @Override
    public int getCount() {
        return mNewFriList.size();
    }

    @Override
    public Object getItem(int i) {
        return mNewFriList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int groupPosition, View convertView, ViewGroup viewGroup) {
        ItemHolder Holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_newfrilist, null);
            Holder = new ItemHolder();
            Holder.id = (TextView) convertView.findViewById(R.id.newfri_id);
            Holder.status = (TextView) convertView.findViewById(R.id.newfri_status);
//            Holder.img = (ImageView) convertView.findViewById(R.id.newfri_avatar);
            convertView.setTag(Holder);
        } else {
            Holder = (ItemHolder) convertView.getTag();
        }
//        mNewFriList.get(groupPosition).get

        Holder.id.setText(mNewFriList.get(groupPosition).getIdentifier());
        discideStatus(Holder, mNewFriList.get(groupPosition));
//        Holder.img.setText(mNewFriList.get(groupPosition).getName());
//        if (imageLoader != null)
//            imageLoader.displayImage(mNewFriList.get(groupPosition).ge, Holder.img);
        return convertView;
    }

    class ItemHolder {
        public TextView id;
        public TextView status;
        public TextView img;

    }

    private void discideStatus(final ItemHolder holder, final TIMFriendFutureItem entity){
        if(entity.getType() == TIMFutureFriendType.TIM_FUTURE_FRIEND_PENDENCY_OUT_TYPE){
            holder.status.setText("待验证");
            holder.status.setBackground(mContext.getResources().getDrawable(R.color.colorGray2));
            holder.status.setClickable(false);
        }else if(entity.getType() == TIMFutureFriendType.TIM_FUTURE_FRIEND_PENDENCY_IN_TYPE){
            holder.status.setBackground(mContext.getResources().getDrawable(R.drawable.newfri_agree));
            holder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mParent.confirmItemStatus(entity.getIdentifier());
                    mParent.finish();
                }

            });
        }else if(entity.getType() == TIMFutureFriendType.TIM_FUTURE_FRIEND_DECIDE_TYPE){
            holder.status.setText("已添加");
            holder.status.setClickable(false);
        }
    }

}
