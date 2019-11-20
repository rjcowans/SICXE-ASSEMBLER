import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class assembler_main {

    private static int size;
    private static String finalValues[];

    public static void main(String[] args) throws IOException {
        File file1 = new File(args[0]);
        File file2 = new File(args[1]);
        BufferedReader set1 = new BufferedReader(new FileReader(file1));
        BufferedReader set2 = new BufferedReader(new FileReader(file2));
        String lineCounter;
        int primer = 0;
        int p = 0;
        int i = 0;
        ArrayList<String> numChecker = new ArrayList<String>();
        while ((lineCounter = set1.readLine()) != null) {
            numChecker.add(lineCounter);
        }
        p = numChecker.size();
        primer = highPrime(p * 2);
        size = primer;
        HashTableQuad sexy = new HashTableQuad(size);
        while (i < numChecker.size()) {
            String x = numChecker.get(i);
            sexy.insert(new DataItem(x));

            i++;
        }
        String lineCounter2 = "";
        numChecker = new ArrayList<>();
        while ((lineCounter2 = set2.readLine()) != null) {
            numChecker.add(lineCounter2);
        }
        int p2 = numChecker.size();
        int primer2 = highPrime(p2 * 2);
        int size2 = primer2;
        HashTableQuad toBoot = new HashTableQuad(size2);
        i = 0;
        while (i < numChecker.size()) {
            String z = numChecker.get(i);
            toBoot.setUP(new DataItem(z));
            i++;
        }
        pass1(sexy, toBoot);

        System.out.println("Address\t\tLabel\t  Instruct Operand\t\t\t  Comments");
        for (i = 0; i < numChecker.size(); i++) {
            String x = numChecker.get(i).trim();
            if (finalValues[i].length() == 4)
            System.out.println(finalValues[i] + "\t\t" + x);
            else
                System.out.println("0" + finalValues[i] + "\t\t" + x);
        }
        System.out.println("Done");
        System.out.println("NEXT TABLE");
        System.out.println("TableL\t\t Label\t\t Address     Use\t\t Cset\t\t");
        for (i = 0; i < toBoot.hashArray.length-1; i++) {
            DataItem works = toBoot.hashArray[i];
            if ((!(works ==null)) && (!(works.getKey().equals("BASE")))&& (!(works.getKey().equals("START"))) && (!(works.getKey().equals("END"))) && (!(works.getLabel().equals("")))){
                System.out.println(i + "\t\t\t " + works.getLabel() + " \t\t " + works.getAddress() + "\t\t main\t\t main\t\t");
            }
        }
    }

    public static String currentString(char[][] truth, int start, int end, int row) {
        String goBack = "";
        for (int i = start; i <= end; i++) {
            goBack = goBack.concat(Character.toString(truth[row][i]));
        }
        goBack = goBack.trim();
        return goBack;
    }

    public static int getLast(String[] values, int row) {
        if (row == 0) return row;
        if (values[row-1].equals("----") || values[row-1].equals("BASE")) {
            for (int i = row-1; i >= 0; i--) {
                if ((values[i].equals("----")) || (values[i].equals("BASE"))) {

                } else {
                    return i;
                }
            }
        }
        return row-1;
    }

    public static int getLTORGOP(char[][] truth, int row){
        int toGo = 0;
        for (int i = row;i >= 1; i--){
            String letsGo = currentString(truth,9,17,i);
            if (letsGo.equals("LTORG")){
                toGo = i;
                break;
            }
        }
        if (toGo > 1){
            while (toGo < 10){
                String label = currentString(truth,0,9,toGo);
                if (label.equals("")){
                    return toGo;
                }
                toGo++;
            }

        }
        return -1;
    }

    public static void pass1(HashTableQuad SECOP, HashTableQuad PGM) {
        String[] tempValues = new String[PGM.rowNum];
        for (int i = 0; i < PGM.rowNum; i++) {
            tempValues[i] = "----";
        }
        int row = 0;
        char[][] truth = PGM.PrintMe;
        DataItem start = PGM.find(currentString(truth, 9, 17, 0));
        String valueofStart = start.getValue();
        tempValues[0] = valueofStart;
        int Lastgood = 0;
        for (row = 1; row < PGM.rowNum-1; row++) {
            String test = currentString(truth, 9, 17, row);
            DataItem confirm = SECOP.find(test);
            if ((confirm.getKey().equals(test)) || test.equals("RESW") || test.equals("BYTE") || test.equals("WORD")) {
                if (!(test.equals("RESW")) && !(test.equals("BYTE"))) {
                    if (!(test.equals("WORD"))){
                        int checker = confirm.getNi();
                        String first = Integer.toString(checker);
                        Lastgood = getLast(tempValues, row+1);
                        String second = tempValues[Lastgood];
                        int decFirst = Integer.parseInt(first, 16);
                        int decSecond = Integer.parseInt(second, 16);
                        String newfinal = Integer.toHexString(decFirst + decSecond);
                        tempValues[row + 1] = newfinal;
                    }else {
                        String num  = "3";
                        Lastgood = getLast(tempValues, row+1);
                        String second = tempValues[Lastgood];
                        int decFirst = Integer.parseInt(num, 16);
                        int decSecond = Integer.parseInt(second, 16);
                        String newfinal = Integer.toHexString(decFirst + decSecond);
                        tempValues[row + 1] = newfinal;
                        String oke = currentString(truth,0,9,row);
                        if (oke.equals("")){
                            if (row != PGM.rowNum-1) {
                                int z = 0;
                                for (int i = row; i > 0; i--) {
                                    if ((currentString(truth,9,17,i).equals("RESW") || (currentString(truth,9,17,i).equals("RESB") || (currentString(truth,9,17,i).equals("BASE"))))){
                                        System.out.println("Error Undefined Label at address " + tempValues[row]);
                                        break;
                                    }else{
                                        if (!(currentString(truth,0,9,i).equals(""))){
                                            break;
                                        }
                                    }
                                }

                            }else {
                                System.out.println("Error Undefined Label at address " + tempValues[row]);
                            }
                        }
                    }
                } else {
                    if (test.equals("RESW")) {
                        String woah = currentString(truth, 9, 17, row);
                        String pusher = currentString(truth, 18, 29, row);
                        DataItem temp2 = PGM.find2(woah,pusher);
                        Lastgood = getLast(tempValues, row+1);
                        String x = temp2.getValue();
                        int z = Integer.parseInt(x);
                        int y = Integer.parseInt(tempValues[Lastgood], 16);
                        y += 3*z;
                        String preFinal = Integer.toHexString(y);
                        tempValues[row + 1] = preFinal;
                        String oke = currentString(truth,0,9,row);
                        if (oke.equals("")){
                            System.out.println("Error Undefined Label at address " + tempValues[row]);
                        }
                    } else {
                        String woah = currentString(truth, 9, 17, row);
                        String pusher = currentString(truth,18,29,row);
                        DataItem temp2 = PGM.find2(woah,pusher);
                        if (pusher.contains("=C'")){

                        }

                        if (pusher.contains("=X'")){

                        }

                        Lastgood = getLast(tempValues, row);
                        String x = temp2.getValue();
                        int z = Integer.parseInt(x);
                        z += Integer.parseInt(tempValues[Lastgood], 16)+ Integer.parseInt("6",16);
                        String preFinal = Integer.toHexString(z);
                        tempValues[row + 1] = preFinal;
                        String oke = temp2.getLabel();
                        if (oke.equals("")){
                            System.out.println("Error Undefined Label at address " + tempValues[row]);
                        }
                    }
                }
                String test2 = currentString(truth,18,29,row);
                DataItem temp = PGM.find2(test,test2);
                PGM.hashArray[temp.getProbe()].setAddress(tempValues[row+1]);
            }else{
                if (!(test.equals("RESW") || test.equals("BYTE") || test.equals("BASE")) || test.equals("WORD")) {
                     String hold = currentString(truth,0,9,row);
                    if (!hold.startsWith(".")) {
                        String newfinal = "----";
                        String newtemp = tempValues[row];
                        tempValues[row+1] = newtemp;
                        tempValues[row] = newfinal;
                        System.out.println("Invalid Mnemonic at Address (line will be ignored)");
                    }
                }


            }
        }

        finalValues = tempValues;
    }

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


