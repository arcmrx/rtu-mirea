package com.example.volkov_av;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MainActivity: onCreate()");
    }

    public void onNextActivity(View view) {
        Toast.makeText(this, "Next Activity", Toast.LENGTH_SHORT).show();
        EditText nameText = findViewById(R.id.editName);
        EditText groupNum = findViewById(R.id.editGroupNum);
        EditText Age = findViewById(R.id.editAge);
        EditText Mark = findViewById(R.id.editMark);

        String name = nameText.getText().toString();
        String group = groupNum.getText().toString();
        String age = Age.getText().toString();
        String mark = Mark.getText().toString();

        Intent intent = new Intent(this, NewActivity.class);
        intent.putExtra("Name", name);
        intent.putExtra("Group", group);
        intent.putExtra("Age", age);
        intent.putExtra("Mark", mark);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity: onResume()");
        Toast.makeText(getApplicationContext(), "Volkov A.V. IKBO-66-23", Toast.LENGTH_SHORT).show();
        Button myButton = findViewById(R.id.buttonProgrammatically);
        // Действие при нажатии на кнопку, например:
        myButton.setOnClickListener(this::onNextActivity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity: onDestroy()");
    }
}