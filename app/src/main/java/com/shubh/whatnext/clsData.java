package com.shubh.whatnext;

import androidx.appcompat.app.AppCompatActivity;

import com.shubh.whatnext.R;

import java.io.IOException;
import java.io.InputStream;

public class clsData extends AppCompatActivity {

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
