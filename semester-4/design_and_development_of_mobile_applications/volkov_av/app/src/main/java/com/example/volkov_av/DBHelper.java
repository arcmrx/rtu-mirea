package com.example.volkov_av;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "family_budget.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE transactions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type TEXT, " +
                "category TEXT, " +
                "amount REAL, " +
                "date TEXT, " +
                "note TEXT)";
        db.execSQL(createTable);

        Log.d("DBHelper", "Таблица transactions создана");
    }

    public long insertTransaction(String type, String category, double amount, String date, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", type);
        values.put("category", category);
        values.put("amount", amount);
        values.put("date", date);
        values.put("note", note);
        Log.d("DBHelper", "Добавление: " + type + ", " + category + ", " + amount + ", " + date + ", " + note);
        return db.insert("transactions", null, values);
    }

    public Cursor getAllTransactions() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM transactions ORDER BY date DESC", null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS transactions");
        onCreate(db);
    }
}
