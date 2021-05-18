package com.example.healthyliving;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import androidx.recyclerview.widget.RecyclerView;

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

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getSystemService;

public class WallFragment extends Fragment {

    EditText inputwall;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;
    String inputwallB, anonymousB = "false";
    CheckBox anonymous;
    int entries, count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wall, container, false);

        //Post button
        Button post = (Button)view.findViewById(R.id.postwall);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inputwall == null){
                    Toast.makeText(getActivity(), "Please enter a message", Toast.LENGTH_SHORT).show();
                }
                else{
                    postInput();
                }
            }
        });

        //Anonymous checkbox
        anonymous = (CheckBox)view.findViewById(R.id.wallcheckBox);
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

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WallFragment wf = (WallFragment) getChildFragmentManager().findFragmentById(R.id.fragment_wall);

        getInput();
        postInput();

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
           // Do nothing
        }
        else{
            //Hides the virtual keyboard after on click
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            //Clear text/focus from  edit text
            inputwall.setText(null);
            inputwall.clearFocus();
            //Reset checkbox
            anonymous.setChecked(false);
            //Set values to DB
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
    //display wall message from db
    public void getInput(){

        /**
         * Dynamic display of list
         * */
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        ListView listView = getView().findViewById(R.id.listView);
        ArrayList <String> list = new ArrayList<String>();
        ArrayAdapter adapter = new ArrayAdapter<String> (getActivity(), R.layout.list_item,list);
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

                    list.clear();

                    for (DataSnapshot ds : snapshot.child("wall").getChildren()) {
                        String strInput = ds.child("input").getValue().toString();
                        if (ds.child("name").exists()){
                        String strName = ds.child("name").getValue().toString();
                        String strCombined = strName + "\n" + strInput;
                        list.add(strCombined);
                        }
                    }
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

}
