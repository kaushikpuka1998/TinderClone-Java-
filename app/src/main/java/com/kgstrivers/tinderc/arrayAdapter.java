package com.kgstrivers.tinderc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kgstrivers.tinderc.Model.Cards;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<Cards> {

    Context context;

    public arrayAdapter(Context context, int resourceid, List<Cards> list)
    {
        super(context,resourceid,list);
    }

    public View getView(int position, View contentView, ViewGroup parent)
    {
        Cards cards_item = getItem(position);


        if(contentView == null)
        {
            contentView = LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);
        }


        TextView name = contentView.findViewById(R.id.helloText);
        ImageView imageView = contentView.findViewById(R.id.itemimageview);

        name.setText(cards_item.getName());

        imageView.setBackgroundResource(R.drawable.femalepic);


        return contentView;



    }
}
