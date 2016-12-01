package com.example.chloe.instantmessaging;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;


public class ChatPage extends AppCompatActivity{

    EditText editText;
    TextView text;
    TextView groupText;
    TextView statusText;
    String groupID;
    ArrayList<String> chatHistory1 = new ArrayList<>();
    ArrayList<String> chatHistory2 = new ArrayList<>();
    ArrayList<String> chatHistory3 = new ArrayList<>();

    private  AsyncTask<String, String, Void> read;
    private boolean checkStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = (EditText) findViewById(R.id.editMessage);
        text = (TextView) findViewById(R.id.message);
        groupText = (TextView) findViewById(R.id.group);
        statusText = (TextView) findViewById(R.id.status);
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

        findViewById(R.id.btnQuitGroup).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                quit();
                text.setText("");
                groupText.setText("Not Connected");
            }
        });

        findViewById(R.id.btnGroupMember).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showMember();
                startActivity(new Intent(ChatPage.this, ShowGroup.class));
            }
        });

        // connect to the server
        connect();
        statusText.setText("Up");
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public void connect() {
        groupText.setText(groupID);
        addUserToGroup();
        read = new AsyncTask<String, String, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    String line;
                    while (!checkStop) {
                        if ((line = Variable.br.readUTF()) != null) {
                            System.out.println("received line: " + line);
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
                if (values[0].equals("@success")) {
                    groupText.setText(Variable.groupID);
                    text.append(Variable.userName + "entered the group" + "\n");
                    Toast.makeText(ChatPage.this, "connected", Toast.LENGTH_SHORT).show();
                } else {
                    String[] s = values[0].split(",");
                    if(s[0].equals("PUBLISH")) {
                        if (s[1].equals("1")) {
                            chatHistory1.add(s[2] + ": " + s[3] + "\n");
                        } else if (s[1].equals("2")) {
                            chatHistory2.add(s[2] + ": " + s[3] + "\n");
                        } else if (s[1].equals("3")) {
                            chatHistory3.add(s[2] + ": " + s[3] + "\n");
                        }
                        text.append(s[2] + " : " + s[3] + "\n");
                    }else if(s[0].equals("MEMBER")) {
                        System.out.println("Get member message ");
                        for(int i = 2; i < s.length; i++) {
                            Variable.groupMember.add(s[i]);
                        }

                    }
                }
                super.onProgressUpdate(values);
            }
        };
        read.execute("10.0.2.2");
    }

    public void send() {
        try {
            if(Variable.groupID.equals("1")) {
                chatHistory1.add(Variable.userName + ": " + editText.getText().toString() + "\n");
            } else if(Variable.groupID.equals("2")) {
                chatHistory2.add(Variable.userName + ": " + editText.getText().toString() + "\n");
            } else if(Variable.groupID.equals("3")) {
                chatHistory3.add(Variable.userName + ": " + editText.getText().toString() + "\n");
            }
            text.append(Variable.userName + ": " + editText.getText().toString() + "\n");

            Variable.bw.writeUTF("PUBLISH" + "," + Variable.groupID + "," + Variable.userName + "," + editText.getText().toString());
            Variable.bw.flush();
            editText.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMember(){
        try {
            Variable.bw.writeUTF("MEMBER" + "," + groupID + "\n");
            Variable.bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void quit() {
        // send quit group message to server
        try {
            Variable.bw.writeUTF("QUIT" + "," + Variable.groupID + "," + Variable.userName + "\n");
            Variable.bw.flush();
            Toast.makeText(ChatPage.this, "quit group", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        checkStop = true;
        if (read != null){
            read.cancel(true);
        }
        super.onStop();
    }

    public void addUserToGroup(){
        try {
            Variable.bw.writeUTF("ADD" + "," + Variable.groupID + "," + Variable.userName + "\n");
            Variable.bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
