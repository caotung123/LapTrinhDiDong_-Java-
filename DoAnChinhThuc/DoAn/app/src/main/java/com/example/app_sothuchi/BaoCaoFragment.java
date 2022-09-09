package com.example.app_sothuchi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class BaoCaoFragment extends Fragment {
    RelativeLayout itemPhanTichChi;
    RelativeLayout itemPhanTichThu;

    public BaoCaoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bao_cao, container, false);
        itemPhanTichChi = view.findViewById(R.id.baocao_itemPhanTichChi);
        itemPhanTichThu = view.findViewById(R.id.baocao_itemPhanTichThu);

        itemPhanTichChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PhanTichChiFragment()).commit();
            }
        });

        itemPhanTichThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PhanTichThuFragment()).commit();
            }
        });

        return view;
    }
}