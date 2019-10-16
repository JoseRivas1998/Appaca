package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import edu.csuci.appaca.R;

public class FruitCatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_catch);
        final ImageView strawberry = findViewById(R.id.strawberry);
        strawberry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exit = new Intent(getApplicationContext(), GameOverActivity.class);
                startActivity(exit);
            }
        });
    }

    public void exit(View view)
    {
        Intent exit = new Intent(getApplicationContext(), MinigameSelectActivity.class);
        startActivity(exit);
    }
}
