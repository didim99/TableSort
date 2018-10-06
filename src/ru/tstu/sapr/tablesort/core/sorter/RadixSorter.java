package ru.tstu.sapr.tablesort.core.sorter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RadixSorter extends Sorter {
    
    @Override
    public void sortInternal(int[] array) {
        
        List<Queue<Integer>> buckets = new ArrayList<>();
        
        //Initialize buckets
        for (int i = 0; i < 10; ++i) {
            buckets.add(new LinkedList<>());
        }
        
        //Calculate max number length
        int length = 0;
        for (int i : array) {
            int l = (int)Math.floor(Math.log10(i) + 1);
            if (l > length) {
                length = l;
            }
        }
        
        //For each digit in number
        for (int i = 0; i < length; ++i) {
            
            //Empty buckets
            buckets.forEach(Collection::clear);
            
            //Fill buckets
            for (int number : array) {
                int digit = ((number % (int) Math.pow(10, i + 1)) - (number % (int) Math.pow(10, i))) / (int) Math.pow(10, i);
        
                buckets.get(digit).add(number);
            }
            
            //Empty buckets
           for (int j = 0; j < array.length; ++j) {
               for (int k = 0; k <= 10; ++k) {
                   if (k == 10) {
                       throw new ArrayIndexOutOfBoundsException();
                   }
                   if (!buckets.get(k).isEmpty()) {
                       array[j] = buckets.get(k).poll();
                       break;
                   }
               }
                   
           }
        }
    }
}
