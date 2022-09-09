package com.example.app_sothuchi;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class ThemFragment extends Fragment {
    RelativeLayout itemChiTieu;
    RelativeLayout itemDoanhThu;
    RelativeLayout itemLoaiDoanhThu;
    RelativeLayout itemHanMucChiTieu;
    RelativeLayout itemViTien;

    public ThemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them, container, false);
        itemChiTieu = view.findViewById(R.id.them_itemChiTieu);
        itemDoanhThu = view.findViewById(R.id.them_itemDoanhThu);
        itemLoaiDoanhThu = view.findViewById(R.id.them_itemLoaiDoanhThu);
        itemHanMucChiTieu = view.findViewById(R.id.them_itemHanMucChiTieu);
        itemViTien = view.findViewById(R.id.them_itemViTien);

        itemChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ChiTieuFragment()).commit();
            }
        });

        itemDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DoanhThuFragment()).commit();
            }
        });

        itemLoaiDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LoaiDoanhThuFragment()).commit();
            }
        });

        itemHanMucChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HanMucChiTieuFragment()).commit();
            }
        });

        itemViTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ViTienFragment()).commit();
            }
        });

        return view;
    }
}