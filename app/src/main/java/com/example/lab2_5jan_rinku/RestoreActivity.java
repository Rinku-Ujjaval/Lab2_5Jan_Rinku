package com.example.lab2_5jan_rinku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class RestoreActivity extends AppCompatActivity {
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private String selectedQuantity = "";
    private Product selectedProduct = null;
    private EditText etQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_restore);
        RecyclerView productListView = findViewById(R.id.rvProductList);
        etQuantity = findViewById(R.id.ediTextQuantity);
        Button btnOk = findViewById(R.id.btnOk);
        Button btnCancel = findViewById(R.id.btnCancel);

        // Retrieve the product list from the intent
        productList = (List<Product>) getIntent().getSerializableExtra("productList");
        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                selectedProduct = product;
                selectedQuantity = String.valueOf(product.getQuantity());
                etQuantity.setText(String.valueOf(selectedProduct.getQuantity()));
            }
        });
        productListView.setLayoutManager(new LinearLayoutManager(this));
        productListView.setAdapter(productAdapter);
        // Populate the Spinner with product names

        btnOk.setOnClickListener(v -> validateAndUpdateQuantity());
        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManagerActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void validateAndUpdateQuantity() {
        selectedQuantity = etQuantity.getText().toString().trim();

        if (selectedProduct == null || selectedQuantity.isEmpty()) {
            Toast.makeText(this, "Please select a product and enter a valid quantity.", Toast.LENGTH_SHORT).show();
            return;
        }

        int addedQuantity = Integer.parseInt(selectedQuantity);
        if (addedQuantity <= 0) {
            Toast.makeText(this, "Quantity must be greater than 0.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the product quantity
        for (Product product : productList) {
            if (product.getName().equals(selectedProduct.getName())) {
                product.setQuantity(product.getQuantity() + addedQuantity);
                break;
            }
        }

        // Navigate back to MainActivity with updated product list
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("updatedProductList", (Serializable) productList);
        startActivity(intent);
        finish();
    }

}
