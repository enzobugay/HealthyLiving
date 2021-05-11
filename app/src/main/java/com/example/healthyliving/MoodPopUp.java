package com.example.healthyliving;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MoodPopUp extends Activity {



    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    ImageView a,b,c,d,e,f,g,h,i;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mood_picker_popup);

        DisplayMetrics  displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;


        //Pop Up Window Size
        getWindow().setLayout((int) (width*.8),(int)(height*.6));


        //-----------------------------Function--------------------------------------------------------

        //Pop Up Picker on Click
        a = (ImageView) findViewById(R.id.Aemoji);
        b = (ImageView) findViewById(R.id.Bemoji);
        c = (ImageView) findViewById(R.id.Cemoji);
        d = (ImageView) findViewById(R.id.Demoji);
        e = (ImageView) findViewById(R.id.Eemoji);
        f = (ImageView) findViewById(R.id.Femoji);
        g = (ImageView) findViewById(R.id.Gemoji);
        h = (ImageView) findViewById(R.id.Hemoji);
        i = (ImageView) findViewById(R.id.Iemoji);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
                databaseReference = rootNode.getReference().child("emoji");
                databaseReference.setValue("A");
                finish();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
                databaseReference = rootNode.getReference().child("emoji");
                databaseReference.setValue("B");
                finish();
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
                databaseReference = rootNode.getReference().child("emoji");
                databaseReference.setValue("C");
                finish();
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
                databaseReference = rootNode.getReference().child("emoji");
                databaseReference.setValue("D");
                finish();
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
                databaseReference = rootNode.getReference().child("emoji");
                databaseReference.setValue("E");
                finish();
            }
        });

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
                databaseReference = rootNode.getReference().child("emoji");
                databaseReference.setValue("F");
                finish();
            }
        });

        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
                databaseReference = rootNode.getReference().child("emoji");
                databaseReference.setValue("G");
                finish();
            }
        });

        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
                databaseReference = rootNode.getReference().child("emoji");
                databaseReference.setValue("H");
                finish();
            }
        });

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
                databaseReference = rootNode.getReference().child("emoji");
                databaseReference.setValue("I");
                finish();
            }
        });
    }
}
