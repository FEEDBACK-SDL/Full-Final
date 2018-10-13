package com.example.fedsev.feedback;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.shashank.sony.fancytoastlib.FancyToast;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.ceryle.segmentedbutton.SegmentedButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class Calling extends AppCompatActivity {
    ArrayList<String> listDataHeader;
    ArrayList<RecycleData> finalList;
    ArrayList<String> listDataChild;
    Context context;
    String service_id;
    BottomSheetBehavior sheetBehavior;
    RadioButton radioButton1,radioButton2,radioButton3;
    TextView name,number;
    SyncData syncData;
    RadioGroup radioGroup1,radioGroup2,radioGroup3;
    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;
    LinearLayout linearLayout;
    View avi ;
    CallRecyclerAdapter callRecyclerAdapter;
    RecyclerView recyclerView;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        radioGroup1 = findViewById(R.id.radiogroup1);
        radioGroup2 = findViewById(R.id.radiogroup2);
        radioGroup3 = findViewById(R.id.radiogroup3);
        recyclerView = findViewById(R.id.recycle);
        context = getApplicationContext();
        b = savedInstanceState;

        ButterKnife.bind(this);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        radioButton1 = findViewById(R.id.radiobutton11);
        radioButton1.setChecked(true);
        radioButton2 = findViewById(R.id.radiobutton21);
        radioButton2.setChecked(true);
        radioButton3 = findViewById(R.id.radiobutton31);
        radioButton3.setChecked(true);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        sertData();
        prepareListData();
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        callRecyclerAdapter = new CallRecyclerAdapter(finalList);
        recyclerView.setAdapter(callRecyclerAdapter);
        SwipeButton enableButton = (SwipeButton) findViewById(R.id.swipe_btn);
        enableButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                if(active) {
                    if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        check();
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        avi = findViewById(R.id.avi);
                        avi.setVisibility(View.VISIBLE);
                        linearLayout = findViewById(R.id.lock);
                        SimpleRatingBar simpleRatingBar1 = findViewById(R.id.RatingBar1);
                        simpleRatingBar1.setIndicator(true);
                        SimpleRatingBar simpleRatingBar2 = findViewById(R.id.RatingBar2);
                        simpleRatingBar1.setIndicator(true);
                        SimpleRatingBar simpleRatingBar3 = findViewById(R.id.RatingBar3);
                        simpleRatingBar1.setIndicator(true);
                        disableAllSettings(linearLayout,false);
                    }
                }else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    linearLayout = findViewById(R.id.lock);
                    SimpleRatingBar simpleRatingBar1 = findViewById(R.id.RatingBar1);
                    simpleRatingBar1.setIndicator(false);
                    SimpleRatingBar simpleRatingBar2 = findViewById(R.id.RatingBar2);
                    simpleRatingBar1.setIndicator(false);
                    SimpleRatingBar simpleRatingBar3 = findViewById(R.id.RatingBar3);
                    simpleRatingBar1.setIndicator(false);
                    avi = findViewById(R.id.avi);
                    avi.setVisibility(View.GONE);
                    disableAllSettings(linearLayout,true);
                }

            }
        });
        prefetchData();
    }
    public void check(){

        SimpleRatingBar simpleRatingBar1 = findViewById(R.id.RatingBar1);
        simpleRatingBar1.setIndicator(true);
        SimpleRatingBar simpleRatingBar2 = findViewById(R.id.RatingBar2);
        simpleRatingBar2.setIndicator(true);
        SimpleRatingBar simpleRatingBar3 = findViewById(R.id.RatingBar3);
        simpleRatingBar3.setIndicator(true);

    }

    public void disableAllSettings(ViewGroup mGroup, Boolean visiblity) {

        int itotal = mGroup.getChildCount();
        for (int i = 0; i < itotal; i++) {
            if (mGroup.getChildAt(i) instanceof ViewGroup) {

                disableAllSettings((ViewGroup) mGroup.getChildAt(i),visiblity);
            } else {
                mGroup.getChildAt(i).setEnabled(visiblity);
            }
        }
    }
    private void prefetchData(){
        avi = findViewById(R.id.avi);
        avi.setVisibility(View.VISIBLE);
        SyncData syncData = MainActivity.myAppDatabase.myDao().getData();
        this.syncData = syncData;
        name.setText(syncData.getFirst_name() + " " + syncData.getLast_name());
        number.setText(syncData.getPhone());
        listDataChild = new ArrayList<>();
        service_id = syncData.getService_id();
        listDataChild.add(String.valueOf(syncData.getVehical_number()));
        listDataChild.add(String.valueOf(syncData.getModel()));
        listDataChild.add(String.valueOf(syncData.getL_service()));
        listDataChild.add(String.valueOf(syncData.getN_service()));
        listDataChild.add(String.valueOf(syncData.getKms()));

        prepareListData();

        callRecyclerAdapter = new CallRecyclerAdapter(finalList);
        recyclerView.setAdapter(callRecyclerAdapter);
        avi.setVisibility(View.GONE);


    }

    private void sertData(){
        listDataHeader = new ArrayList<String>();
        listDataChild = new ArrayList<String>();
        listDataHeader.add("Vehical Number");
        listDataHeader.add("Model");
        listDataHeader.add("Last Service");
        listDataHeader.add("Next Service");
        listDataHeader.add("Kilometers");


        // Adding child data
        listDataChild.add("wait....");
        listDataChild.add("wait.... ");
        listDataChild.add("wait...");
        listDataChild.add("wait...");
        listDataChild.add("wait...");
    }
    private void prepareListData() {

        finalList = new ArrayList<>();
        {
            RecycleData recycleData = new RecycleData();
            recycleData.setHeader("Vehical Number");
            recycleData.setFooter(listDataChild.get(0));
            recycleData.setColor(1);
            finalList.add(recycleData);
        }
        {
            RecycleData recycleData4 = new RecycleData();
            recycleData4.setHeader("Vehical Model");
            recycleData4.setFooter(listDataChild.get(1));
            recycleData4.setColor(5);
            finalList.add(recycleData4);
        }
        {
            RecycleData recycleData1 = new RecycleData();
            recycleData1.setHeader("Last Service");
            recycleData1.setFooter(listDataChild.get(2));
            recycleData1.setColor(2);
            finalList.add(recycleData1);
        }
        {
            RecycleData recycleData2 = new RecycleData();
            recycleData2.setHeader("Next Service");
            recycleData2.setFooter(listDataChild.get(3));
            recycleData2.setColor(3);
            finalList.add(recycleData2);
        }
        {
            RecycleData recycleData3 = new RecycleData();
            recycleData3.setHeader("Kilometers");
            recycleData3.setFooter(listDataChild.get(4));
            recycleData3.setColor(4);
            finalList.add(recycleData3);
        }
        //hello
        // Adding child data

    }

    public void Call(View view) {
        String finalnumber = String.valueOf(number.getText());
        Intent callintent = new Intent(Intent.ACTION_CALL);
        callintent.setData(Uri.parse("tel:" + finalnumber));
        Log.d("Asd","call");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);

            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            //You already have permission
            try {
                startActivity(callintent);
            } catch(SecurityException e) {
                e.printStackTrace();
            }
        }
    }



    public void newl(View v){

        RadioButton rb = findViewById(R.id.button21);
        if(rb.isChecked()){
            RadioButton rb1 = findViewById(radioGroup1.getCheckedRadioButtonId());
            RadioButton rb2 = findViewById(radioGroup2.getCheckedRadioButtonId());
            RadioButton rb3 = findViewById(radioGroup3.getCheckedRadioButtonId());
            SimpleRatingBar simpleRatingBar1 = findViewById(R.id.RatingBar1);
            SimpleRatingBar simpleRatingBar2 = findViewById(R.id.RatingBar2);
            SimpleRatingBar simpleRatingBar3 = findViewById(R.id.RatingBar3);
            SimpleRatingBar custr = findViewById(R.id.crating);
            int a1 = (int) simpleRatingBar1.getRating();
            int a2 = 0;
            if(rb1.getText().toString().trim().equals("yes"))
                a2 = 1;
            int a3 = (int) simpleRatingBar2.getRating();
            int a4 = 0;
            if(rb2.getText().toString().trim().equals("yes"))
                a4 = 1;
            int a5 = (int) simpleRatingBar3.getRating();
            int a6 = 0;
            if(rb3.getText().toString().trim().equals("yes"))
                a6 = 1;
            int cr = (int) custr.getRating();
            CallStatEntity callStatEntity = new CallStatEntity();
            callStatEntity.setName(syncData.getFirst_name() + " " + syncData.getLast_name());
            callStatEntity.setNumber(syncData.getPhone());
            callStatEntity.setServiceid(Integer.valueOf(syncData.getService_id()));
            DateTimeFormatter dff = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter dff1 = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            callStatEntity.setTime(dff1.format(now));
            callStatEntity.setDate1(dff.format(now));
            MainActivity.myAppDatabase.myDao().updateCall(syncData.getService_id(),1);
            MainActivity.myAppDatabase.myDao().addrecord(callStatEntity);
            Log.d("values",String.valueOf(a1) + String.valueOf(a2) + String.valueOf(a3) + String.valueOf(a4) + String.valueOf(a5) + String.valueOf(a6) + String.valueOf(cr));
        }
        else{
            MainActivity.myAppDatabase.myDao().updateCall(service_id,2);
        }

        Intent i = getIntent();
        finish();
        startActivity(i);

    }
}
