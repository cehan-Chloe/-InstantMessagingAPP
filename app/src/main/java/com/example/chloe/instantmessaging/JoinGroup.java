package com.example.chloe.instantmessaging;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;


public class JoinGroup extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_group);



        findViewById(R.id.groupA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variable.groupID = "1";
                Intent main = new Intent(JoinGroup.this, ChatPage.class);
                startActivity(main);
            }
        });

        findViewById(R.id.groupB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variable.groupID = "2";
                Intent main = new Intent(JoinGroup.this, ChatPage.class);
                startActivity(main);
            }
        });

        findViewById(R.id.groupC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variable.groupID = "3";
                Intent main = new Intent(JoinGroup.this, ChatPage.class);
                startActivity(main);
            }
        });
    }


}