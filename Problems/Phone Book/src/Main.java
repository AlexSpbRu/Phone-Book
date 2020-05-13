import java.util.*;

public class Main {
    private static class TableEntry<T> {
        private final int key;
        private final T value;
        private boolean removed;

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

        public void remove() {
             removed = true;   
        }

        public boolean isRemoved() {
             return removed;   
        }
    }

    private static class HashTable<T> {
        private int size;
        private TableEntry[] table;

        public HashTable(int size) {
            this.size = size;
            table = new TableEntry[size];
        }

        public boolean put(int key, T value) {
            int pos = findKey(key);
            if( pos >= 0 ) {
                table[pos] = new TableEntry<>(key,value);
                return true;
             }
            return false;
        }

        public T get(int key) {
            int pos = findKey(key);
            if( pos >= 0 && table[pos] != null && !table[pos].isRemoved() ) {
                return (T)table[pos].getValue();
            }
            return null;
        }

        public void remove(int key) {
            int pos = findKey(key);
            if( pos >= 0 && table[pos] != null ) {
                table[pos].remove();
            }
        }

        private int findKey(int key) {
            int hash = key % size;

            while (!(table[hash] == null || table[hash].isRemoved() || table[hash].getKey() == key)) {
                hash = (hash + 1) % size;

                if (hash == key % size) {
                    return -1;
                }
            }

            return hash;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
            int num = scan.nextInt();
            //System.out.println(num);

            HashTable<String> table = new HashTable<>(num);

            for( int i = 0; i <= num; ++i) {
                //while( scan.hasNext()) {
                String sss = scan.nextLine();
                //System.out.println(sss);
                String[] str = sss.split("\\s");
                switch(str[0]) {
                    case "put" :
                        if(str.length == 3 )
                            table.put(Integer.parseInt(str[1]), str[2]);
                        break;
                    case "get" :
                        if(str.length == 2 ) {
                            String s = table.get(Integer.parseInt(str[1]));
                            if (s == null) {
                                System.out.println("-1");
                            } else {
                                System.out.println(s);
                            }
                        }
                        break;
                    case "remove" :
                        if(str.length == 2 )
                            table.remove(Integer.parseInt(str[1]));
                        break;
                }
        }
    }
}