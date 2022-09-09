package com.example.app_sothuchi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import Model.MyDataBase;

public class Login extends AppCompatActivity {
    public static MyDataBase myDataBase;
    EditText txtSDT;
    EditText txtMatKhau;
    Button btnDangNhap;
    Button btnDangKy;
    CheckBox cbRemember;


    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASS = "passKey";
    public static final String REMEMBER = "remember";
    public static SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        taoCSDL();
        anhXa();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        loadData();
        if(txtSDT.getText().length() > 0 && txtMatKhau.getText().length() > 0)
        {
            xuLyDangNhap();
        }

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDangNhap();
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKy();
            }
        });

    }

    public void taoCSDL()
    {
        myDataBase = new MyDataBase(this,"DB_SoThuChi",null,1);
    }

    public void anhXa()
    {
        txtSDT = findViewById(R.id.txtSDT);
        txtMatKhau = findViewById(R.id.txtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
        cbRemember = findViewById(R.id.cbRemember);
    }

    public void xuLyDangNhap()
    {
        if(txtSDT.getText().length() == 0 || txtMatKhau.getText().length() == 0)
        {
            Toast.makeText(this,"Không được bỏ trống SĐT và Mật khẩu!",Toast.LENGTH_SHORT).show();
        }
        else {
            String sdt = txtSDT.getText().toString();
            String matKhau = txtMatKhau.getText().toString();

            MyDataBase.TaiKhoan taiKhoan = myDataBase.searchTaiKhoan(sdt);
            if (taiKhoan != null) {
                if (taiKhoan.getMatKhau().equals(matKhau)) {
                    //nếu người dùng có chọn ghi nhớ
                    if (cbRemember.isChecked())
                    {
                        //lưu lại thông tin đăng nhập
                        saveData(txtSDT.getText().toString(), txtMatKhau.getText().toString());
                    }
                    else
                    {
                        clearData();//xóa thông tin đã lưu
                    }

                    Intent intent = new Intent(this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("TaiKhoan",taiKhoan);
                    intent.putExtras(bundle);

                    startActivity(intent);

                    txtSDT.setText("");
                    txtMatKhau.setText("");
                    Toast.makeText(this, "Chào mừng bạn đến với app của LeLuuHoangNhan!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Số điện thoại này chưa đăng ký!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void dangKy()
    {
        Intent intent = new Intent(this, DangKy.class);
        startActivity(intent);
    }

    //--Ghi nhớ tài khoản khi đăng nhập
    public static void clearData() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    private void saveData(String username, String Pass) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USERNAME, username);
        editor.putString(PASS, Pass);
        editor.putBoolean(REMEMBER,cbRemember.isChecked());
        editor.commit();
    }

    private void loadData() {
        if(sharedpreferences.getBoolean(REMEMBER,false)) {
            txtSDT.setText(sharedpreferences.getString(USERNAME, ""));
            txtMatKhau.setText(sharedpreferences.getString(PASS, ""));
            cbRemember.setChecked(true);
        }
        else
            cbRemember.setChecked(false);

    }
}

