package com.ruth.articleapplication.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.ruth.articleapplication.R;
import com.ruth.articleapplication.databinding.MainFragmentBinding;
import com.ruth.articleapplication.model.response.Article;
import com.ruth.articleapplication.model.response.Resource;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private MainFragmentBinding mBinding;
    private ArticleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mBinding.setLifecycleOwner(getActivity());
        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.swipeLayout.setRefreshing(true);

        adapter = new ArticleAdapter(new ArrayList<>());
        adapter.getPositionClicks().subscribe(response -> {
            navigate(response);
        });
        mBinding.recyclerView.setAdapter(adapter);

        mViewModel.fetchArticles(false);
        mViewModel.getAllArticles().observe(getViewLifecycleOwner(), result -> {
            setData(result);
        });

        mBinding.swipeLayout.setOnRefreshListener(() -> {
            mViewModel.fetchArticles(true);
            mViewModel.getAllArticles().observe(getViewLifecycleOwner(), result -> {
                setData(result);
            });
        });
    }

    private void navigate(Article response) {
        MainFragmentDirections.ActionMainFragmentToDetailsFragment directions =
                MainFragmentDirections.actionMainFragmentToDetailsFragment(response);
        NavHostFragment.findNavController(this).navigate(directions);
    }

    private void setData(Resource<List<Article>> result) {
        if (result.status == Resource.Status.SUCCESS) {
            adapter.reset(result.data);
        } else {
            Toast.makeText(getContext(), getString(R.string.network_error), Toast.LENGTH_LONG).show();
        }
        mBinding.swipeLayout.setRefreshing(false);
    }

}