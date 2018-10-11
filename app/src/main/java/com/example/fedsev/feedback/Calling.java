package com.example.fedsev.feedback;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.shashank.sony.fancytoastlib.FancyToast;


import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    BottomSheetBehavior sheetBehavior;

    TextView name,number;
    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;
    int flag;
    adapter2 a2;
    adapter a;
    LinearLayout linearLayout;
    View avi ;
    RecyclerView recyclerView;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.recycle);
        context = getApplicationContext();
        b = savedInstanceState;
        setContentView(R.layout.activity_calling);
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


        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        prepareListData();
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        CallRecyclerAdapter callRecyclerAdapter = new CallRecyclerAdapter(finalList);
        recyclerView.setAdapter(callRecyclerAdapter);
        SwipeButton enableButton = (SwipeButton) findViewById(R.id.swipe_btn);
        enableButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                if(active) {
                    if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        avi = findViewById(R.id.avi);
                        avi.setVisibility(View.VISIBLE);
                        linearLayout = findViewById(R.id.lock);
                        disableAllSettings(linearLayout,false);
                    }
                }else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    linearLayout = findViewById(R.id.lock);
                    avi = findViewById(R.id.avi);
                    avi.setVisibility(View.GONE);
                    disableAllSettings(linearLayout,true);
                }

            }
        });
        prefetchData();
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<DataFromServer> call = api.getData();
        avi = findViewById(R.id.avi);
        avi.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<DataFromServer>() {
            @Override
            public void onResponse(Call<DataFromServer> call, Response<DataFromServer> response) {
                DataFromServer dataFromServer = response.body();
                Log.d("asd",dataFromServer.getFname());
                name.setText(dataFromServer.getFname() + " " + dataFromServer.getLname());
                number.setText(dataFromServer.getPhone());
                listDataChild = new ArrayList<>();
                listDataChild.add(String.valueOf(dataFromServer.getService_id()));
                listDataChild.add(String.valueOf(dataFromServer.getD_Service()));
                listDataChild.add(String.valueOf(dataFromServer.getN_Service()));
                listDataChild.add(String.valueOf(dataFromServer.getKms()));
                a = new adapter();
                a2 = new adapter2();


                avi.setVisibility(View.GONE);
                FancyToast.makeText(Calling.this,"Success",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
            }

            @Override
            public void onFailure(Call<DataFromServer> call, Throwable t) {
                FancyToast.makeText(Calling.this,"Server Error",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                //Toast.makeText(Calling.this,"Server Error",Toast.LENGTH_LONG).show();
                avi.setVisibility(View.GONE);
            }
        });

    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new ArrayList<String>();
        finalList = new ArrayList<>();
        {
            RecycleData recycleData = new RecycleData();
            recycleData.setHeader("Service ID");
            recycleData.setFooter("wait....");
            recycleData.setColor(1);
            finalList.add(recycleData);
        }
        {
            RecycleData recycleData1 = new RecycleData();
            recycleData1.setHeader("Last Service");
            recycleData1.setFooter("wait....");
            recycleData1.setColor(2);
            finalList.add(recycleData1);
        }
        {
            RecycleData recycleData2 = new RecycleData();
            recycleData2.setHeader("Next Service");
            recycleData2.setFooter("wait....");
            recycleData2.setColor(3);
            finalList.add(recycleData2);
        }
        {
            RecycleData recycleData3 = new RecycleData();
            recycleData3.setHeader("Kilometers");
            recycleData3.setFooter("wait....");
            recycleData3.setColor(4);
            finalList.add(recycleData3);
        }
        //hello
        // Adding child data
        listDataHeader.add("Service ID");
        listDataHeader.add("Last Service");
        listDataHeader.add("Next Service");
        listDataHeader.add("Kilometers");


        // Adding child data
        listDataChild.add("wait....");
        listDataChild.add("wait.... ");
        listDataChild.add("wait...");
        listDataChild.add("wait...");
    }

    public void Call(View view) {
        String finalnumber = String.valueOf(number.getText());
        Intent callintent = new Intent(Intent.ACTION_CALL);
        callintent.setData(Uri.parse("tel:" + finalnumber));

        if (ActivityCompat.checkSelfPermission(Calling.this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            return;
        }
        startActivity(callintent);
    }


    class adapter extends BaseAdapter {
        private int mLastPosition;

        @Override
        public int getCount() {
            return listDataHeader.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater infalInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = infalInflater.inflate(R.layout.list_layout, null);
            }
            TextView textView = (TextView) view.findViewById(R.id.footer);
            //Log.d("asd",String.valueOf(i) + String.valueOf(flag));
            if (flag == 0){
                textView.setText(listDataHeader.get(i));}
            else{
                textView.setText(listDataChild.get(i));}
            return view;
        }
    }
    class adapter2 extends BaseAdapter {
        private int mLastPosition;

        @Override
        public int getCount() {
            return listDataHeader.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater infalInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = infalInflater.inflate(R.layout.list_layout, null);
            }
            TextView textView = (TextView) view.findViewById(R.id.header);
            // Log.d("asd",String.valueOf(i) + String.valueOf(flag));

            textView.setText(listDataChild.get(i));
            return view;
        }
    }
    public void newl(View v){
        Intent i = getIntent();
        finish();
        startActivity(i);

    }
}
