package com.example.familybudget;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {

    RecyclerView rvTransactions;
    DBHelper dbHelper;
    List<Transaction> transactionList;
    TransactionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        rvTransactions = findViewById(R.id.rv_transactions);
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DBHelper(this);

        loadTransactions();
    }

    void loadTransactions() {
        transactionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM transactions ORDER BY date DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Transaction t = new Transaction();
                t.setId(cursor.getInt(0));
                t.setType(cursor.getString(1));
                t.setCategory(cursor.getString(2));
                t.setAmount(cursor.getDouble(3));
                t.setDate(cursor.getString(4));
                t.setNote(cursor.getString(5));
                transactionList.add(t);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        adapter = new TransactionAdapter(transactionList);
        rvTransactions.setAdapter(adapter);
    }
}
