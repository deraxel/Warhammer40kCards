package com.example.warhammer40kcards;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//010101000101110100100111 is the precode denoting a title
public class NewDeck extends AppCompatActivity {

    EditText maeDecTitle, numOCards;
    String fileTitle;
    static final int READ_BLOCK_SIZE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_deck);
        maeDecTitle = (EditText) findViewById(R.id.deckTitle);
        numOCards = (EditText) findViewById(R.id.numCardsOutput);

    }

    public void saveButton(View view){
        try {
            fileTitle = maeDecTitle.getText().toString();;
            FileOutputStream fileout, titleout;
            fileout = openFileOutput(fileTitle+".txt", MODE_PRIVATE);
            titleout = openFileOutput("listofdecks.txt", MODE_PRIVATE);
            OutputStreamWriter listoftitles=new OutputStreamWriter(titleout);
            FileInputStream fileIn=openFileInput("listofdecks.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);
            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;
            while ((charRead=InputRead.read(inputBuffer))>0) {
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            s=s+"010101000101110100100111"+fileTitle+".txt";
            listoftitles.write(s);
            listoftitles.close();
            OutputStreamWriter fileoutDeck=new OutputStreamWriter(fileout);
            s="";
            s="010101000101110100100111"+fileTitle+"110001101000100000101"+numOCards.getText().toString();
            fileoutDeck.write(s);
            fileoutDeck.close();

            maeDecTitle.setVisibility(View.INVISIBLE);

            numOCards.setVisibility(View.INVISIBLE);

            TextView numCards = (TextView) findViewById(R.id.numCards);
            numCards.setVisibility(View.INVISIBLE);

            Button saveButton = (Button) findViewById(R.id.savebutton);
            saveButton.setVisibility(View.INVISIBLE);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
