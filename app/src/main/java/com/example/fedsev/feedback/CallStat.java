package com.example.fedsev.feedback;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CallStat extends Fragment {

    private View view;
    Spinner s;
    ArrayAdapter<CharSequence> adapter_s;
    RecyclerView recyclerView;
    CardAdapter adapter;
//    List<InfoCard> infolist;
    List<CallStatEntity> recordlist;
    TextView countview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.callstats, container, false);

//        infolist = new ArrayList<>();

        recordlist = MainActivity.myAppDatabase.myDao().getrecords();

        recyclerView = (RecyclerView) view.findViewById(R.id.rec1);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        maincode();

        return view;
    }

    public void maincode(){


        addcards();

//        adapter = new CardAdapter(getContext(),infolist);
        adapter = new CardAdapter(getContext(),recordlist);
        recyclerView.setAdapter(adapter);

        spinnersort();

    }


    public void spinnersort(){
        s = (Spinner) view.findViewById(R.id.spinner2);

        adapter_s = ArrayAdapter.createFromResource(getContext(),R.array.Dates,android.R.layout.simple_spinner_item);
        adapter_s.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s.setAdapter(adapter_s);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String[] dates = {"Yesterday", "Last 3 Days","Last 7 Days", "Last 15 Days", "Last 30 Days" };
                int po = (int) parent.getItemIdAtPosition(position);
                String d = dates[po];
                filter_process(po);
                //Toast.makeText(getContext(),d + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public boolean filter_process(int po){

//        ArrayList<InfoCard> newlist = new ArrayList<>();
        ArrayList<CallStatEntity> newlist = new ArrayList<>();


        int days = get_days(po);

        LocalDate date = LocalDate.now().minusDays(days);  //base date to be compared
        Toast.makeText(getContext(),date + " selected", Toast.LENGTH_SHORT).show();


//        for(InfoCard p : infolist){
//
//            LocalDate d = LocalDate.parse(p.getDate1());
//
//            if(d.isAfter(date) && d.isBefore(LocalDate.now())){
//                System.out.println("Check");
//                newlist.add(p);
//            }
//
//        }

        for(CallStatEntity p : recordlist){

            LocalDate d = LocalDate.parse(p.getDate1());

            if(d.isAfter(date) && d.isBefore(LocalDate.now())){
                System.out.println("Check");
                newlist.add(p);
            }

        }


        countview = (TextView) view.findViewById(R.id.textViewcount);
        countview.setText(String.valueOf(newlist.size()));
        // newlist.sort((d1,d2) -> d1.compareTo(d2));
//        infolist.sort(Comparator.comparing(InfoCard::getDate1));
        recordlist.sort(Comparator.comparing(CallStatEntity::getDate1));

        adapter.setFilter(newlist);
        return true;

    }

    public int get_days(int day){

        int days = 0;

        switch(day){

            case 0:
                days = 2;
                break;

            case 1:
                days = 4;
                break;

            case 2:
                days = 8;
                break;

            case 3:
                days = 16;
                break;

            case 4:
                days = 31;
                break;

        }

        return days;

    }


    public void addcards(){

//        infolist.add(
//                new InfoCard(
//                        "Ayush Gupta",
//                        1234567,
//                        1234567890,
//                        "19:10:23",
//                        "2018-09-22"
//
//                )
//        );
//
//        infolist.add(
//                new InfoCard(
//                        "Amey",
//                        2224567,
//                        1234567890,
//                        "19:10:23",
//                        "2018-09-29"
//
//                )
//        );
//        infolist.add(
//                new InfoCard(
//                        "ABc",
//                        1224567,
//                        1234567999,
//                        "19:10:23",
//                        "2018-09-21"
//
//                )
//        );
//        infolist.add(
//                new InfoCard(
//                        "pqc",
//                        3334567,
//                        1234567999,
//                        "19:10:23",
//                        "2018-09-13"
//
//                )
//        );
//        infolist.add(
//                new InfoCard(
//                        "xyz",
//                        4434567,
//                        1234567999,
//                        "19:10:23",
//                        "2018-09-16"
//
//                )
//        );


    }
}