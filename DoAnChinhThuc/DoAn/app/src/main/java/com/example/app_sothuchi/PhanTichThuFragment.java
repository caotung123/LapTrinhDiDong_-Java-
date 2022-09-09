package com.example.app_sothuchi;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Model.MyDataBase;


public class PhanTichThuFragment extends Fragment {
    AutoCompleteTextView dropdown;
    BarChart barChart;

    public PhanTichThuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phan_tich_thu, container, false);
        dropdown = view.findViewById(R.id.phanTichThu_dropdown);
        barChart = view.findViewById(R.id.phanTichThu_barchart);

        loadDuLieuLenDropDown();
        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String giaTri = adapterView.getItemAtPosition(i).toString();
                xulyOnItemClick(giaTri);
            }
        });

        return view;
    }


    public void loadDuLieuLenDropDown()
    {
        String []values = getResources().getStringArray(R.array.dropdown_menu02);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,values);
        dropdown.setAdapter(adapter);
    }

    public void xulyOnItemClick(String giaTri)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        String []values = date.split("/");

        int month = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[2]);

        switch (giaTri)
        {
            case "Tháng này":
            {
                veBieuDo(month,year,0);
                break;
            }
            case "Năm này":
            {
                veBieuDo(month,year,1);
                break;
            }
        }
    }

    public void veBieuDo(int month, int year, int k)
    {
        ArrayList<BarEntry> visitors = new ArrayList<>();
        ArrayList<MyDataBase.DoanhThu> lsDoanhThu;
        MyDataBase.TaiKhoan taiKhoan = (MyDataBase.TaiKhoan) getActivity().getIntent().getExtras().get("TaiKhoan");
        int thoiGianHienTai = 0;

        switch (k)
        {
            case 0:
            {
                int soNgayTrongThang = demSoNgayTrongThang(month, year);
                for(int i = 1 ; i <= soNgayTrongThang ; i++)
                {
                    visitors.add(new BarEntry(i,0));
                }

                lsDoanhThu = Login.myDataBase.searchDoanhThu_TheoThang(taiKhoan.getSdt(),month,year);
                for(MyDataBase.DoanhThu x : lsDoanhThu)
                {
                    int ngay = Integer.parseInt(x.getTgian().split(" ")[0].split("/")[0]);
                    float soTien = x.getSoTien();

                    visitors.add(new BarEntry(ngay,soTien));
                    thoiGianHienTai = ngay;
                }

                break;
            }
            case 1:
            {
                for(int i = 1 ; i <= 12 ; i++)
                {
                    visitors.add(new BarEntry(i,0));
                }

                lsDoanhThu = Login.myDataBase.searchDoanhThu_TheoNam(taiKhoan.getSdt(),year);
                for(MyDataBase.DoanhThu x : lsDoanhThu)
                {
                    int thang = Integer.parseInt(x.getTgian().split(" ")[0].split("/")[1]);
                    float soTien = x.getSoTien();

                    visitors.add(new BarEntry(thang,soTien));
                    thoiGianHienTai = thang;
                }

                break;
            }
        }

        BarDataSet barDataSet = new BarDataSet(visitors,"Visitors");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(0f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar chart Example");
        barChart.animateY(thoiGianHienTai);

    }

    public boolean kiemTraNamNhuan(int year)
    {
        if(year % 4 == 0 && year % 100 != 0)
        {
            return true;
        }
        return false;
    }

    public int demSoNgayTrongThang(int month, int year)
    {
        if (month == 2)
        {
            boolean kt = kiemTraNamNhuan(year);
            if(kt == true)
            {
                return 29;
            }
            else
            {
                return 28;
            }

        }
        else if(month == 4 || month == 6 || month == 9 || month == 11)
        {
            return 30;
        }
        else
        {
            return 31;
        }
    }
}