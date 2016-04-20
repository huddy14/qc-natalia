package com.example.natalia.quadraticfun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //przypisanie do przycisku obsługi kliknięcia
        ((Button) findViewById(R.id.button)).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {


        String textA = ((EditText)findViewById(R.id.editText)).getText().toString();
        String textB = ((EditText)findViewById(R.id.editText2)).getText().toString();
        String textC = ((EditText)findViewById(R.id.editText3)).getText().toString();


        if (textA == "0")
        {
            /*Toast.makeText(MainActivity.this, "Fields must be filled", Toast.LENGTH_LONG) .show();*/
            Toast.makeText(getApplicationContext(), "A cant be zero", Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            try
            {
                double abc[] = new double[3];
                abc[0] = Double.parseDouble(textA);
                abc[1] = Double.parseDouble(textB);
                abc[2] = Double.parseDouble(textC);
                Intent intent = new Intent(MainActivity.this,Draw.class);
                intent.putExtra("parametry",abc);
                startActivity(intent);
            }
            catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(),"Please enter correct values",Toast.LENGTH_SHORT).show();
            }
        }


    }

}
