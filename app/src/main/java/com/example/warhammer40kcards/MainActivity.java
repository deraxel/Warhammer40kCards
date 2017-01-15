package com.example.warhammer40kcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pwtab(View view){
        Intent pwtab = new Intent(getApplicationContext(), pwtab.class);
        pwtab.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pwtab);
    }

    public void md(View view){
        Intent md = new Intent(getApplicationContext(), md.class);
        md.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(md);
    }

    public void mcd(View view){
        Intent mcd = new Intent(getApplicationContext(), mcd.class);
        mcd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mcd);
    }

    public void pd(View view){
        Intent pd = new Intent(getApplicationContext(), pd.class);
        pd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pd);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
