package com.example.volkov_av;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StatisticsActivity extends AppCompatActivity {

    private TextView tvTotalIncome, tvTotalExpense, tvBalance;
    private PieChart pieChart;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tvTotalIncome = findViewById(R.id.tv_total_income);
        tvTotalExpense = findViewById(R.id.tv_total_expense);
        tvBalance = findViewById(R.id.tv_balance);
        pieChart = findViewById(R.id.pie_chart);
        dbHelper = new DBHelper(this);

        loadStatistics();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStatistics();
    }

    private void loadStatistics() {
        double totalIncome = 0;
        double totalExpense = 0;
        Map<String, Float> expenseCategories = new HashMap<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT type, category, amount FROM transactions", null);

        if (cursor.moveToFirst()) {
            do {
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));

                if ("income".equals(type)) {
                    totalIncome += amount;
                } else if ("expense".equals(type)) {
                    totalExpense += amount;
                    expenseCategories.put(category,
                            expenseCategories.getOrDefault(category, 0f) + (float) amount);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();

        // Форматирование чисел
        NumberFormat format = NumberFormat.getNumberInstance(Locale.getDefault());
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);

        // Обновление UI
        tvTotalIncome.setText(format.format(totalIncome) + " ₽");
        tvTotalExpense.setText(format.format(totalExpense) + " ₽");
        tvBalance.setText(format.format(totalIncome - totalExpense) + " ₽");

        // Настройка диаграммы
        setupPieChart(expenseCategories);
    }

    private void setupPieChart(Map<String, Float> expenses) {
        if (expenses.isEmpty()) {
            pieChart.setVisibility(View.GONE);
            return;
        }

        pieChart.setVisibility(View.VISIBLE);

        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : expenses.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(12f);

        // Собственная палитра цветов для темной темы
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FF5722")); // Глубокий оранжевый
        colors.add(Color.parseColor("#2196F3")); // Синий
        colors.add(Color.parseColor("#4CAF50")); // Зеленый
        colors.add(Color.parseColor("#9C27B0")); // Фиолетовый
        colors.add(Color.parseColor("#FFC107")); // Янтарный
        colors.add(Color.parseColor("#03A9F4")); // Голубой
        colors.add(Color.parseColor("#CDDC39")); // Лаймовый
        colors.add(Color.parseColor("#795548")); // Коричневый
        colors.add(Color.parseColor("#607D8B")); // Серо-голубой

        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);

        // Настройка стилей
        pieChart.setBackgroundColor(Color.TRANSPARENT);
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);

        // Настройка легенды
        Legend legend = pieChart.getLegend();
        legend.setTextColor(Color.WHITE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        // Настройка центра круга
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setHoleRadius(30f); // Размер отверстия в центре
        pieChart.setTransparentCircleRadius(35f);

        // Анимация
        pieChart.animateY(1000, Easing.EaseInOutCubic);

        pieChart.invalidate(); // Обновление диаграммы
    }
}