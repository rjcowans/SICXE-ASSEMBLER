import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class assembler_main {

        private static int size;

        public static void main(String[] args) throws IOException {
        File file1 = new File(args[0]);     
        //File writeIt = new File("test.txt");//This would be if I wanted to write to a file
        BufferedReader set1 = new BufferedReader(new FileReader(file1));
        String lineCounter;
        DataItem blank = new DataItem();
        int k;
        int primer = 0;
        int p = 0;
        int i = 0;
        boolean check = false;
        ArrayList<String> numChecker = new ArrayList<String>();
        //Reading the file for the size of the table
        while ((lineCounter = set1.readLine()) != null) {
            numChecker.add(lineCounter);
        }
        p = numChecker.size();
        primer = highPrime(p * 2);
        size = primer;
        HashTableQuad sexy = new HashTableQuad(size);
        //insert and find method is running
	while (i < numChecker.size()) {
            String x = numChecker.get(i);
            for (int j = 0; j < x.length(); j++) {
                char t = x.charAt(j);
                if (t >= '0' && t <= '9')
                    check = true;
            }
            if (check) {
                sexy.insert(new DataItem(x));
            } else {
                blank = sexy.find(x);
                if (blank.getKey() == "null") {
                    System.out.println("Error " + x + " not found");
                    //sexy.printToFile(x, 1);
                } else {
		    //This prints if it was found from the find method
                    System.out.println(blank.getKey() + " with a value of " + blank.getValue() + " found at location " + blank.getProbe());
                    //sexy.printToFile(blank, 1,0);
                }
            }
            check = false;
            i++;
        }
        //sexy.closeIt(sexy.input);
    }

	//Prime checker with next method going to the highestPrime
        static private boolean isPrime(int n) {
        for (int i = 2; i < n / 2; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

        static private int highPrime(int n) {
        int i = 1;
        while (i != 0) {
            if (isPrime(n))
                i = 0;

            else {
                n++;
                i = 1;
            }
        }
        return n;
    }


    }


