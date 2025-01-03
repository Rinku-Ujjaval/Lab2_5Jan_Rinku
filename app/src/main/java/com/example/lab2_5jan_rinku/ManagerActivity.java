package com.example.lab2_5jan_rinku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.util.List;

public class ManagerActivity extends AppCompatActivity {
    private List<PurchaseHistory> historyList;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manager);
        Button history = findViewById(R.id.buttonHistory);
        Button restock = findViewById(R.id.buttonRestock);
        historyList = (List<PurchaseHistory>) getIntent().getSerializableExtra("historyList");

        productList = (List<Product>) getIntent().getSerializableExtra("productList");
        history.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProductHistoryListActivity.class);
            intent.putExtra("historyList", (Serializable) historyList);
            startActivity(intent);
        });

        restock.setOnClickListener(view -> {
            Intent intent = new Intent(this, RestoreActivity.class);
            intent.putExtra("productList", (Serializable) productList);
            startActivity(intent);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}