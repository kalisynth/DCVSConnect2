package org.nac.kalisynth.dcvsconnect2;

/**
 * values for the xml
 */
public class XmlValuesModel {

    private int id;
    private String skypename = "";
    private String personname = "";
    private int slot;

    /* Define Setter Methods */

    public void setid(int id){
        this.id = id;
    }
    public void setskypename(String skypename){
        this.skypename = skypename;
    }
    public void setpersonname(String personname){
        this.personname = personname;
    }
    public void setslot(int slot){
        this.slot = slot;
    }

    /* Define Get Methods */

    public int getid(){
        return id;
    }
    public String getskyename()
    {
        return skypename;
    }
    public String getpersonname()
    {
        return personname;
    }
    public int getslot()
    {
        return slot;
    }
}
