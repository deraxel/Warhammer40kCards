package com.example.warhammer40kcards;

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
public class NewDeck extends AppCompatActivity {

    EditText maeDecTitle, numOCards, deckStart;
    Spinner cardsPerTens;
    String fileTitle;
    TextView cardRadix, numCards;
    ArrayAdapter<CharSequence> ztt;
    Button saveButton;
    private CheckBox autoCount;
    static final int READ_BLOCK_SIZE = 1000;
    boolean aCount, flagSave =false;
    int radix, start, totalcards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_deck);
        ztt = ArrayAdapter.createFromResource(this, R.array.cardsPerTen, android.R.layout.simple_spinner_item);
        ztt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardsPerTens = (Spinner) findViewById(R.id.radix);
        cardsPerTens.setAdapter(ztt);
        maeDecTitle = (EditText) findViewById(R.id.deckTitle);
        numOCards = (EditText) findViewById(R.id.numCardsOutput);
        deckStart = (EditText) findViewById(R.id.cardStart);
        autoCount=(CheckBox) findViewById(R.id.isAutoCount);
        cardRadix = (TextView) findViewById(R.id.radixCount);
        numCards = (TextView) findViewById(R.id.numCards);
        saveButton = (Button) findViewById(R.id.savebutton);
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
        }
    }

    public void saveButton(View view){
        try {
            fileTitle = maeDecTitle.getText().toString();
            if (!fileTitle.equals("")) {
                FileOutputStream fileout, titleout;
                fileout = openFileOutput(fileTitle + ".txt", MODE_PRIVATE);
                titleout = openFileOutput("listofdecks.txt", MODE_PRIVATE);
                OutputStreamWriter listoftitles = new OutputStreamWriter(titleout);
                FileInputStream fileIn = openFileInput("listofdecks.txt");
                InputStreamReader InputRead = new InputStreamReader(fileIn);
                char[] inputBuffer = new char[READ_BLOCK_SIZE];
                String s = "";
                int charRead;
                while ((charRead = InputRead.read(inputBuffer)) > 0) {
                    String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                    s += readstring;
                }
                s = s + "010101000101110100100111" + fileTitle + ".txt";
                listoftitles.write(s);
                listoftitles.close();
                OutputStreamWriter fileoutDeck = new OutputStreamWriter(fileout);
                s = "";
                s = "010101000101110100100111" + fileTitle + "110001101000100000101" + numOCards.getText().toString();
                fileoutDeck.write(s);
                fileoutDeck.close();
                totalcards = Integer.parseInt(numOCards.getText().toString());
                if (aCount) {
                    start = Integer.parseInt(deckStart.getText().toString());
                }
                maeDecTitle.setVisibility(View.INVISIBLE);
                numOCards.setVisibility(View.INVISIBLE);
                numCards.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.INVISIBLE);
                cardsPerTens.setVisibility(View.INVISIBLE);
                deckStart.setVisibility(View.INVISIBLE);
                cardRadix.setVisibility(View.INVISIBLE);
                autoCount.setVisibility(View.INVISIBLE);
                flagSave = true;
            }else{
                Toast.makeText(getBaseContext(), "YOU MUST FILL EVERY SECTION", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            Toast.makeText(getBaseContext(), "YOU MUST FILL EVERY SECTION", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
