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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ModDeck extends AppCompatActivity {
    Button modifyDeck,goBack,modifySpecificDeck,goBack2,saveCard;
    Spinner listOFiles,listofcards;
    TextView decks,listofcardstext,CardNumberText,CardNumberOutput,SerialNumberText,SerialNumberOutput;
    static final int READ_BLOCK_SIZE = 1000;
    ArrayAdapter ztt;
    String dtbm, gtcn;
    RadioButton getCardNumbers,getSerialNumbers;
    boolean serialNumFlag;
    EditText newDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_deck);
        modifyDeck = (Button) findViewById(R.id.modifyDeck);
        goBack = (Button) findViewById(R.id.goBack);
        modifySpecificDeck = (Button) findViewById(R.id.modifySpecificDeck);
        goBack2 = (Button) findViewById(R.id.goBack2);
        saveCard = (Button) findViewById(R.id.saveCard);
        listOFiles = (Spinner) findViewById(R.id.listOFiles);
        listofcards = (Spinner) findViewById(R.id.listofcards);
        decks = (TextView) findViewById(R.id.decks);
        listofcardstext = (TextView) findViewById(R.id.listofcardstext);
        CardNumberText = (TextView) findViewById(R.id.CardNumberText);
        CardNumberOutput = (TextView) findViewById(R.id.CardNumberOutput);
        SerialNumberOutput = (TextView) findViewById(R.id.SerialNumberOutput);
        SerialNumberText = (TextView) findViewById(R.id.SerialNumberText);
        getSerialNumbers = (RadioButton) findViewById(R.id.getSerialNumbers);
        getCardNumbers = (RadioButton) findViewById(R.id.getCardNumbers);
        ztt= new ArrayAdapter(this, android.R.layout.simple_list_item_1, lOFs());
        ztt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listOFiles.setAdapter(ztt);
        newDesc = (EditText) findViewById(R.id.newDesc);
    }

    public void goBackClick(View view){
        if(modifyDeck.isShown()) {
            Intent mcd = new Intent(getApplicationContext(), mcd.class);
            mcd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mcd);
        }else if (listofcardstext.isShown()){
            Intent md = new Intent(getApplicationContext(), ModDeck.class);
            md.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(md);
        }else{
            listofcardstext.setVisibility(View.VISIBLE);
            listofcards.setVisibility(View.VISIBLE);
            modifySpecificDeck.setVisibility(View.VISIBLE);
            getSerialNumbers.setVisibility(View.VISIBLE);
            getCardNumbers.setVisibility(View.VISIBLE);
            goBack.setVisibility(View.VISIBLE);
            CardNumberText.setVisibility(View.INVISIBLE);
            CardNumberOutput.setVisibility(View.INVISIBLE);
            SerialNumberText.setVisibility(View.INVISIBLE);
            SerialNumberOutput.setVisibility(View.INVISIBLE);
            goBack2.setVisibility(View.INVISIBLE);
            saveCard.setVisibility(View.INVISIBLE);
            newDesc.setVisibility(View.INVISIBLE);
        }
    }

    public void getCardNumbersClick(View view){
        ztt= new ArrayAdapter(this, android.R.layout.simple_list_item_1, lOCBNs());
        ztt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listofcards.setAdapter(ztt);
        getSerialNumbers.setChecked(false);
        serialNumFlag=false;
    }

    public void getSerialNumbersClick(View view){
        ztt= new ArrayAdapter(this, android.R.layout.simple_list_item_1, lOCBSs());
        ztt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listofcards.setAdapter(ztt);
        getCardNumbers.setChecked(false);
        serialNumFlag=true;
    }

    public void modifySpecificDeckClick(View view){
        gtcn=listofcards.getSelectedItem().toString();
        if(!gtcn.equals("")) {
            newDesc.setText(getDesc());
            CardNumberOutput.setText(getCardNum());
            SerialNumberOutput.setText(getSerialNum());
            listofcardstext.setVisibility(View.INVISIBLE);
            listofcards.setVisibility(View.INVISIBLE);
            modifySpecificDeck.setVisibility(View.INVISIBLE);
            getSerialNumbers.setVisibility(View.INVISIBLE);
            getCardNumbers.setVisibility(View.INVISIBLE);
            goBack.setVisibility(View.INVISIBLE);
            CardNumberText.setVisibility(View.VISIBLE);
            CardNumberOutput.setVisibility(View.VISIBLE);
            SerialNumberText.setVisibility(View.VISIBLE);
            SerialNumberOutput.setVisibility(View.VISIBLE);
            goBack2.setVisibility(View.VISIBLE);
            saveCard.setVisibility(View.VISIBLE);
            newDesc.setVisibility(View.VISIBLE);
        } else{
            Toast.makeText(getBaseContext(), "SELECT A CARD", Toast.LENGTH_LONG).show();
        }
    }

    public void modifyDeckClick(View view){
        dtbm=listOFiles.getSelectedItem().toString();
        if(!dtbm.equals("")) {
            modifyDeck.setVisibility(View.INVISIBLE);
            listOFiles.setVisibility(View.INVISIBLE);
            decks.setVisibility(View.INVISIBLE);
            listofcardstext.setVisibility(View.VISIBLE);
            listofcards.setVisibility(View.VISIBLE);
            modifySpecificDeck.setVisibility(View.VISIBLE);
            getSerialNumbers.setVisibility(View.VISIBLE);
            getCardNumbers.setVisibility(View.VISIBLE);
        } else{
            Toast.makeText(getBaseContext(), "SELECT A DECK", Toast.LENGTH_LONG).show();
        }
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

    public String[] lOCBSs(){
        String s="";
        try{
            FileInputStream fileIn = openFileInput(dtbm);
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0){
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;                        
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        String delim="~0000010111001111010010001100101~";
        String[] token1=s.split(delim);
        delim="~10001100000101010001100111~";
        token1[0]="";
        for(int i=1; i<token1.length;i++){
            String[] token2=token1[i].split(delim);
            token1[i]=token2[0];
        }
        return token1;
    }

    public String[] lOCBNs(){
        String s="";
        try{
            FileInputStream fileIn = openFileInput(dtbm);
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0){
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        String delim="~0000010111001111010010001100101~";
        String[] token1=s.split(delim);
        delim="~10001100000101010001100111~";
        token1[0]="";
        for(int i=1; i<token1.length;i++){
            String[] token2=token1[i].split(delim);
            token1[i]=token2[1];
        }
        return token1;
    }

    String getDesc(){
        String delim="~10001100000101010001100111~";
        int index;
        if(serialNumFlag)
        {
            index=0;
        }else{
            index=1;
        }

        String[] token = getCardAll(index).split(delim);
        return token[2];
    }

    String getSerialNum(){
        String delim="~10001100000101010001100111~";
        int index;
        if(serialNumFlag)
        {
            index=0;
        }else{
            index=1;
        }
        String[] token = getCardAll(index).split(delim);
        return token[0];
    }

    String getCardNum(){
        String delim="~10001100000101010001100111~";
        int index;
        if(serialNumFlag)
        {
            index=0;
        }else{
            index=1;
        }
        String[] token = getCardAll(index).split(delim);
        return token[1];
    }

    String getDeckAll(){
        String s="";
        try{
            FileInputStream fileIn = openFileInput(dtbm);
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0){
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }

    String getCardAll(int index){
        String thing="";
        String delim="~0000010111001111010010001100101~";
        String[] token1=getDeckAll().split(delim);
        delim="~10001100000101010001100111~";
        for(int i=1; i<token1.length;i++){
            if(token1[i].split(delim)[index].equals(gtcn)){
                thing=token1[i];
            }
        }
        return thing;
    }

    public void saveCard(){

    }
}
