package com.example.chloe.instantmessaging;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
/**
 * Created by Chloe on 2016-11-30.
 */

public class ChatPage extends AppCompatActivity{

    EditText editText;
    TextView text;
    TextView groupText;
    TextView statusText;
    String port;
    String groupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editMessage);
        text = (TextView) findViewById(R.id.message);
        groupText = (TextView) findViewById(R.id.group);
        statusText = (TextView) findViewById(R.id.status);
        Variable va = new Variable();
        port = Variable.socketPort;
        groupID = Variable.groupID;


        findViewById(R.id.btnJoinGroup).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // direct to join group page
                Intent joinGroup = new Intent(ChatPage.this, JoinGroup.class);
                startActivity(joinGroup);
            }
        });



        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

        // connect to the server
        System.out.println("port is: " + port);
        System.out.println(port != null);
        if (port != null){
            System.out.println("enter connect");
            connect();
            statusText.setText("Up");
        }
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
        groupText.setText(groupID);
        AsyncTask<String, String, Void> read = new AsyncTask<String, String, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    socket = new Socket(params[0], Integer.parseInt(port));
                    br = new DataInputStream(socket.getInputStream());
                    bw = new DataOutputStream(socket.getOutputStream());
                    publishProgress("@success");
                } catch (UnknownHostException e) {
                    Toast.makeText(ChatPage.this, "cannot connect", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {

                    Toast.makeText(ChatPage.this, "cannot connect", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


                try {
                    String line;
                    while (true) {
                        if ((line = br.readLine()) != null) {
                            publishProgress(line);
                            // add alert to user
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
                    Toast.makeText(ChatPage.this, "connected", Toast.LENGTH_SHORT).show();
                }
                text.append(values[0] + "\n");
                super.onProgressUpdate(values);
            }

        };
        read.execute("10.0.2.2");
    }


    public void send() {
        try {
            if(bw != null) {
                text.append("me: " + editText.getText().toString() + "\n");
                bw.writeUTF(Variable.userName + " says: " + editText.getText().toString() + "\n");
                bw.flush();
                editText.setText("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
