package com.example.searchview.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.searchview.R;
import com.example.searchview.adapter.FollowerListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FollowersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowersFragment extends Fragment {

    public static final String FOLLOWERS = "followers";

    private FollowerListAdapter adapter;
    private RecyclerView recyclerView;
    private List<Follower> followers;

    public FollowersFragment() {
        // Required empty public constructor
    }


    public static FollowersFragment getInstance(List<Follower> followers) {
        FollowersFragment fragment = new FollowersFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(FOLLOWERS, (ArrayList<Follower>) followers);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followers, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.followers);
        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_follower_list);
        if (getArguments() != null) {
            followers = getArguments().getParcelableArrayList(FOLLOWERS);
        }
        onFollowerList();
        return view;
    }

    private void onFollowerList() {
        adapter = new FollowerListAdapter(getActivity(), followers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        initSearch(searchView);
  //     customizeSearchView(searchView);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void customizeSearchView(SearchView searchView) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.LTGRAY);
        searchAutoComplete.setTextColor(Color.GRAY);

        View searchPlate = searchView.findViewById(androidx.recyclerview.widget.RecyclerView.R.id.search_plate);
        searchPlate.setBackgroundColor(getResources().getColor(R.color.colorWhite));

        ImageView searchCloseIcon = (ImageView)searchView.findViewById(androidx.recyclerview.widget.RecyclerView.R.id.search_close_btn);
        searchCloseIcon.setColorFilter(getResources().getColor(R.color.colorSecondaryText));

    }

    private void initSearch(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}