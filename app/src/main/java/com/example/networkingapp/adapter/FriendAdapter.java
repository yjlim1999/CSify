package com.example.networkingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.networkingapp.R;
import com.example.networkingapp.activity.ProfileActivity;
import com.example.networkingapp.model.FriendsModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    Context context;
    List<FriendsModel.Friend> friends;


    public FriendAdapter(List<FriendsModel.Friend> friends, Context context) {
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //able to click on file of person who is in my frame adapter
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        //get(position) so that you know which friend you are setting inside
        final FriendsModel.Friend friend = friends.get(position);
        //taking holder,(which is individual friend) and setting a name
        holder.activityTitleSingle.setText(friend.getName());

        if (!friend.getProfileUrl().isEmpty()) {
            //loading picture
            Picasso.get().load(friend.getProfileUrl()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.img_default_user).into(holder.activityProfileSingle, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(friend.getProfileUrl()).placeholder(R.drawable.img_default_user).into(holder.activityProfileSingle);
                }

            });
        }
        //setting onclick listener so that when you click on it, it directs you to the profile
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ProfileActivity.class).putExtra("uid", friend.getUid()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.activity_profile_single)
        CircleImageView activityProfileSingle;
        @BindView(R.id.activity_title_single)
        TextView activityTitleSingle;
        @BindView(R.id.action_btn)
        Button actionBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            actionBtn.setVisibility(View.GONE);
        }
    }


}
