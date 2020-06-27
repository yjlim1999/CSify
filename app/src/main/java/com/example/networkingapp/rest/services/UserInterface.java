package com.example.networkingapp.rest.services;

import com.example.networkingapp.activity.LoginActivity;
import com.example.networkingapp.model.User;
import com.example.networkingapp.model.FriendsModel;
import com.example.networkingapp.model.PostModel;
import com.example.networkingapp.activity.ProfileActivity;

import java.util.Map;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface UserInterface {

    @POST("login")
    Call<Integer> singin(@Body LoginActivity.UserInfo userInfo);

    @GET("loadownprofile")
    Call<User> loadownProfile(@QueryMap Map<String, String> params);

    //will be called inside
    @POST("poststatus")
    //will be called inside our uploadactivity, inside our uploadpost method
    Call<Integer> uploadStatus(@Body MultipartBody requestBody);

    @POST("uploadImage")
    Call<Integer> uploadImage(@Body MultipartBody requestBody);

    @GET("search")
    Call<List<User>> search(@QueryMap Map<String, String> params);

    @GET("loadotherprofile")
    Call<User> loadOtherProfile(@QueryMap Map<String, String> params);

    @POST("performaction")
    Call<Integer> performAction(@Body ProfileActivity.PerformAction performAction);

    @GET("loadfriends")
    Call<FriendsModel> loadFriendsData(@QueryMap Map<String, String> params);

    @GET("profiletimeline")
    Call<List<PostModel>> getProfilePosts(@QueryMap Map<String, String> params);

    @GET("gettimelinepost")
    Call<List<PostModel>> getTimeline(@QueryMap Map<String, String> params);
}
