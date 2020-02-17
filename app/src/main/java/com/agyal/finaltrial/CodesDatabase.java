package com.agyal.finaltrial;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.agyal.finaltrial.Model.CodesModel;

@Database(entities = {CodesModel.class},version = 1)
@TypeConverters({Converters.class})
public abstract class CodesDatabase extends RoomDatabase {

     public abstract CodesDao codesDao();
}
