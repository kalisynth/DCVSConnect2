package org.nac.kalisynth.dcvsconnect2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

public class ParseXmlAndroid extends AppCompatActivity {

    List<XmlValuesModel> myData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_xml_android);

        final TextView output = (TextView) findViewById(R.id.output);
        final Button xmlparsebtn = (Button) findViewById(R.id.xmlparse);

        /*xmlparsebtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try{
                    InputSource is=new InputSource(getResources().openRawResource(R.raw.skypespeeddial));
                    XMLParser
                }

            }
        });*/
    }

}
