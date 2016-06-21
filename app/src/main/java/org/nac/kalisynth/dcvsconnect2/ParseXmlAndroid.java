package org.nac.kalisynth.dcvsconnect2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ParseXmlAndroid extends AppCompatActivity {

    List<XmlValuesModel> myData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_xml_android);

        final TextView output = (TextView) findViewById(R.id.output);
        final Button xmlparsebtn = (Button) findViewById(R.id.xmlparse);
    }
}
