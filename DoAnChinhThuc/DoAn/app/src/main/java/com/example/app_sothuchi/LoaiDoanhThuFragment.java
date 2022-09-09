package com.example.app_sothuchi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import Model.MyDataBase;


public class LoaiDoanhThuFragment extends Fragment {
    AutoCompleteTextView txtLoaiDT;
    AutoCompleteTextView txtTenDT;
    Button btnThem;
    RecyclerView lsLoaiDT;
    ArrayList<MyDataBase.Loai_DoanhThu> data;
    LoaiDoanhThu_Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_doanh_thu, container, false);
        txtLoaiDT = view.findViewById(R.id.loaiDoanhThu_txtLoaiDoanhThu);
        txtTenDT = view.findViewById(R.id.loaiDoanhThu_txtTenDoanhThu);
        btnThem = view.findViewById(R.id.loaiDoanhThu_btnThem);
        lsLoaiDT = view.findViewById(R.id.loaiDoanhThu_ListLoaiDoanhThu);

        loadDuLieu_txtLoaiDT();
        txtLoaiDT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String loaiDT = adapterView.getItemAtPosition(i).toString();
                loadDuLieu_txtTenDT(loaiDT);
            }
        });

        doDSVaoAdapter();
        lsLoaiDT.setAdapter(adapter);
        lsLoaiDT.setLayoutManager(new LinearLayoutManager(getContext()));

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThem();
            }
        });
        
        return view;
    }


    public void loadDuLieu_txtLoaiDT()
    {
        ArrayList<MyDataBase.Loai_DoanhThu> lsLoaiDT = Login.myDataBase.selectALLLoaiDoanhThu();
        String duLieu = "";
        for (MyDataBase.Loai_DoanhThu x : lsLoaiDT)
        {
            duLieu += x.getTenLoai() + ";";
        }

        String []values = duLieu.split(";");
        int n = values.length;
        for(int i = 0 ; i < n - 1 ; i++)
        {
            for(int j = i + 1 ; j < n ; j++)
            {
                if(values[i].equals(values[j]))
                {
                    for (int k = i; k < n - 1 ; k++)
                    {
                        values[k] = values[k+1];
                    }
                    n--;
                }
            }
        }
        String []giaTri = new String[n];
        for(int i = 0 ; i < n ; i++)
        {
            giaTri[i] = values[i];
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,giaTri);
        txtLoaiDT.setAdapter(adapter);
    }

    public void loadDuLieu_txtTenDT(String loaiDT)
    {
        ArrayList<MyDataBase.Loai_DoanhThu> lsTenDT = Login.myDataBase.searchLoaiDoanhThu_TenLoaiPhanCap(loaiDT);
        String duLieu = "";
        for (MyDataBase.Loai_DoanhThu x : lsTenDT)
        {
            duLieu += x.getTenLoai_PhanCap() + ";";
        }

        String []values = duLieu.split(";");
        int n = values.length;
        for(int i = 0 ; i < n - 1 ; i++)
        {
            for(int j = i + 1 ; j < n ; j++)
            {
                if(values[i].equals(values[j]))
                {
                    for (int k = i; k < n - 1 ; k++)
                    {
                        values[k] = values[k+1];
                    }
                    n--;
                }
            }
        }
        String []giaTri = new String[n];
        for(int i = 0 ; i < n ; i++)
        {
            giaTri[i] = values[i];
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,giaTri);
        txtTenDT.setAdapter(adapter);
    }

    public void doDSVaoAdapter()
    {
        data = Login.myDataBase.selectALLLoaiDoanhThu();
        adapter = new LoaiDoanhThu_Adapter(data,getContext());
    }

    public int kiemTraRong()
    {
        if(txtLoaiDT.getText().length() == 0)
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
            boolean kq = Login.myDataBase.insertLoaiDoanhThu(new MyDataBase.Loai_DoanhThu(txtLoaiDT.getText().toString(),txtTenDT.getText().toString()));
            if(kq == true)
            {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LoaiDoanhThuFragment()).commit();
                Toast.makeText(getContext(),"Thêm loại doanh thu thành công!",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getContext(),"Thêm loại doanh thu thất bại!",Toast.LENGTH_LONG).show();
            }
        }
    }
}