package com.example.warhammer40kcards;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//010101000101110100100111 is the precode denoting a title radix encode for title
//110001101000100000101 is the precode denoting the number of cards code radix encode for nocs
//10001001110000111101000011 is the precode denoting the description of an individual card radix encode for descs
//10001100000101010001100111 is the precode denoting the card display number radix encode for carnu
//0000010111001111010010001100101 is the precode denoting the card's serial number radix encode for serial
public class NewDeck extends AppCompatActivity {

    EditText maeDecTitle, numOCards, deckStart, cardNumberManualM, descriptionM;
    Spinner cardsPerTens;
    String fileTitle, putNum, putNum2= "";
    TextView cardRadix, numCards, cardNumberM, cardNumberAutoM, totalNumberofCardsM,ofTextBoxM, cardTotalSerial, otherOfTextBoxM,serialNum;
    ArrayAdapter<CharSequence> ztt;
    Button saveButton, lastM, nextM, goBack;
    private CheckBox autoCount;
    static final int READ_BLOCK_SIZE = 1000;
    boolean aCount, flagSave =false;
    boolean firstFlag=true;
    int radix, startP, start, totalcards, cardCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_deck);
        cardCount=0;
        ztt = ArrayAdapter.createFromResource(this, R.array.cardsPerTen, android.R.layout.simple_spinner_item);
        ztt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardsPerTens = (Spinner) findViewById(R.id.radix);
        cardsPerTens.setAdapter(ztt);
        maeDecTitle = (EditText) findViewById(R.id.deckTitle);
        numOCards = (EditText) findViewById(R.id.numCardsOutput);
        deckStart = (EditText) findViewById(R.id.cardStart);
        cardNumberManualM = (EditText) findViewById(R.id.cardNumbManual);
        descriptionM = (EditText) findViewById(R.id.description);
        autoCount=(CheckBox) findViewById(R.id.isAutoCount);
        cardRadix = (TextView) findViewById(R.id.radixCount);
        numCards = (TextView) findViewById(R.id.numCards);
        cardNumberM = (TextView) findViewById(R.id.cardNumb);
        cardNumberAutoM = (TextView) findViewById(R.id.cardNumbAuto);
        ofTextBoxM = (TextView) findViewById(R.id.ofTextBoxM);
        totalNumberofCardsM = (TextView) findViewById(R.id.totalNumberofCards);
        cardTotalSerial = (TextView) findViewById(R.id.cardTotalSerial);
        otherOfTextBoxM = (TextView) findViewById(R.id.otherOfTextBoxM);
        serialNum = (TextView) findViewById(R.id.serialNum);
        saveButton = (Button) findViewById(R.id.savebutton);
        goBack = (Button) findViewById(R.id.goBack);
        lastM = (Button) findViewById(R.id.last);
        nextM = (Button) findViewById(R.id.next);

        autoCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autoCount.isChecked()){
                    cardsPerTens.setVisibility(View.VISIBLE);
                    deckStart.setVisibility(View.VISIBLE);;
                    cardRadix.setVisibility(View.VISIBLE);
                    aCount=true;
                }else {
                    cardsPerTens.setVisibility(View.INVISIBLE);
                    deckStart.setVisibility(View.INVISIBLE);
                    cardRadix.setVisibility(View.INVISIBLE);
                    aCount=false;
                }
            }
        });
        cardsPerTens.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                radix=Integer.parseInt(cardsPerTens.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                radix=Integer.parseInt(cardsPerTens.getSelectedItem().toString());
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(aCount){
            cardsPerTens.setVisibility(View.VISIBLE);
            deckStart.setVisibility(View.VISIBLE);;
            cardRadix.setVisibility(View.VISIBLE);
        }else {
            cardsPerTens.setVisibility(View.INVISIBLE);
            deckStart.setVisibility(View.INVISIBLE);
            cardRadix.setVisibility(View.INVISIBLE);
        }
        if(flagSave) {
            maeDecTitle.setVisibility(View.INVISIBLE);
            numOCards.setVisibility(View.INVISIBLE);
            numCards.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.INVISIBLE);
            cardsPerTens.setVisibility(View.INVISIBLE);
            deckStart.setVisibility(View.INVISIBLE);
            cardRadix.setVisibility(View.INVISIBLE);
            autoCount.setVisibility(View.INVISIBLE);
            goBack.setVisibility(View.INVISIBLE);
        }
    }

    public void saveButton(View view){
        String s = "";
        fileTitle = maeDecTitle.getText().toString();
        if (!fileTitle.equals("")) {
            try{
                FileInputStream fileIn = openFileInput("listofdecks.txt");
                InputStreamReader InputRead = new InputStreamReader(fileIn);
                char[] inputBuffer = new char[READ_BLOCK_SIZE];
                int charRead;
                while ((charRead = InputRead.read(inputBuffer)) > 0) {
                    String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                    s += readstring;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                FileOutputStream fileout, titleout;
                fileout = openFileOutput(fileTitle + ".txt", MODE_PRIVATE);
                titleout = openFileOutput("listofdecks.txt", MODE_PRIVATE);
                OutputStreamWriter listoftitles = new OutputStreamWriter(titleout);
                s = s + "010101000101110100100111" + fileTitle + ".txt";
                listoftitles.write(s);
                listoftitles.close();

                OutputStreamWriter fileoutDeck = new OutputStreamWriter(fileout);
                s = "~010101000101110100100111~" + fileTitle + "~110001101000100000101~" + numOCards.getText().toString();
                fileoutDeck.write(s);
                fileoutDeck.close();
                totalcards = Integer.parseInt(numOCards.getText().toString());
                if (aCount) {
                    start = Integer.parseInt(deckStart.getText().toString());
                    cardNumberAutoM.setVisibility(View.VISIBLE);
                    String stuff = start + "";
                    cardNumberAutoM.setText(stuff);
                } else {
                    cardNumberManualM.setVisibility(View.VISIBLE);
                }
                if(aCount) {
                    int totalCardNum, totalCardCountRad;
                    totalCardCountRad = start - 1;
                    totalCardNum = Integer.parseInt(numOCards.getText().toString());
                    for (int i = 1; i < totalCardNum; i++) {
                        totalCardCountRad = 1 + totalCardCountRad;
                        if (((totalCardCountRad) % 10) == radix) {
                            totalCardCountRad += 10;
                            totalCardCountRad -= radix;
                        }
                    }
                    totalCardCountRad = 1 + totalCardCountRad;
                    String totalCardRadString = "" + totalCardCountRad;
                    totalNumberofCardsM.setText(totalCardRadString);
                    totalNumberofCardsM.setVisibility(View.VISIBLE);
                    ofTextBoxM.setVisibility(View.VISIBLE);
                }
                String thing = ""+(Integer.parseInt(numOCards.getText().toString())-1);
                cardTotalSerial.setVisibility(View.VISIBLE);
                otherOfTextBoxM.setVisibility(View.VISIBLE);
                serialNum.setVisibility(View.VISIBLE);
                cardTotalSerial.setText(thing);
                serialNum.setText("0");
                cardNumberM.setVisibility(View.VISIBLE);
                cardNumberAutoM.setVisibility(View.VISIBLE);
                descriptionM.setVisibility(View.VISIBLE);
                lastM.setVisibility(View.VISIBLE);
                nextM.setVisibility(View.VISIBLE);
                maeDecTitle.setVisibility(View.INVISIBLE);
                numOCards.setVisibility(View.INVISIBLE);
                numCards.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.INVISIBLE);
                cardsPerTens.setVisibility(View.INVISIBLE);
                deckStart.setVisibility(View.INVISIBLE);
                cardRadix.setVisibility(View.INVISIBLE);
                autoCount.setVisibility(View.INVISIBLE);
                goBack.setVisibility(View.INVISIBLE);
                flagSave = true;
            } catch (Exception e){
                Toast.makeText(getBaseContext(), "YOU MUST FILL EVERY SECTION", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getBaseContext(), "YOU MUST FILL EVERY SECTION", Toast.LENGTH_LONG).show();
        }
    }

    public void onLast(View view){
        if(cardCount>0){
            cardCount-=1;
            String s = "";
            String s1 = "";
            try{
                FileInputStream fileIn = openFileInput(fileTitle + ".txt");
                InputStreamReader InputRead = new InputStreamReader(fileIn);
                char[] inputBuffer = new char[READ_BLOCK_SIZE];
                int charRead;
                while ((charRead = InputRead.read(inputBuffer)) > 0) {
                    String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                    s += readstring;
                    s1 += readstring;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                String temp, temp2;
                temp=""+cardCount;
                String delim=temp+"~10001100000101010001100111~";
                String[] token=s1.split(delim);
                delim="~10001001110000111101000011~";
                temp2=token[1];
                String[] token2=temp2.split(delim);
                if(aCount) {
                    start-=1;
                    cardNumberAutoM.setText(token2[0]);
                }
                String delims="~0000010111001111010010001100101~"+temp;
                String[] tokens=s.split(delims);
                FileOutputStream fileout;
                fileout = openFileOutput(fileTitle + ".txt", MODE_PRIVATE);
                OutputStreamWriter fileoutDeck = new OutputStreamWriter(fileout);
                fileoutDeck.write(tokens[0]);
                fileoutDeck.close();
                int temp1=start%10;
                if(temp1==0) {
                    start -= 10;
                    start += radix;
                }
                }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void onNext(View view){
        if(cardCount+2<=totalcards) {
            if (aCount) {
                storeCard(descriptionM.getText().toString(), cardNumberAutoM.getText().toString(), cardCount);
            }else{
                storeCard(descriptionM.getText().toString(), cardNumberManualM.getText().toString(), cardCount);
            }
            int temp1, temp2;
            if (aCount) {
                temp1=start%10;
                temp2=start/10;
                putNum=1+temp1+"";
                putNum2=temp2+"";
                putNum=putNum2+putNum;
                start+=1;
                cardNumberAutoM.setText(putNum);
                if(temp1+1==radix){
                    start+=10;
                    start-=radix;
                }
            }else{
                putNum=cardNumberManualM.getText().toString();
            }
            cardCount += 1;
            String thing = cardCount+"";
            serialNum.setText(thing);
        } else {
            if (aCount) {
                storeCard(descriptionM.getText().toString(), cardNumberAutoM.getText().toString(), cardCount);
            }else{
                storeCard(descriptionM.getText().toString(), cardNumberManualM.getText().toString(), cardCount);
            }
            int temp1, temp2;
            if (aCount) {
                temp1=start%10;
                temp2=start/10;
                putNum=1+temp1+"";
                putNum2=temp2+"";
                putNum=putNum2+putNum;
                start+=1;
                cardNumberAutoM.setText(putNum);
                if(temp1+1==radix){
                    start+=10;
                    start-=radix;
                }
            }else{
                putNum=cardNumberManualM.getText().toString();
            }
            cardCount += 1;
            String thing = cardCount+"";
            serialNum.setText(thing);
            Intent mcd = new Intent(getApplicationContext(), mcd.class);
            mcd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mcd);
        }
        descriptionM.setText("");
    }

    public void storeCard(String desc, String num, int serialNum){
        String s="";
        try{
            FileInputStream fileIn = openFileInput(fileTitle + ".txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            FileOutputStream fileout;
            fileout = openFileOutput(fileTitle + ".txt", MODE_PRIVATE);
            OutputStreamWriter fileoutDeck = new OutputStreamWriter(fileout);
            String sN = serialNum+"";
            s = s+"~0000010111001111010010001100101~" + sN +"~10001100000101010001100111~" +
                    num + "~10001100000101010001100111~" + desc;
            fileoutDeck.write(s);
            fileoutDeck.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void goBackClick(View view) {
        Intent mcd = new Intent(getApplicationContext(), mcd.class);
        mcd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mcd);
    }
}