import java.io.BufferedWriter;
import java.io.IOException;

public class HashTableQuad  {
    protected int[] inLineComments;
    protected int rowNum;
    protected DataItem[] hashArray;    // array holds hash table
    protected int arraySize;
    protected DataItem nonItem;        // for deleted items
    protected char[][] PrintMe;
    protected  BufferedWriter writer;
    // -------------------------------------------------------------
    public HashTableQuad(int size,BufferedWriter intake)       // constructor
    {
        arraySize = size;
        hashArray = new DataItem[arraySize];
        nonItem = new DataItem("DEL");   // deleted item key is -1
        PrintMe = new char[(100) + 1][80];
        inLineComments = new int[(arraySize / 2) + 1];
        writer = intake;
    }
    // -------------------------------------------------------------
    private int hashFunc(String key) {
        int hasVal = 0;
        for (int j = 0; j < key.length(); j++) {
            int letter = key.charAt(j);
            hasVal = (hasVal * 26 + letter) % arraySize;
        }
        return hasVal;
    }
    // -------------------------------------------------------------
    public void insert(DataItem item) // insert a DataItem
    // (assumes table not full)
    {
        String key = "";
        if (!(item.getKey().matches("^\\w+ \\w$"))) {
            key = item.getKey();      // extract key
            item.setKey(key.substring(0, 7).trim());
            item.setValue(key.substring(8, 11).trim());
            item.setNi(Integer.parseInt(key.substring(14, 16).trim()));
            item.setXbp(Integer.parseInt(key.substring(16, 19).trim()));
        } else {
            key = item.getKey();
            if (key.length() == 3) {
                item.setKey(key.substring(0, 1).trim());
                item.setXbp(Character.getNumericValue(key.charAt(2)));
            } else {
                item.setKey(key.substring(0, 2).trim());
                item.setXbp(Character.getNumericValue(key.charAt(3)));
            }
        }
        key = item.getKey();
        int hashVal = hashFunc(key);  // hash the key
        int newHashval = hashVal;
        while (hashArray[newHashval] != null) {

            if (hashArray[newHashval].getKey().equals(item.getKey()) && hashArray[newHashval].getValue().equals(item.getValue())) {
            }
            newHashval++;                 // go to next cell
            newHashval %= arraySize;             // wraparound if necessary
        }
        item.setProbe(newHashval);
        hashArray[newHashval] = item;    // insert item
    }  // end insert()
    // -------------------------------------------------------------
    public void insert2(DataItem item) throws IOException // insert a DataItem
    // (assumes table not full)
    {
        String key = item.getKey();      // extract key
        if (key.length() < 32) {
            for (int i = key.length(); i < 40; i++) {
                key = key.concat(" ");
            }
        }
        item.setLabel(key.substring(0, 8).trim());
        item.setKey(key.substring(9, 17).trim());
        item.setValue(key.substring(18, 29).trim());
        item.setComment(key.substring(32, key.length() - 1).trim());
        key = item.getKey();
        int hashVal = hashFunc(key);  // hash the key
        int newHashval = hashVal;
        while (hashArray[newHashval] != null) {
            if (hashArray[newHashval].getKey().equals(item.getKey()) && item.getKey().equals("RESW") && hashArray[newHashval].getLabel().equals(item.getLabel())) {
                writer.write("Error Duplicate Label Exist: \"" + item.getLabel() + "\"" + "\n");
            }
            newHashval++;                 // go to next cell
            newHashval %= arraySize;             // wraparound if necessary
        }
        item.setProbe(newHashval);
        hashArray[newHashval] = item;
        for (int j = 0; j < 79; j++) {
            switch (j) {
                case 0:
                    for (int z = 0; z < item.getLabel().length(); z++) {
                        char x = item.getLabel().charAt(z);
                        PrintMe[rowNum][z] = x;
                    }
                    break;
                case 9:
                    PrintMe[rowNum][9] = ' ';
                    break;
                case 10:
                    for (int z = 0; z < item.getKey().length(); z++) {
                        PrintMe[rowNum][j + z] = item.getKey().charAt(z);
                    }
                    break;
                case 18:
                    PrintMe[rowNum][18] = ' ';
                    break;
                case 19:
                    for (int z = 0; z < item.getValue().length(); z++) {
                        PrintMe[rowNum][j + z] = item.getValue().charAt(z);
                    }
                    break;
                case 30:
                    PrintMe[rowNum][30] = ' ';
                    PrintMe[rowNum][31] = ' ';
                    break;
                case 32:
                    if (item.getComment() != null) {
                        for (int z = 0; z < item.getComment().length(); z++) {
                            PrintMe[rowNum][j + z] = item.getComment().charAt(z);
                        }
                    } else {
                        for (int z = 0; z < 47; z++) {
                            PrintMe[rowNum][j + z] = ' ';
                        }
                    }
                    break;
            }
        }
    }
    // end insert()
    // -------------------------------------------------------------
    public DataItem find(String key)    // find item with key
    {
        int hashVal = hashFunc(key);  // hash the key
        int newhashVal = hashVal;
        int probe = 1;
        while (hashArray[newhashVal] != null)   // until empty cell,
        {
            // found the key?
            if (hashArray[newhashVal].getKey().equals(key)) {
                DataItem x = hashArray[newhashVal];
                x.setProbe(newhashVal);
                return x;   // yes, return item
            }
            newhashVal++;                 // go to next cell
            newhashVal %= arraySize;      // wraparound if necessary

            probe++;
        }
        DataItem x = new DataItem("null");
        x.setProbe(probe);
        return x;                  // can't find item
    }

