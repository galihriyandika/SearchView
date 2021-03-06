package com.example.searchview.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.searchview.R;
import com.example.searchview.util.AppProgressDialog;
import com.example.searchview.util.Util;
import com.google.android.material.textfield.TextInputEditText;

public class EnterDataFragment extends Fragment {

    public static final String TAG = EnterDataFragment.class.getSimpleName();

    private Disposable disposable;

    private TextInputEditText tietUserName;
    private Button btnSearchFollowers;

    public EnterDataFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_data, container, false);
        tietUserName = (TextInputEditText) view.findViewById(R.id.tiet_username);
        btnSearchFollowers = (Button) view.findViewById(R.id.btn_search_followers);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
        btnSearchFollowers.setOnClickListener(v -> getFollowers(tietUserName.getText().toString()));
    }

    private void getFollowers(String userName) {
        Util.hideKeyboard(getActivity());
        disposable = GitHubManager.getInstance(getActivity())
                .getFollowers(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> AppProgressDialog.show(getActivity()))
                .doOnTerminate(AppProgressDialog::dismiss)
                .subscribe(this::initFollowersList,
                        e -> {
                            initFailure();
                            Log.e(TAG, "Get followers error", e);
                        });
    }
    // Show follower list
    private void initFollowersList(List<Follower> followers) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, FollowersFragment.getInstance(followers))
                .addToBackStack(TAG)
                .commit();
    }
    // Show failure message
    private void initFailure() {
        Toast.makeText(getContext(), "Error getting Follower list", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}