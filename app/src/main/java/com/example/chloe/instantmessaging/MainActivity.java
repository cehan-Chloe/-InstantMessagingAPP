package com.example.chloe.instantmessaging;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
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
                connServer();
                Variable.userName = inputID.getText().toString();
                Intent main = new Intent(MainActivity.this, JoinGroup.class);
                startActivity(main);
            }
        });
    }

    public void connServer() {
        AsyncTask<String, String, Void> read = new AsyncTask<String, String, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    Variable.socket = new Socket("10.0.2.2", 12345);
                    Variable.bw = new DataOutputStream(Variable.socket.getOutputStream());
                    Variable.br = new DataInputStream(Variable.socket.getInputStream());
                    publishProgress("@success");
                } catch (UnknownHostException e) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "cannot connect server", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();

                } catch (IOException e) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "cannot connect server", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate (String... values){
                if (values[0].equals("@success")) {
                    Toast.makeText(MainActivity.this, "connected", Toast.LENGTH_SHORT).show();
                }

                super.onProgressUpdate(values);
            }
        };read.execute();
    }
}

