package com.example.healthyliving;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WallFragment extends Fragment {

    EditText inputwall;
    FirebaseDatabase firebaseDatabase;

    String inputwallB, anonymousB = "false";
    int count = 0;
    int entries;
    int childCount;


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

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button viewPost = (Button) view.findViewById(R.id.viewPost);
        viewPost.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getInput();
            }
        }
        );
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
    //display wall message from db
    public void getInput(){
        
        //Static display of list
        /**
        ListView listView = getView().findViewById(R.id.listView);
        String[] list = {"This is a sample post", "Another post","Last Post","This is a test"};
        ArrayAdapter adapter = new ArrayAdapter<String> (getActivity() , R.layout.list_item, list);
        listView.setAdapter(adapter);
         **/

        /**
         * Dynamic display of list
         * */
        ListView listView = getView().findViewById(R.id.listView);
        ArrayList <String> list = new ArrayList<String>();
        ArrayAdapter adapter = new ArrayAdapter<String> (getActivity(), R.layout.list_item,list);
        listView.setAdapter(adapter);
        firebaseDatabase = FirebaseDatabase.getInstance("https://healthyliving-da296-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("wall");

        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //getting specific child branch "input"  to add in the list
                    list.add(ds.child("input").getValue().toString());
                }
                adapter.notifyDataSetChanged();
                //System.out.println(list);
            }

            //Single node display of text

            /**DatabaseReference databaseReference = firebaseDatabase.getReference("wall").child("1");
             viewPost = getView().findViewById(R.id.viewWallPost);
             String post = snapshot.child("input").getValue().toString();
             viewPost.setText(post);
             ***/
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

}
