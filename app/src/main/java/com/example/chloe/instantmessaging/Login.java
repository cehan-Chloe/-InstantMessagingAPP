package com.example.chloe.instantmessaging;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Chloe on 2016-11-27.
 */

public class Login extends Activity implements View.OnClickListener{
    EditText inputID;
    Button buttonJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        buttonJoin = (Button) findViewById(R.id.btnJoin);
        buttonJoin.setOnClickListener(this);

        //connect to the server
        //direct to main page
    }

    @Override
    public void onClick(View v) {

    }
}
