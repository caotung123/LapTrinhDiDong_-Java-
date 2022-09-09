package Model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;

public class MyDataBase extends SQLiteOpenHelper {

    public MyDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql= "CREATE TABLE IF NOT EXISTS PHANQUYEN (LOAITK TEXT PRIMARY KEY, QUYEN TEXT);";
        sqLiteDatabase.execSQL(sql);

        String sql1= "CREATE TABLE IF NOT EXISTS TAIKHOAN (SDT TEXT PRIMARY KEY, HOTEN TEXT, HINHANH TEXT, EMAIL TEXT, LOAITK TEXT, MATKHAU TEXT, FOREIGN KEY (LOAITK) REFERENCES PHANQUYEN(LOAITK));";
        sqLiteDatabase.execSQL(sql1);

        String sql6= "CREATE TABLE IF NOT EXISTS VITIEN (TENVI TEXT PRIMARY KEY, SODU REAL, SDT TEXT, FOREIGN KEY (SDT) REFERENCES TAIKHOAN(SDT));";
        sqLiteDatabase.execSQL(sql6);

        String sql2= "CREATE TABLE IF NOT EXISTS LOAI_DOANHTHU (TENLOAI TEXT, TENLOAI_PHANCAP TEXT, PRIMARY KEY(TENLOAI, TENLOAI_PHANCAP));";
        sqLiteDatabase.execSQL(sql2);

        String sql3= "CREATE TABLE IF NOT EXISTS DOANHTHU (MADT INTEGER PRIMARY KEY AUTOINCREMENT, TENDT TEXT, SOTIEN REAL, TENLOAI TEXT, TENLOAI_PHANCAP TEXT, TGIAN NUMERIC, SDT TEXT, TENVI TEXT, FOREIGN KEY (TENLOAI) REFERENCES LOAI_DOANHTHU(TENLOAI), FOREIGN KEY (TENLOAI_PHANCAP) REFERENCES LOAI_DOANHTHU(TENLOAI_PHANCAP), FOREIGN KEY (SDT) REFERENCES TAIKHOAN(SDT), FOREIGN KEY (TENVI) REFERENCES VITIEN(TENVI));";
        sqLiteDatabase.execSQL(sql3);

        String sql4= "CREATE TABLE IF NOT EXISTS HANMUCCHITIEU (LOAIKC TEXT, LOAIKC_PHANCAP TEXT, HANMUC REAL, TGIAN NUMERIC, PRIMARY KEY(LOAIKC, LOAIKC_PHANCAP));";
        sqLiteDatabase.execSQL(sql4);

