package com.blackhole.blackhole.mainpage.ui;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackhole.blackhole.R;
import com.blackhole.blackhole.data.entities.Message;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: perqin
 * Date  : 5/17/17
 */

public class MessagesListRecyclerAdapter extends RecyclerView.Adapter<MessagesListRecyclerAdapter.ViewHolder> {
    private ArrayList<Message> mDataSet = new ArrayList<>();
    private OnItemClickListener mListener;
    private String mSessionId;

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void prependMessages(ArrayList<Message> messages) {
        mDataSet.addAll(0, messages);
        notifyItemRangeInserted(0, messages.size());
    }

    public void appendMessages(ArrayList<Message> messages) {
        mDataSet.addAll(messages);
        notifyItemRangeInserted(mDataSet.size() - messages.size(), messages.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnLongClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(mDataSet.get(holder.getAdapterPosition()));
            }
            return true;
        });
        holder.itemView.setBackgroundColor(mSessionId != null && mSessionId.equals(mDataSet.get(position).getReply())
                ? ContextCompat.getColor(holder.itemView.getContext(), R.color.colorMessageBackgroundLinked)
                : ContextCompat.getColor(holder.itemView.getContext(), R.color.colorMessageBackground));
        holder.nicknameTextView.setText(mDataSet.get(position).getNickname());
        holder.createdTimeTextView.setText(" ");
        holder.contentTextView.setText(mDataSet.get(position).getContent());
        holder.contentTextView.setTextColor(mDataSet.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setSessionId(String sessionId) {
        mSessionId = sessionId;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.message_nickname)
        TextView nicknameTextView;
        @BindView(R.id.message_sent_time)
        TextView createdTimeTextView;
        @BindView(R.id.message_content)
        TextView contentTextView;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Message message);
    }
}
