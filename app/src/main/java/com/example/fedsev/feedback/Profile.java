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
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.roger.match.library.MatchTextView;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;

public class Profile extends Fragment {

    private View view;
    private ImageView profileImageView;
    private CircleImageView profileCircleImageView;
    private FloatingActionButton editFAB;
    private EditText EmailEditText,ContactEditText;
    private RatingBar ratingBar;
    private boolean editMode = false;
    private final static int MaxRating = 5;

    CallsRoom call;
    public Profile()
    {
        call = new CallsRoom();
    }

    public void setColumn(int id, String name, String phoneNo)
    {
        call.setId(id);
        call.setName(name);
        call.setPhoneNo(phoneNo);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_profile , container, false);

        setColumn(1,"David James" , "9123456780");
        MainActivity.myAppDatabase.myDao().addCalls(call);
//        setColumn(2,"Steve Smith" , "12345678901");
//        MainActivity.myAppDatabase.myDao().addCalls(call);

        profileImageView = (ImageView) view.findViewById(R.id.ProfileImage);
        editFAB = (FloatingActionButton) view.findViewById(R.id.EditProfilefab);
        ratingBar = (RatingBar) view.findViewById(R.id.RatingBar);
        EmailEditText = (EditText) view.findViewById(R.id.EmailEditText);
        ContactEditText = (EditText) view.findViewById(R.id.ContactEditText);

        final SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("private_data", getContext().MODE_PRIVATE);
//        System.out.println(sharedPreferences.getString("email",""));
        EmailEditText.setText(sharedPreferences.getString("email",""));
        ContactEditText.setText(sharedPreferences.getString("contact",""));

        int rating = sharedPreferences.getInt("rating",2);

        ratingBar.setMax(MaxRating);
        ratingBar.setRating(rating);
        ratingBar.setEnabled(false);

        MatchTextView matchTextView = (MatchTextView) view.findViewById(R.id.ProfileUsername);
        matchTextView.setText(sharedPreferences.getString("first_name","John") +" "+ sharedPreferences.getString("last_name","Doe"));
        matchTextView.setTextSize(40);
        matchTextView.setTextColor(Color.RED);
        matchTextView.setAlpha(0.75f);

        editFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editMode) {
                    editFAB.setImageResource(R.drawable.ic_check);

                    EmailEditText.setEnabled(true);
                    ContactEditText.setEnabled(true);
                }
                else {
                    editFAB.setImageResource(R.drawable.ic_edit);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String email = EmailEditText.getText().toString();
                    editor.putString("email",email);

                    String contact = ContactEditText.getText().toString();
                    editor.putString("contact",contact);
                    editor.commit();
                    editor.apply();

                    EmailEditText.setText(email);
                    ContactEditText.setText(contact);

                    EmailEditText.setEnabled(false);
                    ContactEditText.setEnabled(false);
                }

                editMode = !editMode;
            }
        });


        return view;
    }
}
