package org.nac.kalisynth.dcvsconnect2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * XMLParser
 */
public class XMLParser extends DefaultHandler {

    List<XmlValuesModel> list=null;

    //string builder acts as a buffer
    StringBuilder builder;

    XmlValuesModel skypeValues = null;

    @Override
    public void startDocument() throws SAXException{
        /* Create ArrayList to Store XMLValuesModel */

        list = new ArrayList<XmlValuesModel>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{

        builder = new StringBuilder();

        if(localName.equals("speeddial")){
            // Log.i("parse", "----Skype Start---");
            skypeValues = new XmlValuesModel();
        }

    }

    @Override
    public void endElement(String uri, String localname, String qName) throws SAXException{
        if(localname.equals("speeddial")){
            list.add( skypeValues );
        }
        else if(localname.equalsIgnoreCase("slot")){
            skypeValues.setslot(Integer.parseInt(builder.toString()));
        }else if(localname.equalsIgnoreCase("skype_name")){
            skypeValues.setskypename(builder.toString());
        }else if(localname.equalsIgnoreCase("person_name")){
            skypeValues.setpersonname(builder.toString());
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException{
        String tempString=new String(ch, start, length);
        builder.append(tempString);
    }

}
