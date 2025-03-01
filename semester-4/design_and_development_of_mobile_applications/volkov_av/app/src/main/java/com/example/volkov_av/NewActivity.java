package com.example.volkov_av;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class NewActivity extends AppCompatActivity {

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
            messageText.setText("Name:" + name);
        }

    }
}