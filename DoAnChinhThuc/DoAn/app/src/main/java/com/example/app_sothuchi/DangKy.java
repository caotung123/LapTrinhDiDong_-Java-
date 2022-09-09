package com.example.app_sothuchi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import Model.MyDataBase;

public class DangKy extends AppCompatActivity {
    EditText hoTen;
    EditText sdt;
    EditText email;
    EditText matKhau;
    EditText xacNhanMatKhau;
    Button dangKy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        anhXa();
        dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDangKy();
            }
        });
    }

    public void anhXa()
    {
        hoTen = findViewById(R.id.dangKyHoTen);
        sdt = findViewById(R.id.dangKySDT);
        email = findViewById(R.id.dangKyEmail);
        matKhau = findViewById(R.id.dangKyMatKhau);
        xacNhanMatKhau = findViewById(R.id.dangKyXacNhanMatKhau);
        dangKy = findViewById(R.id.btnDangKyTaiKhoan);
    }

    public void xuLyDangKy()
    {
        if(hoTen.getText().length() == 0 || sdt.getText().length() == 0 || email.getText().length() == 0 || matKhau.getText().length() == 0 || xacNhanMatKhau.getText().length() == 0)
        {
            Toast.makeText(this,"Không được bỏ trống các trường trên!",Toast.LENGTH_SHORT).show();
        }
        else if(!matKhau.getText().toString().equals(xacNhanMatKhau.getText().toString()))
        {
            Toast.makeText(this,"Mật khẩu xác nhận không trùng khớp!",Toast.LENGTH_SHORT).show();
        }
        else {
            String _sdt = sdt.getText().toString();
            String _hoTen = hoTen.getText().toString();
            String _hinhAnh = "R.drawable.icon_nam";
            String _email = email.getText().toString();
            String _loaiTK = "ThongThuong";
            String _matKhau = matKhau.getText().toString();

            boolean kq = Login.myDataBase.insertTaiKhoan(new MyDataBase.TaiKhoan(_sdt,_hoTen,_hinhAnh,_email,_loaiTK,_matKhau));

            if(kq == true)
            {
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                Toast.makeText(this,"Đăng ký tài khoản thành công!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this,"Đăng ký thất bại!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}