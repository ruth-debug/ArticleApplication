package com.ruth.articleapplication.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ruth.articleapplication.R;
import com.ruth.articleapplication.databinding.FragmentDetailsBinding;


public class DetailsFragment extends Fragment {


    private DetailsViewModel mViewModel;
    private FragmentDetailsBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);
        mBinding.setLifecycleOwner(getActivity());
        mBinding.setModel(DetailsFragmentArgs.fromBundle(getArguments()).getArticle());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}