package com.kgstrivers.tinderc;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kgstrivers.tinderc.Fragment.EmptyFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView name;
    CircleImageView circleImageView;

    EmptyFragment m = new EmptyFragment();
    public MatchviewHolder(View itemview)
    {
        super(itemview);
        itemview.setOnClickListener(this);

        initialize(itemview);
    }
    @Override
    public void onClick(View v) {

        loadFragment(m,v);

    }


    private void initialize(View itemview)
    {
        name = itemview.findViewById(R.id.matchname);
        circleImageView = itemview.findViewById(R.id.matchcircular);
    }
    public void loadFragment(Fragment fragment,View v) {
        FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.commit();
    }


}
