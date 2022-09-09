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

public class ChiTieu_Adapter extends RecyclerView.Adapter<ChiTieu_Adapter.chiTieuViewHolder> {
    ArrayList<MyDataBase.ChiTieu> data;
    Context context;
    public static MyDataBase.ChiTieu chiTieu_Click;

    public ChiTieu_Adapter(ArrayList<MyDataBase.ChiTieu> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public chiTieuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mot_doanh_thu,parent,false);
        ChiTieu_Adapter.chiTieuViewHolder chiTieuViewHolder = new ChiTieu_Adapter.chiTieuViewHolder(view);

        return chiTieuViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull chiTieuViewHolder holder, int position) {
        MyDataBase.ChiTieu chiTieu = data.get(position);
        holder.txt_STT.setText(chiTieu.getMaCT() + "");
        holder.txt_TenCT.setText(chiTieu.getTenCT());
        holder.txt_Tgian.setText(chiTieu.getTgian());
        holder.txt_SoTien.setText("-" + String.format("%,d",(int)chiTieu.getSoTien()));
        holder.txt_SoTien.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chiTieu_Click = chiTieu;
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ChiTietMotChiTieuFragment()).commit();
            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Xác nhận");
                dialog.setMessage("Bạn có muốn xóa ví '" + chiTieu.getTenCT() + "' không?");
                dialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean kq = Login.myDataBase.deleteChiTieu(chiTieu.getMaCT());

                        if(kq == true)
                        {
                            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ChiTieuFragment()).commit();
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

    public class chiTieuViewHolder extends RecyclerView.ViewHolder {
        TextView txt_STT;
        TextView txt_TenCT;
        TextView txt_SoTien;
        TextView txt_Tgian;
        RelativeLayout item;

        public chiTieuViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_STT = itemView.findViewById(R.id.itemDoanhThu_txtSTT);
            txt_TenCT = itemView.findViewById(R.id.itemDoanhThu_txtTenDT) ;
            txt_SoTien = itemView.findViewById(R.id.itemDoanhThu_txtSoTien);
            txt_Tgian = itemView.findViewById(R.id.itemDoanhThu_txtThoiGian);
            item = itemView.findViewById(R.id.itemDoanhThu);
        }
    }
}
