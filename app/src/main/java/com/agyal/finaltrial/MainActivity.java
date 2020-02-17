package com.agyal.finaltrial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.agyal.finaltrial.Adapter.CodesAdapter;
import com.agyal.finaltrial.Model.CodesModel;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

        private ZXingScannerView scannerView;
        TextView TV;
        Button codes;

        RecyclerView recyclerView;
        CodesAdapter codesAdapter;
        ArrayList<CodesModel> codesModels;

        public static CodesDatabase codesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TV = findViewById(R.id.TV);
        scannerView = findViewById(R.id.barcode);

        codes = findViewById(R.id.codes);

        codes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AllCodesActivity.class);
                startActivity(intent);
            }
        });

        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(MainActivity.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MainActivity.this,"you must accept this permission",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

        codesModels = new ArrayList<>();
        recyclerView = findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,true));
//        codesAdapter = new CodesAdapter(codesModels);
//        recyclerView.setAdapter(codesAdapter);
//        codesAdapter.notifyDataSetChanged();
        getDB();

    }

          public void getDB(){
             codesDatabase = Room.databaseBuilder(this,CodesDatabase.class,"codedb").allowMainThreadQueries().build();
                 }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }



    @Override
    protected void onResume() {
        super.onResume();
        scannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        // TV.setText(rawResult.getText());

//         list = new ArrayList<>();
//         list.add(rawResult.getText());
//         if (list.size() == 2){
//             Log.d("TTT", "handleResult:  ");
//             TV.setText(list.get(1));
//             TV2.setText(list.get(0));
//             list.clear();
//         }

//        for (int i = 0; i<list.size();i++){
//            TV.setText(list.get(i+1));
//            TV2.setText(list.get(i));
//        }
        //we started the camera again because it will stop after the first scan by default
        scannerView.resumeCameraPreview(this);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
        String date = sdf.format(new Date());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(new Date());


//        SimpleDateFormat  = new SimpleDateFormat("HH/MM/SS");
//        String time = sdf.format(new Date());
//        String pattern = "dd/MM/yyyy";
//        DateFormat df = new SimpleDateFormat(pattern);


//        String todayAsString = df.format(today);
//        Date d = null;
//        try {
//            d = df.parse(todayAsString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        String pattern1 = "HH/MM/SS";
//        DateFormat dff = new SimpleDateFormat(pattern1);
//        Date time;
//        try {
//            time = dff.parse(String.valueOf(Calendar.getInstance().getTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String todaytimeAsString = dff.format(todaytime);
//        Date t = null;
//        try {
//            d = df.parse(todaytimeAsString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        codesModels.add(new CodesModel(rawResult.getText(),date, time));


        codesAdapter = new CodesAdapter(codesModels);
        recyclerView.setAdapter(codesAdapter);
        codesAdapter.notifyDataSetChanged();

        Log.d("TTT", "handleResult: " + codesModels);

        for (int i = 0; i < codesModels.size(); i++) {
            codesDatabase.codesDao().AddCode(codesModels.get(i));
        }
        Log.d("PPP", "handleResult: " + codesDatabase.codesDao().getCodes().toString());

    }

}
