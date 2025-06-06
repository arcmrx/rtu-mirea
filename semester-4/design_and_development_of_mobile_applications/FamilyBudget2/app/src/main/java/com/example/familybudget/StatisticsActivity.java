package com.example.familybudget;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    TextView tvTotalIncome, tvTotalExpense, tvBalance;
    PieChart pieChart;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tvTotalIncome = findViewById(R.id.tv_total_income);
        tvTotalExpense = findViewById(R.id.tv_total_expense);
        tvBalance = findViewById(R.id.tv_balance);
        pieChart = findViewById(R.id.pie_chart);
        dbHelper = new DBHelper(this);

        loadStats();
    }

    @SuppressLint("SetTextI18n")
    void loadStats() {
        double income = 0, expense = 0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT type, amount FROM transactions", null);

        if (cursor.moveToFirst()) {
            do {
                String type = cursor.getString(0);
                double amount = cursor.getDouble(1);
                if (type.equals("income")) income += amount;
                else expense += amount;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        double balance = income - expense;
        tvTotalIncome.setText("Доходов: " + income + " ₽");
        tvTotalExpense.setText("Расходов: " + expense + " ₽");
        tvBalance.setText("Баланс: " + balance + " ₽");

        showPieChart(income, expense);
    }

    void showPieChart(double income, double expense) {
        List<PieEntry> entries = new ArrayList<>();
        if (income > 0) entries.add(new PieEntry((float) income, "Доход"));
        if (expense > 0) entries.add(new PieEntry((float) expense, "Расход"));

        PieDataSet dataSet = new PieDataSet(entries, "Статистика");
        dataSet.setColors(Color.GREEN, Color.RED);
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
    }
}
