package com.agyal.finaltrial;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.agyal.finaltrial.Model.CodesModel;

import java.util.List;

@Dao
public interface CodesDao {

    @Insert
    public void AddCode(CodesModel codesModel);

  @Query("select * from  Products_codes")
    public List<CodesModel> getCodes();

  @Query("select * from  Products_codes where Code_date between :startDate AND :endDate")
     List<CodesModel> newAllCodesFromTo(String startDate,String endDate);



}
