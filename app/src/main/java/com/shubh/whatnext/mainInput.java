package com.shubh.whatnext;

import android.R.layout;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class mainInput extends AppCompatActivity {

    ListView simpleList;
   // String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};
    String passionList[] = {""};
    JSONArray arrPassion;
    String myJasonArray[];
    Integer mintSubPassionCount;
    String mSelectedItem = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_input);
       Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        JSONObject rootobj;
        String s = getJSONFile();

        //
        // Try to parse some JSON and display it in a listview.
        //

            try {
                rootobj = new JSONObject(s);
                JSONArray arrLevel  = rootobj.getJSONArray(("Level"));

                myJasonArray = new String[arrLevel.length()+1];
                myJasonArray[0]="Please Select an Option";
                for(int i=0; i< arrLevel.length(); i++)
                {
                    myJasonArray[i+1] = arrLevel.getJSONObject(i).getString("Name");
              //      Toast.makeText( getApplicationContext(),myJasonArray[i].toString(), Toast.LENGTH_LONG).show();

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }






        simpleList = (ListView)findViewById(R.id.passionListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mainInput.this, layout.simple_list_item_1,passionList );
        simpleList.setAdapter(arrayAdapter);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // for (int j = 0; j < parent.getChildCount(); j++)
                 //   parent.getChildAt(j).setBackgroundColor(R.Color.Color.TRANSPARENT);

                String selectedItem = (String) parent.getItemAtPosition(position);
          //      Toast.makeText( getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
               // getSharedPreferences(Context.MODE_PRIVATE);
                //write
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Passion",selectedItem);
                editor.putInt("Passion_Index",position);
                editor.putString("SubPassion",selectedItem);
                mSelectedItem = selectedItem;
                editor.putInt("SubPassion_Index",0);
                boolean bolind =  editor.commit();

               // Toast.makeText(getApplicationContext(), String.valueOf(bolind), Toast.LENGTH_LONG).show();

               // Toast.makeText(getApplicationContext(), String.valueOf(sharedPreferences.getInt("Passion_index",0)), Toast.LENGTH_LONG).show();

            }
        });

        ArrayAdapter<String> arrayAdapterLevel = new ArrayAdapter<String>(mainInput.this, layout.simple_list_item_1,myJasonArray);
        Spinner grageSelect = (Spinner)findViewById(R.id.grade_spinner);
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mainInput.this, layout.simple_list_item_1,countryList );
        grageSelect.setAdapter(arrayAdapterLevel);
        grageSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {
                //Toast.makeText(getApplicationContext(), (CharSequence) adapter.getSelectedItem(), Toast.LENGTH_SHORT).show();
                if (i > 0) {

                    try {

                        JSONObject rootobj;
                        String s = getJSONFile();
                        rootobj = new JSONObject(s);
                        JSONArray arrLevel = rootobj.getJSONArray(("Level"));
                        JSONObject objLvl = arrLevel.getJSONObject(i-1);
                        arrPassion = objLvl.getJSONArray(("Passion"));
                        if (arrPassion == null)
                            Toast.makeText(getApplicationContext(), "null object", Toast.LENGTH_LONG).show();

                        //    else
                        //      Toast.makeText( getApplicationContext(),arrPassion.getJSONObject(0).getString("Name"), Toast.LENGTH_LONG).show();


                        String myJasonArray[] = new String[arrPassion.length()];
                        for (int intcounter = 0; intcounter < arrPassion.length(); intcounter++) {
                            myJasonArray[intcounter] = arrPassion.getJSONObject(intcounter).getString("Name");
                            //  Toast.makeText( getApplicationContext(),myJasonArray[intcounter].toString(), Toast.LENGTH_LONG).show();

                        }
                        passionList = myJasonArray;

                        if (simpleList == null)
                            Toast.makeText(getApplicationContext(), "Simplelist object null", Toast.LENGTH_LONG).show();

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mainInput.this, layout.simple_list_item_1, passionList);
                        simpleList.setAdapter(arrayAdapter);

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        //write
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Level",adapter.getSelectedItem().toString());
                        editor.putInt("Level_Index",i-1);
                        editor.commit();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(getApplicationContext(), "Nothing selected", Toast.LENGTH_SHORT).show();

            }
        });


        FloatingActionButton fab = findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //  Toast.makeText( getApplicationContext(), "getting there", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Integer intLevel = sharedPreferences.getInt("Level_Index", -1);
                Integer intPassion = sharedPreferences.getInt("Passion_Index", -1);
                Integer ncount = 0;

                //    Toast.makeText(getApplicationContext(),intPassion, Toast.LENGTH_LONG).show();

                if (mSelectedItem.equals("")) {
                    Snackbar.make(view, "Please select your option", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {

                    try {
                        ncount = arrPassion.getJSONObject(intPassion).getJSONArray("SubPassion").length();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

               //     Toast.makeText(getApplicationContext(), "hellon" + ncount.toString(), Toast.LENGTH_SHORT).show();


                    Intent i;
                    if (intLevel == 3) {
                        i = new Intent(view.getContext(), mainTelent.class);

                    } else if (intLevel != 3 && ncount > 1) {
                        i = new Intent(view.getContext(), mainSubPassion.class);
                    } else {
                        i = new Intent(view.getContext(), mainType.class);

                    }

                    startActivity(i);

                }
            }
        });
    }

    //Just a simple helper method to read the file,
    //no need to read this or understand it for this exercise
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
