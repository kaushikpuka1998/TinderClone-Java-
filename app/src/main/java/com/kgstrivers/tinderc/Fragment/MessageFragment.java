package com.kgstrivers.tinderc.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kgstrivers.tinderc.Matchesadapter;
import com.kgstrivers.tinderc.Model.Cards;
import com.kgstrivers.tinderc.Model.Users;
import com.kgstrivers.tinderc.R;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    RecyclerView.Adapter matchesadapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_message, container, false);
        initialize(view);
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initialize(View view)
    {


        mAuth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.matchedrecycle);





        DatabaseReference h =  FirebaseDatabase.getInstance().getReference().child("Users").child("male").child(mAuth.getUid()).child("Users");
        h.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {



                    matchesadapter = new Matchesadapter(getMatches("male"),getContext());
                    recyclerView.setAdapter(matchesadapter);

                    layoutManager = new LinearLayoutManager(getContext());

                    recyclerView.setLayoutManager(layoutManager);

                    DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                    recyclerView.addItemDecoration(mDividerItemDecoration);

                    matchesadapter.notifyDataSetChanged();

                }else
                {
                    matchesadapter = new Matchesadapter(getMatches("female"),getContext());
                    recyclerView.setAdapter(matchesadapter);

                    layoutManager = new LinearLayoutManager(getContext());

                    recyclerView.setLayoutManager(layoutManager);


                    DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                    recyclerView.addItemDecoration(mDividerItemDecoration);

                    matchesadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    List<Cards> resultmatch = new ArrayList<>();
    private List<Cards> getMatches(String userf)
    {
        String k;
        if(userf == "male")
        {
            k= "female";
        }
        else
        {
            k = "male";
        }
        DatabaseReference mainref = FirebaseDatabase.getInstance().getReference().child("Users").child(userf).child(mAuth.getUid()).child("Connections").child("Matched");
        
        
        mainref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    for (DataSnapshot snap:snapshot.getChildren()) {

                            Fetchinform(snap.getKey().toString(),k);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return resultmatch;
    }

    private void Fetchinform(String key,String opp)
    {
        DatabaseReference mainref = FirebaseDatabase.getInstance().getReference().child("Users").child(opp).child(key).child("Users");


        mainref.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Cards card = new Cards(snapshot.child("imageurl").getValue().toString(),snapshot.child("name").getValue().toString(),"","");


                    resultmatch.add(card);
                    matchesadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}