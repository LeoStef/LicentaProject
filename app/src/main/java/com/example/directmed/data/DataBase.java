package com.example.directmed.data;

import android.content.Context;

import androidx.room.Room;

public class DataBase {

    public static DataBase instance;
    private LocalDatabase database;


    private DataBase(Context context){
        database = Room.databaseBuilder(context,LocalDatabase.class,"med_db").allowMainThreadQueries().build();
    }


    public static DataBase getInstance(Context context){
        if(instance == null){
            instance = new DataBase(context);
        }
        return instance;
    }


    public LocalDatabase getDatabase(){
        return database;
    }

}
