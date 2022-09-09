package com.example.app_sothuchi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import Model.MyDataBase;

public class ViTienFragment extends Fragment {
    EditText txtTenVi;
    Button btnThem;
    Button btnSua;
    RecyclerView lsViTien;
    ArrayList<MyDataBase.ViTien> data;
    ViTien_Adapter adapter;

    public ViTienFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vi_tien, container, false);
        txtTenVi = view.findViewById(R.id.viTien_txtTenVi);
        btnThem = view.findViewById(R.id.viTien_btnThem);
        lsViTien = view.findViewById(R.id.viTien_ListViTien);

        doDSVaoAdapter();
        lsViTien.setAdapter(adapter);
        lsViTien.setLayoutManager(new LinearLayoutManager(getContext()));

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThem();
            }
        });


        return view;
    }

    public void doDSVaoAdapter()
    {
        data = Login.myDataBase.selectALLViTien();
        adapter = new ViTien_Adapter(data,getContext());
    }

    public int kiemTraRong()
    {
        if(txtTenVi.getText().length() == 0)
            return 0;
        return 1;
    }

    public void xuLyThem()
    {
        if(kiemTraRong() == 0)
        {
            Toast.makeText(getContext(),"Không được bỏ trống!",Toast.LENGTH_LONG).show();
        }
        else
        {
            MyDataBase.TaiKhoan taiKhoan = (MyDataBase.TaiKhoan) getActivity().getIntent().getExtras().get("TaiKhoan");
            String sdt = taiKhoan.getSdt();

            boolean kq = Login.myDataBase.insertViTien(new MyDataBase.ViTien(txtTenVi.getText().toString(),0,sdt));
            if(kq == true)
            {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ViTienFragment()).commit();
                Toast.makeText(getContext(),"Thêm ví thành công!",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getContext(),"Thêm ví thất bại!",Toast.LENGTH_LONG).show();
            }
        }
    }
}
