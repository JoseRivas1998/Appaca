package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import edu.csuci.appaca.R;

public class AlpacaJumpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpaca_jump);
    }

    public void exit(View view)
    {
        setContentView(R.layout.activity_main);
    }
}
