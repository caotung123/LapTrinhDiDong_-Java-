package com.example.app_sothuchi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Model.MyDataBase;

public class ChiTietMotChiTieuFragment extends Fragment {
    TextView txtTenCT;
    TextView txtSoTien;
    TextView txtLoai;
    TextView txtTenLoai;
    TextView txtTgian;
    TextView txtTenVi;


    public ChiTietMotChiTieuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi_tiet_mot_chi_tieu, container, false);
        txtTenCT = view.findViewById(R.id.chiTietMotChiTieu_txtTenCT);
        txtSoTien = view.findViewById(R.id.chiTietMotChiTieu_txtSoTien);
        txtLoai = view.findViewById(R.id.chiTietMotChiTieu_txtLoai);
        txtTenLoai = view.findViewById(R.id.chiTietMotChiTieu_txtTenLoai);
        txtTgian = view.findViewById(R.id.chiTietMotChiTieu_txtThoiGian);
        txtTenVi = view.findViewById(R.id.chiTietMotChiTieu_txtTenVi);

        MyDataBase.ChiTieu chiTieu = ChiTieu_Adapter.chiTieu_Click;
        txtTenCT.setText(chiTieu.getTenCT());
        txtSoTien.setText(chiTieu.getSoTien() + "");
        txtLoai.setText(chiTieu.getLoaiKC());
        txtTenLoai.setText(chiTieu.getLoaiKC_PhanCap());
        txtTgian.setText(chiTieu.getTgian());
        txtTenVi.setText(chiTieu.getTenVi());

        return view;
    }
}