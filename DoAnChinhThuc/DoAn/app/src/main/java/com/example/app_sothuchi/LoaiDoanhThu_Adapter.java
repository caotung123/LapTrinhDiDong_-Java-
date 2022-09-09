package com.example.app_sothuchi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Model.MyDataBase;

public class LoaiDoanhThu_Adapter extends RecyclerView.Adapter<LoaiDoanhThu_Adapter.loaiDoanhThuViewHolder> {
    ArrayList<MyDataBase.Loai_DoanhThu> data;
    Context context;

    public LoaiDoanhThu_Adapter(ArrayList<MyDataBase.Loai_DoanhThu> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public loaiDoanhThuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mot_loai_doanh_thu,parent,false);
        LoaiDoanhThu_Adapter.loaiDoanhThuViewHolder loaiDoanhThuViewHolder = new LoaiDoanhThu_Adapter.loaiDoanhThuViewHolder(view);

        return loaiDoanhThuViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull loaiDoanhThuViewHolder holder, int position) {
        MyDataBase.Loai_DoanhThu loai_doanhThu = data.get(position);
        holder.txt_loaiDT.setText(loai_doanhThu.getTenLoai());
        holder.txt_tenDT.setText(loai_doanhThu.getTenLoai_PhanCap());

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Xác nhận");
                dialog.setMessage("Bạn có muốn xóa ví '" + loai_doanhThu.getTenLoai() + "/" + loai_doanhThu.getTenLoai_PhanCap() + "' không?");
                dialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean kq = Login.myDataBase.deleteLoaiDoanhThu(loai_doanhThu.getTenLoai(), loai_doanhThu.getTenLoai_PhanCap());

                        if(kq == true)
                        {
                            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LoaiDoanhThuFragment()).commit();
                            Toast.makeText(context,"Bạn đã xóa thành công!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(context,"Xóa thất bại!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.setCancelable(true);
                    }
                });

                AlertDialog alterdialog = dialog.create();
                alterdialog.show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class loaiDoanhThuViewHolder extends RecyclerView.ViewHolder {
        TextView txt_loaiDT;
        TextView txt_tenDT;
        RelativeLayout item;

        public loaiDoanhThuViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_loaiDT = itemView.findViewById(R.id.itemLoaiDoanhThu_txtLoaiDT);
            txt_tenDT = itemView.findViewById(R.id.itemLoaiDoanhThu_txtTenDT);
            item = itemView.findViewById(R.id.itemLoaiDoanhThu);
        }
    }
}
