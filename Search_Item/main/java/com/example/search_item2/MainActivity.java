package com.example.search_item2;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button butplay, butach,butexit;
    SaveAndLoad sl = new SaveAndLoad();
    ActivitySettings as=new ActivitySettings(this);
    private final static String FILE_NAME = "contents.ser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        as.onWindowFocusChanged(true);
        InitFile();

        butplay=(Button) findViewById(R.id.buttonplay);
        butach=(Button) findViewById(R.id.buttonach);
        butexit=(Button) findViewById(R.id.buttonexit);
        butplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,MapActivity.class);
                startActivity(i);
            }
        });
        butach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,AchievementsActivity.class);
                startActivity(i);
            }
        });
        butexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                dlgAlert.setMessage("Вы увеерены что хотите выйти?");
                dlgAlert.setTitle("Предупреждение");
                dlgAlert.setPositiveButton("Да",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.finish();
                            }
                        });
                dlgAlert.setNegativeButton("Нет", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
        });
    }
    public void InitFile() {
        ArrayList<Saves> saves = sl.LoadProgress(this);
        if(saves==null)
        {
            Toast.makeText(this, "ERROR SAVES", Toast.LENGTH_SHORT).show();
        }
    }
}