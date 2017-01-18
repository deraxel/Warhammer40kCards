package com.example.warhammer40kcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ModDeck extends AppCompatActivity {
    Button modifyDeck,goBack;
    Spinner listOFiles;
    TextView decks;
    static final int READ_BLOCK_SIZE = 1000;
    ArrayAdapter ztt;
    String dtbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_deck);
        modifyDeck = (Button) findViewById(R.id.modifyDeck);
        goBack = (Button) findViewById(R.id.goBack);
        listOFiles = (Spinner) findViewById(R.id.listOFiles);
        decks = (TextView) findViewById(R.id.decks);
        ztt= new ArrayAdapter(this, android.R.layout.simple_list_item_1, lOFs());
        ztt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listOFiles.setAdapter(ztt);

    }

    public void goBackClick(View view){
        Intent mcd = new Intent(getApplicationContext(), mcd.class);
        mcd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mcd);
    }

    public void modifySpecificDeckClick(View view){
        dtbm=listOFiles.getSelectedItem().toString();
        modifyDeck.setVisibility(View.INVISIBLE);
        goBack.setVisibility(View.INVISIBLE);
        listOFiles.setVisibility(View.INVISIBLE);
        decks.setVisibility(View.INVISIBLE);
    }

    public void modifyDeckClick(View view){
        dtbm=listOFiles.getSelectedItem().toString();
        modifyDeck.setVisibility(View.INVISIBLE);
        goBack.setVisibility(View.INVISIBLE);
        listOFiles.setVisibility(View.INVISIBLE);
        decks.setVisibility(View.INVISIBLE);
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
