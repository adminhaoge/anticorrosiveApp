package com.example.imitationtaobao.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.imitationtaobao.R;
import com.example.imitationtaobao.activity.MainActivity;
import com.example.imitationtaobao.customview.CustomPersonal;

public class PersonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.person_fragment, container, false);
        ImageView iv = view.findViewById(R.id.img_head);
        ImageView bg_iv = view.findViewById(R.id.bg_vague);
        TextView tv = view.findViewById(R.id.text_name);
        CustomPersonal  cp = view.findViewById(R.id.setup);
        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cp.animate().scaleX(0.8f).scaleY(0.8f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                });
//                ObjectAnimator scaleX = ObjectAnimator.ofFloat(cp, "scaleX", 0, 1);
//                ObjectAnimator scaleY = ObjectAnimator.ofFloat(cp, "scaleY", 0, 1);
//                AnimatorSet as = new AnimatorSet();
//                as.play(scaleX).with(scaleY);
//                as.setDuration(1000);
//                as.start();
//                as.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        startActivity(new Intent(getActivity(),MainActivity.class));
//                    }
//                });
            }
        });
        return view;
    }

}