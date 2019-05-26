package com.tck.nepluginapk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/26</p>
 *
 * @author tck
 * @version 1.0
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private Context context;
    private List<GroupBean> list;
    private LayoutInflater inflater;


    public GroupAdapter(Context context, List<GroupBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupViewHolder(inflater.inflate(R.layout.group_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, final int position) {
        final GroupBean groupBean = list.get(position);
        holder.tvGroupName.setText(groupBean.name);
        holder.commentView.setData(groupBean);
        holder.commentView.setOnCommentViewListener(new CommentView.OnCommentViewListener() {
            @Override
            public void seeMore() {
                groupBean.isOpen=!groupBean.isOpen;
               // notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGroupName;
        private CommentView commentView;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGroupName = (TextView) itemView.findViewById(R.id.tv_group_name);
            commentView = (CommentView) itemView.findViewById(R.id.comment_view);
        }
    }
}
