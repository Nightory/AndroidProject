package com.example.search_item2;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class Saves implements Serializable {
    public int levelNum;
    public int[] image_id;
    public int[] item_number;
    public boolean[] achieveMas;
    public int timer;
    public int timerHint;
    Saves(){this.image_id= new int[15];
        this.item_number=null;
        this.achieveMas=  new boolean[]{false,false,false,false,false};
        this.timer=0;
        this.timerHint=0;}
    Saves(int level,int[] image_id, int[] item_number, boolean[] achieveMas, int timer, int timerHint )
    {
        this.levelNum=level;
        this.image_id=image_id;
        this.item_number=item_number;
        this.achieveMas=achieveMas;
        this.timer=timer;
        this.timerHint=timerHint;
    }

}
