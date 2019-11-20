import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HashTableQuad {
    public int string;
    public double avgProb;
    public double avgProb2;
    public File goods;
    public FileWriter input;
    private DataItem[] hashArray;    // array holds hash table
    public int arraySize;
    private DataItem nonItem;        // for deleted items

    // -------------------------------------------------------------
    public HashTableQuad(int size, File writeIt)       // constructor
    {
        try {
            input = new FileWriter(writeIt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        arraySize = size;
        hashArray = new DataItem[arraySize];
        nonItem = new DataItem("DEL");   // deleted item key is -1
    }

    public HashTableQuad(int size)       // constructor
    {
        arraySize = size;
        hashArray = new DataItem[arraySize];
        nonItem = new DataItem("DEL");   // deleted item key is -1
    }

    public void closeIt(FileWriter input) {
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // -------------------------------------------------------------
    private int hashFunc(String key) {//Hash Function for string
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

        String key = item.getKey();      // extract key
        String holder[] = key.split(" ");
        item.setKey(holder[0]);
        item.setValue(Integer.parseInt(holder[1]));
        key = item.getKey();
        int hashVal = hashFunc(key);  // hash the key
        int i = 0;
        int newHashval = hashVal;
        while (hashArray[newHashval] != null) {//checking to see if full
							
            if (hashArray[newHashval].getKey().equals(item.getKey()) && hashArray[newHashval].getValue() == item.getValue()) {//checks to see if item already exist
                System.out.println("Error " + item.getKey() + " " + item.getValue() + " already exist at " + newHashval);
                //printToFile(item, 2, newHashval);
                return;
            }
            newHashval = hashVal + (i * i);                 // go to next cell
            newHashval %= arraySize;             // wraparound if necessary
            if (hashArray[newHashval] != null && (!(hashArray[newHashval].getKey().equals(item.getKey()) && hashArray[newHashval].getValue() == item.getValue()))) {//checks for collison
                System.out.println("Collison at index " + newHashval + " for " + item.getKey() + " at value " + item.getValue());
                //printToFile(item, 3, newHashval);
            }
            i++;
        }
        System.out.println("stored " + item.getKey() + " " + item.getValue() + " at location " + newHashval);//stating where it is stored
        item.setProbe(newHashval);
        //printToFile(item, 4, newHashval);
        hashArray[newHashval] = item;    // insert item
        //string++;

    }  // end insert()

    // -------------------------------------------------------------
    public DataItem find(String key)    // find item with key
    {
        int hashVal = hashFunc(key);  // hash the key
        int i = 0;
        int newhashVal = hashVal;
        int probe = 1;
        while (hashArray[newhashVal] != null)   // until empty cell,
        {
            // found the key?
            if (hashArray[newhashVal].getKey().equals(key)) {//looking for key
                DataItem x = hashArray[newhashVal];
                x.setProbe(newhashVal);
                return x;   // yes, return item
            }
            i++;
            newhashVal += hashVal + i * i;                 // go to next cell
            newhashVal %= arraySize;      // wraparound if necessary

            probe++;
        }
        DataItem x = new DataItem("null");
        x.setProbe(probe);
        return x;                  // can't find item
    }
//-------------------------------------------------------------------
    public void printToFile(String x, int value) {//print to a file
        switch (value) {
            case (1):
                try {
                    input.write("Error " + x + " not found\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case (2):

        }
    }
//---------------------------------------------------------------------------
    public void printToFile(DataItem item, int value, int index) {//print to a file part two
        switch (value) {
            case (1):
                try {
                    input.write(item.getKey() + " with a value of " + item.getValue() + " found at location " + item.getProbe() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case (2):
                try {
                    input.write("Error " + item.getKey() + " " + item.getValue() + " already exist at " + index + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case (3):
                try {
                    input.write("Collison at index " + index + " for " + item.getKey() + " at a value " + item.getValue() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case (4):
                try {
                    input.write("stored " + item.getKey() + " " + item.getValue() + " at location " + index + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
//--------------------------------------------------------------------------------------------
}

