package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class oneday_xitunFragment extends Fragment {

    public oneday_xitunFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.onedaytour, container, false);
        WebView webview = view.findViewById(R.id.onedayroute);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        String url="https://www.google.com/maps/dir/?api=1&%22+%22origin=%22+%22逢甲炒餅條 Taiwan Pancake"+"&destination="+"KUN HOTEL"+"&travelmode=bicycling"+
                "&waypoints="+"國立自然科學博物館"+"%7C"+"樹兒早午餐（週二不定休，參考粉絲團）" + "%7c" + "國立臺灣美術館" + "%7c" + "好豆堂" + "%7c" + "秋紅谷景觀生態公園" +
                "%7c" + "中央公園" + "%7c" + "逢甲夜市";
        webview.loadUrl(url);
        return view;
    }
}
