package com.example.app_sothuchi;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Model.MyDataBase;

public class HanMucChiTieuFragment extends Fragment {
    public static AutoCompleteTextView txtLoaiKC;
    public static AutoCompleteTextView txtKhoanChi;
    public static EditText txtHanMuc;
    public static EditText txtThoiGian;
    Button btnThem;
    RecyclerView lsHanMucCT;

    ArrayList<MyDataBase.HanMucChiTieu> data;
    HanMucChiTieu_Adapter adapter;

    public HanMucChiTieuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_han_muc_chi_tieu, container, false);
        txtLoaiKC = view.findViewById(R.id.hmct_txtLoaiKC);
        txtKhoanChi = view.findViewById(R.id.hmct_txtKhoanChi);
        txtHanMuc = view.findViewById(R.id.hmct_txtHanMuc);
        txtThoiGian = view.findViewById(R.id.hmct_txtThoiGian);
        btnThem = view.findViewById(R.id.hmct_btnThem);
        lsHanMucCT = view.findViewById(R.id.hmct_ListHanMucChiTieu);

        loadDuLieu_txtLoaiKC();
        txtLoaiKC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String loaiKC = adapterView.getItemAtPosition(i).toString();
                loadDuLieu_txtKhoanChi(loaiKC);
            }
        });

        loadDuLieu_EditTextDefaultThoiGian();
        txtThoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChonThoiGian();
            }
        });

        doDSVaoAdapter();
        lsHanMucCT.setAdapter(adapter);
        lsHanMucCT.setLayoutManager(new LinearLayoutManager(getContext()));

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThem();
            }
        });



        return view;
    }

    public void loadDuLieu_txtLoaiKC()
    {
        ArrayList<MyDataBase.HanMucChiTieu> lsLoaiKC = Login.myDataBase.selectALLHanMucChiTieu();
        String duLieu = "";
        for (MyDataBase.HanMucChiTieu x : lsLoaiKC)
        {
            duLieu += x.getLoaiKC() + ";";
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
        txtLoaiKC.setAdapter(adapter);
    }

    public void loadDuLieu_txtKhoanChi(String loaiKC)
    {
        ArrayList<MyDataBase.HanMucChiTieu> lsKhoanChi = Login.myDataBase.searchHanMucChiTieu_LoaiKhoanChi(loaiKC);
        String duLieu = "";
        for (MyDataBase.HanMucChiTieu x : lsKhoanChi)
        {
            duLieu += x.getLoaiKC_PhanCap() + ";";
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
        txtKhoanChi.setAdapter(adapter);
    }

    public void doDSVaoAdapter()
    {
        data = Login.myDataBase.selectALLHanMucChiTieu();
        adapter = new HanMucChiTieu_Adapter(data,getContext());
    }

    public int kiemTraRong()
    {
        if(txtLoaiKC.getText().length() == 0 || txtHanMuc.getText().length() == 0)
            return 0;
        return 1;
    }

    public void loadDuLieu_EditTextDefaultThoiGian()
    {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        txtThoiGian.setText(df.format(currentTime));
    }

    public void xuLyChonThoiGian()
    {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String time = df.format(currentTime);

        Calendar calendar = Calendar.getInstance();
        int _day = calendar.get(Calendar.DAY_OF_MONTH);
        int _month = calendar.get(Calendar.MONTH);
        int _year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month += 1;
                String date = day + "/" + month + "/" + year + " " + time;
                txtThoiGian.setText(date);
            }
        },_year,_month,_day);

        datePickerDialog.show();
    }

    public void xuLyThem()
    {
        if(kiemTraRong() == 0)
        {
            Toast.makeText(getContext(),"Không được bỏ trống!",Toast.LENGTH_LONG).show();
        }
        else
        {
            String tgian = txtThoiGian.getText().toString();

            boolean kq = Login.myDataBase.insertHanMucChiTieu(new MyDataBase.HanMucChiTieu(txtLoaiKC.getText().toString(),txtKhoanChi.getText().toString(),Float.parseFloat(txtHanMuc.getText().toString()),tgian));
            if(kq == true)
            {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HanMucChiTieuFragment()).commit();
                Toast.makeText(getContext(),"Thêm hạn mức thành công!",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getContext(),"Thêm hạn mức thất bại!",Toast.LENGTH_LONG).show();
            }
        }
    }

}