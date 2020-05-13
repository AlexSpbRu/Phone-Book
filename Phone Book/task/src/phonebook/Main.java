package phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Contact {
    public String       name;
    public String       phoneNumber;
    Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public boolean lessEQ( Contact cont )  {
        return name.compareToIgnoreCase(cont.name) <= 0;
    }

    public boolean eqName( String Name )  {
        return name.equalsIgnoreCase(Name);
    }

    public boolean gtName( String Name )  {
        return name.compareToIgnoreCase(Name) > 0;
    }
}

class PhoneBook {
    protected ArrayList<Contact>        contakts = new ArrayList<Contact>() ;
    protected Contact[]                 ar_contakts;
    protected HashMap<String, Contact>  hash_contact = null;

    protected boolean loadContakts( String fileName ) {
       try {
           List<String> strList = Files.readAllLines(Paths.get(fileName));
           ar_contakts = new Contact[strList.size()];
           int i = 0;
            for( String str : strList) {
                String[]  strArr = str.split("\\s");
                Contact c = null;
                if( strArr.length == 2 ) {
                    c = new Contact(strArr[1], strArr[0]);
                } else  if( strArr.length == 3 ) {
                    c = new Contact(strArr[1] + " " + strArr[2], strArr[0]);
                }
                contakts.add(c);
                ar_contakts[i] = c;
                i++;
            }
       }
       catch( IOException e) { return  false; }
       return true;
   }

   public PhoneBook( String fileName ) {
       loadContakts( fileName );
    }

    public int size()  {
        return contakts.size();
    }

    public int find( List<String> strList ) {
        int countFind = 0;
        for( String Name : strList) {
            for (var con : contakts) {
                if (con.name.contentEquals(Name)) {
                    countFind++;
                    break;
                }
            }
        }
        return countFind;
    }
    //------------------------------------------------
    public long bubbleSortAR( long maxTime ) {
        var startTime = System.currentTimeMillis();
        boolean changed = false;
        int j = 0;
        do {
            var stTm = System.currentTimeMillis();
            changed = false;
            for( int i = 0; i < contakts.size() - j - 1; i++ ) {
                if( ar_contakts[i].name.compareToIgnoreCase(ar_contakts[i+1].name) < 0 ) {
                    var v = ar_contakts[i];
                    ar_contakts[i] = ar_contakts[i+1];
                    ar_contakts[i+1] = v;
                    changed = true;
                }
            }
            var stTime = System.currentTimeMillis();
            System.out.println(j + "  " + (stTime - stTm));
            if( stTime - startTime > maxTime) {
                System.out.println("STOPPED, moved to linear search");
                break;
            }
            j++;
        }
        while(changed);
        var stopTime = System.currentTimeMillis();
        return stopTime - startTime;
    }

    public long bubbleSort( long maxTime ) {
        var startTime = System.currentTimeMillis();
        boolean changed = false;
        int j = 0;
        do {
            var stTm = System.currentTimeMillis();
            changed = false;
            for( int i = 0; i < contakts.size() - j - 1; i++ ) {
                if( contakts.get(i).name.compareToIgnoreCase(contakts.get(i+1).name)  < 0 ) {
                    var v = contakts.get(i);
                    contakts.set(i, contakts.get(i+1));
                    contakts.set(i+1, v);
                    changed = true;
                }
            }
            var stTime = System.currentTimeMillis();
            //System.out.println(j + "  " + (stTime - stTm));
            if( stTime - startTime > maxTime) {

                break;
            }
            j++;
        }
        while(changed);
        var stopTime = System.currentTimeMillis();
        return stopTime - startTime;
    }
    //------------------------------------------------
    public int jumpSearchSingle(String name) {
        int count = contakts.size();
        if( contakts.isEmpty() ) {
            return -1;
        }
        int blockSize = (int)Math.sqrt(count);

        if( contakts.get(0).name.equalsIgnoreCase(name) ){
            return 0;
        }
        int startBlock = 0;
        int stopBlock = 0;

        while( stopBlock < count-1 ) {

            stopBlock = Math.min(count-1,startBlock + blockSize);
            if( contakts.get(stopBlock).name.compareToIgnoreCase(name) >= 0  ) {
                 break;
            }
            startBlock = stopBlock;
        }
        if( contakts.get(stopBlock).name.compareToIgnoreCase(name) >= 0  ) {
            for( int i = stopBlock ; i > startBlock ; i-- ) {
               if( contakts.get(i).name.compareToIgnoreCase(name) >= 0 )
                   if(contakts.get(i).name.equalsIgnoreCase(name))
                        return i;
                   else
                       return -1;
            }
        }
        return -1;
    }

    public int jumpSearch( List<String> strList ) {
        int countFind = 0;
        for( String Name : strList) {
            if( jumpSearchSingle(Name) >= 0 )
                countFind++;
        }
        return countFind;
    }

    //------------------------------------------------

    public  void quickSort( int left, int right) {
        if (left < right) {
            int pivotIndex = partition(left, right); // the pivot is already on its place
            quickSort( left, pivotIndex - 1);  // sort the left subarray
            quickSort( pivotIndex + 1, right); // sort the right subarray
        }
    }

