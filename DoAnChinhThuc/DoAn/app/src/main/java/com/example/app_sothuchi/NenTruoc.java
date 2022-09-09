package com.example.app_sothuchi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NenTruoc extends AppCompatActivity {
    Button btnOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nen_truoc);

        anhxa();
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyClickOpen();
            }
        });
    }
    void anhxa()
    {
        btnOpen = findViewById(R.id.btnOpen);
    }

    public void xuLyClickOpen()
    {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);;
    }
}