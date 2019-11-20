import java.io.*;
import java.util.ArrayList;

public class FinalMain {
    private static int size;
    public static int mRLT;
    public static String[] baseLine;
    public  static int  baseNumber = 0;
    public static BufferedWriter writer;
    public static BufferedWriter writerObj;
    private static String[] finalValues;
    private static String[] ObjValues;
    private static int amountOfResw;
    private  static String[] array = {"",""};

    public static void main(String[] args) throws IOException {
        File file1 = new File(args[0]);
        File file2 = new File(args[1]);
        BufferedReader set1 = new BufferedReader(new FileReader(file1));
        BufferedReader set2 = new BufferedReader(new FileReader(file2));
        if (args[1].contains(".txt")) {
            writer = new BufferedWriter(new FileWriter(args[1].substring(0, args[1].length() - 4) + ".lst"));
            writerObj = new BufferedWriter(new FileWriter(args[1].substring(0, args[1].length() - 4) + ".obj"));
        }else{
            writer = new BufferedWriter(new FileWriter(args[1] + ".lst"));
            writerObj = new BufferedWriter(new FileWriter(args[1] + ".obj"));
        }
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
        HashTableQuad sexy = new HashTableQuad(size,writer);
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
        HashTableQuad toBoot = new HashTableQuad(size2,writer);
        i = 0;
        while (i < numChecker.size()) {
            String z = numChecker.get(i);
            toBoot.setUP(new DataItem(z));
            i++;
        }
        pass1(sexy, toBoot);
        pass2(finalValues,toBoot,sexy);
        writer.write("Address\tObjectCode\tLabel\t  Instruct Operand\t\t\t  Comments\n");
        for (i = 0; i < numChecker.size(); i++) {
            String x = numChecker.get(i).trim();
                if (x.contains("WORD")){
                        writer.write(finalValues[i] + "\t" + rebuild(ObjValues[i].length(), ObjValues[i], 3) + "\t\t" + x + "\n");
                }else{
                    writer.write(finalValues[i] + "\t" + ObjValues[i] + "\t\t" + x + "\n");
                }
        }

        objectFile(numChecker);
        writer.close();
        System.out.println("Done");
    }