    private  int partition(int left, int right) {
        Contact pivot = contakts.get(right);  // choose the rightmost element as the pivot
        int partitionIndex = left; // the first element greater than the pivot

        /* move large values into the right side of the array */
        for (int i = left; i < right; i++) {
            if (contakts.get(i).lessEQ(pivot) ) { // may be used '<' as well
                swap( i, partitionIndex);
                partitionIndex++;
            }
        }

        swap( partitionIndex, right); // put the pivot on a suitable position

        return partitionIndex;
    }

    private  void swap(int i, int j) {
        Contact temp = contakts.get(i);
        contakts.set(i, contakts.get(j));
        contakts.set(j, temp);
    }

    //------------------------------------------------

    public  int binarySearch( String elem, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2; // the index of the middle element

            if ( contakts.get(mid).eqName(elem) ) {
                return mid; // the element is found, return its index
            } else if (contakts.get(mid).gtName(elem)) {
                right = mid - 1; // go to the left subarray
            } else {
                left = mid + 1;  // go the the right subarray
            }
        }
        return -1; // the element is not found
    }

    public int binarySearch( List<String> strList ) {
        int countFind = 0;
        for( String Name : strList) {
            if( binarySearch(Name, 0 , contakts.size() -1 ) >= 0 )
                countFind++;
        }
        return countFind;
    }
    //------------------------------------------------
    public void createHahsMap() {
        hash_contact = new HashMap<>(ar_contakts.length);
        for( var con : ar_contakts) {
            hash_contact.put(con.name, con );
        }
    }

    public int searchHashMap( List<String> strList ) {
        int countFind = 0;
        for( String Name : strList) {
            if( hash_contact.get(Name)  != null )
                countFind++;
        }
        return countFind;
    }
}

class TimerPB {
    long        startTm = 0l;
    long        stopTm = 0l;

    public void start() {
        startTm = System.currentTimeMillis();
    }

    public void stop() {
        stopTm = System.currentTimeMillis();
    }

    public String toString() {
        var tm = LocalTime.ofNanoOfDay((stopTm-startTm)*1000000);
        return  tm.getMinute() + " min. " + tm.getSecond() + " sec. " +
                (int)(tm.getNano()/1000000) + " ms.";
    }

    public static String toString( long dt ) {
        var tm = LocalTime.ofNanoOfDay(dt*1000000);
        return  tm.getMinute() + " min. " + tm.getSecond() + " sec. " +
                (int)(tm.getNano()/1000000) + " ms.";
    }

    public long getDT() {
        return stopTm - startTm;
    }
}

public class Main {
    static final protected String       phoneboofFile = "d:\\Alex\\Java\\directory.txt";//"..\\..\\directory.txt";//
    static final protected String       finds = "d:\\Alex\\Java\\find.txt";//"..\\..\\find.txt";//
    public static void main(String[] args) {
        PhoneBook phonebook = new PhoneBook(phoneboofFile);
        try {
            List<String> strList = Files.readAllLines(Paths.get(finds));
            System.out.println("Start searching...");
            TimerPB tmr = new TimerPB();

            tmr.start();
            int countFind = phonebook.find(strList);
            tmr.stop();

            System.out.print("Found " + countFind + " / " + strList.size() + " entries.");
            System.out.println(" Time taken: " + tmr.toString());

            //------------------------------------------------
            System.out.println("Start searching (bubble sort + jump search)...");
            TimerPB tmr1 = new TimerPB();
            tmr1.start();
            long maxTime = tmr.getDT()*10;
            long dt = phonebook.bubbleSort(maxTime);
            if( dt > maxTime) {
                tmr.start();
                countFind = phonebook.find(strList);
                tmr.stop();
            } else {
                tmr.start();
                countFind = phonebook.jumpSearch(strList);
                tmr.stop();
            }
            tmr1.stop();

            System.out.print("Found " + countFind + " / " + strList.size() + " entries.");
            System.out.println(" Time taken: " + tmr1.toString());

            System.out.print("Sorting time: " + TimerPB.toString(dt));
            if( dt > maxTime) {
                System.out.println("   STOPPED, moved to linear search...");
            } else {
                System.out.println();
            }

            System.out.println("Searching time: " + tmr.toString());
            //------------------------------------------------
            System.out.println("Start searching (quick sort + binary search)...");
            tmr1.start();

            tmr.start();
            phonebook.quickSort(0, phonebook.size() -1 );
            tmr.stop();
            dt = tmr.getDT();

            tmr.start();
            countFind = phonebook.binarySearch(strList);
            tmr.stop();

            tmr1.stop();

            System.out.print("Found " + countFind + " / " + strList.size() + " entries.");
            System.out.println(" Time taken: " + tmr1.toString());

            System.out.println("Sorting time: " + TimerPB.toString(dt));

            System.out.println("Searching time: " + tmr.toString());
            //------------------------------------------------
            System.out.println("Start searching (hash table))...");
            tmr1.start();

            tmr.start();
            phonebook.createHahsMap();
            tmr.stop();
            dt = tmr.getDT();

            tmr.start();
            countFind = phonebook.searchHashMap(strList);
            tmr.stop();

            tmr1.stop();

            System.out.print("Found " + countFind + " / " + strList.size() + " entries.");
            System.out.println(" Time taken: " + tmr1.toString());

            System.out.println("Creating time: " + TimerPB.toString(dt));

            System.out.println("Searching time: " + tmr.toString());
        }
        catch( IOException e) { }


    }

}
