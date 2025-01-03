package com.example.lab2_5jan_rinku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView selectedProductText, quantityText, totalAmountText;
    private int selectedQuantity = 0;
    private Product selectedProduct = null;
    private ArrayList<Product> products = new ArrayList<>();
    private ProductAdapter productAdapter;

    private GridLayout glNumberPad;
    private List<PurchaseHistory> purchaseHistoryList = new ArrayList<>();

    @SuppressLint({"SetTextI18n", "DefaultLocale", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        selectedProductText = findViewById(R.id.selected_product_text);
        quantityText = findViewById(R.id.quantity_text);
        totalAmountText = findViewById(R.id.total_amount_text);
        Button managerButton = findViewById(R.id.managerButton);
        RecyclerView productListView = findViewById(R.id.rvProductList);
        Button buyButton = findViewById(R.id.buy_button);
        glNumberPad = findViewById(R.id.number_pad);

        // Add sample products
        products.add(new Product("Pants", 10, 20.44));
        products.add(new Product("Shoes", 100, 10.44));
        products.add(new Product("Hats", 30, 5.8));

        // Set ListView Adapter
        // Product selection
        productAdapter = new ProductAdapter(products, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                selectedProduct = product;
                selectedQuantity = product.getQuantity();
                selectedProductText.setText(selectedProduct.getName());
                totalAmountText.setText(String.format("%.2f", selectedProduct.getPrice()));
                quantityText.setText(String.valueOf(selectedProduct.getQuantity()));
            }
        });
        productListView.setLayoutManager(new LinearLayoutManager(this));
        productListView.setAdapter(productAdapter);

        // Number pad listener
        // Add logic for each number button to update `quantityText`.

        setupNumberPad();

        // Buy button listener
        buyButton.setOnClickListener(v -> {
            if (selectedProduct == null || selectedQuantity == 0) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else if (selectedQuantity > selectedProduct.getQuantity()) {
                quantityText.setText("");
                Toast.makeText(this, "Not enough current in the stock!", Toast.LENGTH_SHORT).show();
            } else {
                double total = selectedQuantity * selectedProduct.getPrice();
                totalAmountText.setText("Total: $" + String.format("%.2f", total));

                // Update product quantity

                PurchaseHistory purchase = new PurchaseHistory(
                        selectedProduct.getName(),
                        selectedQuantity,
                        selectedQuantity * selectedProduct.getPrice(),
                        new Date()
                );
                purchaseHistoryList.add(purchase);
                selectedProduct.setQuantity(selectedProduct.getQuantity() - selectedQuantity);
                // Reset UI
                showPurchaseDialog(selectedProduct.getName(), selectedQuantity, total);
// Add purchase to history
                productAdapter.notifyDataSetChanged();
                selectedProduct = null;
                selectedQuantity = 0;
                selectedProductText.setText("Selected Product: ");
                quantityText.setText("Quantity: 0");
                totalAmountText.setText("Total: $" + String.format("%.2f", 0.0));
            }
        });

        managerButton.setOnClickListener(v -> {

            Intent intent = new Intent(this, ManagerActivity.class);
            intent.putExtra("historyList", (Serializable) purchaseHistoryList);
            intent.putExtra("productList", (Serializable) products);
            startActivity(intent);
        });
    }

    private void showPurchaseDialog(String productName, int purchasedQuantity, Double total) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Purchase Confirmation");
        builder.setMessage("Your purchased is " + purchasedQuantity + " " + productName +
                "for " + total);

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @SuppressLint("DefaultLocale")
    private void setupNumberPad() {
        for (int i = 0; i <= 9; i++) {
            Button button = new Button(this);
            button.setText(String.valueOf(i));
            button.setTextSize(18);
            button.setLayoutParams(new GridLayout.LayoutParams());

            // Set button click listener
            int finalI = i;
            button.setOnClickListener(v -> {
                String currentQuantity = quantityText.getText().toString().replaceAll("[^0-9]", "");
                int newQuantity = Integer.parseInt(currentQuantity + finalI);
                selectedQuantity = newQuantity; // Update the selected quantity
                quantityText.setText(String.valueOf(selectedQuantity));
                double total = selectedQuantity * selectedProduct.getPrice();
                totalAmountText.setText("Total: $" + String.format("%.2f", total));
            });

            // Add button to GridLayout
            glNumberPad.addView(button);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check for updated product list
        List<Product> updatedProductList = (List<Product>) getIntent().getSerializableExtra("updatedProductList");
        if (updatedProductList != null) {
            products.clear();
            products.addAll(updatedProductList);
            productAdapter.notifyDataSetChanged();
        }
    }

}