        String sql5= "CREATE TABLE IF NOT EXISTS CHITIEU (MACT INTEGER PRIMARY KEY AUTOINCREMENT, TENCT TEXT, SOTIEN REAL, TGIAN NUMERIC, LOAIKC TEXT, LOAIKC_PHANCAP TEXT, SDT TEXT, TENVI TEXT, FOREIGN KEY (LOAIKC) REFERENCES HANMUCCHITIEU(LOAIKC), FOREIGN KEY (LOAIKC_PHANCAP) REFERENCES HANMUCCHITIEU(LOAIKC_PHANCAP), FOREIGN KEY (SDT) REFERENCES TAIKHOAN(SDT), FOREIGN KEY (TENVI) REFERENCES VITIEN(TENVI));";
        sqLiteDatabase.execSQL(sql5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PHANQUYEN");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TAIKHOAN");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS VITIEN");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LOAI_DOANHTHU");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS DOANHTHU");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS HANMUCCHITIEU");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CHITIEU");

        onCreate(sqLiteDatabase);
    }


    //Create Class
    public static class PhanQuyen implements Serializable {
        String loaiTK;
        String quyen;

        public PhanQuyen(String loaiTK, String quyen) {
            this.loaiTK = loaiTK;
            this.quyen = quyen;
        }

        public String getLoaiTK() {
            return loaiTK;
        }

        public void setLoaiTK(String loaiTK) {
            this.loaiTK = loaiTK;
        }

        public String getQuyen() {
            return quyen;
        }

        public void setQuyen(String quyen) {
            this.quyen = quyen;
        }
    }

    public static class TaiKhoan implements Serializable {
        String sdt;
        String hoTen;
        String hinhAnh;
        String email;
        String loaiTk;
        String matKhau;

        public TaiKhoan(String sdt, String hoTen, String hinhAnh, String email, String loaiTk, String matKhau) {
            this.sdt = sdt;
            this.hoTen = hoTen;
            this.hinhAnh = hinhAnh;
            this.email = email;
            this.loaiTk = loaiTk;
            this.matKhau = matKhau;
        }

        public String getSdt() {
            return sdt;
        }

        public void setSdt(String sdt) {
            this.sdt = sdt;
        }

        public String getHoTen() {
            return hoTen;
        }

        public void setHoTen(String hoTen) {
            this.hoTen = hoTen;
        }

        public String getHinhAnh() {
            return hinhAnh;
        }

        public void setHinhAnh(String hinhAnh) {
            this.hinhAnh = hinhAnh;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLoaiTk() {
            return loaiTk;
        }

        public void setLoaiTk(String loaiTk) {
            this.loaiTk = loaiTk;
        }

        public String getMatKhau() {
            return matKhau;
        }

        public void setMatKhau(String matKhau) {
            this.matKhau = matKhau;
        }
    }

    public static class ViTien implements Serializable {
        String tenVi;
        float soDu;
        String sdt;

        public ViTien(String tenVi, float soDu, String sdt) {
            this.tenVi = tenVi;
            this.soDu = soDu;
            this.sdt = sdt;
        }

        public String getTenVi() {
            return tenVi;
        }

        public void setTenVi(String tenVi) {
            this.tenVi = tenVi;
        }

        public float getSoDu() {
            return soDu;
        }

        public void setSoDu(float soDu) {
            this.soDu = soDu;
        }

        public String getSdt() {
            return sdt;
        }

        public void setSdt(String sdt) {
            this.sdt = sdt;
        }
    }

    public static class Loai_DoanhThu implements Serializable {
        String tenLoai;
        String tenLoai_PhanCap;

        public Loai_DoanhThu(String tenLoai, String tenLoai_PhanCap) {
            this.tenLoai = tenLoai;
            this.tenLoai_PhanCap = tenLoai_PhanCap;
        }

        public String getTenLoai() {
            return tenLoai;
        }

        public void setTenLoai(String tenLoai) {
            this.tenLoai = tenLoai;
        }

        public String getTenLoai_PhanCap() {
            return tenLoai_PhanCap;
        }

        public void setTenLoai_PhanCap(String tenLoai_PhanCap) {
            this.tenLoai_PhanCap = tenLoai_PhanCap;
        }
    }

    public static class DoanhThu implements Serializable {
        int maDT;
        String tenDT;
        float soTien;
        String tenLoai;
        String tenLoai_PhanCap;
        String tgian;
        String sdt;
        String tenVi;

        public DoanhThu(int maDT, String tenDT, float soTien, String tenLoai, String tenLoai_PhanCap, String tgian, String sdt, String tenVi) {
            this.maDT = maDT;
            this.tenDT = tenDT;
            this.soTien = soTien;
            this.tenLoai = tenLoai;
            this.tenLoai_PhanCap = tenLoai_PhanCap;
            this.tgian = tgian;
            this.sdt = sdt;
            this.tenVi = tenVi;
        }

        public int getMaDT() {
            return maDT;
        }

        public void setMaDT(int maDT) {
            this.maDT = maDT;
        }

        public String getTenDT() {
            return tenDT;
        }

        public void setTenDT(String tenDT) {
            this.tenDT = tenDT;
        }

        public float getSoTien() {
            return soTien;
        }

        public void setSoTien(float soTien) {
            this.soTien = soTien;
        }

        public String getTenLoai() {
            return tenLoai;
        }

        public void setTenLoai(String tenLoai) {
            this.tenLoai = tenLoai;
        }

        public String getTenLoai_PhanCap() {
            return tenLoai_PhanCap;
        }

        public void setTenLoai_PhanCap(String tenLoai_PhanCap) {
            this.tenLoai_PhanCap = tenLoai_PhanCap;
        }

        public String getTgian() {
            return tgian;
        }

        public void setTgian(String tgian) {
            this.tgian = tgian;
        }

        public String getSdt() {
            return sdt;
        }

        public void setSdt(String sdt) {
            this.sdt = sdt;
        }

        public String getTenVi() {
            return tenVi;
        }

        public void setTenVi(String tenVi) {
            this.tenVi = tenVi;
        }
    }

    public static class HanMucChiTieu implements Serializable {
        String loaiKC;
        String loaiKC_PhanCap;
        float hanMuc;
        String tgian;

        public HanMucChiTieu(String loaiKC, String loaiKC_PhanCap, float hanMuc, String tgian) {
            this.loaiKC = loaiKC;
            this.loaiKC_PhanCap = loaiKC_PhanCap;
            this.hanMuc = hanMuc;
            this.tgian = tgian;
        }

        public String getLoaiKC() {
            return loaiKC;
        }

        public void setLoaiKC(String loaiKC) {
            this.loaiKC = loaiKC;
        }

        public String getLoaiKC_PhanCap() {
            return loaiKC_PhanCap;
        }

        public void setLoaiKC_PhanCap(String loaiKC_PhanCap) {
            this.loaiKC_PhanCap = loaiKC_PhanCap;
        }

        public float getHanMuc() {
            return hanMuc;
        }

        public void setHanMuc(float hanMuc) {
            this.hanMuc = hanMuc;
        }

        public String getTgian() {
            return tgian;
        }

        public void setTgian(String tgian) {
            this.tgian = tgian;
        }
    }

    public static class ChiTieu implements Serializable {
        int maCT;
        String tenCT;
        float soTien;
        String tgian;
        String loaiKC;
        String loaiKC_PhanCap;
        String sdt;
        String tenVi;

        public ChiTieu(int maCT, String tenCT, float soTien, String tgian, String loaiKC, String loaiKC_PhanCap, String sdt, String tenVi) {
            this.maCT = maCT;
            this.tenCT = tenCT;
            this.soTien = soTien;
            this.tgian = tgian;
            this.loaiKC = loaiKC;
            this.loaiKC_PhanCap = loaiKC_PhanCap;
            this.sdt = sdt;
            this.tenVi = tenVi;
        }

        public int getMaCT() {
            return maCT;
        }

        public void setMaCT(int maCT) {
            this.maCT = maCT;
        }

        public String getTenCT() {
            return tenCT;
        }

        public void setTenCT(String tenCT) {
            this.tenCT = tenCT;
        }

        public float getSoTien() {
            return soTien;
        }

        public void setSoTien(float soTien) {
            this.soTien = soTien;
        }

        public String getTgian() {
            return tgian;
        }

        public void setTgian(String tgian) {
            this.tgian = tgian;
        }

        public String getLoaiKC() {
            return loaiKC;
        }

        public void setLoaiKC(String loaiKC) {
            this.loaiKC = loaiKC;
        }

        public String getLoaiKC_PhanCap() {
            return loaiKC_PhanCap;
        }

        public void setLoaiKC_PhanCap(String loaiKC_PhanCap) {
            this.loaiKC_PhanCap = loaiKC_PhanCap;
        }

        public String getSdt() {
            return sdt;
        }

        public void setSdt(String sdt) {
            this.sdt = sdt;
        }

        public String getTenVi() {
            return tenVi;
        }

        public void setTenVi(String tenVi) {
            this.tenVi = tenVi;
        }
    }







    //Create Method PhanQuyen
    public ArrayList<PhanQuyen> selectALLPhanQuyen()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<PhanQuyen> dt = new ArrayList<>();
        String sql= "SELECT * FROM PHANQUYEN";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    String loaiTK = cursor.getString(0);
                    String quyen = cursor.getString(1);
                    dt.add(new PhanQuyen(loaiTK,quyen));
                }while (cursor.moveToNext());
            }
        }
        db.close();
        return dt;
    }

    public boolean insertPhanQuyen(PhanQuyen x)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("LOAITK",x.loaiTK);
        values.put("QUYEN",x.quyen);

        boolean kq = db.insert("PHANQUYEN",null,values)>0;
        db.close();
        return kq;
    }

    public boolean updatePhanQuyen(PhanQuyen x)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("LOAITK",x.loaiTK);
        values.put("QUYEN",x.quyen);
        String []where = {x.loaiTK};

        boolean kq = db.update("PHANQUYEN",values,"LOAITK = ?",where)>0;

        return kq;
    }

    public boolean deletePhanQuyen(String loaiTK)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String []values = {loaiTK};
        boolean kq = db.delete("PHANQUYEN","LOAITK = ?",values)>0;

        return kq;
    }

    public PhanQuyen searchPhanQuyen(String loaiTK)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT * FROM PHANQUYEN";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    if(cursor.getString(0).equals(loaiTK))
                    {
                        String _loaiTK = cursor.getString(0);
                        String _quyen = cursor.getString(1);

                        db.close();
                        return new PhanQuyen(_loaiTK, _quyen);
                    }
                }while (cursor.moveToNext());
            }
        }

        db.close();
        return null;
    }



    //Create Method TaiKhoan
    public ArrayList<TaiKhoan> selectALLTaiKhoan()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TaiKhoan> dt = new ArrayList<>();
        String sql= "SELECT * FROM TAIKHOAN";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    String sdt = cursor.getString(0);
                    String hoTen = cursor.getString(1);
                    String hinhAnh = cursor.getString(2);
                    String email = cursor.getString(3);
                    String loaiTK = cursor.getString(4);
                    String matKhau = cursor.getString(5);

                    dt.add(new TaiKhoan(sdt,hoTen,hinhAnh,email,loaiTK, matKhau));
                }while (cursor.moveToNext());
            }
        }
        db.close();
        return dt;
    }

    public boolean insertTaiKhoan(TaiKhoan x)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SDT",x.sdt);
        values.put("HOTEN",x.hoTen);
        values.put("HINHANH",x.hinhAnh);
        values.put("EMAIL",x.email);
        values.put("LOAITK",x.loaiTk);
        values.put("MATKHAU",x.matKhau);

        boolean kq = db.insert("TAIKHOAN",null,values)>0;
        db.close();

        return kq;
    }

    public boolean updateTaiKhoan(TaiKhoan x)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SDT",x.sdt);
        values.put("HOTEN",x.hoTen);
        values.put("HINHANH",x.hinhAnh);
        values.put("EMAIL",x.email);
        values.put("LOAITK",x.loaiTk);
        values.put("MATKHAU",x.matKhau);
        String []where = {x.sdt};

        boolean kq = db.update("TAIKHOAN",values,"SDT = ?",where)>0;

        return kq;
    }

    public boolean deleteTaiKhoan(String sdt)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String []values = {sdt};
        boolean kq = db.delete("TAIKHOAN","SDT = ?",values)>0;

        return kq;
    }

    public TaiKhoan searchTaiKhoan(String _sdt)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM TAIKHOAN";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    if(cursor.getString(0).equals(_sdt))
                    {
                        String sdt = cursor.getString(0);
                        String hoTen = cursor.getString(1);
                        String hinhAnh = cursor.getString(2);
                        String email = cursor.getString(3);
                        String loaiTK = cursor.getString(4);
                        String matKhau = cursor.getString(5);

                        db.close();
                        return new TaiKhoan(sdt,hoTen,hinhAnh,email,loaiTK, matKhau);
                    }
                }while (cursor.moveToNext());

            }
        }

        db.close();
        return null;
    }


    //Create Method ViTien
    public ArrayList<ViTien> selectALLViTien()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ViTien> dt = new ArrayList<>();
        String sql= "SELECT * FROM VITIEN";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    String tenVi = cursor.getString(0);
                    float soDu = cursor.getFloat(1);
                    String sdt = cursor.getString(2);

                    dt.add(new ViTien(tenVi, soDu, sdt));
                }while (cursor.moveToNext());
            }
        }
        db.close();
        return dt;
    }

    public boolean insertViTien(ViTien x)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TENVI",x.tenVi);
        values.put("SODU",x.soDu);
        values.put("SDT",x.sdt);

        boolean kq = db.insert("VITIEN",null,values)>0;
        db.close();

        return kq;
    }

    public boolean updateViTien(ViTien x)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TENVI",x.tenVi);
        values.put("SODU",x.soDu);
        values.put("SDT",x.sdt);
        String []where = {x.tenVi};

        boolean kq = db.update("VITIEN",values,"TENVI = ?",where)>0;

        return kq;
    }

    public boolean deleteViTien(String tenVi)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String []values = {tenVi};
        boolean kq = db.delete("VITIEN","TENVI = ?",values)>0;

        return kq;
    }

    public ViTien searchViTien(String _tenVi)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT * FROM VITIEN";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    if(cursor.getString(0).equals(_tenVi))
                    {
                        String tenVi = cursor.getString(0);
                        float soDu = cursor.getFloat(1);
                        String sdt = cursor.getString(2);

                        db.close();
                        return new ViTien(tenVi, soDu, sdt);
                    }
                }while (cursor.moveToNext());
            }
        }

        db.close();
        return null;
    }




    //Create Method Loai_DoanhThu
    public ArrayList<Loai_DoanhThu> selectALLLoaiDoanhThu()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Loai_DoanhThu> dt = new ArrayList<>();
        String sql= "SELECT * FROM LOAI_DOANHTHU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    String tenLoai = cursor.getString(0);
                    String tenLoai_PhanCap = cursor.getString(1);

                    dt.add(new Loai_DoanhThu(tenLoai,tenLoai_PhanCap));
                }while (cursor.moveToNext());
            }
        }
        db.close();
        return dt;
    }

    public boolean insertLoaiDoanhThu(Loai_DoanhThu x)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TENLOAI",x.tenLoai);
        values.put("TENLOAI_PHANCAP",x.tenLoai_PhanCap);

        boolean kq = db.insert("LOAI_DOANHTHU",null,values)>0;
        db.close();

        return kq;
    }

    public boolean deleteLoaiDoanhThu(String tenLoai, String tenLoai_PhanCap)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM LOAI_DOANHTHU WHERE TENLOAI = '" + tenLoai + "' AND TENLOAI_PHANCAP = '" + tenLoai_PhanCap + "'";
        boolean kq;
        try
        {
            db.execSQL(sql);
            kq = true;
        }
        catch (Exception ex)
        {
            kq = false;
        }

        db.close();
        return kq;
    }

    public Loai_DoanhThu searchLoaiDoanhThu(String _tenLoai, String _tenLoai_PhanCap)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT * FROM LOAI_DOANHTHU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    if(cursor.getString(0).equals(_tenLoai) && cursor.getString(1).equals(_tenLoai_PhanCap))
                    {
                        db.close();
                        return new Loai_DoanhThu(_tenLoai,_tenLoai_PhanCap);
                    }
                }while (cursor.moveToNext());
            }
        }

        db.close();
        return null;
    }

    public ArrayList<Loai_DoanhThu> searchLoaiDoanhThu_TenLoaiPhanCap(String _tenLoai)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Loai_DoanhThu> dt = new ArrayList<>();
        String sql= "SELECT * FROM LOAI_DOANHTHU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    if(cursor.getString(0).equals(_tenLoai))
                    {
                        dt.add(new Loai_DoanhThu(_tenLoai,cursor.getString(1)));
                    }
                }while (cursor.moveToNext());
            }
        }

        db.close();
        return dt;
    }


    //Create Method DoanhThu
    public ArrayList<DoanhThu> selectALLDoanhThu()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DoanhThu> dt = new ArrayList<>();
        String sql= "SELECT * FROM DOANHTHU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    int maDT = cursor.getInt(0);
                    String tenDT = cursor.getString(1);
                    float soTien = cursor.getFloat(2);
                    String tenLoai = cursor.getString(3);
                    String tenLoai_PhanCap = cursor.getString(4);
                    String tgian = cursor.getString(5).split(" ")[0];
                    String sdt =  cursor.getString(6);
                    String tenVi =  cursor.getString(7);

                    dt.add(new DoanhThu(maDT,tenDT,soTien,tenLoai, tenLoai_PhanCap,tgian,sdt,tenVi));
                }while (cursor.moveToNext());
            }
        }
        db.close();
        return dt;
    }

    public boolean insertDoanhThu(DoanhThu x)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TENDT",x.tenDT);
        values.put("SOTIEN",x.soTien);
        values.put("TENLOAI",x.tenLoai);
        values.put("TENLOAI_PHANCAP",x.tenLoai_PhanCap);
        values.put("TGIAN",x.tgian);
        values.put("SDT",x.sdt);
        values.put("TENVI",x.tenVi);


        boolean kq = db.insert("DOANHTHU",null,values)>0;
        db.close();

        return kq;
    }

    public boolean updateDoanhThu(DoanhThu x)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TENDT",x.tenDT);
        values.put("SOTIEN",x.soTien);
        values.put("TENLOAI",x.tenLoai);
        values.put("TENLOAI_PHANCAP",x.tenLoai_PhanCap);
        values.put("TGIAN",x.tgian);
        values.put("SDT",x.sdt);
        values.put("TENVI",x.tenVi);
        String []where = {String.valueOf(x.maDT)};

        boolean kq = db.update("DOANHTHU",values,"MADT = ?",where)>0;

        return kq;
    }

    public boolean deleteDoanhThu(int maDT)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String []values = {String.valueOf(maDT)};
        boolean kq = db.delete("DOANHTHU","MADT = ?",values)>0;

        return kq;
    }

    public DoanhThu searchDoanhThu(int _maDT)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT * FROM DOANHTHU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    if(cursor.getInt(0) == _maDT)
                    {
                        int maDT = cursor.getInt(0);
                        String tenDT = cursor.getString(1);
                        float soTien = cursor.getFloat(2);
                        String tenLoai = cursor.getString(3);
                        String tenLoai_PhanCap = cursor.getString(4);
                        String tgian = cursor.getString(5).split(" ")[0];
                        String sdt =  cursor.getString(6);
                        String tenVi =  cursor.getString(7);
                        db.close();
                        return new DoanhThu(maDT,tenDT,soTien,tenLoai, tenLoai_PhanCap,tgian,sdt, tenVi);
                    }
                }while (cursor.moveToNext());
            }
        }

        db.close();
        return null;
    }

    public ArrayList<DoanhThu> searchDoanhThu_TheoNgay(String _sdt, int _ngay, int _thang, int _nam)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DoanhThu> dt = new ArrayList<>();
        String sql= "SELECT * FROM DOANHTHU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    int ngay = Integer.parseInt(cursor.getString(5).split(" ")[0].split("/")[0]);
                    int thang = Integer.parseInt(cursor.getString(5).split(" ")[0].split("/")[1]);
                    int nam = Integer.parseInt(cursor.getString(5).split(" ")[0].split("/")[2]);

                    if(cursor.getString(6).equals(_sdt) && ngay == _ngay && thang == _thang && nam == _nam)
                    {
                        int maDT = cursor.getInt(0);
                        String tenDT = cursor.getString(1);
                        float soTien = cursor.getFloat(2);
                        String tenLoai = cursor.getString(3);
                        String tenLoai_PhanCap = cursor.getString(4);
                        String tgian = cursor.getString(5).split(" ")[0];
                        String sdt =  cursor.getString(6);
                        String tenVi =  cursor.getString(7);

                        dt.add(new DoanhThu(maDT,tenDT,soTien,tenLoai, tenLoai_PhanCap,tgian,sdt,tenVi));
                    }
                }while (cursor.moveToNext());
            }
        }

        db.close();
        return dt;
    }

    public ArrayList<DoanhThu> searchDoanhThu_TheoThang(String _sdt, int _thang, int _nam)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DoanhThu> dt = new ArrayList<>();
        String sql= "SELECT * FROM DOANHTHU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    int thang = Integer.parseInt(cursor.getString(5).split(" ")[0].split("/")[1]);
                    int nam = Integer.parseInt(cursor.getString(5).split(" ")[0].split("/")[2]);

                    if(cursor.getString(6).equals(_sdt) && thang == _thang && nam == _nam)
                    {
                        int maDT = cursor.getInt(0);
                        String tenDT = cursor.getString(1);
                        float soTien = cursor.getFloat(2);
                        String tenLoai = cursor.getString(3);
                        String tenLoai_PhanCap = cursor.getString(4);
                        String tgian = cursor.getString(5).split(" ")[0];
                        String sdt =  cursor.getString(6);
                        String tenVi =  cursor.getString(7);

                        dt.add(new DoanhThu(maDT,tenDT,soTien,tenLoai, tenLoai_PhanCap,tgian,sdt,tenVi));
                    }
                }while (cursor.moveToNext());
            }
        }

        db.close();
        return dt;
    }

    public ArrayList<DoanhThu> searchDoanhThu_TheoNam(String _sdt, int _nam)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DoanhThu> dt = new ArrayList<>();
        String sql= "SELECT * FROM DOANHTHU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    int nam = Integer.parseInt(cursor.getString(5).split(" ")[0].split("/")[2]);
                    if(cursor.getString(6).equals(_sdt) && nam == _nam)
                    {
                        int maDT = cursor.getInt(0);
                        String tenDT = cursor.getString(1);
                        float soTien = cursor.getFloat(2);
                        String tenLoai = cursor.getString(3);
                        String tenLoai_PhanCap = cursor.getString(4);
                        String tgian = cursor.getString(5).split(" ")[0];
                        String sdt =  cursor.getString(6);
                        String tenVi =  cursor.getString(7);

                        dt.add(new DoanhThu(maDT,tenDT,soTien,tenLoai, tenLoai_PhanCap,tgian,sdt,tenVi));
                    }
                }while (cursor.moveToNext());
            }
        }

        db.close();
        return dt;
    }

    public float tinhTongDoanhThu(String sdt)
    {
        float tong = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT * FROM DOANHTHU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    if (cursor.getString(6).equals(sdt))
                    {
                        float soTien = cursor.getFloat(2);
                        tong += soTien;
                    }
                }while (cursor.moveToNext());
            }
        }
        db.close();
        return tong;
    }



    //Create Method HanMucChiTieu
    public ArrayList<HanMucChiTieu> selectALLHanMucChiTieu()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HanMucChiTieu> dt = new ArrayList<>();
        String sql= "SELECT * FROM HANMUCCHITIEU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    String loaiKC = cursor.getString(0);
                    String loaiKC_PhanCap = cursor.getString(1);
                    float hanMuc = cursor.getFloat(2);
                    String tgian = cursor.getString(3).split(" ")[0];

                    dt.add(new HanMucChiTieu(loaiKC, loaiKC_PhanCap, hanMuc,tgian));
                }while (cursor.moveToNext());
            }
        }
        db.close();
        return dt;
    }

    public boolean insertHanMucChiTieu(HanMucChiTieu x)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("LOAIKC",x.loaiKC);
        values.put("LOAIKC_PHANCAP",x.loaiKC_PhanCap);
        values.put("HANMUC",x.hanMuc);
        values.put("TGIAN",x.tgian);

        boolean kq = db.insert("HANMUCCHITIEU",null,values)>0;
        db.close();

        return kq;
    }

    public boolean updateHanMucChiTieu(HanMucChiTieu x)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE HANMUCCHITIEU SET HANMUC = " + x.hanMuc + " WHERE LOAIKC = '" + x.loaiKC +"' AND LOAIKC_PHANCAP = '" + x.loaiKC_PhanCap +"'";
        boolean kq;
        try
        {
            db.execSQL(sql);
            kq = true;
        }
        catch (Exception ex)
        {
            kq = false;
        }

        db.close();
        return kq;
    }

    public boolean deleteHanMucChiTieu(String loaiKC, String loaiKC_PhanCap)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        boolean kq;
        try
        {
            db.execSQL("DELETE FROM HANMUCCHITIEU WHERE LOAIKC = '" + loaiKC + "' AND LOAIKC_PHANCAP = '" + loaiKC_PhanCap +"'");
            kq = true;
        }
        catch (Exception ex)
        {
            kq = false;
        }

        db.close();
        return kq;
    }

    public HanMucChiTieu searchHanMucChiTieu(String _loaiKC, String _loaiKC_PhanCap)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT * FROM HANMUCCHITIEU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    if(cursor.getString(0).equals(_loaiKC) && cursor.getString(1).equals(_loaiKC_PhanCap))
                    {
                        float hanMuc = cursor.getFloat(2);
                        String tgian = cursor.getString(3).split(" ")[0];

                        db.close();
                        return new HanMucChiTieu(_loaiKC, _loaiKC_PhanCap, hanMuc, tgian);
                    }
                }while (cursor.moveToNext());
            }
        }

        db.close();
        return null;
    }

    public HanMucChiTieu searchHanMucChiTieu_TheoThang(String _loaiKC, String _loaiKC_PhanCap, int _month, int _year)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT * FROM HANMUCCHITIEU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    int month = Integer.parseInt(cursor.getString(3).split(" ")[0].split("/")[1]);
                    int year = Integer.parseInt(cursor.getString(3).split(" ")[0].split("/")[2]);

                    if(cursor.getString(0).equals(_loaiKC) && cursor.getString(1).equals(_loaiKC_PhanCap) && month == _month && year == _year)
                    {
                        float hanMuc = cursor.getFloat(2);
                        String tgian = cursor.getString(3).split(" ")[0];

                        db.close();
                        return new HanMucChiTieu(_loaiKC, _loaiKC_PhanCap, hanMuc, tgian);
                    }

                }while (cursor.moveToNext());
            }
        }

        db.close();
        return null;
    }

    public ArrayList<HanMucChiTieu> searchHanMucChiTieu_LoaiKhoanChi(String _loaiKC)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HanMucChiTieu> dt = new ArrayList<>();
        String sql= "SELECT * FROM HANMUCCHITIEU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    if(cursor.getString(0).equals(_loaiKC))
                    {
                        String loai_PhanCap = cursor.getString(1);
                        float hanMuc = cursor.getFloat(2);
                        String tgian = cursor.getString(3).split(" ")[0];

                        dt.add(new HanMucChiTieu(_loaiKC, loai_PhanCap, hanMuc,tgian));
                    }
                }while (cursor.moveToNext());
            }
        }

        db.close();
        return dt;
    }



    //Create Method ChiTieu
    public ArrayList<ChiTieu> selectALLChiTieu()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChiTieu> dt = new ArrayList<>();
        String sql= "SELECT * FROM CHITIEU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    int maCT = cursor.getInt(0);
                    String tenCT = cursor.getString(1);
                    float soTien = cursor.getFloat(2);
                    String tgian =  cursor.getString(3).split(" ")[0];
                    String loaiKC = cursor.getString(4);
                    String loaiKC_PhanCap = cursor.getString(5);
                    String sdt = cursor.getString(6);
                    String tenVi = cursor.getString(7);

                    dt.add(new ChiTieu(maCT,tenCT,soTien,tgian,loaiKC, loaiKC_PhanCap,sdt,tenVi));
                }while (cursor.moveToNext());
            }
        }
        db.close();
        return dt;
    }

    public boolean insertChiTieu(ChiTieu x)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TENCT",x.tenCT);
        values.put("SOTIEN",x.soTien);
        values.put("TGIAN",x.tgian);
        values.put("LOAIKC",x.loaiKC);
        values.put("LOAIKC_PHANCAP",x.loaiKC_PhanCap);
        values.put("SDT",x.sdt);
        values.put("TENVI",x.tenVi);

        boolean kq = db.insert("CHITIEU",null,values)>0;
        db.close();

        return kq;
    }

    public boolean updateChiTieu(ChiTieu x)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TENCT",x.tenCT);
        values.put("SOTIEN",x.soTien);
        values.put("TGIAN",x.tgian);
        values.put("LOAIKC",x.loaiKC);
        values.put("LOAIKC_PHANCAP",x.loaiKC_PhanCap);
        values.put("SDT",x.sdt);
        values.put("TENVI",x.tenVi);
        String []where = {String.valueOf(x.maCT)};

        boolean kq = db.update("CHITIEU",values,"MACT = ?",where)>0;

        return kq;
    }

    public boolean deleteChiTieu(int maCT)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String []values = {String.valueOf(maCT)};
        boolean kq = db.delete("CHITIEU","MACT = ?",values)>0;

        return kq;
    }

    public ChiTieu searchChiTieu(int _maCT)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT * FROM CHITIEU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    if(cursor.getInt(0) == _maCT)
                    {
                        int maCT = cursor.getInt(0);
                        String tenCT = cursor.getString(1);
                        float soTien = cursor.getFloat(2);
                        String tgian =  cursor.getString(3).split(" ")[0];;
                        String loaiKC = cursor.getString(4);
                        String loaiKC_PhanCap = cursor.getString(5);
                        String sdt = cursor.getString(6);
                        String tenVi = cursor.getString(7);

                        db.close();
                        return new ChiTieu(maCT,tenCT,soTien,tgian,loaiKC, loaiKC_PhanCap,sdt,tenVi);
                    }
                }while (cursor.moveToNext());
            }
        }

        db.close();
        return null;
    }

    public ArrayList<ChiTieu> searchChiTieu_TheoNgay(String _sdt, int _ngay, int _thang, int _nam)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChiTieu> dt = new ArrayList<>();
        String sql= "SELECT * FROM CHITIEU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    int ngay = Integer.parseInt(cursor.getString(3).split(" ")[0].split("/")[0]);
                    int thang = Integer.parseInt(cursor.getString(3).split(" ")[0].split("/")[1]);
                    int nam = Integer.parseInt(cursor.getString(3).split(" ")[0].split("/")[2]);

                    if(cursor.getString(6).equals(_sdt) && ngay == _ngay && thang == _thang && nam == _nam)
                    {
                        int maCT = cursor.getInt(0);
                        String tenCT = cursor.getString(1);
                        float soTien = cursor.getFloat(2);
                        String tgian =  cursor.getString(3).split(" ")[0];
                        String loaiKC = cursor.getString(4);
                        String loaiKC_PhanCap = cursor.getString(5);
                        String sdt = cursor.getString(6);
                        String tenVi = cursor.getString(7);

                        dt.add(new ChiTieu(maCT,tenCT,soTien,tgian,loaiKC, loaiKC_PhanCap,sdt,tenVi));
                    }
                }while (cursor.moveToNext());
            }
        }

        db.close();
        return dt;
    }

    public ArrayList<ChiTieu> searchChiTieu_TheoThang(String _sdt, int _thang, int _nam)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChiTieu> dt = new ArrayList<>();
        String sql= "SELECT * FROM CHITIEU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    int thang = Integer.parseInt(cursor.getString(3).split(" ")[0].split("/")[1]);
                    int nam = Integer.parseInt(cursor.getString(3).split(" ")[0].split("/")[2]);

                    if(cursor.getString(6).equals(_sdt) && thang == _thang && nam == _nam)
                    {
                        int maCT = cursor.getInt(0);
                        String tenCT = cursor.getString(1);
                        float soTien = cursor.getFloat(2);
                        String tgian =  cursor.getString(3).split(" ")[0];
                        String loaiKC = cursor.getString(4);
                        String loaiKC_PhanCap = cursor.getString(5);
                        String sdt = cursor.getString(6);
                        String tenVi = cursor.getString(7);

                        dt.add(new ChiTieu(maCT,tenCT,soTien,tgian,loaiKC, loaiKC_PhanCap,sdt,tenVi));
                    }
                }while (cursor.moveToNext());
            }
        }

        db.close();
        return dt;
    }

    public ArrayList<ChiTieu> searchChiTieu_TheoNam(String _sdt, int _nam)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChiTieu> dt = new ArrayList<>();
        String sql= "SELECT * FROM CHITIEU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    int nam = Integer.parseInt(cursor.getString(3).split(" ")[0].split("/")[2]);
                    if(cursor.getString(6).equals(_sdt) && nam == _nam)
                    {
                        int maCT = cursor.getInt(0);
                        String tenCT = cursor.getString(1);
                        float soTien = cursor.getFloat(2);
                        String tgian =  cursor.getString(3).split(" ")[0];
                        String loaiKC = cursor.getString(4);
                        String loaiKC_PhanCap = cursor.getString(5);
                        String sdt = cursor.getString(6);
                        String tenVi = cursor.getString(7);

                        dt.add(new ChiTieu(maCT,tenCT,soTien,tgian,loaiKC, loaiKC_PhanCap,sdt,tenVi));
                    }
                }while (cursor.moveToNext());
            }
        }

        db.close();
        return dt;
    }

    public float tinhTongChiTieuTrenHanMuc(String sdt, String loaiKC, String loaiKC_PhanCap, int _month, int _year)
    {
        float tong = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT * FROM CHITIEU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    int month = Integer.parseInt(cursor.getString(3).split(" ")[0].split("/")[1]);
                    int year = Integer.parseInt(cursor.getString(3).split(" ")[0].split("/")[2]);

                    if (cursor.getString(6).equals(sdt) && cursor.getString(4).equals(loaiKC) && cursor.getString(5).equals(loaiKC_PhanCap) && month == _month && year == _year)
                    {
                        float soTien = cursor.getFloat(2);
                        tong += soTien;
                    }

                }while (cursor.moveToNext());
            }
        }
        db.close();
        return tong;
    }

    public float tinhTongChiTieu(String sdt)
    {
        float tong = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT * FROM CHITIEU";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    if (cursor.getString(6).equals(sdt))
                    {
                        float soTien = cursor.getFloat(2);
                        tong += soTien;
                    }
                }while (cursor.moveToNext());
            }
        }
        db.close();
        return tong;
    }



    //Load image dùng Bitmap
    public static Bitmap loadImage(Context context, String fileName)
    {
        AssetManager assetManager = context.getAssets();
        try
        {
            InputStream is = assetManager.open(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }



}
