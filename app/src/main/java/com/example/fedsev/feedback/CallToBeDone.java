package com.example.fedsev.feedback;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class CallToBeDone extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //addrecords();
        return inflater.inflate(R.layout.calltobedone, container, false);
    }

//    public void addrecords(){
//
//        CallStatEntity c1 = new CallStatEntity();
//        c1.setName("Amey");
//        c1.setServiceid(1000001);
//        c1.setNumber(912345678);
//        c1.setTime("19:10:23");
//        c1.setDate1("2018-09-22");
//
//        MainActivity.myAppDatabase.myDao().addrecord(c1);
//        Toast.makeText(getActivity(), "recordaddedsuccessfully",Toast.LENGTH_SHORT).show();
//
//    }
}
