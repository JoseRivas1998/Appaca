package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import android.os.Bundle;
import android.widget.ImageButton;

import edu.csuci.appaca.R;

public class MatchingActivity extends AppCompatActivity {

    private ImageButton returnToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        returnToMain = (ImageButton) findViewById(R.id.exitButton);

        returnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}




