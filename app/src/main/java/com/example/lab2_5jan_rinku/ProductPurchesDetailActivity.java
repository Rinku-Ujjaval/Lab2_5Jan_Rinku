package com.example.lab2_5jan_rinku;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;

public class ProductPurchesDetailActivity extends AppCompatActivity {
    private TextView tvDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proudct_purches_detail_acivity);

        tvDetails = findViewById(R.id.tvDetails);

        // Retrieve the purchase details
        PurchaseHistory purchase = (PurchaseHistory) getIntent().getSerializableExtra("purchaseDetails");

        // Display details
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String details = "Product: " + purchase.getName() +
                "\nQuantity: " + purchase.getQuantity() +
                "\nTotal Price: $" + String.format("%.2f", purchase.getTotalPrice()) +
                "\nPurchase Date: " + sdf.format(purchase.getPurchaseDate());
        tvDetails.setText(details);
    }
}