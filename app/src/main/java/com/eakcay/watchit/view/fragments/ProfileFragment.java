package com.eakcay.watchit.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.eakcay.watchit.R;
import com.eakcay.watchit.auth.FirebaseAuthHelper;


public class ProfileFragment extends Fragment {

    private FirebaseAuthHelper firebaseAuthHelper;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuthHelper = new FirebaseAuthHelper(getContext());

        TextView userName = view.findViewById(R.id.user_name_text);
        TextView email = view.findViewById(R.id.email_text);
        TextView movieCount = view.findViewById(R.id.count_movie_text);
        TextView totalMovieTime = view.findViewById(R.id.total_movie_time_text);
        ImageView profileImg = view.findViewById(R.id.profile_img);
        Button signOut = view.findViewById(R.id.sign_out_btn2);
        Button settings = view.findViewById(R.id.settings_btn);
        Button watchedList = view.findViewById(R.id.watched_list_btn);
        Button favoriList = view.findViewById(R.id.favori_list_btn);


        //sign out
        signOut.setOnClickListener(view1 -> {
           firebaseAuthHelper.signOut();
        });

        userName.setText(firebaseAuthHelper.getUserName());
        email.setText(firebaseAuthHelper.getUserEmail());



        return view;
    }

}