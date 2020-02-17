package com.agyal.finaltrial.Model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;

@Entity(tableName = "Products_codes")
public class CodesModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Code_num")
    private String codenum;

    @ColumnInfo(name = "Code_date")
    private String codedate;

    @ColumnInfo(name = "Code_time")
    private String codetime;

    public CodesModel(String codenum, String codedate, String codetime) {
        this.codenum = codenum;
        this.codedate = codedate;
        this.codetime = codetime;
    }

    public String getCodenum() {
        return codenum;
    }

    public void setCodenum(String codenum) {
        this.codenum = codenum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodedate() {
        return codedate;
    }

    public void setCodedate(String codedate) {
        this.codedate = codedate;
    }

    public String getCodetime() {
        return codetime;
    }

    public void setCodetime(String codetime) {
        this.codetime = codetime;
    }
}
