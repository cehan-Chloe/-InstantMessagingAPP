package com.example.chloe.instantmessaging;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    EditText ip;
    EditText editText;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip = (EditText) findViewById(R.id.ip);
        editText = (EditText) findViewById(R.id.editMessage);
        text = (TextView) findViewById(R.id.message);

        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });

        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    Socket socket = null;
    DataInputStream br = null;
    DataOutputStream bw = null;

    public void connect() {
        String ipAddress = ip.getText().toString();

        AsyncTask<String, String, Void> read = new AsyncTask<String, String, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    socket = new Socket(params[0], 12345);
                    br = new DataInputStream(socket.getInputStream());
                    bw = new DataOutputStream(socket.getOutputStream());
                    publishProgress("@success");
                } catch (UnknownHostException e) {
                    Toast.makeText(MainActivity.this, "cannot connect", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {

                    Toast.makeText(MainActivity.this, "cannot connect", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


                try {
                    String line;
                    while (true) {
                        if ((line = br.readLine()) != null) {
                            publishProgress(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                if(values[0].equals("@success")) {
                    Toast.makeText(MainActivity.this, "connected", Toast.LENGTH_SHORT).show();
                }
                text.append("she say: " + values[0] + "\n");
                super.onProgressUpdate(values);
            }

        };
        read.execute(ipAddress);
    }


    public void send() {
        try {
            if(bw != null) {
                text.append("me: " + editText.getText().toString() + "\n");
                bw.writeUTF(editText.getText().toString() + "\n");
                bw.flush();
                editText.setText("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
