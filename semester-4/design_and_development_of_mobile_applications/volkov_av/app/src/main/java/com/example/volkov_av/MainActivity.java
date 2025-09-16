package com.example.volkov_av;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView tvBalance;
    ImageButton btnAddIncome, btnAddExpense, btnHistory;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBalance = findViewById(R.id.tv_balance);
        btnAddIncome = findViewById(R.id.btn_add_income);
        btnAddExpense = findViewById(R.id.btn_add_expense);
        btnHistory = findViewById(R.id.btn_history);
        dbHelper = new DBHelper(this);

        btnAddIncome.setOnClickListener(v -> openAddTransaction("income"));
        btnAddExpense.setOnClickListener(v -> openAddTransaction("expense"));
        btnHistory.setOnClickListener(v -> startActivity(new Intent(this, TransactionHistoryActivity.class)));
        btnHistory.setOnClickListener(v -> startActivity(new Intent(this, TransactionHistoryActivity.class)));
        ImageButton btnStats = findViewById(R.id.btn_stats);
        btnStats.setOnClickListener(v -> startActivity(new Intent(this, StatisticsActivity.class)));

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBalance();
    }


    void openAddTransaction(String type) {
        Intent intent = new Intent(this, AddTransactionActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    @SuppressLint("DefaultLocale")
    void updateBalance() {
        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getAllTransactions();

        double income = 0;
        double expense = 0;

        if (cursor.moveToFirst()) {
            do {
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));

                if ("income".equals(type)) {
                    income += amount;
                } else if ("expense".equals(type)) {
                    expense += amount;
                }
            } while (cursor.moveToNext());
        }

        double balance = income - expense;

        TextView tvBalance = findViewById(R.id.tv_balance);
        tvBalance.setText(String.format("Баланс: %.2f ₽", balance));

        cursor.close();
    }


}
