package com.shubh.whatnext;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class mainJob extends AppCompatActivity {

    RecyclerView mrecycleView;
    CourseAdapter mcourseadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_courses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mrecycleView = findViewById(R.id.RecycleCourses);
        mrecycleView.setLayoutManager(new LinearLayoutManager(this));
        mcourseadapter = new CourseAdapter(this, getmylist());
        mrecycleView.setAdapter(mcourseadapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (mcourseadapter.getSelected() != null) {
                    editor.putString("Job",mcourseadapter.getSelected().txtCourseText1.toString());
                    // Toast.makeText(getApplicationContext(), mcourseadapter.getSelected().txtCourseText2.toString() , Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("Job","Various Job Opprtunities");
                    //Toast.makeText(getApplicationContext(), "No Selection" , Toast.LENGTH_SHORT).show();
                }
                editor.commit();

                Intent i = new Intent(view.getContext(),mainEnd.class);
                startActivity(i);
            }
        });
    }

    private ArrayList<CourseModel> getmylist()
    {

        ArrayList<CourseModel>  models=  new ArrayList<>();


        //Toast.makeText(getApplicationContext(), "before data object" , Toast.LENGTH_SHORT).show();

        JSONObject rootobj;
        String s = getJSONFile();

        try {

            // Toast.makeText(getApplicationContext(), "in try block" , Toast.LENGTH_SHORT).show();

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Integer intPassion = sharedPreferences.getInt("Passion_Index",0);
            Integer intLevel = sharedPreferences.getInt("Level_Index",0);
            Integer intSubPassion = sharedPreferences.getInt("SubPassion_Index",0);


            // Toast.makeText(getApplicationContext(), String.valueOf(intPassion), Toast.LENGTH_LONG).show();


            rootobj = new JSONObject(s);
            JSONArray arrLevel = rootobj.getJSONArray(("Level"));
            JSONObject objLvl = arrLevel.getJSONObject(intLevel);
            JSONArray arrPassion = objLvl.getJSONArray(("Passion"));

            JSONArray arrSubPassion  =  arrPassion.getJSONObject(intPassion).getJSONArray("SubPassion");

            JSONArray arrJasonTypeArray = arrSubPassion.getJSONObject(intSubPassion).getJSONArray("Jobs");





            //   if (arrJasonTypeArray == null)
            //      Toast.makeText(getApplicationContext(), "null object", Toast.LENGTH_LONG).show();
            // else
            //    Toast.makeText( getApplicationContext(),String.valueOf(arrJasonTypeArray.length()), Toast.LENGTH_LONG).show();


            // myJasonArray = new String[arrJasonTypeArray.length()];
            for (int intcounter = 0; intcounter < arrJasonTypeArray.length(); intcounter++) {
                CourseModel m = new CourseModel();
                m.setTxtCourseText1(arrJasonTypeArray.getString(intcounter));
                m.setTxtCourseText2("Job details will come here:");
                //m.setTxtCourseText1(arrJasonTypeArray.getJSONObject(intcounter).getString("Name"));
                //m.setTxtCourseText2(arrJasonTypeArray.getJSONObject(intcounter).getString("Site"));
                models.add(m);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return models;
    }


    public String getJSONFile(){
        String json = null;
        try {

            InputStream is = getResources().openRawResource(R.raw.data);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
