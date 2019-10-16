package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.csuci.appaca.R;

public class FruitCatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_catch);
    }

    public void exit(View view)
    {
        Intent exit = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(exit);
    }
}
