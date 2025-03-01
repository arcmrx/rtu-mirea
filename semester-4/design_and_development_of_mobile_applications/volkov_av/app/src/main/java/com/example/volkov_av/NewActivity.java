package com.example.volkov_av;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class NewActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new);

        TextView messageText = new TextView(this);
        messageText.setTextSize(26);
        messageText.setPadding(18, 18, 18, 18);


        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            String name = Objects.requireNonNull(arguments.get("Name")).toString();
            String group = Objects.requireNonNull(arguments.get("Group")).toString();
            String age = Objects.requireNonNull(arguments.get("Age")).toString();
            String mark = Objects.requireNonNull(arguments.get("Mark")).toString();
            messageText.setText("\n\nName: " + name + "\nGroup: " + group + "\nAge: " + age + "\nMark: " + mark);
        }

    }
}