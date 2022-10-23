package com.example.search_item2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;


public class PlayActivity1 extends AppCompatActivity  implements View.OnTouchListener {
    int MAX_ITEM = 15;
    ArrayList<Saves> saves = new ArrayList<Saves>();
    Saves save = new Saves();
    CheckAchieves achieves = new CheckAchieves();
    SaveAndLoad sl = new SaveAndLoad();
    String[] ItemName;
    Thread t,t2;
    ActivitySettings as=new ActivitySettings(this);
    HashSet<Integer> Number = new HashSet<Integer>();
    int WIN =0;

    int Text_Id[] =new int[]{R.id.Item1,R.id.Item2,R.id.Item3,R.id.Item4,R.id.Item5,R.id.Item6,R.id.Item7,R.id.Item8,R.id.Item9,R.id.Item10};
    int Image_Id[] = new int[15];

    int[] item_number_save = new int[10];
    int[] item_number = new int[10];

    int Level;
    int missClick = 0;
    ConstraintLayout L;
    public TextView timer, hints,menu;
    int timerSec = 60;
    int timerHintSec = 20;
    CountDownTimer cTimer = null,cTimer2 = null;
    TextView[] item_picture = new TextView[MAX_ITEM];
    ImageView[] Image = new ImageView[MAX_ITEM];
    Button butmenu, buthint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        as.onWindowFocusChanged(true);

        Bundle arguments = getIntent().getExtras();
        Level = arguments.getInt("level")-1;

        ItemName = getString(getResources().getIdentifier("level" + Integer.toString(arguments.getInt("level")), "string", getPackageName())).split(" ");
        setContentView(getResources().getIdentifier("activity_play" + Integer.toString(arguments.getInt("level")), "layout", getPackageName()));

        L=(ConstraintLayout) findViewById(getResources().getIdentifier("Layout" + Integer.toString(arguments.getInt("level")), "id", getPackageName()));
        L.setOnTouchListener(this::onTouch);

        Init();

        //Инициализация кнопок и полей которые будут на любом уровне
        timer = (TextView) findViewById(R.id.timer);
        menu = (TextView) findViewById(R.id.textViewMenu);
        hints = (TextView) findViewById(R.id.textViewHint);
        timer.setVisibility(View.VISIBLE);
        timer.setText(timerSec+"");
        hints.setVisibility(View.VISIBLE);
        menu.setVisibility(View.VISIBLE);
        runThread();
        //переменные для кнопок
        butmenu = (Button) findViewById(R.id.buttonmenu);
        buthint = (Button) findViewById(R.id.buttonhint);
        butmenu.setVisibility(View.VISIBLE);
        buthint.setVisibility(View.VISIBLE);
        //переменные для изображений

