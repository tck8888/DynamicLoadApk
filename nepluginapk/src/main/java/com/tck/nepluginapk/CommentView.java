package com.tck.nepluginapk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


/**
 * <p>description:</p>
 * <p>created on: 2019/5/26</p>
 *
 * @author tck
 * @version 1.0
 */
public class CommentView extends LinearLayout {

    private Context context;

    public CommentView(Context context) {
        this(context, null);
    }

    public CommentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(VERTICAL);
    }


    public void setData(GroupBean groupBean) {
        removeAllViews();
        if (groupBean == null) {
            return;
        }

        if (groupBean.childBeans == null || groupBean.childBeans.isEmpty()) {
            return;
        }
        if ( groupBean.isOpen) {
            for (int i = 0; i < groupBean.childBeans.size(); i++) {
                ChildBean childBean = groupBean.childBeans.get(i);
                TextView textView = getTextView(childBean.name);
                addView(textView);
            }
        } else {
            ChildBean childBean = groupBean.childBeans.get(0);
            TextView textView = getTextView(childBean.name);
            addView(textView);
            if (groupBean.childBeans.size() > 1) {
                TextView tvSeeMore = getTextView("展开" + groupBean.childBeans.size() + "条回复");
                addView(tvSeeMore);

                tvSeeMore.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCommentViewListener != null) {
                            onCommentViewListener.seeMore();
                        }
                    }
                });
            }
        }
    }

    private TextView getTextView(String name) {
        TextView textView = new TextView(context);
        textView.setText(name);
        textView.setLayoutParams(new LayoutParams(-1, -2));
        return textView;
    }

    private OnCommentViewListener onCommentViewListener;

    public void setOnCommentViewListener(OnCommentViewListener onCommentViewListener) {
        this.onCommentViewListener = onCommentViewListener;
    }

    public interface OnCommentViewListener {
        void seeMore();
    }
}
