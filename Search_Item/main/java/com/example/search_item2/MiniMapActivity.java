package com.example.search_item2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MiniMapActivity extends AppCompatActivity {
    ActivitySettings as=new ActivitySettings(this);
    ImageView level[];
    int level_ID[] = new int[]{R.id.level1,R.id.level2,R.id.level3};
    int LevelNum;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        as.onWindowFocusChanged(true);
        setContentView(R.layout.activity_mini_map);
        getSupportActionBar().hide();
        Bundle arguments = getIntent().getExtras();
        LevelNum = arguments.getInt("level");
        level = new ImageView[LevelNum];
        for(int i = 0; i < LevelNum; i++){
            level[i] = (ImageView) findViewById(level_ID[i]);
            level[i].setOnTouchListener(this::onTouch);
        }
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MiniMapActivity.this.finish();
            }
        });

}

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int ID = view.getId();
        for (int i = 0; i < level_ID.length; i++) {
            if(ID==level_ID[i]){
                Intent intent=new Intent(MiniMapActivity.this,PlayActivity1.class);
                intent.putExtra("level", i+1);
                startActivity(intent);
            }
        }
        return false;
    }
    }