    public static void objectFile(ArrayList<String> values) throws IOException{
        String loader = "000000";
        String startAdd = rebuild(finalValues[0].length(),finalValues[0],3)+"\n";
        writerObj.write(startAdd);
        writerObj.write(loader+"\n");
        int check =0;
        for (int i = 1; i < values.size();i++) {
            String x = values.get(i).trim();

                if (x.contains("RESW") || x.contains("RESB")) {
                    check++;
                    writerObj.write("!\n");
                    String boom =  rebuild(finalValues[i+1].length(),finalValues[i+1],3);
                    if (check != amountOfResw-1) {
                        writerObj.write(boom + "\n");
                        writerObj.write(loader + "\n");
                    } else {
                        writerObj.write(boom + "\n");
                        writerObj.write(startAdd);
                    }

                } else if (x.contains("END")) {
                    writerObj.write("!\n");
                } else {
                    if (x.contains("WORD")) {
                        writerObj.write(rebuild(ObjValues[i].length(), ObjValues[i], 3)+"\n");
                    } else {
                        if (!ObjValues[i].equals("")) {
                            writerObj.write(ObjValues[i] + "\n");
                        }
                    }
                }
            }

        writerObj.close();
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

    public static void setLTORG(int spot){
        mRLT = spot;
    }

    public static void pass1(HashTableQuad SECOP, HashTableQuad PGM) throws  IOException {
        String[] tempValues = new String[PGM.rowNum];
        String[] tips = new String[PGM.rowNum];
        for (int i = 0; i < PGM.rowNum; i++) {
            tempValues[i] = "----";
            tips[i] = "";
        }
        int row = 0;
        char[][] truth = PGM.PrintMe;
        DataItem start = PGM.find(currentString(truth, 9, 17, 0));
        String valueofStart = start.getValue();
        tempValues[0] = valueofStart;
        int Lastgood = 0;
        for (row = 1; row < PGM.rowNum - 1; row++) {
            String test = currentString(truth, 9, 17, row);
            DataItem confirm = SECOP.find(test);
            String go = null;
            if (test.equals("WORD") || test.equals("RESW") || test.equals("BYTE") || test.equals("RESB") || test.equals("LTORG") || confirm.getKey().equals(test) || test.equals("BASE")) {
                switch (test) {
                    case "WORD":
                        String num = "3";
                        Lastgood = getLast(tempValues, row + 1);
                        String second = tempValues[Lastgood];
                        int decFirst = Integer.parseInt(num, 16);
                        int decSecond = Integer.parseInt(second, 16);
                        String newfinal = Integer.toHexString(decFirst + decSecond);
                        tempValues[row + 1] = newfinal;
                        String oke = currentString(truth, 0, 9, row);
                        if (oke.equals("")) {
                            if (row != PGM.rowNum - 1) {
                                int z = 0;
                                for (int i = row; i > 0; i--) {
                                    if ((currentString(truth, 9, 17, i).equals("RESW") || (currentString(truth, 9, 17, i).equals("RESB") || (currentString(truth, 9, 17, i).equals("BASE"))))) {
                                        writer.write("Error Undefined Label at address " + tempValues[row] + "\n");
                                        break;
                                    } else {
                                            String take = currentString(truth, 0, 9, i);
                                        if (!take.equals("")) {
                                            take = currentString(truth, 0, 9, row);
                                            String number = currentString(truth, 17,29,row);
                                            DataItem temp1 = PGM.findWord(test,number,take);
                                            PGM.hashArray[temp1.getProbe()].setAddress(tempValues[getLast(tempValues,row+1)]);
                                            break;
                                        }
                                    }
                                }

                            } else {
                                writer.write("Error Undefined Label at address " + tempValues[row] + "\n");
                            }
                        }else{
                            String take = currentString(truth, 0, 9, row);
                            String number = currentString(truth, 17,29,row);
                            DataItem temp1 = PGM.findWord(test,number,take);
                            PGM.hashArray[temp1.getProbe()].setAddress(tempValues[getLast(tempValues,row+1)]);
                        }
                        break;
                    case "RESW":
                        amountOfResw++;
                        String woah = currentString(truth, 0, 9, row);
                        String pusher = currentString(truth, 9, 17, row);
                        DataItem temp2 = PGM.find2(pusher,woah);
                        Lastgood = getLast(tempValues, row + 1);
                        String x = temp2.getValue();
                        int z = Integer.parseInt(x);
                        int y = Integer.parseInt(tempValues[Lastgood], 16);
                        y += 3 * z;
                        String preFinal = Integer.toHexString(y);
                        tempValues[row + 1] = preFinal;
                        String oke2 = currentString(truth, 0, 9, row);
                        if (oke2.equals("")) {
                            writer.write("Error Undefined Label at address " + tempValues[row] + "\n");
                        }
                        PGM.hashArray[temp2.getProbe()].setAddress(tempValues[getLast(tempValues,row+1)]);
                        break;
                    case "BYTE":
                        String woah2 = currentString(truth, 9, 17, row);
                        String pusher2 = currentString(truth, 18, 29, row);
                        DataItem temp3 = PGM.findWord(woah2, pusher2,currentString(truth,0,9,row));
                        Lastgood = getLast(tempValues, row);
                        String we = temp3.getValue();
                        String alive = we.substring(2, we.length()-1);
                        int weCheck = (we.length()-3) % 2;
                        int go5 = 0;
                        if (we.startsWith("C\'") && we.endsWith("\'")){
                             go5 = alive.length() + Integer.parseInt(tempValues[Lastgood+1],16);
                            PGM.hashArray[temp3.getProbe()].setAddress(Integer.toHexString(go5));
                        }else if (we.startsWith("X\'") && (weCheck == 0) && we.endsWith("\'") ){
                            go5 = (alive.length()/2) + Integer.parseInt(tempValues[Lastgood+1],16);
                            PGM.hashArray[temp3.getProbe()].setAddress(Integer.toHexString(go5));
                        }else{
                            writer.write("Invalid format please revist");
                        }
                        String preFinal1 = Integer.toHexString(go5);
                        tempValues[row+1] = preFinal1;
                        String oke1 = temp3.getLabel();
                        if (oke1.equals("")) {
                            writer.write("Error Undefined Label at address " + tempValues[row] + "\n");
                        }
                        break;
                    case ("LTORG"):
                        setLTORG(row);
                        tempValues[row + 1] = "----";
                        break;
                    case "BASE":
                    default:
                        int checker = confirm.getNi();
                        String first = Integer.toString(checker);
                        Lastgood = getLast(tempValues, row + 1);
                        String second2 = tempValues[Lastgood];
                        int decFirst2 = Integer.parseInt(first, 16);
                        int decSecond2 = Integer.parseInt(second2, 16);
                        String newfinal2 = Integer.toHexString(decFirst2 + decSecond2);
                        tempValues[row + 1] = newfinal2;
                        String boom = currentString(truth,0,9,row);
                        DataItem temp5 = PGM.find2(test,boom);
                        PGM.hashArray[temp5.getProbe()].setAddress(tempValues[getLast(tempValues,row+1)]);
                        PGM.hashArray[temp5.getProbe()].setFormat(confirm.getNi());
                        PGM.hashArray[temp5.getProbe()].setPreObj(confirm.getValue());
                        break;
                }
            } else {
                String newfinal = "----";
                String newtemp = tempValues[row];
                tempValues[row + 1] = newtemp;
                tempValues[row] = newfinal;
                if (truth[row][0] != '.') {
                    writer.write("Invalid Mnemonic at Address (line will be ignored)" + "\n");
                }
            }
        }
        finalValues = tempValues;
        ObjValues=tips;
    }

    public static void pass2(String[] tempValues, HashTableQuad PGM,HashTableQuad SECOPS) throws IOException{
        PGM.cleanUP();
        String[] ovalues = new String[PGM.rowNum];
        for (int i = 0; i < PGM.rowNum; i++) {
            ovalues[i] = "";
        }
        char[][] newTruth = PGM.PrintMe;
        for (int i = 1;i < tempValues.length; i++){
            baseNumber = i;
            String wasGood = currentString(newTruth,9,17,i);
            DataItem cake =  SECOPS.find(wasGood);
             String good = cake.getKey();
            if (good.equals(wasGood) || wasGood.equals("WORD") || wasGood.equals("END") || wasGood.equals("BYTE") || wasGood.equals("BASE") || wasGood.equals("RESW") || wasGood.equals("RESB")){
                if (wasGood.equals("END")){
                    break;
                }
                String tester =currentString(newTruth,9,17,i);
                String wow2 = currentString(newTruth,0,9,i);
                String wow3 = currentString(newTruth,18,29,i);
                DataItem Helpme =null;
                if (wow2.equals("")){
                    Helpme = PGM.findUnstruct(tester,wow3);
                }else{
                    Helpme = PGM.findWord(tester,wow3,wow2);
                }
                if (!(tester.equals("WORD") || tester.equals("RESW") || tester.equals("END") || tester.equals("BYTE") || tester.equals("BASE"))){
                    if (Helpme.getValue().matches("\\#[0-9]+")){
                        String tempObj = NormNum(Helpme);
                        ovalues[i] = tempObj;
                        PGM.hashArray[Helpme.getProbe()].setObject(tempObj);
                    }else if (Helpme.getValue().matches("\\@[0-9]+")){
                        String tempObj = NormNum(Helpme);
                        ovalues[i] = tempObj;
                        PGM.hashArray[Helpme.getProbe()].setObject(tempObj);
                    } else if(Helpme.getValue().matches("[0-9]+")){
                        String tempObj = NormNum(Helpme);
                        ovalues[i] = tempObj;
                        PGM.hashArray[Helpme.getProbe()].setObject(tempObj);
                    } else if (Helpme.getValue().matches("\\#[a-zA-z]+")){
                        String tempMover = Helpme.getValue().substring(1);
                        DataItem weGood = PGM.findME(tempMover);
                        String tee = NormWord(Helpme,weGood,PGM,SECOPS);
                        PGM.hashArray[Helpme.getProbe()].setObject(tee);
                        ovalues[i] = tee;
                    }else if (Helpme.getValue().matches("\\@[a-zA-z]+")){
                        String tempMover = Helpme.getValue().substring(1);
                        DataItem weGood = PGM.findME(tempMover);
                        String tee = NormWord(Helpme,weGood,PGM,SECOPS);
                        PGM.hashArray[Helpme.getProbe()].setObject(tee);
                        ovalues[i] = tee;
                    } else{
                        String tempMover = Helpme.getValue();
                        DataItem weGood = PGM.findME(tempMover);
                        String b = NormWord(Helpme,weGood,PGM,SECOPS);
                        PGM.hashArray[Helpme.getProbe()].setObject(b);
                        ovalues[i] = b;
                    }
                }else{
                    if (tester.equals("WORD")) {
                        String goodWow = "";
                        String goodWow2 = Integer.toHexString(Integer.parseInt(wow3,16));
                        if (goodWow2.length() > 6) {
                            goodWow = goodWow2.substring(goodWow2.length() - 6);
                        }else{
                            goodWow = goodWow2;
                        }
                        ovalues[i] = goodWow;
                        int len = goodWow.length();
                        goodWow = rebuild(len, goodWow, 3);
                        PGM.hashArray[Helpme.getProbe()].setObject(goodWow);
                    }else if (tester.equals("BYTE")){
                        String woke = "";
                        if (Helpme.getValue().startsWith("C\'") && Helpme.getValue().endsWith("\'") ){
                            String x = Helpme.getValue().substring(2, Helpme.getValue().length()-1);
                            for (int z = 0;z < x.length(); z++){
                                int  b = x.charAt(z);
                                woke += Integer.toHexString(b);
                            }
                            if (woke.length() > 7) {
                                PGM.hashArray[Helpme.getProbe()].setObject(woke.substring(0, 7));
                                ovalues[i] = woke.substring(0,7);
                            }else{
                                PGM.hashArray[Helpme.getProbe()].setObject(woke);
                                ovalues[i] = woke;
                            }
                        }else if (Helpme.getValue().startsWith("X\'") && Helpme.getValue().endsWith("\'")) {
                            woke = Helpme.getValue().substring(2, Helpme.getValue().length()-1);
                            if (woke.length() > 7) {
                                PGM.hashArray[Helpme.getProbe()].setObject(woke.substring(0, 7));
                                ovalues[i] = woke.substring(0,7);
                            }else{
                                PGM.hashArray[Helpme.getProbe()].setObject(woke);
                                ovalues[i] = woke;
                            }
                        }
                    }else {
                        ovalues[i] = "";
                        PGM.hashArray[Helpme.getProbe()].setObject("");
                    }
                }
            }
        }
       ObjValues = ovalues;
    }

    public static String[] pcorNah(DataItem first, DataItem second,HashTableQuad toBoot){
        String[] obj = new String[2];
        int a = Integer.parseInt(first.getAddress(),16);
        int b = Integer.parseInt(second.getAddress(),16);
        int c = b -(a+first.getFormat());
        if ( c >= -2048 && c <= 2047) {
            obj[0] = Integer.toHexString(c);
            obj[1] = "pcRelative";
        }else {
            if (  c >= 2048 && c <= 4096) {
                obj[0] = baseMe(toBoot, b);
                obj[1] = "base";
            }else{
                obj[0] = "";
                obj[1] = "noGood";
            }
        }

        return obj;
    }

    public static String baseMe(HashTableQuad toBoot,int c){
           String[] obj =  toBoot.findBase(baseNumber);
           DataItem food = toBoot.findME(obj[2]);
           int a = Integer.parseInt(food.getAddress(),16);
           int d = c-a;
           return Integer.toHexString(d);

    }

    public static String NormNum(DataItem HelpMe){
        String obj = "";
        int a = 0;
        int b = 0;
        String mid = "";
        if (HelpMe.getFormat() == 4){
            a = Integer.parseInt(HelpMe.getPreObj(),16) + 3;
            b = Integer.parseInt(HelpMe.getValue(),16);
            mid = "100";
        }else if (HelpMe.getValue().contains("@")){
            a =  Integer.parseInt(HelpMe.getPreObj(),16) + 2;
            String why = HelpMe.getValue().substring(1);
            b = Integer.parseInt(why,16);
            mid = "0";
        }else if (HelpMe.getValue().contains("#")){
            a =  Integer.parseInt(HelpMe.getPreObj(),16) + 1;
            String why = HelpMe.getValue().substring(1);
            b = Integer.parseInt(why,16);
            mid = "0";
        }else{
            a = Integer.parseInt(HelpMe.getPreObj(),16) + 3;
            b = Integer.parseInt(HelpMe.getValue(),16);
            mid = "0";
        }
        String begin = Integer.toHexString(a);
        String end = Integer.toHexString(b);
        String meB = zeros(begin.length(),2).concat(begin);
        String meE = zeros(end.length(),3).concat(end);
        return meB + mid + meE;

    }
    public static String middle(String[] obj, DataItem HelpMe) {
        String mid = "";
        if (HelpMe.getValue().contains(",")) {
            if (obj[1].equals("pcRelative")) {
                mid = "10";
            } else if (obj[1].equals("base")) {
                mid = "12";
            }
        } else {
            if (obj[1].equals("pcRelative")) {
                mid = "2";
            } else if (obj[1].equals("base")) {
                mid = "4";
            }
        }
        return mid;
    }

    public static String NormWord(DataItem HelpMe, DataItem Second,HashTableQuad toBoot,HashTableQuad SECOP) throws IOException{
        String[] obj = null;
        int a = 0;
        int b = 0;
        String mid = "";
        String  preB1 = "";
        String  preB2 = "";
         if (HelpMe.getFormat() == 4){
             obj=array;
            if (HelpMe.getValue().contains("@"))
                a = Integer.parseInt(HelpMe.getPreObj(),16) + 2;
            else if (HelpMe.getValue().contains("#"))
                a =  Integer.parseInt(HelpMe.getPreObj(),16) + 1;
            else
                a = Integer.parseInt(HelpMe.getPreObj(),16) + 3;
            b = Integer.parseInt(Second.getAddress(),16);
            mid = "100";
            if (HelpMe.getValue().contains(",")){
                mid = "900";
            }
        }else if (HelpMe.getFormat()==2){
            a = Integer.parseInt(HelpMe.getPreObj(),16);
            String[] taker = HelpMe.getValue().split(",");
            preB1 = Integer.toString(SECOP.find(taker[0]).getXbp());
            preB2 = Integer.toString(SECOP.find(taker[1]).getXbp());
        }else if (HelpMe.getValue().contains("@")){
             obj= pcorNah(HelpMe,Second,toBoot);
             if (!obj[1].equals("noGood")) {
                 a = Integer.parseInt(HelpMe.getPreObj(), 16) + 2;
                 mid = middle(obj, HelpMe);
                 b = (int) Long.parseLong(obj[0], 16);
             }else{
                 a = 0;
                 b = 0;
             }
        }else if (HelpMe.getValue().contains("#")){
             obj = pcorNah(HelpMe, Second, toBoot);
             if (!obj[1].equals("noGood")) {
                 a = Integer.parseInt(HelpMe.getPreObj(), 16) + 1;
                 mid = middle(obj, HelpMe);
                 b = (int) Long.parseLong(obj[0], 16);
             }else{
                 a = 0;
                 b = 0;
             }
        }else{
             obj = pcorNah(HelpMe,Second,toBoot);
             if (!obj[1].equals("noGood")) {
                 a = Integer.parseInt(HelpMe.getPreObj(), 16) + 3;
                 mid = middle(obj, HelpMe);
                 b = (int) Long.parseLong(obj[0], 16);
             }else{
                 a = 0;
                 b = 0;
             }
        }
        String meB = "";
        String meE = "";
        if (obj !=null) {
            if (!obj[1].equals("noGood")) {
                if (HelpMe.getFormat() != 2) {
                    String begin = Integer.toHexString(a);
                    String end = Integer.toHexString(b);
                    if (b > 0) {
                        meB = zeros(begin.length(), 2).concat(begin);
                        meE = zeros(end.length(), 3).concat(end);
                    }
                    if (b < 0) {
                        meB = zeros(begin.length(), 2).concat(begin);
                        meE = end.substring(end.length() - 3);
                    }
                } else {
                    meB = Integer.toHexString(a);
                    meE = preB1 + preB2;
                }
            } else {
                writer.write("instruction is out of PC range and Base\n");
            }
        }
        return meB + mid + meE;
    }

    public static String zeros(int a,int b){
        String boy="";
        while (a < b){
            boy+= "0";
            a++;
        }
        return boy;
    }

    public static String  rebuild(int a, String pre,int format){
        int i = 0;
        String toReturn = "";
        int max = 0;
        if (format == 3) max=6; else max=8;
        if (a < 0){
            for (i=a;i <max-2;i++){
                toReturn +="f";
            }
            toReturn+=pre;
        }else if (a> 0){
            for (i=a;i <max;i++){
                toReturn +="0";
            }
            toReturn+=pre;
        }else{
            for (i=a;i <max;i++){
                toReturn +="0";
            }
            toReturn+=pre;
        }
        return toReturn;
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
