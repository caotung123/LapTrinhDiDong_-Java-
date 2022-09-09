package com.example.app_sothuchi;

import android.Manifest;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.List;

import Model.MyDataBase;
import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedbottompicker.TedBottomPicker;

public class TaiKhoanFragment extends Fragment {
    CircleImageView hinhAnh;
    TextView txtHoTen;
    TextView txtSDT;
    TextView txtEmail;
    TextView txtMatKhau;
    TextView txtXNMatKhau;
    Button btnSua;

    public TaiKhoanFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        hinhAnh = view.findViewById(R.id.taikhoan_img);
        txtHoTen = view.findViewById(R.id.taikhoan_txtHoTen);
        txtEmail = view.findViewById(R.id.taikhoan_txtEmail);
        txtSDT = view.findViewById(R.id.taikhoan_txtSDT);
        txtMatKhau = view.findViewById(R.id.taikhoan_txtMatKhau);
        txtXNMatKhau = view.findViewById(R.id.taikhoan_txtXNMatKhau);
        btnSua = view.findViewById(R.id.taikhoan_btnSua);

        loadThongTinTaiKhoan();
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyClickSua();
            }
        });


        return view;
    }

    public void loadThongTinTaiKhoan()
    {
        MyDataBase.TaiKhoan taiKhoan = (MyDataBase.TaiKhoan) getActivity().getIntent().getExtras().get("TaiKhoan");
        txtHoTen.setText(taiKhoan.getHoTen());
        txtEmail.setText(taiKhoan.getEmail());
        txtSDT.setText(taiKhoan.getSdt());
        txtMatKhau.setText(taiKhoan.getMatKhau());
        txtXNMatKhau.setText("");
    }

    public void xuLyClickSua()
    {
        if(txtHoTen.getText().toString().length() == 0 ||txtEmail.getText().toString().length() == 0 ||txtSDT.getText().toString().length() == 0 ||txtMatKhau.getText().toString().length() == 0 ||txtXNMatKhau.getText().toString().length() == 0)
        {
            Toast.makeText(getContext(),"Không được bỏ trống các mục trên!", Toast.LENGTH_LONG).show();
        }
        else
        {
            if(!txtMatKhau.getText().toString().equals(txtXNMatKhau.getText().toString()))
            {
                Toast.makeText(getContext(),"Mật khẩu xác nhận không trùng khớp!", Toast.LENGTH_LONG).show();
            }
            else
            {
                boolean kt = Login.myDataBase.updateTaiKhoan(new MyDataBase.TaiKhoan(txtSDT.getText().toString(),txtHoTen.getText().toString(),"",txtEmail.getText().toString(),"ThongThuong",txtMatKhau.getText().toString()));
                if(kt == true)
                {
                    Toast.makeText(getContext(),"Cập nhật thành công!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(),"Cập nhật thất bại!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}