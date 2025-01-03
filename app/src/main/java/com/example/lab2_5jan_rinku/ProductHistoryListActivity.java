package com.example.lab2_5jan_rinku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ProductHistoryListActivity extends AppCompatActivity {
    private ListView lvHistory;
    private List<PurchaseHistory> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_history_list);

        lvHistory = findViewById(R.id.lvHistory);

        // Retrieve the history list from the intent
        historyList = (List<PurchaseHistory>) getIntent().getSerializableExtra("historyList");

        // Populate the ListView
        assert historyList != null;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                historyList.stream().map(history ->
                        history.getName() + " - " + history.getQuantity() + " $" +
                                String.format("%.2f", history.getTotalPrice())
                ).toArray(String[]::new)
        );
        lvHistory.setAdapter(adapter);

        // Handle item click
        lvHistory.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, ProductPurchesDetailActivity.class);
            intent.putExtra("purchaseDetails", historyList.get(position));
            startActivity(intent);
        });
    }
}