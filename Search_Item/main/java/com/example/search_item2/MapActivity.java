package com.example.search_item2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MapActivity extends AppCompatActivity {
    ActivitySettings as=new ActivitySettings(this);
    ImageView location1;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        as.onWindowFocusChanged(true);

        setContentView(R.layout.activity_map);

        location1=(ImageView) findViewById(R.id.locationkonoha);
        back = (Button) findViewById(R.id.back);
        location1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i=new Intent(MapActivity.this,MiniMapActivity.class);
                i.putExtra("level", 3);
                startActivity(i);
                return false;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapActivity.this.finish();
            }
        });
    }

}