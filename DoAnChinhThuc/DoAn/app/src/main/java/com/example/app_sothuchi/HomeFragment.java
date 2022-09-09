package com.example.app_sothuchi;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Model.MyDataBase;

public class HomeFragment extends Fragment {
    MyDataBase.TaiKhoan taiKhoan;
    TextView txtName;
    TextView txtSoDu;
    ImageView imageEye;
    AutoCompleteTextView dropdown;
    TextView txtDoanhThu;
    TextView txtChiTieu;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        txtName = view.findViewById(R.id.home_txtName);
        txtSoDu = view.findViewById(R.id.home_txtSoDu);
        imageEye = view.findViewById(R.id.home_imageEye);
        dropdown = view.findViewById(R.id.home_dropdown);
        txtDoanhThu = view.findViewById(R.id.home_txtDoanhThu);
        txtChiTieu = view.findViewById(R.id.home_txtChiTieu);

        taiKhoan = (MyDataBase.TaiKhoan) getActivity().getIntent().getExtras().get("TaiKhoan");

        txtName.setText("Chào " + taiKhoan.getHoTen() +"!");
        float soDu = tinhSoDu();
        txtSoDu.setText(String.format("%,d",(int)soDu));
        imageEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xemSoDu(soDu);
            }
        });

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

    public float tinhSoDu()
    {
        String sdt = taiKhoan.getSdt();

        return Login.myDataBase.tinhTongDoanhThu(sdt) - Login.myDataBase.tinhTongChiTieu(sdt);
    }

    public void xemSoDu(float soDu)
    {
        //int type is password: 129
        //int type is visible password: 145

        if(txtSoDu.getInputType()==129)
        {
            txtSoDu.setInputType(145);
            setMauSoDu(soDu);
        }
        else
        {
            txtSoDu.setInputType(129);
            txtSoDu.setTextColor(getResources().getColor(android.R.color.darker_gray));
        }
    }

    public void setMauSoDu(float soDu)
    {
        if(soDu>=0)
        {
            txtSoDu.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }
        else
        {
            txtSoDu.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    public void loadDuLieuLenDropDown()
    {
        String []values = getResources().getStringArray(R.array.dropdown_menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,values);
        dropdown.setAdapter(adapter);
    }

    public void xulyOnItemClick(String giaTri)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        String []values = date.split("/");

        int day = Integer.parseInt(values[0]);
        int month = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[2]);

        switch (giaTri)
        {
            case "Hôm nay":
            {
                baoCaoThuChi(day,month,year,0);
                break;
            }
            case "Tháng này":
            {
                baoCaoThuChi(day,month,year,1);
                break;
            }
            case "Năm này":
            {
                baoCaoThuChi(day,month,year,2);
                break;
            }
        }
    }

    public void baoCaoThuChi(int day, int month, int year, int khoangCach)
    {
        ArrayList<MyDataBase.DoanhThu> lsDoanhThu;
        ArrayList<MyDataBase.ChiTieu> lsChiTieu;
        float tongDoanhThu = 0;
        float tongChiTieu = 0;

        switch (khoangCach)
        {
            case 0: //Hôm nay
            {
                lsDoanhThu = Login.myDataBase.searchDoanhThu_TheoNgay(taiKhoan.getSdt(),day,month,year);
                for(MyDataBase.DoanhThu x : lsDoanhThu)
                {
                    tongDoanhThu += x.getSoTien();
                }

                lsChiTieu = Login.myDataBase.searchChiTieu_TheoNgay(taiKhoan.getSdt(),day,month,year);
                for(MyDataBase.ChiTieu x : lsChiTieu)
                {
                    tongChiTieu += x.getSoTien();
                }

                break;
            }
            case 1: //Tháng này
            {
                lsDoanhThu = Login.myDataBase.searchDoanhThu_TheoThang(taiKhoan.getSdt(),month,year);
                for(MyDataBase.DoanhThu x : lsDoanhThu)
                {
                    tongDoanhThu += x.getSoTien();
                }

                lsChiTieu = Login.myDataBase.searchChiTieu_TheoThang(taiKhoan.getSdt(),month,year);
                for(MyDataBase.ChiTieu x : lsChiTieu)
                {
                    tongChiTieu += x.getSoTien();
                }

                break;
            }
            case 2: //Năm này
            {
                lsDoanhThu = Login.myDataBase.searchDoanhThu_TheoNam(taiKhoan.getSdt(),year);
                for(MyDataBase.DoanhThu x : lsDoanhThu)
                {
                    tongDoanhThu += x.getSoTien();
                }

                lsChiTieu = Login.myDataBase.searchChiTieu_TheoNam(taiKhoan.getSdt(),year);
                for(MyDataBase.ChiTieu x : lsChiTieu)
                {
                    tongChiTieu += x.getSoTien();
                }

                break;
            }
        }

        txtDoanhThu.setText("+" + String.format("%,d",(int)tongDoanhThu) + " đ");
        txtChiTieu.setText("-" + String.format("%,d",(int)tongChiTieu) + " đ");
    }

}


