package com.example.app_sothuchi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Model.MyDataBase;

public class ChiTietMotDoanhThuFragment extends Fragment {
    TextView txtTenDT;
    TextView txtSoTien;
    TextView txtLoai;
    TextView txtTenLoai;
    TextView txtTgian;
    TextView txtTenVi;

    public ChiTietMotDoanhThuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi_tiet_mot_doanh_thu, container, false);
        txtTenDT = view.findViewById(R.id.chiTietMotDoanhThu_txtTenDT);
        txtSoTien = view.findViewById(R.id.chiTietMotDoanhThu_txtSoTien);
        txtLoai = view.findViewById(R.id.chiTietMotDoanhThu_txtLoai);
        txtTenLoai = view.findViewById(R.id.chiTietMotDoanhThu_txtTenLoai);
        txtTgian = view.findViewById(R.id.chiTietMotDoanhThu_txtThoiGian);
        txtTenVi = view.findViewById(R.id.chiTietMotDoanhThu_txtTenVi);

        MyDataBase.DoanhThu doanhThu = DoanhThu_Adapter.doanhThu_Click;
        txtTenDT.setText(doanhThu.getTenDT());
        txtSoTien.setText(doanhThu.getSoTien() + "");
        txtLoai.setText(doanhThu.getTenLoai());
        txtTenLoai.setText(doanhThu.getTenLoai_PhanCap());
        txtTgian.setText(doanhThu.getTgian());
        txtTenVi.setText(doanhThu.getTenVi());

        return view;
    }
}