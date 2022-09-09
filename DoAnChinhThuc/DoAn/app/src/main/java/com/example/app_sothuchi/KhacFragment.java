package com.example.app_sothuchi;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class KhacFragment extends Fragment {
    RelativeLayout relativeDangXuat;

    public KhacFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khac, container, false);
        relativeDangXuat = view.findViewById(R.id.khac_itemDangXuat);

        relativeDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDangXuat();
            }
        });

        return view;
    }

    public void xuLyDangXuat()
    {
        Login.clearData();

        Intent intent = new Intent(getContext(),Login.class);
        startActivity(intent);
        Toast.makeText(getContext(),"Đăng xuất thành công!",Toast.LENGTH_SHORT).show();
    }
}