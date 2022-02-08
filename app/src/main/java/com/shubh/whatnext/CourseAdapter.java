package com.shubh.whatnext;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseHolder> {

    int selectedPosition=-1;
    Context c;
    ArrayList<CourseModel> models;

    public CourseAdapter(Context c, ArrayList<CourseModel> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main_courses, parent,  false);

        return new CourseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseHolder holder, final int position) {
        holder.mText1.setText(models.get(position).getTxtCourseText1());
        holder.mText2.setText(models.get(position).getTxtCourseText2());
        if (models.get(position).getTxtCourseText3() != null)
            holder.mText3.setText(models.get(position).getTxtCourseText3());


        if(selectedPosition==position)
            holder.itemView.setBackgroundColor(Color.parseColor("#ff99cc00"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public CourseModel getSelected() {
        if (selectedPosition != -1) {
            return models.get(selectedPosition);
        }
        return null;
    }
}
