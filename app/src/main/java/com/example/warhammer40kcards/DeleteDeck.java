package com.example.warhammer40kcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class DeleteDeck extends AppCompatActivity {
    Button delete,confirm,cancel,goBack;
    Spinner listOFiles;
    TextView decks;
    static final int READ_BLOCK_SIZE = 1000;
    ArrayAdapter ztt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_deck);
        delete = (Button) findViewById(R.id.delete);
        confirm = (Button) findViewById(R.id.confirm);
        cancel = (Button) findViewById(R.id.cancel);
        goBack = (Button) findViewById(R.id.goBack);
        listOFiles = (Spinner) findViewById(R.id.listOFiles);
        decks = (TextView) findViewById(R.id.decks);
        ztt= new ArrayAdapter(this, android.R.layout.simple_list_item_1, lOFs());
        ztt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listOFiles.setAdapter(ztt);
    }

    public void deleteClick(View view){
        delete.setVisibility(View.INVISIBLE);
        confirm.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
        goBack.setVisibility(View.INVISIBLE);
        listOFiles.setVisibility(View.INVISIBLE);
        decks.setVisibility(View.INVISIBLE);
    }

    public void confirmClick(View view){
        try {
            delete.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            goBack.setVisibility(View.VISIBLE);
            listOFiles.setVisibility(View.VISIBLE);
            decks.setVisibility(View.VISIBLE);
            String s = "";
            int count = 0;
            String temp = "";
            FileInputStream fileIn = openFileInput("listofdecks.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            String delim = "010101000101110100100111";
            String[] token = s.split(delim);
            temp = token[0];
            while (!temp.equals(listOFiles.getSelectedItem().toString())) {
                count++;
                temp = token[count];
            }
            deleteFile(temp);
            s = "";
            for (int i = 0; i < token.length; i++) {
                if (!(token[i].equals(temp) || token[i].equals(""))) {
                    s = s + "010101000101110100100111" + token[i];
                }
            }
            Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
            FileOutputStream fileout;
            deleteFile("listofdecks.txt");
            fileout = openFileOutput("listofdecks.txt", MODE_PRIVATE);
            OutputStreamWriter listoftitles = new OutputStreamWriter(fileout);
            listoftitles.write(s);
            listoftitles.close();
        }catch(Exception e){
            Toast.makeText(getBaseContext(), "failed delete", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        ztt= new ArrayAdapter(this, android.R.layout.simple_list_item_1, lOFs());
        ztt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listOFiles.setAdapter(ztt);
    }

    public void cancelClick(View view){
        delete.setVisibility(View.VISIBLE);
        confirm.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        goBack.setVisibility(View.VISIBLE);
        listOFiles.setVisibility(View.VISIBLE);
        decks.setVisibility(View.VISIBLE);
    }

    public void goBackClick(View view){
        Intent mcd = new Intent(getApplicationContext(), mcd.class);
        mcd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mcd);
    }

    public String[] lOFs(){
        String s="";
        try {
            FileInputStream fileIn = openFileInput("listofdecks.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        String delim="010101000101110100100111";
        String[] token=s.split(delim);

        return token;
    }
}