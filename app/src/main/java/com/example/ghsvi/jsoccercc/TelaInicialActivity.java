package com.example.ghsvi.jsoccercc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TelaInicialActivity extends AppCompatActivity {

    Button pesquisar;
    static EditText time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);

        pesquisar = (Button) findViewById(R.id.button);
        time = (EditText) findViewById(R.id.editText);

        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchActivity = new Intent(TelaInicialActivity.this, MainActivity.class);
                startActivity(launchActivity);
            }
        });

    }
}
