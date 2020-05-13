import java.util.*;

public class Main {
    private static class TableEntry<T> {
        private final int key;
        private final T value;

        public TableEntry(int key, T value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }
    }

    private static class HashTable<T> {
        private int size;
        private TableEntry[] table;
        private LinkedHashMap<Integer, ArrayList<String>> keySet;

        public HashTable(int size) {
            this.size = size;
            table = new TableEntry[size];
            keySet = new LinkedHashMap<>();
        }

        public boolean put(int key, T value) {
            int n = findKey(key);
            if( n == -1 ) {
                rehash();
            }
            n = findKey(key);
            table[n] = new TableEntry(key, value);
            //
            var v = keySet.get(key);
            // System.out.println(v);
            if( v == null ) {
                v = new ArrayList<String>();
            }
             v.add((String)value);
            keySet.put(key, v);
            //
            return true;
        }

        public T get(int key) {
            int idx = findKey(key);

            if (idx == -1 || table[idx] == null) {
                return null;
            }

            return (T) table[idx].getValue();
        }

        public LinkedHashMap<Integer, ArrayList<String>> entrySet() {
            return keySet;
        }

        private int findKey(int key) {
            int hash = key % size;

            while (!(table[hash] == null || table[hash].getKey() == key)) {
                hash = (hash + 1) % size;

                if (hash == key % size) {
                    return -1;
                }
            }

            return hash;
        }

        private void rehash() {
            TableEntry[] newTable = new TableEntry[table.length*2];
            TableEntry[] temp = table.clone();
            table = newTable;
            size = table.length;
            for( TableEntry en : temp ) {
                put(en.key, (T)en.value);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int num = Integer.parseInt(scan.nextLine());

        HashTable<String> table = new HashTable<>(num*2);

        for( int i = 0; i < num; ++i) {
            String sss = scan.nextLine();
            //System.out.println(sss);
            String[] str = sss.split("\\s");
            if(str.length == 2 )
                table.put(Integer.parseInt(str[0]), str[1]);
        }
        LinkedHashMap<Integer, ArrayList<String>> ret =  table.entrySet();
        ret.forEach( ( k, v ) -> {
            System.out.print(k + ":");
            for( var val : v ) {
                System.out.print(" " + val);
            }
            System.out.println();
        }   );

        //System.out.println(table.toString());
    }
}