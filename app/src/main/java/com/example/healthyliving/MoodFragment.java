package com.example.healthyliving;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MoodFragment extends Fragment implements View.OnClickListener {
    View view;

    ImageView MainMood, MyMood;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int count = 0;
    ProgressDialog progressDialog;

    public MoodFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mood, container, false);

        //Button Listener
        Button b = (Button) view.findViewById(R.id.moodbutton);
        b.setOnClickListener(this);
        return view;

    }

    //Button Action (Pop Up Window)
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), MoodPopUp.class);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadEmoji();
    }

    //--------------------Get Emoji value from Database------------------------------------
    private void loadEmoji(){

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Setting your mood...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        MainMood = (ImageView)getView().findViewById(R.id.MainMoodimageview);
        MyMood = (ImageView)getView().findViewById(R.id.mymood);

        firebaseDatabase = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference();
        Log.d("DebugEmoji", "loadEmoji called");


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                count++;
                //Counts the data in database. If count is correct, progress dialog will close
                if(count >= snapshot.getChildrenCount()){
                    Log.d("DebugEmoji", "ChildAdded called");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Get emoji value on database then set to ImageView
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("DebugEmoji", "ValueEventListener called");

                String emoji = (String) snapshot.child("emoji").getValue(); //Get value of emoji node in DB

                switch (emoji){
                    case "A":
                        MainMood.setImageResource(R.drawable.smiling);
                        MyMood.setImageResource(R.drawable.smiling);
                        break;

                    case "B":
                        MainMood.setImageResource(R.drawable.angry);
                        MyMood.setImageResource(R.drawable.angry);
                        break;

                    case "C":
                        MainMood.setImageResource(R.drawable.mad);
                        MyMood.setImageResource(R.drawable.mad);
                        break;

                    case "D":
                        MainMood.setImageResource(R.drawable.sad);
                        MyMood.setImageResource(R.drawable.sad);
                        break;

                    case "E":
                        MainMood.setImageResource(R.drawable.weary);
                        MyMood.setImageResource(R.drawable.weary);
                        break;

                    case "F":
                        MainMood.setImageResource(R.drawable.sick);
                        MyMood.setImageResource(R.drawable.sick);
                        break;

                    case "G":
                        MainMood.setImageResource(R.drawable.loudlycrying);
                        MyMood.setImageResource(R.drawable.loudlycrying);
                        break;

                    case "H":
                        MainMood.setImageResource(R.drawable.udsmiling);
                        MyMood.setImageResource(R.drawable.udsmiling);
                        break;

                    case "I":
                        MainMood.setImageResource(R.drawable.fearful);
                        MyMood.setImageResource(R.drawable.fearful);
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