    public DataItem findWord(String key, String number, String label)    // find item with key
    {
        int hashVal = hashFunc(key);  // hash the key
        int newhashVal = hashVal;
        int probe = 1;
        while (hashArray[newhashVal] != null)   // until empty cell,
        {
            // found the key?
            if (hashArray[newhashVal].getKey().equals(key) &&  hashArray[newhashVal].getValue().equals(number) && hashArray[newhashVal].getLabel().equals(label)) {
                DataItem x = hashArray[newhashVal];
                x.setProbe(newhashVal);
                return x;   // yes, return item
            }
            newhashVal++;                 // go to next cell
            newhashVal %= arraySize;      // wraparound if necessary

            probe++;
        }
        DataItem x = new DataItem("null");
        x.setProbe(probe);
        return x;                  // can't find item
    }

    public DataItem findUnstruct(String key, String number)    // find item with key
    {
        int hashVal = hashFunc(key);  // hash the key
        int newhashVal = hashVal;
        int probe = 1;
        while (hashArray[newhashVal] != null)   // until empty cell,
        {
            // found the key?
            if (hashArray[newhashVal].getKey().equals(key) &&  hashArray[newhashVal].getValue().equals(number)) {
                DataItem x = hashArray[newhashVal];
                x.setProbe(newhashVal);
                return x;   // yes, return item
            }
            newhashVal++;                 // go to next cell
            newhashVal %= arraySize;      // wraparound if necessary

            probe++;
        }
        DataItem x = new DataItem("null");
        x.setProbe(probe);
        return x;                  // can't find item
    }

    public DataItem find2(String key, String label)     // find item with key
    {
        int hashVal = hashFunc(key);  // hash the key
        int i = 0;
        int newhashVal = hashVal;
        int probe = 1;
        int a = 1;
        while (a != 0) {
            if (hashArray[newhashVal].getKey().equals(key) && hashArray[newhashVal].getLabel().equals(label)) {
                DataItem x = hashArray[newhashVal];
                x.setProbe(newhashVal);
                return x;   // yes, return item
            }
            if (i == hashArray.length) {
                a = 1;
                break;
            }
            i++;
            newhashVal++;                 // go to next cell
            newhashVal %= arraySize;      // wraparound if necessary

            probe++;
        }
        DataItem x = new DataItem("null");
        x.setProbe(probe);
        return x;                  // can't find item
    }

    public void setUP(DataItem temp) throws IOException {
        if (temp.getKey().startsWith(".")) {
            char[] boom = temp.getKey().toCharArray();
            for (int i = 0; i < boom.length; i++) {
                PrintMe[rowNum][i] = boom[i];
                inLineComments[rowNum] = 0;
            }
        }else if(temp.getKey().contains("LTORG")){
            rowNum = + 10;
            return;
        }else {
            insert2(temp);
        }
        rowNum++;
    }

    public void cleanUP(){
        for (int i = 0; i < hashArray.length;i++){
            if (hashArray[i] == null){
                hashArray[i] = nonItem;
            }
        }

    }

    public DataItem findME(String label){
        int i = 0;
        for (i = 0; i < hashArray.length;i++)
            if (hashArray[i].getLabel().equals(label))
                return hashArray[i];
        return nonItem;

    }

    public static String currentString(char[][] truth, int start, int end, int row) {
        String goBack = "";
        for (int i = start; i <= end; i++) {
            goBack = goBack.concat(Character.toString(truth[row][i]));
        }
        goBack = goBack.trim();
        return goBack;
    }

    public String[] findBase(int j){
        int i = 0;
        String[] obj = new String[3];
        obj[0] = "";
        obj[1] = "";
        obj[2] = "";
        String tester = null;
        for (i = j; 0 < i;i--) {
         tester = currentString(PrintMe, 10, 18, i);
            if (tester.equals("BASE")) {
                obj[0] = tester;
                obj[1] = currentString(PrintMe,0,9,i);
                obj[2] = currentString(PrintMe,17,27,i);
                return obj;
            }
        }
        return null;
    }
}

