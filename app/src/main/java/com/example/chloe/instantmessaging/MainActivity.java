package com.example.chloe.instantmessaging;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Chloe on 2016-11-27.
 */

public class MainActivity extends Activity {
    EditText inputID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        inputID = (EditText) findViewById(R.id.id);

        findViewById(R.id.btnJoin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variable.userName = inputID.getText().toString();
                Intent main = new Intent(MainActivity.this, ChatPage.class);
                startActivity(main);
            }
        });
    }

}

