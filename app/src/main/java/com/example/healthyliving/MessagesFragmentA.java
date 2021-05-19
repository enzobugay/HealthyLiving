package com.example.healthyliving;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class MessagesFragmentA extends Fragment {

    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;
    int count = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages_a, container, false);
        //Create button
        Button create = (Button) view.findViewById(R.id.createMessageBtn);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WallFragment wf = (WallFragment) getChildFragmentManager().findFragmentById(R.id.fragment_message_a);
        //call display method here
        getMessagesList();

    }


    //MessagesFragmentA to MessagesFragment method
    private void setFragment() {
        MessagesFragment messagesFragment = new MessagesFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, messagesFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    //method to display messages
    public void getMessagesList(){

        /**
         * Dynamic display of list
         * */
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        ListView listView = getView().findViewById(R.id.listViewMessages);
        //Creating a list to store the data values from database and passed through arrayadapter
        ArrayList<String> list = new ArrayList<String>();
        ArrayAdapter adapter = new ArrayAdapter<String> (getActivity(), R.layout.list_item,list); //reuse the same layout
        listView.setAdapter(adapter);
        firebaseDatabase = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                count++;
                //Progress dialog counter -> will close
                if (count >= snapshot.getChildrenCount()) {
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

        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                ArrayList<String> userList = new ArrayList<String>();
                ArrayList<String> anonymousList = new ArrayList<String>();


                //list.clear();

                for (DataSnapshot ds : snapshot.child("message").getChildren()) {
                    String strInput = ds.child("input").getValue().toString();
                    String strReceiver = ds.child("receiver").getValue().toString();
                    if (ds.child("name").exists()){
                        String strName = ds.child("name").getValue().toString();
                        String strCombined = "to " + strReceiver + " from " + strName + "\n" + strInput;
                        //storing values to a list
                        list.add(strCombined);
                    }
                }


                //reversing the list order
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}