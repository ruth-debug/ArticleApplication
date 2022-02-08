package com.ruth.articleapplication.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruth.articleapplication.databinding.ArticleItemBinding;
import com.ruth.articleapplication.model.response.Article;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    List<Article> Articles;
    private final PublishSubject<Article> onClickSubject = PublishSubject.create();

    public ArticleAdapter(List<Article> Articles) {
        this.Articles = Articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArticleItemBinding layout = ArticleItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.layout.setModel(Articles.get(position));
        holder.layout.getRoot().setOnClickListener(v -> onClickSubject.onNext(Articles.get(position)));
    }

    @Override
    public int getItemCount() {
        return Articles.size();
    }

    public void reset(List<Article> array) {
        Articles.clear();
        Articles.addAll(array);
        notifyDataSetChanged();


    }
    public Observable<Article> getPositionClicks(){
        return onClickSubject;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ArticleItemBinding layout;

        public ViewHolder(@NonNull ArticleItemBinding layout) {
            super(layout.getRoot());
            this.layout = layout;
        }
    }
}
