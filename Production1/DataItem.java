public class DataItem {
    public void setKey(String KeySetter) {//string setter
        iData = KeySetter;
    }

    public int getProbe() {//index getter
        return probe;
    }

    public int getValue() {//the integer getter
        return value;
    }

    public void setValue(int ValueSetter) {//value setter
        value = ValueSetter;
    }

    public void setProbe(int ProbeSetter) {//probe setter
        probe = ProbeSetter;
    }  

    private String iData;               // data item (key)
    private int probe;
    private int value;

    //--------------------------------------------------------------
    public DataItem(String ii)          // constructor
    {
        iData = ii;
        probe = 1;
        value = 0;
    }

    public DataItem() {//constructor
        iData = "null";
        probe = 0;
        value = 0;
    }

    //--------------------------------------------------------------
    public String getKey() {//string getter
        return iData;
    }
//--------------------------------------------------------------
}  // end class DataItem
////////////////////////////////////////////////////////////////


