package com.eakcay.watchit.view.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eakcay.watchit.R;
import com.eakcay.watchit.databinding.FragmentSearchBinding;


public class SearchFragment extends Fragment {

private FragmentSearchBinding searchBinding;
    private SearchView searchView;
    private RecyclerView searchRv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        return view;
    }
}