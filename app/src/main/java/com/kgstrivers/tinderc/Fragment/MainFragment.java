package com.kgstrivers.tinderc.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kgstrivers.tinderc.MainPageActivity;
import com.kgstrivers.tinderc.Model.Cards;
import com.kgstrivers.tinderc.Model.Logoutd;
import com.kgstrivers.tinderc.R;
import com.kgstrivers.tinderc.SignInActivity;
import com.kgstrivers.tinderc.arrayAdapter;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private TextView email;
    private Button logout;
    private FirebaseAuth mAuth;
    private long backPressedtime;
    private com.kgstrivers.tinderc.arrayAdapter arrayAdapter;
    SwipeFlingAdapterView filingContainer;
    TextView vf;
    List<Cards> listcard;
    BottomNavigationView bottomNavigationView;

    DatabaseReference userdb;


    String usersex;

    public void setOpposex(String  u)
    {
        opposex = u;
    }

    public void setusersex(String u)
    {
        usersex = u;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initialize(view);




        mAuth = FirebaseAuth.getInstance();



        userdb = FirebaseDatabase.getInstance().getReference().child("Users");


        listcard= new ArrayList<Cards>();
        //choose your favorite adapter
        arrayAdapter = new arrayAdapter(getContext(), R.layout.item, listcard);


        filingContainer.setAdapter(arrayAdapter);

        if(mAuth!=null)
        {


            usersexfind();



        }
        filingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

                listcard.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {

                vf.setText("UPDATED");



                Cards newcard = (Cards)o;

                String userid = newcard.getUserid();
                userdb.child(opposex).child(userid).child("Connections").child("Rejected").child(mAuth.getUid()).setValue("true");

                Toast.makeText(getContext(), "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object o) {

                vf.setText("Updated");

                Cards newcard = (Cards)o;

                String userid = newcard.getUserid();

                isMatched(userid);
                userdb.child(opposex).child(userid).child("Connections").child("Liked").child(mAuth.getUid()).setValue("true");
                Toast.makeText(getContext(), "Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int arri) {


            }

            @Override
            public void onScroll(float v) {

            }
        });
        return view;
    }


    private void isMatched(String userid)
    {
        DatabaseReference currentuserref = userdb.child(usersex).child(mAuth.getUid()).child("Connections").child("Liked").child(userid);

        currentuserref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.exists())
                {
                    Toast.makeText(getContext(), "New Connection", Toast.LENGTH_SHORT).show();
                    userdb.child(opposex).child(snapshot.getKey()).child("Connections").child("Matched").child(mAuth.getUid()).setValue("true");
                    userdb.child(usersex).child(mAuth.getUid()).child("Connections").child("Matched").child(snapshot.getKey()).setValue("true");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }public void usersexfind()
    {
        Boolean[] present = {false};
        final DatabaseReference maleref = FirebaseDatabase.getInstance().getReference().child("Users").child("male");
        maleref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {




                Log.d("UserKey:",snapshot.getKey());
                if(snapshot.getKey().equals(mAuth.getUid()))
                {
                    System.out.println(snapshot.getKey().equals(mAuth.getUid()));
                    oppositesexfind("female");
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

        final DatabaseReference femaleref = FirebaseDatabase.getInstance().getReference().child("Users").child("female");

        femaleref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getKey().equals(mAuth.getUid()))
                {
                    System.out.println(snapshot.getKey().equals(mAuth.getUid()));
                    oppositesexfind("male");
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





    }
    String opposex;

    private void oppositesexfind(String oppositesex)
    {

        if(oppositesex == "female")
        {
            setusersex("male");
        }

        if(oppositesex == "male")
        {
            setusersex("female");
        }
        setOpposex(oppositesex);
        final DatabaseReference oppref = FirebaseDatabase.getInstance().getReference().child("Users").child(oppositesex);

        oppref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                if(snapshot.exists() && !snapshot.child("Connections").child("Rejected").hasChild(mAuth.getUid()) && !snapshot.child("Connections").child("Liked").hasChild(mAuth.getUid()))
                {
                    Cards cards = new Cards("",snapshot.child("Users").child("name").getValue().toString(),snapshot.getKey());
                    listcard.add(cards);
                    arrayAdapter.notifyDataSetChanged();
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



    }


    private void initialize(View view)
    {

        email = view.findViewById(R.id.email);
        logout = view.findViewById(R.id.logout);
        filingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.swipeadapter);
        vf = view.findViewById(R.id.value);
        bottomNavigationView = view.findViewById(R.id.bottomnavigationview);

    }
}