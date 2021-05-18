package com.example.healthyliving;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

public class MessagesFragmentA extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages_a, container, false);
        //Create button
        Button create = (Button)view.findViewById(R.id.createMessageBtn);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment();
            }
        });

        return view;
    }

    //MessagesFragmentA to MessagesFragment method
    private void setFragment() {
        MessagesFragment messagesFragment = new MessagesFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, messagesFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}