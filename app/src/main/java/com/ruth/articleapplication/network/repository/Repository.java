package com.ruth.articleapplication.network.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ruth.articleapplication.model.request.TopHeadlinesRequest;
import com.ruth.articleapplication.model.response.Article;
import com.ruth.articleapplication.model.response.ArticleResponse;
import com.ruth.articleapplication.model.response.Resource;
import com.ruth.articleapplication.network.retrofit.ApiService;
import com.ruth.articleapplication.network.retrofit.NetworkClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class Repository {

    private volatile static Repository repository = null;
    private ApiService networkApi;


    private Repository(Application application) {
        networkApi = NetworkClient.getInstance(application);

    }

    public static Repository getInstance(Application application) {
        if (repository == null) {
            synchronized (Repository.class) {
                repository = new Repository(application);
            }
        }
        return repository;
    }

    /***
     *
     * @return List of articles from server
     */
    public LiveData<Resource<List<Article>>> fetchArticles(boolean forceRefresh) {
        MutableLiveData<Resource<List<Article>>> newsData = new MutableLiveData();

        if (forceRefresh){
            fetchServer(newsData);
        }else {
            fetchCache(newsData);

        }
        return newsData;
    }


    /***
     *
     * @return List of articles from cache
     */
    public LiveData<Resource<List<Article>>> fetchCache( MutableLiveData<Resource<List<Article>>> newsData) {
        networkApi.getTopHeadlines("il").enqueue(new retrofit2.Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.isSuccessful()) {
                    newsData.setValue(Resource.success(response.body().getArticles()));
                    fetchServer(newsData);
                }
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                newsData.setValue(Resource.error(t.getMessage() ,null));
            }
        });
        return newsData;
    }

    private LiveData<Resource<List<Article>>> fetchServer(MutableLiveData<Resource<List<Article>>> newsData) {
        networkApi.getTopHeadlinesRefresh("il").enqueue(new retrofit2.Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.isSuccessful()) {
                    newsData.setValue(Resource.success(response.body().getArticles()));
                }
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                newsData.setValue(Resource.error(t.getMessage() ,null));
            }
        });
        return newsData;

    }


}
