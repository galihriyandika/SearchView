package com.example.searchview.adapter;

import android.content.Context;
import android.media.ImageReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchview.util.Util;


public class FollowerListAdapter extends RecyclerView.Adapter<FollowerListAdapter.FollowerViewHolder>implements Filterable {

    private static final String TAG = FollowerListAdapter.class.getSimpleName();

    private Context context;
    private List<Follower> followers;
    private List<Follower> filteredList;

    public FollowerListAdapter(Context context, List<Follower> followers) {
        this.context = context;
        this.followers = followers;
        this.filteredList = followers;
    }

    public static class FollowerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView ivLogo;
        TextView tvUserName;
        TextView tvUserId;
        TextView tvGithubURL;

        public FollowerViewHolder(final View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.cv_follower);
            ivLogo = (ImageView) view.findViewById(R.id.iv_user_logo);
            tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
            tvUserId = (TextView) view.findViewById(R.id.tv_user_id);
            tvGithubURL = (TextView) view.findViewById(R.id.tv_github_url);
        }

    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredList = followers;
                } else {
                    filteredList = Util.searchFollowersFilter(followers, charString);
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }

            @NonNull
    @Override
    public FollowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follower_item, parent, false);
        return new FollowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerViewHolder holder, int position) {
        if (filteredList.size() > 0) {
            Follower follower = filteredList.get(position);
            if (follower != null) {
                holder.cardView.setOnClickListener(v -> onCardClick(position));
                ImageReader.setCircleImage(context, holder.ivLogo, follower.getAvatarUrl());
                holder.tvUserName.setText(follower.getLogin());
                holder.tvUserId.setText(String.valueOf(follower.getId()));
                holder.tvGithubURL.setText(follower.getUrl());
            }
        }
    }

    // Handle click by card github user
    private void onCardClick(int position) {
        Toast.makeText(context, "Show GitHub " + filteredList.get(position).getLogin(), Toast.LENGTH_SHORT).show();
    }


        @Override
        public int getItemCount () {
            return filteredList == null ? 0 : filteredList.size();
        }
    }
