package com.example.volkov_av;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_2);

        TextView messageText = findViewById(R.id.textMessage);
        MyObject myObject = (MyObject)
                getIntent().getSerializableExtra("myObject");
        if (myObject != null) {
            String message = "Фамилия и имя:\n" + myObject.getName();
            messageText.setText(message);
        }

        Button backButton = findViewById(R.id.buttonBackToMainActivity);
        backButton.setOnClickListener(v -> finish());
        Button moreInfButton = findViewById(R.id.moreInfButton);
        moreInfButton.setOnClickListener(this::onActivity3);
    }

    public void onActivity3(View view) {
        Toast.makeText(this, "Activity №3", Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent(this, Activity3.class);
        startActivity(intent2);
    }
}