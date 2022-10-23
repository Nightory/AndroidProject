package com.example.search_item2;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SaveAndLoad {
    private final static String FILE_NAME = "contents.ser";
    public static <T extends Serializable> void SaveProgress(Context context, ArrayList<T> objectToSave) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(objectToSave);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static<T extends Serializable> ArrayList<T> LoadProgress(Context context) {
        ArrayList<T> objectToReturn = null;

        try {
            FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            objectToReturn = (ArrayList<T>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
                int[] image= new int[15];
                int[] item_number= null;
                boolean[] mas = {false,false,false,false,false};
                Saves save = new Saves(-1,image,item_number,mas,60,20);
                ArrayList<Saves> saves = new ArrayList<Saves>();
                saves.add(save);
                SaveProgress(context,saves);
                objectToReturn = LoadProgress(context);
        }

        return objectToReturn;
    }
    public static void DeleteFile(Context context) {
        context.deleteFile(FILE_NAME);
    }

}
