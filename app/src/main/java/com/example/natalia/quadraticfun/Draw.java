package com.example.natalia.quadraticfun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Draw extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        double []abc;
        abc = getIntent().getDoubleArrayExtra("parametry");
        ((DrawFunction)findViewById(R.id.plot)).setFunctionParameters(abc[0],abc[1],abc[2]);
    }
}
