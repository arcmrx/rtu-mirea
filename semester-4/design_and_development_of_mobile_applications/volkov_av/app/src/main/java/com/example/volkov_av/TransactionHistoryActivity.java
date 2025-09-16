package com.example.volkov_av;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private List<Transaction> transactionList;
    private TransactionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        RecyclerView rvTransactions = findViewById(R.id.rv_transactions);
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        rvTransactions.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        transactionList = new ArrayList<>();
        adapter = new TransactionAdapter(this, transactionList);
        rvTransactions.setAdapter(adapter);

        dbHelper = new DBHelper(this);

        loadTransactions();
    }

    private void loadTransactions() {
        transactionList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM transactions ORDER BY date DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Transaction t = new Transaction();
                t.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                t.setType(cursor.getString(cursor.getColumnIndexOrThrow("type")));
                t.setCategory(cursor.getString(cursor.getColumnIndexOrThrow("category")));
                t.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("amount")));
                t.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                t.setNote(cursor.getString(cursor.getColumnIndexOrThrow("note")));
                transactionList.add(t);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        Log.d("DB_DEBUG", "Transactions loaded: " + transactionList.size());
        adapter.updateData(transactionList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTransactions();
    }
}
