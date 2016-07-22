package org.nac.kalisynth.dcvsconnect2;

import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cade2 on 13/07/2016.
 */
public class Xml2 {
    private static final String ns = null;
    public List<Entry> parse(InputStream in ) throws XmlPullParserException, IOException {
        try{
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readXml(parser);
        } finally {
            in.close();
        }
    }
    private List<Entry> readXml(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Entry> entries = new ArrayList<Entry>();
        parser.require(XmlPullParser.START_TAG, ns, "skype");
        while (parser.next() !=XmlPullParser.END_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if (name.equals("speeddial")){
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }
    public static class Entry {
        public final String sname;
        public final String pname;
        public final String slot;

        private Entry(String sname, String pname, String slot){
            this.sname = sname;
            this.pname = pname;
            this.slot = slot;
        }
    }

    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "speeddial");
        String sname = null;
        String pname = null;
        String slot = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("skype_name")) {
                sname = readSkype(parser);
            } else if (name.equals("person_name")) {
                pname = readPerson(parser);
            } else if (name.equals("slot")) {
                slot = readSlot(parser);
            } else {
                skip(parser);
            }
        }
            return new Entry(sname, pname, slot);
        }
        private String readSkype(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "skype_name");
                String sname = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "skype_name");
            return sname;
        }
    private String readPerson(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "person_name");
            String pname = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "person_name");
        return pname;
    }
    private String readSlot(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "slot");
            String slot = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "slot");
        return slot;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if(parser.next() == XmlPullParser.TEXT){
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG){
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth !=0) {
            switch (parser.next()){
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
