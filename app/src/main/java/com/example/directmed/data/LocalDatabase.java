package com.example.directmed.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Medicament.class},version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract MedicamentDAO medicamentDAO();
}
