package com.example.healthyliving;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;


public class MessagesFragment extends Fragment {

    EditText messageinput;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;

    String messageinputB, anonymousB = "false";
    TextView message;
    int entries;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        //Back button
        Button back = (Button)view.findViewById(R.id.messagesBackBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment();
            }
        });

        //Post button
        Button post = (Button)view.findViewById(R.id.messagesendbtn);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        //Anonymous checkbox
        CheckBox anonymous = (CheckBox)view.findViewById(R.id.messagecheckBox);
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


    //----------------------------Send message to DB method----------------------------------
    public void sendMessage(){

        firebaseDatabase = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference databaseReference = firebaseDatabase.getReference("/message");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                entries = (int) snapshot.getChildrenCount(); //counts number of children under node "message'

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        int childentries = entries + 1; //adds +1 to the number of children under message

        messageinput = getView().findViewById(R.id.messageinput);
        messageinputB = messageinput.getText().toString().trim();

        if(messageinputB.isEmpty()){ //Validates if edittext has input value
            Toast.makeText(getActivity(), "Please enter a message", Toast.LENGTH_SHORT).show();
        }
        else{
            databaseReference.child(String.valueOf(childentries)).child("input").setValue(messageinputB);
            databaseReference.child(String.valueOf(childentries)).child("time").setValue(ServerValue.TIMESTAMP); //Time is in EPOCH format. Need to convert for display (Variable is 'long')
            //static message receiver for now
            databaseReference.child(String.valueOf(childentries)).child("receiver").setValue("Leader");
            if (anonymousB == "true"){ //Sets the name if anonymous or not
                databaseReference.child(String.valueOf(childentries)).child("name").setValue("Anonymous");
            }
            else{
                databaseReference.child(String.valueOf(childentries)).child("name").setValue("Jose Bugay");
            }
        }

    }


    //MessagesFragment to MessagesFragmentA method
    private void setFragment() {
        MessagesFragmentA messagesFragmentA = new MessagesFragmentA();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, messagesFragmentA);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


}
