package com.example.app_sothuchi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Model.MyDataBase;

public class ViTien_Adapter extends RecyclerView.Adapter<ViTien_Adapter.viTienViewHolder> {
    ArrayList<MyDataBase.ViTien> data;
    Context context;

    public ViTien_Adapter(ArrayList<MyDataBase.ViTien> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public viTienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mot_vi_tien,parent,false);
        viTienViewHolder viTienViewHolder = new viTienViewHolder(view);

        return viTienViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull viTienViewHolder holder, int position) {
        MyDataBase.ViTien viTien = data.get(position);
        holder.txt_STT.setText((position + 1) + "");
        holder.txt_TenVi.setText(viTien.getTenVi());
        holder.txt_SoDu.setText(String.format("%,d",(int)viTien.getSoDu()));
        if(viTien.getSoDu() >= 0)
        {
            holder.txt_SoDu.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        }
        else
        {
            holder.txt_SoDu.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }

        holder.itemViTien.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Xác nhận");
                dialog.setMessage("Bạn có muốn xóa ví '" + viTien.getTenVi() + "' không?");
                dialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean kq = Login.myDataBase.deleteViTien(viTien.getTenVi());

                        if(kq == true)
                        {
                            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ViTienFragment()).commit();
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


    public class viTienViewHolder extends RecyclerView.ViewHolder {
        TextView txt_STT;
        TextView txt_TenVi;
        TextView txt_SoDu;
        RelativeLayout itemViTien;

        public viTienViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_STT = itemView.findViewById(R.id.itemViTien_txtSTT);
            txt_TenVi = itemView.findViewById(R.id.itemViTien_txtTenVi);
            txt_SoDu = itemView.findViewById(R.id.itemViTien_txtSoDu);
            itemViTien = itemView.findViewById(R.id.itemViTien);
        }
    }
}
