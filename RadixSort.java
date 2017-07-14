import java.util.*;

/**
 * Created by dullam on 7/12/17.
 */

public class RadixSort {

    private class Bucket{
        public Queue<Integer> queue;

        public Bucket() {
            queue = new LinkedList<Integer>();
        }
    }

    public int[] radixSort(int[] a) {

         if (a.length < 2) return a;
         Bucket[] buckets = new Bucket[10];

         for(int i=0;i<10;i++) {
             buckets[i] = new Bucket();
         }

         /* Find Max */
         int max = a[0];
         for (int i=1;i<a.length;i++) {
             if(a[i] > max) max =a[i];
         }
         /* find number of digits in max number */
         int digitsInMax = 0;
         int temp = max;
         while(temp != 0) {
             digitsInMax++;
             temp = temp/10;
         }
         System.out.println("Max digits are " + digitsInMax);

         int k = 1;
         for( int w=1; w<=digitsInMax; w++) {
             /** Put into Buckets **/
             for(int i =0; i< a.length; i++) {
                 buckets[(a[i]/k)%10].queue.add(a[i]);
             }

             /** Re arrange Array */
             int s=0;
             for(int i=0; i<10;i++) {
                  Integer elem = null;
                  while(buckets[i].queue.size() > 0 && (elem = buckets[i].queue.remove()) != null){
                      a[s] = (int)elem;
                      s = s+1;
                  }
              }
              k = k*10;
         }
        return a;
    }



    public static void main(String[] args) {

//        System.out.println("Enter numbers of elements to sort ");
//        Scanner s = new Scanner(System.in);
//
//
//        int n=s.nextInt();
//        int a[]=new int[n];
//
//        System.out.println("enter elements");
//
//        for(int i=0;i<n;i++){//for reading array
//            a[i]=s.nextInt();
//        }
         int[] a = {1,345,987,93,774,98,62,189,341,9870293,989999999,54,3,6};
         for(int i=0;i<a.length;i++){//for reading array
            System.out.println(a[i]);
         }
        System.out.println("Sorted List is");
        new RadixSort().radixSort(a);
        System.out.println(" ***************** ");
        for(int i=0;i<a.length;i++){//for reading array
            System.out.println(a[i]);
        }
    }
}
