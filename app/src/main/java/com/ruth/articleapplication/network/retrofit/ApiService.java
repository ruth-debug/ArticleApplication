package com.ruth.articleapplication.network.retrofit;


import com.ruth.articleapplication.model.response.ArticleResponse;
import com.ruth.articleapplication.model.response.Resource;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static com.ruth.articleapplication.utils.Constants.CACHE_CONTROL_HEADER;
import static com.ruth.articleapplication.utils.Constants.CACHE_CONTROL_NO_CACHE;

public interface ApiService {

    @GET("/v2/top-headlines")
    Call<ArticleResponse> getTopHeadlines(@Query("country")  String country);

    @GET("/v2/top-headlines")
    @Headers("Cache-Control: no-cache")
    Call<ArticleResponse> getTopHeadlinesRefresh(@Query("country")  String country);

}