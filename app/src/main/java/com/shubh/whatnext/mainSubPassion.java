package com.shubh.whatnext;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class mainSubPassion extends AppCompatActivity {

    ListView simpleList;
    // String myJasonArray[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};

    String myJasonArray[];
    JSONArray arrJasonTypeArray;
    String mSelectedItem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sub_passion);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.Myfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                if (mSelectedItem.equals("")) {
                    Snackbar.make(view, "Please select your option", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    Integer intSubPassion = sharedPreferences.getInt("SubPassion_Index", 0);
                    Integer ncount = 0;

                    try {
                        ncount = arrJasonTypeArray.getJSONObject(intSubPassion).getJSONArray("Types").length();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                //    Toast.makeText(getApplicationContext(), ncount.toString(), Toast.LENGTH_SHORT).show();


                    Intent i;
                    if (ncount > 1) {
                        i = new Intent(view.getContext(), mainType.class);
                    } else {
                        i = new Intent(view.getContext(), mainCourses.class);

                    }
                    startActivity(i);
                }
            }
        });

       // Toast.makeText(getApplicationContext(), "type oncreate object", Toast.LENGTH_SHORT).show();

        JSONObject rootobj;
        String s = getJSONFile();

        try {


            //SharedPreferences sharedPreferences = getPreferences( Context.MODE_PRIVATE);

            // SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Integer intPassion = sharedPreferences.getInt("Passion_Index",0);
            Integer intLevel = sharedPreferences.getInt("Level_Index",0);

     //       Toast.makeText(getApplicationContext(), String.valueOf(intPassion), Toast.LENGTH_LONG).show();


            rootobj = new JSONObject(s);
            JSONArray arrLevel = rootobj.getJSONArray(("Level"));
            JSONObject objLvl = arrLevel.getJSONObject(intLevel);
            JSONArray arrPassion = objLvl.getJSONArray(("Passion"));
            arrJasonTypeArray = arrPassion.getJSONObject(intPassion).getJSONArray("SubPassion");

            if (arrJasonTypeArray == null)
                Toast.makeText(getApplicationContext(), "null object", Toast.LENGTH_LONG).show();
       //     else
        //        Toast.makeText( getApplicationContext(),String.valueOf(arrJasonTypeArray.length()), Toast.LENGTH_LONG).show();


            myJasonArray = new String[arrJasonTypeArray.length()];
            for (int intcounter = 0; intcounter < arrJasonTypeArray.length(); intcounter++) {
                myJasonArray[intcounter] = arrJasonTypeArray.getJSONObject(intcounter).getString("Name");
                //  Toast.makeText( getApplicationContext(),myJasonArray[intcounter].toString(), Toast.LENGTH_SHORT).show();

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Toast.makeText( getApplicationContext(),myJasonArray[0].toString(), Toast.LENGTH_LONG).show();


        simpleList = (ListView)findViewById(R.id.SubPassionListView);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(mainSubPassion.this, android.R.layout.simple_list_item_1,myJasonArray );
        simpleList.setAdapter(arrayAdapter1);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = (String) parent.getItemAtPosition(position);
            //    Toast.makeText( getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("SubPassion",selectedItem);
                editor.putInt("SubPassion_Index",position);
                editor.commit();
                mSelectedItem = selectedItem;
            }
        });



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
