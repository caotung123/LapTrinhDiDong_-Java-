package com.example.app_sothuchi;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
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

import Model.MyApplication;
import Model.MyDataBase;


public class ChiTieuFragment extends Fragment {
    AutoCompleteTextView dropdownTenVi;
    AutoCompleteTextView dropdownLoai;
    AutoCompleteTextView dropdownTenLoai;
    EditText txtTenCT;
    EditText txtSoTien;
    EditText txtThoiGian;
    Button btnThem;
    RecyclerView lsChiTieu;
    ArrayList<MyDataBase.ChiTieu> data;
    ChiTieu_Adapter adapter;

    String txtTenVi;
    String txtLoai;
    String txtTenLoai;

    int _hour, _minute;

    public ChiTieuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi_tieu, container, false);
        dropdownTenVi = view.findViewById(R.id.chiTieu_dropdownTenVi);
        dropdownLoai = view.findViewById(R.id.chiTieu_dropdownLoai);
        dropdownTenLoai = view.findViewById(R.id.chiTieu_dropdownTenLoai);
        txtTenCT = view.findViewById(R.id.chiTieu_txtTenCT);
        txtSoTien = view.findViewById(R.id.chiTieu_txtSoTien);
        txtThoiGian = view.findViewById(R.id.chiTieu_txtThoiGian);
        btnThem = view.findViewById(R.id.chiTieu_btnThem);
        lsChiTieu = view.findViewById(R.id.chiTieu_ListChiTieu);

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
        lsChiTieu.setAdapter(adapter);
        lsChiTieu.setLayoutManager(new LinearLayoutManager(getContext()));

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
        ArrayList<MyDataBase.HanMucChiTieu> lsLoaiDT = Login.myDataBase.selectALLHanMucChiTieu();
        String duLieu = "";
        for (MyDataBase.HanMucChiTieu x : lsLoaiDT)
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
        dropdownLoai.setAdapter(adapter);
    }

    public void loadDuLieu_DropdownTenLoai()
    {
        ArrayList<MyDataBase.HanMucChiTieu> lsLoaiKC = Login.myDataBase.searchHanMucChiTieu_LoaiKhoanChi(txtLoai);
        String duLieu = "";
        for (MyDataBase.HanMucChiTieu x : lsLoaiKC)
        {
            duLieu += x.getLoaiKC_PhanCap() + ";";
        }

        String []values = duLieu.split(";");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,values);
        dropdownTenLoai.setAdapter(adapter);
    }

    public void doDSVaoAdapter()
    {
        data = Login.myDataBase.selectALLChiTieu();
        adapter = new ChiTieu_Adapter(data,getContext());
    }

    public int kiemTraRong()
    {
        if(txtTenCT.getText().length() == 0 && txtSoTien.getText().length() == 0)
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

            try
            {
                Login.myDataBase.insertChiTieu(new MyDataBase.ChiTieu(1,txtTenCT.getText().toString(),Float.parseFloat(txtSoTien.getText().toString()),tgian,txtLoai,txtTenLoai,sdt,txtTenVi));
                float soDuViTienHienTai = Login.myDataBase.searchViTien(txtTenVi).getSoDu();
                float soDuViTienMoi = soDuViTienHienTai - Float.parseFloat(txtSoTien.getText().toString());
                Login.myDataBase.updateViTien(new MyDataBase.ViTien(txtTenVi,soDuViTienMoi,sdt));

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ChiTieuFragment()).commit();
                Toast.makeText(getContext(),"Thêm chi tiêu thành công!",Toast.LENGTH_LONG).show();
            }
            catch (Exception ex)
            {
                Toast.makeText(getContext(),"Thêm chi tiêu thất bại!",Toast.LENGTH_LONG).show();
            }

            //Nếu chi tiêu vượt 85% hạn mức thì cảnh báo người dùng tối ưu hóa lại chi tiêu
            int month = Integer.parseInt(tgian.split(" ")[0].split("/")[1]);
            int year = Integer.parseInt(tgian.split(" ")[0].split("/")[2]);

            float tongChiTieuTrenHanMucDaSuDung = Login.myDataBase.tinhTongChiTieuTrenHanMuc(sdt,txtLoai,txtTenLoai,month,year);
            MyDataBase.HanMucChiTieu hanMuc = Login.myDataBase.searchHanMucChiTieu_TheoThang(txtLoai,txtTenLoai,month,year);
            if(hanMuc!=null)
            {
                float hanMucChiTieu = hanMuc.getHanMuc();
                float hanMucThongBao = hanMucChiTieu * 85 / 100;
                if(tongChiTieuTrenHanMucDaSuDung >= hanMucThongBao)
                {
                    canhBaoChiTieu();
                }
            }

        }
    }

    public int getNotificationID()
    {
        return (int) new Date().getTime();
    }

    public void canhBaoChiTieu()
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(getContext(), MyApplication.CHANNEL_ID)
                .setContentTitle("Cảnh báo hạn mức chi tiêu!")
                .setContentText("Chi tiêu sắp vượt hạn mức, bạn cần tối ưu hóa lại chi tiêu")
                .setSmallIcon(R.drawable.icon_warning)
                .build();

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getContext().NOTIFICATION_SERVICE);
        if(notificationManager != null)
        {
            notificationManager.notify(getNotificationID(), notification);
        }
    }



}