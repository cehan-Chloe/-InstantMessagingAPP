package com.example.chloe.instantmessaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Chloe on 2016-11-30.
 */

public class Variable {
    //groupID
    static public String groupID;
    // user name
    static public String userName;
    // socket
    public static Socket socket;
    // make in and out accessible by all of the program
    public static DataInputStream br;
    public static DataOutputStream bw;

    // use arraylist to show group member
    public static ArrayList<String> groupMember = new ArrayList<>();

}
