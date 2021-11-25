package edu.ycce.rssreader;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit.model.News;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.RecyclerViewHolder> {
    private MenuItemClickListener mListener;
    private List<News> mNewsList;

    public MenuAdapter(List<News> newsList, Activity activity) {
        this.mListener = (MenuItemClickListener) activity;
        this.mNewsList = newsList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_menu, viewGroup, false);

        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int position) {
        final int currentPosition = position;
        final News news = mNewsList.get(position);
        recyclerViewHolder.tvTitle.setText(news.getNewsTitle());
        recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onMenuItemClick(news.getNewsId());
                for (int i = -1; i <= mNewsList.size() ; i++) {
                    if (i == currentPosition) {
                        news.setSelected(true);
                    } else {
 //                       mNewsList.get(i).setSelected(false);
                    }
                }
                notifyDataSetChanged();
            }
        });

        recyclerViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mListener.onMenuItemLongClick(news.getNewsId());
                return false;
            }
        });
    }

    public void removeItemWidthId(int newsId) {
        for (int i = 0; i <= mNewsList.size(); i++) {
            if (mNewsList.get(i).getNewsId() == newsId) {
                mNewsList.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }

    public interface MenuItemClickListener {
        void onMenuItemClick(int newsId);
        void onMenuItemLongClick(int newsId);
    }
}
