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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class mainTelent extends AppCompatActivity {


    RecyclerView mrecycleView;
    TalentAdapter mcourseadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_telent);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      //  Toast.makeText(getApplicationContext(), "in try 1" , Toast.LENGTH_SHORT).show();

        mrecycleView = findViewById(R.id.RecycleTalent);
        mrecycleView.setLayoutManager(new LinearLayoutManager(this));
        mcourseadapter = new TalentAdapter(this, getmylist());
        mrecycleView.setAdapter(mcourseadapter);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainTelent.this.onBackPressed();

             //   Intent i = new Intent(view.getContext(),mainInput.class);
               // startActivity(i);

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

            //Toast.makeText(getApplicationContext(), "in try block" , Toast.LENGTH_SHORT).show();

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Integer intPassion = sharedPreferences.getInt("Passion_Index",0);
            Integer intLevel = sharedPreferences.getInt("Level_Index",0);

            // Toast.makeText(getApplicationContext(), String.valueOf(intPassion), Toast.LENGTH_LONG).show();


            rootobj = new JSONObject(s);
            JSONArray arrLevel = rootobj.getJSONArray(("Level"));
            JSONObject objLvl = arrLevel.getJSONObject(intLevel);
            JSONArray arrPassion = objLvl.getJSONArray(("Passion"));
            JSONObject objPass = arrPassion.getJSONObject(intPassion);

            //   if (arrJasonTypeArray == null)
            //      Toast.makeText(getApplicationContext(), "null object", Toast.LENGTH_LONG).show();
            // else
            //    Toast.makeText( getApplicationContext(),String.valueOf(arrJasonTypeArray.length()), Toast.LENGTH_LONG).show();


            // myJasonArray = new String[arrJasonTypeArray.length()];
       //     for (int intcounter = 0; intcounter < arrJasonTypeArray.length(); intcounter++) {
                //myJasonArray[intcounter] = arrJasonTypeArray.getString(intcounter);
                //  Toast.makeText( getApplicationContext(),myJasonArray[intcounter].toString(), Toast.LENGTH_SHORT).show();
                CourseModel m = new CourseModel();

                m.setTxtCourseText1("Name : " + objPass.getString("Name"));
                m.setTxtCourseText2("Eligiblity : "  + objPass.getString("Eligiblity"));
                m.setTxtCourseText3("Site : " + objPass.getString("Site"));

                models.add(m);
         //   }


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
