package com.example.warhammer40kcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class mcd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcd);
    }
    public void createdeck(View view){
        Intent pwtab = new Intent(getApplicationContext(), NewDeck.class);
        pwtab.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pwtab);
    }

    public void modifydeck(View view){
        Intent md = new Intent(getApplicationContext(), ModDeck.class);
        md.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(md);
    }

    public void deleteDeck(View view){
        Intent mcd = new Intent(getApplicationContext(), DeleteDeck.class);
        mcd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mcd);
    }



}
