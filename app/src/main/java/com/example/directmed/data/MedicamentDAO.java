package com.example.directmed.data;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MedicamentDAO {
    @Query("SELECT * FROM Medicament")
    List<Medicament> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertMeds(Medicament...medicaments);

    @Delete
    public void deleteMeds(Medicament...medicaments);


}
