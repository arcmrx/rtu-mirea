package com.example.volkov_av;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button myButton = findViewById(R.id.buttonProgrammatically);
        myButton.setOnClickListener(this::onActivity2);
    }

    public void onActivity2(View view) {
        Toast.makeText(this, "Activity №2", Toast.LENGTH_SHORT).show();
        EditText nameText = findViewById(R.id.editName);
        String name = nameText.getText().toString();
        MyObject myObject = new MyObject(name);
        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra("myObject", myObject);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "Main Activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}