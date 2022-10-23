package com.example.search_item2;

import android.content.Context;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class CheckAchieves {
    SaveAndLoad sl;
    private final static String FILE_NAME = "contents.ser";
    public boolean[] achieveMas;
    CheckAchieves(){this.achieveMas= new boolean[]{false,false,false,false,false};}

    void CheckAllAchieves(int levelNum,int timer){
        CheckLevelAchieves(levelNum);
        CheckTimerAchieves(levelNum,timer);
        CheckLastAchieve();
    }
    void CheckLevelAchieves(int levelNum){
        achieveMas[levelNum]=true;
    }
    void CheckTimerAchieves(int levelNum,int timer) {
        if(levelNum==3 && timer>30)
        {
            achieveMas[levelNum]=true;
        }
    }
    void CheckLastAchieve(){
        for(int i =0;i<achieveMas.length-1;i++)
        {
            if (achieveMas[i]==false)
            {
                return;
            }
        }
        achieveMas[achieveMas.length-1] = true;
    }
}
