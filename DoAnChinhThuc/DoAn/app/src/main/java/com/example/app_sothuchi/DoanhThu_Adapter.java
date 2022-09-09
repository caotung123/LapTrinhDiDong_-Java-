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

public class DoanhThu_Adapter extends RecyclerView.Adapter<DoanhThu_Adapter.doanhThuViewHolder> {
    ArrayList<MyDataBase.DoanhThu> data;
    Context context;
    public static MyDataBase.DoanhThu doanhThu_Click;

    public DoanhThu_Adapter(ArrayList<MyDataBase.DoanhThu> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public doanhThuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mot_doanh_thu,parent,false);
        DoanhThu_Adapter.doanhThuViewHolder doanhThuViewHolder = new DoanhThu_Adapter.doanhThuViewHolder(view);

        return doanhThuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull doanhThuViewHolder holder, int position) {
        MyDataBase.DoanhThu doanhThu = data.get(position);
        holder.txt_STT.setText(doanhThu.getMaDT() + "");
        holder.txt_TenDT.setText(doanhThu.getTenDT());
        holder.txt_Tgian.setText(doanhThu.getTgian());
        holder.txt_SoTien.setText("+" + String.format("%,d",(int)doanhThu.getSoTien()));
        holder.txt_SoTien.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doanhThu_Click = doanhThu;
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ChiTietMotDoanhThuFragment()).commit();
            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Xác nhận");
                dialog.setMessage("Bạn có muốn xóa ví '" + doanhThu.getTenDT() + "' không?");
                dialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean kq = Login.myDataBase.deleteDoanhThu(doanhThu.getMaDT());

                        if(kq == true)
                        {
                            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DoanhThuFragment()).commit();
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

    public class doanhThuViewHolder extends RecyclerView.ViewHolder {
        TextView txt_STT;
        TextView txt_TenDT;
        TextView txt_SoTien;
        TextView txt_Tgian;
        RelativeLayout item;

        public doanhThuViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_STT = itemView.findViewById(R.id.itemDoanhThu_txtSTT);
            txt_TenDT = itemView.findViewById(R.id.itemDoanhThu_txtTenDT) ;
            txt_SoTien = itemView.findViewById(R.id.itemDoanhThu_txtSoTien);
            txt_Tgian = itemView.findViewById(R.id.itemDoanhThu_txtThoiGian);
            item = itemView.findViewById(R.id.itemDoanhThu);
        }
    }
}
