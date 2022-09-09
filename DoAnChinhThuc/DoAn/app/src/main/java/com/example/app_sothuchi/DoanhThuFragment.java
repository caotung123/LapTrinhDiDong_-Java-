package com.example.app_sothuchi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Model.MyDataBase;


public class DoanhThuFragment extends Fragment {
    AutoCompleteTextView dropdownTenVi;
    AutoCompleteTextView dropdownLoai;
    AutoCompleteTextView dropdownTenLoai;
    EditText txtTenDT;
    EditText txtSoTien;
    EditText txtThoiGian;
    Button btnThem;
    RecyclerView lsDoanhThu;
    ArrayList<MyDataBase.DoanhThu> data;
    DoanhThu_Adapter adapter;

    String txtTenVi;
    String txtLoai;
    String txtTenLoai;

    int _hour, _minute;

    public DoanhThuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        dropdownTenVi = view.findViewById(R.id.doanhThu_dropdownTenVi);
        dropdownLoai = view.findViewById(R.id.doanhThu_dropdownLoai);
        dropdownTenLoai = view.findViewById(R.id.doanhThu_dropdownTenLoai);
        txtTenDT = view.findViewById(R.id.doanhThu_txtTenDT);
        txtSoTien = view.findViewById(R.id.doanhThu_txtSoTien);
        txtThoiGian = view.findViewById(R.id.doanhThu_txtThoiGian);
        btnThem = view.findViewById(R.id.doanhThu_btnThem);
        lsDoanhThu = view.findViewById(R.id.doanhThu_ListDoanhThu);

        loadDuLieu_EditTextDefaultThoiGian();
        txtThoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChonThoiGian();
            }
        });


        loadDuLieu_DropdownTenVi();
        loadDuLieu_DropdownLoai();

        dropdownTenVi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                txtTenVi = adapterView.getItemAtPosition(i).toString();
            }
        });

        dropdownLoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                txtLoai = adapterView.getItemAtPosition(i).toString();
                loadDuLieu_DropdownTenLoai();
            }
        });

        dropdownTenLoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                txtTenLoai = adapterView.getItemAtPosition(i).toString();
            }
        });

        doDSVaoAdapter();
        lsDoanhThu.setAdapter(adapter);
        lsDoanhThu.setLayoutManager(new LinearLayoutManager(getContext()));

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThem();
            }
        });

        return view;
    }

    public void loadDuLieu_DropdownTenVi()
    {
        ArrayList<MyDataBase.ViTien> lsViTien = Login.myDataBase.selectALLViTien();
        String duLieu = "";
        for (MyDataBase.ViTien x : lsViTien)
        {
            duLieu += x.getTenVi() + ";";
        }

        String []values = duLieu.split(";");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,values);
        dropdownTenVi.setAdapter(adapter);
    }

    public void loadDuLieu_DropdownLoai()
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
        dropdownLoai.setAdapter(adapter);
    }

    public void loadDuLieu_DropdownTenLoai()
    {
        ArrayList<MyDataBase.Loai_DoanhThu> lsLoaiDT = Login.myDataBase.searchLoaiDoanhThu_TenLoaiPhanCap(txtLoai);
        String duLieu = "";
        for (MyDataBase.Loai_DoanhThu x : lsLoaiDT)
        {
            duLieu += x.getTenLoai_PhanCap() + ";";
        }

        String []values = duLieu.split(";");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,values);
        dropdownTenLoai.setAdapter(adapter);
    }

    public void doDSVaoAdapter()
    {
        data = Login.myDataBase.selectALLDoanhThu();
        adapter = new DoanhThu_Adapter(data,getContext());

        //Toast.makeText(getContext(), data.size() + "", Toast.LENGTH_LONG).show();
    }

    public int kiemTraRong()
    {
        if(txtTenDT.getText().length() == 0 && txtSoTien.getText().length() == 0)
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
        Calendar calendar = Calendar.getInstance();
        int _day = calendar.get(Calendar.DAY_OF_MONTH);
        int _month = calendar.get(Calendar.MONTH);
        int _year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month += 1;
                String date = day + "/" + month + "/" + year + " ";
                txtThoiGian.setText(date);

                xuLyChonGio_Phut();
            }
        },_year,_month,_day);

        datePickerDialog.show();
    }

    public void xuLyChonGio_Phut()
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                _hour = hour;
                _minute = minute;
                String tgian = txtThoiGian.getText().toString() + String.format(Locale.getDefault(), "%02d:%02d:%02d",_hour,_minute,0);
                txtThoiGian.setText(tgian);
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),onTimeSetListener,_hour,_minute,true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
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

            String tgian = txtThoiGian.getText().toString();

            try {
                Login.myDataBase.insertDoanhThu(new MyDataBase.DoanhThu(1,txtTenDT.getText().toString(),Float.parseFloat(txtSoTien.getText().toString()),txtLoai,txtTenLoai,tgian,sdt,txtTenVi));
                float soDuViTienHienTai = Login.myDataBase.searchViTien(txtTenVi).getSoDu();
                float soDuViTienMoi = soDuViTienHienTai + Float.parseFloat(txtSoTien.getText().toString());
                Login.myDataBase.updateViTien(new MyDataBase.ViTien(txtTenVi,soDuViTienMoi,sdt));

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DoanhThuFragment()).commit();
                Toast.makeText(getContext(),"Thêm doanh thu thành công!",Toast.LENGTH_LONG).show();
            }
            catch (Exception ex)
            {
                Toast.makeText(getContext(),"Thêm doanh thu thất bại!",Toast.LENGTH_LONG).show();
            }

        }
    }

}