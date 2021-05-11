package com.example.healthyliving;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class WallFragment extends Fragment {

    EditText inputwall;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;

    String inputwallB, anonymousB = "false";
    int count = 0;
    int entries;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wall, container, false);

        //Post button
        Button post = (Button)view.findViewById(R.id.postwall);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postInput();
            }
        });

        //Anonymous checkbox
        CheckBox anonymous = (CheckBox)view.findViewById(R.id.wallcheckBox);
        //set text to checkbox
        anonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(anonymous.isChecked()){
                    anonymousB = "true";
                }
                else{
                    anonymousB = "false";
                }
            }
        });
        return view;
    }


    //----------------------------Send wall message to DB method----------------------------------
    public void postInput(){

        firebaseDatabase = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference databaseReference = firebaseDatabase.getReference("/wall");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                entries = (int) snapshot.getChildrenCount(); //counts number of children under node "wall'
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        int childentries = entries + 1; //adds +1 to the number of children under wall

        inputwall = getView().findViewById(R.id.inputwall);
        inputwallB = inputwall.getText().toString().trim();


        if(inputwallB.isEmpty()){ //Validates if edittext has input value
            Toast.makeText(getActivity(), "Please enter a message", Toast.LENGTH_SHORT).show();
        }
        else{
            databaseReference.child(String.valueOf(childentries)).child("input").setValue(inputwallB);
            databaseReference.child(String.valueOf(childentries)).child("time").setValue(ServerValue.TIMESTAMP); //Time is in EPOCH format. Need to convert for display
            if (anonymousB == "true"){ //Sets the name if anonymous or not
                databaseReference.child(String.valueOf(childentries)).child("name").setValue("Anonymous");
            }
            else{
                databaseReference.child(String.valueOf(childentries)).child("name").setValue("Jose Bugay");
            }
        }
    }

    public void getInput(){

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseDatabase = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference databaseReference = firebaseDatabase.getReference("/wall");


    }
}
