package com.example.chloe.instantmessaging;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class ShowGroup extends Activity {
    TextView show;
    TextView groupId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_group);

        show = (TextView) findViewById(R.id.showMember);
        groupId = (TextView) findViewById(R.id.groupID);
        groupId.setText(Variable.groupID);

        for(String s : Variable.groupMember) {
            show.append(s + "\n");
        }
    }
}
