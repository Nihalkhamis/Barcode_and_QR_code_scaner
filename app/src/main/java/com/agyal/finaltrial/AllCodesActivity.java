package com.agyal.finaltrial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.agyal.finaltrial.Model.CodesModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AllCodesActivity extends AppCompatActivity {
    TextView TV,fromTV,toTV;
    Button from,to,showResult;
    Calendar c;
    DatePickerDialog dpd;
    List<CodesModel> dates;
    List<CodesModel> codesModels;
    List<CodesModel> datesBetween;

    String startDate;
    String endDate;

    String result = "";



    // boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_codes);

         TV = findViewById(R.id.TV);
        fromTV = findViewById(R.id.fromTV);
        toTV = findViewById(R.id.toTV);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        showResult = findViewById(R.id.showResult);

        dates = new ArrayList<>();
        codesModels = new ArrayList<>();
        datesBetween = new ArrayList<>();



        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // clicked = true;
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(AllCodesActivity.this,R.style.DatePickerDialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                           //fromTV.setText((mMonth+1)+"/"+mDay+"/"+mYear);
                        c.set(mYear,mMonth,mDay);
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = format.format(c.getTime());
                        fromTV.setText(dateString);
                        startDate = dateString;
                        Log.d("DDD", "onCreate: "+startDate);

                    }
                },month,day,year);
                dpd.show();
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(AllCodesActivity.this,R.style.DatePickerDialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                           //toTV.setText((mMonth+1)+"/"+mDay+"/"+mYear);
                        c.set(mYear,mMonth,mDay);
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = format.format(c.getTime());
                        toTV.setText(dateString);
                         endDate = dateString;
                        Log.d("DDD", "onCreate: "+endDate);

                    }
                },month,day,year);
                dpd.show();
            }
        });


         codesModels = MainActivity.codesDatabase.codesDao().getCodes();



        showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("StartEnd", "onClick: "+startDate);
                Log.d("StartEnd", "onClick: "+endDate);
                Dialog dialog = new Dialog(AllCodesActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                dialog.setContentView(R.layout.filter_dialog);
                TextView textView = dialog.findViewById(R.id.filter);
                result ="";
                if(startDate != null && endDate == null) {
                    for (CodesModel codesModel : dates) {
                        int id = codesModel.getId();
                        String code = codesModel.getCodenum();
                        String date = codesModel.getCodedate();
                        String time = codesModel.getCodetime();
                        // dates.add(codesModel.getCodetime());

                        // Log.d("Time", "onCreate: "+time);
                        result = result + "\n \n" + "ID:" + id + "\n" + "Product code:" + code + "\n" +"Date:"+date+"\n" +"Time:" + time + "\n";
                        textView.setText(result);

                    }
                }
                else{
                    Log.d("else", "onClick: byd5ol f el else");
                    datesBetween = MainActivity.codesDatabase.codesDao().newAllCodesFromTo(startDate,endDate);
                    Log.d("between", "onClick: "+datesBetween);
                    for (CodesModel codesModel : datesBetween) {
                        int id = codesModel.getId();
                        String code = codesModel.getCodenum();
                        String date = codesModel.getCodedate();
                        String time = codesModel.getCodetime();

                        result = result + "\n \n" + "ID:" + id + "\n" + "Product code:" + code + "\n" +"Date:"+date+"\n" +"Time:" + time + "\n";
                        textView.setText(result);
                }
                }
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.setTitle("Selected codes");
                dialog.setCancelable(true);
                dialog.show();

        }
        });


        String info = "";

        fromTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

              // shaghala s7  Log.d("SSS", "afterTextChanged: "+s);
                for (CodesModel codesModel : codesModels) {
                    String date = codesModel.getCodedate();
                   //shaghala s7 Log.d("SSS", "afterTextChanged: "+codesModels.get(493).getCodetime());
//                    Log.d("CCC", "afterTextChanged: "+time.substring(0,10));
//                    Log.d("CCC", "afterTextChanged: "+s.toString());
//                    Log.d("CCC", "afterTextChanged: "+time.substring(0,10).equals(s.toString()));
                    if (date.equals(s.toString())) {
                        Log.d("SSS", "afterTextChanged: "+s.toString());

                        dates.add(codesModel);
                    }
                }
                Log.d("RRR", "onCreate: " + dates.size());
                //Log.d("RRR", "onCreate: " + dates);
            }
        });


        for (CodesModel codesModel : codesModels){
            int id = codesModel.getId();
            String code = codesModel.getCodenum();
            String date = codesModel.getCodedate();
            String time = codesModel.getCodetime();
           // dates.add(codesModel.getCodetime());

           // Log.d("Time", "onCreate: "+time);

            info = info +"\n \n" + "ID:" +id +"\n" +"Product code:" +code +"\n"+"Date:" + date+"\n"+ "Time:"+time+"\n";
        }

      // Log.d("RRR", "onCreate: "+dates.size());
        TV.setText(info);

}
}
