package com.example.networkingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.networkingapp.R;
import com.example.networkingapp.adapter.PostAdapter;
import com.example.networkingapp.rest.ApiClient;
import com.example.networkingapp.model.PostModel;
import com.example.networkingapp.rest.services.UserInterface;

import retrofit2.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    Context context;
    //Tutorial 8
    @BindView(R.id.newsfeed)
    RecyclerView newsfeed;
    @BindView(R.id.newsfeedProgressBar)
    ProgressBar newsfeedProgressBar;

    //limit and offset is going to help with the infinite scrolling
    int limit = 2; //only allow 2 posts to show
    int offset = 0;
    boolean isFromStart = true;
    PostAdapter postAdapter;
    List<PostModel> postModels = new ArrayList<>();

    String uid = "0";
    String current_state = "0";
    Unbinder unbinder;
    //Tutorial 8

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //Tutorial 8
        unbinder = ButterKnife.bind(this, view);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        newsfeed.setLayoutManager(linearLayoutManager);
        postAdapter = new PostAdapter(context, postModels);
        uid = getArguments().getString("uid","0");
        current_state = getArguments().getString("current_state","0");
        newsfeed.setAdapter(postAdapter);

        newsfeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int passVisibleItems = linearLayoutManager.findFirstCompletelyVisibleItemPosition();

                if (passVisibleItems + visibleItemCount >= (totalItemCount)) {
                    isFromStart = false;
                    newsfeedProgressBar.setVisibility(View.VISIBLE);
                    offset = offset + limit;
                    loadProfilePost();
                }
            }
        });
        //Tutorial 8

        return view;
    }

    //Tutorial 8 for code below

    @Override
    public void onStart() {
        super.onStart();
        isFromStart = true;
        offset = 0;
        loadProfilePost();

    }

    private void loadProfilePost() {

        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Map<String, String> params = new HashMap<String, String>();

        params.put("uid", uid);
        params.put("limit", limit + "");
        params.put("offset", offset + "");
        params.put("current_state", current_state);

        Call<List<PostModel>> postModelCall = userInterface.getProfilePosts(params);
        postModelCall.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                newsfeedProgressBar.setVisibility(View.GONE);
                if(response.body()!=null){
                    postModels.addAll(response.body());
                    if(isFromStart){
                        newsfeed.setAdapter(postAdapter);
                    }else{
                        postAdapter.notifyItemRangeInserted(postModels.size(),response.body().size());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                newsfeedProgressBar.setVisibility(View.GONE);
                Toast.makeText(context,"Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        postModels.clear();
        postAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
