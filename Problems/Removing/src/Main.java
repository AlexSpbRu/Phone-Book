import java.util.Scanner;

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
            int n = findKey(key);
            if( n == -1 ) {
                rehash();
            }
            n = findKey(key);
            table[n] = new TableEntry(key, value);
            return true;
        }

        public T get(int key) {
            int pos = findKey(key);
            if( pos >= 0 && table[pos] != null && !table[pos].isRemoved() ) {
                return (T)table[pos].getValue();
            }
            return null;
        }

        public void remove(int key) {
            //System.out.println("remove " + key);
            int pos = findKey(key);
            //System.out.println("pos " + pos);
            if( pos >= 0 && table[pos] != null ) {
                table[pos].remove();
            }
        }

        private int findKey(int key) {
            int hash = key % size;
            //System.out.println("hash " + hash);
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

        @Override
        public String toString() {
            StringBuilder tableStringBuilder = new StringBuilder();

            for (int i = 0; i < table.length; i++) {
                if (table[i] == null) {
                    tableStringBuilder.append(i + ": null");
                } else {
                    tableStringBuilder.append(i + ": key=" + table[i].getKey()
                                                + ", value=" + table[i].getValue()
                                                + ", removed=" + table[i].isRemoved());
                }

                if (i < table.length - 1) {
                    tableStringBuilder.append("\n");
                }
            }

            return tableStringBuilder.toString();
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[] nums = scan.nextLine().split("\\s");
        int num1 = Integer.parseInt(nums[0]);
        int num2 = Integer.parseInt(nums[1]);


        HashTable<String> table = new HashTable<>(5);

        for( int i = 0; i < num1; ++i) {
            String sss = scan.nextLine();
            //System.out.println(sss);
            String[] str = sss.split("\\s");
            if(str.length == 2 )
                table.put(Integer.parseInt(str[0]), str[1]);
        }
        for( int i = 0; i < num2; ++i) {
            String sss = scan.nextLine();
            //System.out.println(sss);
            table.remove(Integer.parseInt(sss));
        }
        System.out.println(table.toString());
    }
}