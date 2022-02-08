package com.shubh.whatnext;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CourseHolder extends RecyclerView.ViewHolder {

    public TextView mText1;
    public TextView mText2;
    public TextView mText3;
    public CardView inside;

    public CourseHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setClickable(true);

        this.mText1 = itemView.findViewById(R.id.txtText1);
        this.mText2 = itemView.findViewById(R.id.txtText2);
        if (itemView.findViewById(R.id.txtText3) != null)
            this.mText3 = itemView.findViewById(R.id.txtText3);


    }
}

