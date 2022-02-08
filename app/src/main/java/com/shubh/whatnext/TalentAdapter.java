package com.shubh.whatnext;

import android.content.Context;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TalentAdapter extends RecyclerView.Adapter<CourseHolder> {

    Context c;
    ArrayList<CourseModel> models;

    public TalentAdapter(Context c, ArrayList<CourseModel> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main_telent, parent,  false);

        return new CourseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        holder.mText1.setText(models.get(position).getTxtCourseText1());
        holder.mText2.setText(models.get(position).getTxtCourseText2());
        if (models.get(position).getTxtCourseText3() != null)
            holder.mText3.setText(models.get(position).getTxtCourseText3());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