        //Обработчик кнопки меню
        butmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(PlayActivity1.this);
                dlgAlert.setMessage("Вы увеерены что хотите выйти? Вам будет засчитан проигрыш уровня");
                dlgAlert.setTitle("Предупреждение");
                dlgAlert.setPositiveButton("Да",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                PlayActivity1.this.finish();
                            }
                        });
                dlgAlert.setNegativeButton("Нет", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
        });
        //Обрабочик кнопки подсказка
        buthint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runThreadHint();
                buthint.setEnabled(false);
            }
        });
    }
    public void Init()
    {
        initTextView(); //Инициализация текстовых полей
        initImage(); //Инициализация картинок
        LoadSaves();
        if(save.item_number==null)
        {
            item_number = new int[10];
            CreateList();//генерация спска рандомных предметов
        }
        loadText();
        while(Image_Id[WIN]!=0)
        {
            CheckId(Image_Id[WIN],true);
            WIN++;
        }
    }
    public void loadText() 
    {
        for(int i=0;i<10;i++){//Заполняем переменные текстом на основе полученных чисел
            if(item_number[i]!=-1)
            {
                item_picture[i].setText(ItemName[item_number[i]]);
            }
            else
            {
                item_picture[i].setText("");
            }
        }
    }
    //генерация спска рандомных предметов
    public void CreateList() {
        Random r = new Random();
        while(Number.size()<10){//В этот списое нельзя вносить одинаковые числа, он их игнорирует
            Number.add(r.nextInt(15));
        }
        Iterator<Integer> item = Number.iterator();
        int n=0;
        while (item.hasNext()) { //Переписываем полученные числа в наш массив, так как из этого списка не очень приятно доставать
            item_number[n] = item.next();
            n++;
        }
        item_number_save=item_number;
    }

    public void initTextView() {
        //Инициализируем текстовые поля
        for(int i=0;i<Text_Id.length;i++){
            item_picture[i]=(TextView) findViewById(Text_Id[i]);
        }

    }
    public void initImage(){
        //Инициализируем картинки и делаем их кликабельными
        for(int i=0;i<MAX_ITEM;i++){
            Image[i]=(ImageView) findViewById(getResources().getIdentifier("imageView" + Integer.toString(i+1), "id", getPackageName()));
            Image[i].setOnTouchListener(this::onTouch);
            Image_Id[i]=0;
        }
    }

    public void delete_text(int i) {
        //Стирание конкретного элемента из списка
        item_picture[i].setText("");
    }

    public void LoadSaves()
    {
        saves = sl.LoadProgress(this);
        save = saves.get(saves.size()-1);
        achieves.achieveMas=save.achieveMas;
        if(save.levelNum==Level+1)
        {

            item_number=save.item_number;
            item_number_save=item_number;
            Image_Id = save.image_id;
            timerSec=save.timer;
            timerHintSec=save.timerHint;
        }
        else
        {
            save.item_number=null;
            item_number_save=item_number;
        }

    }
    public void unlockAchieve(){
        achieves.CheckAllAchieves(Level, timerSec);
        save.achieveMas=achieves.achieveMas;
    }

    public void runThread() {//Запуск потока с таймером для карты
        t = new Thread(new Runnable() {
            public void run() {
                runOnUiThread(runn1);
            }
        });
        t.start();
    }
    public void runThreadHint() {//Запуск потока с таймером для подсказки
        t2 = new Thread(new Runnable() {
            public void run() {
                runOnUiThread(runn2);
            }
        });
        t2.start();
    }

    Runnable runn1 = new Runnable() { //Поток с таймером для игры
        public void run() {
            cTimer = new CountDownTimer(timerSec*1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    timerSec= Integer.parseInt(timer.getText()+"")-1-missClick;
                    missClick=0;
                    if(timerSec<=0){
                        cTimer.cancel();
                        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(PlayActivity1.this);
                        dlgAlert.setMessage("Неудача, вы не успели найти предметы за отведенное время");
                        dlgAlert.setTitle("Вы проиграли");
                        dlgAlert.setPositiveButton("Ок",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        PlayActivity1.this.finish();
                                    }
                                });
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                        timer.setText("0");
                    }
                    timer.setText(timerSec+"");
                    if(WIN==10) {//Проверяем каждую секудну количество найденных предметов если нашли все останавдиваем таймер и выводим соответствующее окно
                        cTimer.cancel();
                        unlockAchieve();
                        save.item_number=null;
                        save.image_id = new int[10];
                        saves = new ArrayList<Saves>();
                        saves.add(save);
                        sl.SaveProgress(PlayActivity1.this,saves);
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PlayActivity1.this);
                        dlgAlert.setMessage("Поздравляем, вы успешно прошли уровень");
                        dlgAlert.setTitle("Вы выиграли");
                        dlgAlert.setPositiveButton("Ок",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        PlayActivity1.this.finish();
                                    }
                                });
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                    }
                }
                public void onFinish() {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(PlayActivity1.this);
                    dlgAlert.setMessage("Неудача, вы не успели найти предметы за отведенное время");
                    dlgAlert.setTitle("Вы проиграли");
                    dlgAlert.setPositiveButton("Ок",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    PlayActivity1.this.finish();
                                }
                            });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    timer.setText("0");
                }
            };
            cTimer.start();

        }
    };
    Runnable runn2 = new Runnable() {//Поток с таймером для подсказки
        @Override
        public void run() {//Проверяем список предметов и берем самый первый не найденный
            int num = -1;
            for(int i = 0; i < 10; i++){
                if (item_number[i]!=-1){
                    num = item_number[i];
                    break;
                }
            }
            if(num !=-1) {//Если такой есть то делаем выбранный предмет моргающим
                Image[num].setVisibility(View.GONE);
                Handler handler = new Handler();
                int finalNum = num;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Image[finalNum].setVisibility(View.VISIBLE);
                    }
                }, 500);

            }
            cTimer2  = new CountDownTimer(timerHintSec*1000, 1000) {//Как только кончится таймер делаем кнопку активной

                public void onTick(long millisUntilFinished) {
                    hints.setText((timerHintSec) +"");
                    timerHintSec--;
                }
                public void onFinish() {
                    buthint.setEnabled(true);
                    hints.setText("Hint");
                }
            };
            cTimer2.start();
        }
    };
    public void addID(int ID)
    {
        int i=0;
        while(Image_Id[i]!=0)
        {
            i++;
        }
        Image_Id[i]=ID;
    }
    public boolean CheckId(int ID, boolean visible)
    {
        for (int i = 0; i < 15; i++) {
            if (ID == getResources().getIdentifier("imageView" + Integer.toString(i+1), "id", getPackageName()) && !visible) {
                for (int j = 0; j < item_number.length; j++) {
                    if (item_number[j] == i) {
                        addID(ID);
                        Image[i].setVisibility(View.GONE);
                        delete_text(j);
                        missClick-=2;
                        item_number[j]=-1;
                        WIN++;
                        save = new Saves(Level+1,Image_Id,item_number_save,achieves.achieveMas, timerSec,timerHintSec);
                        saves.add(save);
                        sl.SaveProgress(this,saves);
                        return true;
                    }
                }
            }
            else if(ID == getResources().getIdentifier("imageView" + Integer.toString(i+1), "id", getPackageName()) && visible)
            {
                Image[i].setVisibility(View.GONE);
            }
        }
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {//Обработчик нажатий на картинки
        int ID = view.getId();
        if(CheckId(ID,false)==false)
        {
            missClick +=2;
        }
        return false;
    }

}

