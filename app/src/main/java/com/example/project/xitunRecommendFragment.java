package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class xitunRecommendFragment extends Fragment {

    public xitunRecommendFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        Button btnoneday,btnhalf;
        ScrollView backimage =view.findViewById(R.id.scrollView2);
        backimage.setBackgroundResource(R.drawable.xitun_pic2);
        btnoneday= view.findViewById(R.id.XRe1);
        btnoneday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_xitunRecommendFragment_to_oneday_xitunFragment);
            }
        });
        btnhalf= view.findViewById(R.id.XRe2);
        btnhalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_xitunRecommendFragment_to_halfday_xitunFragment);
            }
        });
        return view;
    }
}
