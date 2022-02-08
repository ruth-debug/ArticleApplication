package com.ruth.articleapplication.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.ruth.articleapplication.model.response.Article;
import com.ruth.articleapplication.model.response.Resource;
import com.ruth.articleapplication.network.repository.Repository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private Repository repository;
    public LiveData<Resource<List<Article>>> getAllArticles;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public void  fetchArticles(boolean forceRefresh) {
        getAllArticles = repository.fetchArticles(forceRefresh);
    }


    public LiveData<Resource<List<Article>>> getAllArticles() {
        return getAllArticles;
    }
}