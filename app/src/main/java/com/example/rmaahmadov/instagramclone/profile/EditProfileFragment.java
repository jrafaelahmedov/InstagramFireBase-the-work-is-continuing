package com.example.rmaahmadov.instagramclone.profile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rmaahmadov.instagramclone.R;
import com.example.rmaahmadov.instagramclone.utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

public class EditProfileFragment extends Fragment {
    private static final String TAG = "EditProfileFragment";
    private ImageView profileImage;
    ImageView backarrow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile,container,false);
        profileImage=view.findViewById(R.id.profile_photo);
        backarrow=view.findViewById(R.id.backArrow);
        setProfileImage();
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        
        
        return view;
    }
    
 
    
    
    private void setProfileImage(){
        String imageUrl= "drawable://" + R.drawable.profile;
        UniversalImageLoader.setImage(imageUrl,profileImage,null,"");
    }
}
