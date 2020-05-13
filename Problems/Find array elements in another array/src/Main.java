import java.util.*;

class Main {
  public static int binarySearch(int elem, int[] array) {

    int left = 0;
    int right = array.length - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (elem == array[mid]) {
        /*while( mid >= 0 && elem == array[mid] ) {
          mid--;
        }*/
        return ++mid;
      } else if (elem < array[mid]) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    int arrSize = scan.nextInt();
    int[]  arr = new int[arrSize];
    for( int i = 0 ; i < arrSize ; ++i ) {
      arr[i] = scan.nextInt();
    }

    int fndSize = scan.nextInt();
    int[]  fnd = new int[fndSize];
    for( int i = 0 ; i < fndSize ; ++i ) {
      fnd[i] = scan.nextInt();
    }

    for (int num : fnd ) {
      System.out.println(binarySearch(num, arr) + " ");
    }

  }
}