package com.example.chloe.instantmessaging;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Chloe on 2016-11-30.
 */

public class JoinGroup extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Variable va = new Variable();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_group);

        findViewById(R.id.groupA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variable.socketPort = "12345";
                Variable.groupID = "12345";
                Intent main = new Intent(JoinGroup.this, ChatPage.class);
                main.putExtra("type",1);
                startActivity(main);
            }
        });

        findViewById(R.id.groupB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variable.socketPort = "23456";
                Variable.groupID = "23456";
                Intent main = new Intent(JoinGroup.this, ChatPage.class);
                main.putExtra("type",2);
                startActivity(main);
            }
        });

        findViewById(R.id.groupC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variable.socketPort = "34567";
                Variable.groupID = "34567";
                Intent main = new Intent(JoinGroup.this, ChatPage.class);
                main.putExtra("type",3);
                startActivity(main);
            }
        });
    }
}
