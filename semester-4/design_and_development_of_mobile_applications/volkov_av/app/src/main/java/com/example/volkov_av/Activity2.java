package com.example.volkov_av;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class NewActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new);
        TextView messageText = findViewById(R.id.textMessage);

        MyObject myObject = (MyObject)
                getIntent().getSerializableExtra("myObject");
        if (myObject != null) {
            String message = "Фамилия и имя:\n" + myObject.getName();
            messageText.setText(message);
        }


        Button backButton = findViewById(R.id.buttonBackToMainActivity);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}