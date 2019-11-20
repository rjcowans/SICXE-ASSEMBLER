public class DataItem {
    public void setKey(String KeySetter) {
        iData = KeySetter;
    }

    public int getProbe() {
        return probe;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String ValueSetter) {
        value = ValueSetter;
    }

    public void setProbe(int ProbeSetter) {
        probe = ProbeSetter;
    }       // (could have more data)

    public int getNi() {
        return ni;
    }

    public int getXbp() {
        return xbp;
    }

    public void setNi(int ni) {
        this.ni = ni;
    }

    public void setXbp(int xbp) {
        this.xbp = xbp;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setObject(String obaddress){
        this.objectAddress = obaddress;
    }

    public String getObject(){
        return objectAddress;
    }

    public String getPreObj() {
        return preObj;
    }

    public void setPreObj(String preObj) {
        this.preObj = preObj;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    private  int format;
    private String iData;               // data item (key)
    private String label;
    private int probe;
    private String value;
    private int ni;
    private int xbp;
    private String preObj;
    private String comment;
    public String address;
    public String operand;
    private String objectAddress;
    private boolean flipped;
    //--------------------------------------------------------------
    public DataItem(String ii)          // constructor
    {
        this.flipped=false;
        this.format = 0;
        this.iData = ii;
        this.probe = 1;
        this.value = "";
        this.ni = 0;
        this.xbp = 0;
        this.label = "";
        this.comment = "";
        this.address = "";
        this.operand = "";
        this.objectAddress = "";
    }

    public DataItem() {
        iData = "null";
        probe = 0;
        value = "";
    }
    //--------------------------------------------------------------
    public String getKey() {
        return iData;
    }
//--------------------------------------------------------------
}  // end class DataItem
////////////////////////////////////////////////////////////////






