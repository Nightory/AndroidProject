package com.example.search_item2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class AchievementsActivity extends AppCompatActivity {
    ActivitySettings as=new ActivitySettings(this);
    private final static String FILE_NAME = "contents.ser";
    SaveAndLoad sl = new SaveAndLoad();
    Saves save = new Saves();
    ImageView Image[] = new ImageView[5];
    TextView Text[] = new TextView[5];
    int Image_ID[] = new int[]{R.id.imageView,R.id.imageView2,R.id.imageView3,R.id.imageView4,R.id.imageView5};
    int Text_ID[] = new int[]{R.id.textView2,R.id.textView3,R.id.textView5,R.id.textView7,R.id.textView9};
    int unlock_ID[] = new int[]{R.drawable.achiv1,R.drawable.achiv2,R.drawable.achiv3,R.drawable.achiv4,R.drawable.achiv5};

    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        as.onWindowFocusChanged(true);

        setContentView(R.layout.activity_achievements);

        for(int i=0;i<5;i++){
            Image[i]=(ImageView) findViewById(Image_ID[i]);
            Text[i]=(TextView) findViewById(Text_ID[i]);
        }
        back = (Button) findViewById(R.id.button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AchievementsActivity.this.finish();
            }
        });
        checkAchieves();
    }
    public void checkAchieves()
    {
        ArrayList<Saves> saves = sl.LoadProgress(this);
        saves = sl.LoadProgress(this);
        Saves save = saves.get(saves.size()-1);

        for(int i =0;i<save.achieveMas.length;i++)
        {
            if(save.achieveMas[i]==true)
            {
                Image[i].setImageResource(unlock_ID[i]);
                Text[i].setText("Открыто");
            }
        }

    }

}