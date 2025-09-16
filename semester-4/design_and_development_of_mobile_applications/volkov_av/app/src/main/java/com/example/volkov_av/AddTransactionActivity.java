package com.example.volkov_av;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddTransactionActivity extends AppCompatActivity {

    private static final String TAG = "AddTransactionActivity";
    EditText etAmount, etDate, etNote;
    AutoCompleteTextView spinnerCategory;
    Button btnSave;
    String type;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        etAmount = findViewById(R.id.et_amount);
        etDate = findViewById(R.id.et_date);
        etNote = findViewById(R.id.et_note);
        spinnerCategory = findViewById(R.id.spinner_category);
        btnSave = findViewById(R.id.btn_save);
        dbHelper = new DBHelper(this);

        type = getIntent().getStringExtra("type");

        if (type == null) {
            Toast.makeText(this, "Ошибка: не указан тип операции", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String[] incomeCategories = {"Зарплата", "Подарок", "Другое"};
        String[] expenseCategories = {"Еда", "Транспорт", "Развлечения", "Другое"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                type.equals("income") ? incomeCategories : expenseCategories);

        spinnerCategory.setAdapter(adapter);

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        etDate.setText(currentDate);

        etDate.setOnClickListener(v -> showDatePicker());
        btnSave.setOnClickListener(v -> saveTransaction());

        spinnerCategory.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) spinnerCategory.showDropDown();
        });

        spinnerCategory.setOnClickListener(v -> spinnerCategory.showDropDown());
    }

    void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String formattedMonth = String.format(Locale.getDefault(), "%02d", month + 1);
                    String formattedDay = String.format(Locale.getDefault(), "%02d", dayOfMonth);
                    String date = year + "-" + formattedMonth + "-" + formattedDay;
                    etDate.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    void saveTransaction() {
        String category = spinnerCategory.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String note = etNote.getText().toString().trim();

        if (amountStr.isEmpty()) {
            etAmount.setError("Введите сумму");
            return;
        }

        if (category.isEmpty()) {
            spinnerCategory.setError("Выберите категорию");
            return;
        }

        if (date.isEmpty()) {
            etDate.setError("Укажите дату");
            return;
        }

        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            etDate.setError("Неверный формат даты. Используйте ГГГГ-ММ-ДД");
            return;
        }

        double amount;
        try {
            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            amount = Objects.requireNonNull(format.parse(amountStr)).doubleValue();

            if (amount <= 0) {
                etAmount.setError("Сумма должна быть больше нуля");
                return;
            }
        } catch (ParseException | NullPointerException e) {
            etAmount.setError("Некорректная сумма");
            return;
        }

        // Используем метод insertTransaction из DBHelper
        try {
            long result = dbHelper.insertTransaction(type, category, amount, date, note);

            if (result == -1) {
                Log.e(TAG, "Ошибка вставки данных: type=" + type +
                        ", category=" + category + ", amount=" + amount +
                        ", date=" + date + ", note=" + note);
                Toast.makeText(this, "Ошибка сохранения", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "Данные сохранены, ID: " + result);
                Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            Log.e(TAG, "Ошибка базы данных: " + e.getMessage(), e);
            Toast.makeText(this, "Ошибка базы данных: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}