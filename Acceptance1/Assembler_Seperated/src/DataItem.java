public class DataItem {
    public void setKey(String KeySetter) {
        iData = KeySetter;
    }

    public int getProbe() {
        return probe;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int ValueSetter) {
        value = ValueSetter;
    }

    public void setProbe(int ProbeSetter) {
        probe = ProbeSetter;
    }       // (could have more data)

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

    public DataItem() {
        iData = "null";
        probe = 0;
        value = 0;
    }

    //--------------------------------------------------------------
    public String getKey() {
        return iData;
    }
//--------------------------------------------------------------
}  // end class DataItem
////////////////////////////////////////////////////////////////


