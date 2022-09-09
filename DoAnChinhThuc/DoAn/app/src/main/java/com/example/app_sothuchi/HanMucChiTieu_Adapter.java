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

public class HanMucChiTieu_Adapter extends RecyclerView.Adapter<HanMucChiTieu_Adapter.hanMucChiTieuViewHolder> {
    ArrayList<MyDataBase.HanMucChiTieu> data;
    Context context;

    public HanMucChiTieu_Adapter(ArrayList<MyDataBase.HanMucChiTieu> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public hanMucChiTieuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mot_han_muc_chi_tieu,parent,false);
        HanMucChiTieu_Adapter.hanMucChiTieuViewHolder hanMucChiTieuViewHolder = new HanMucChiTieu_Adapter.hanMucChiTieuViewHolder(view);

        return hanMucChiTieuViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull hanMucChiTieuViewHolder holder, int position) {
        MyDataBase.HanMucChiTieu hanMucChiTieu = data.get(position);
        holder.txt_loaiKC.setText(hanMucChiTieu.getLoaiKC());
        holder.txt_khoanChi.setText(hanMucChiTieu.getLoaiKC_PhanCap());
        holder.txt_hanMuc.setText(String.format("%,d",(int)hanMucChiTieu.getHanMuc()));
        holder.txt_Tgian.setText(hanMucChiTieu.getTgian());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HanMucChiTieuFragment.txtLoaiKC.setText(hanMucChiTieu.getLoaiKC());
                HanMucChiTieuFragment.txtKhoanChi.setText(hanMucChiTieu.getLoaiKC_PhanCap());
                HanMucChiTieuFragment.txtHanMuc.setText(hanMucChiTieu.getHanMuc() + "");
            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Xác nhận");
                dialog.setMessage("Bạn có muốn xóa ví '" + hanMucChiTieu.getLoaiKC() + "/" + hanMucChiTieu.getLoaiKC_PhanCap() + "' không?");
                dialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean kq = Login.myDataBase.deleteHanMucChiTieu(hanMucChiTieu.getLoaiKC(),hanMucChiTieu.getLoaiKC_PhanCap());

                        if(kq == true)
                        {
                            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HanMucChiTieuFragment()).commit();
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

    public class hanMucChiTieuViewHolder extends RecyclerView.ViewHolder {
        TextView txt_loaiKC;
        TextView txt_khoanChi;
        TextView txt_hanMuc;
        TextView txt_Tgian;
        RelativeLayout item;

        public hanMucChiTieuViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_loaiKC = itemView.findViewById(R.id.itemHanMucChiTieu_txtLoaiKC);
            txt_khoanChi = itemView.findViewById(R.id.itemHanMucChiTieu_txtKhoanChi);
            txt_hanMuc = itemView.findViewById(R.id.itemHanMucChiTieu_txtHanMuc);
            txt_Tgian = itemView.findViewById(R.id.itemHanMucChiTieu_txtTgian);
            item = itemView.findViewById(R.id.itemHanMucChiTieu);
        }
    }
}
