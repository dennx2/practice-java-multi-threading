package synced_concurrent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

// Jaydenn
public class ConcurrentThread {
    public static void main(String[] args) {
        // create tasks
        Runnable addNewNumber = new AddNewNumber();
        Runnable traverseIterator = new TraverseIterator(((AddNewNumber) addNewNumber).getNumbers());

        // create threads
        Thread thread1 = new Thread(addNewNumber);
        Thread thread2 = new Thread(traverseIterator);

        // start threads
        thread1.start();
        thread2.start();
    }
}

class AddNewNumber implements Runnable {

    // create a hashset to store numbers
    HashSet<Integer> numbers = new HashSet<>(Arrays.asList(5, 4, 3));

    // getter for the hashset
    public HashSet<Integer> getNumbers() {
        return numbers;
    }

    // add a new number to the hashset every second
    @Override
    public void run() {
        // create random number object
        Random rand = new Random();

        // keep the program running
        while (true) {
            // generate a random number
            Integer rn = rand.nextInt(2000);

            // create a lock on the hashset so the other thread cannot iterate through it while adding
            synchronized (numbers) {
                // print current hashset content
                System.out.println(numbers);
                // add the random number to the hashset
                numbers.add(rn);
            }


            // wait for a second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class TraverseIterator implements Runnable {

    HashSet hashset;

    public TraverseIterator(HashSet hs) {
        this.hashset = hs;
    }

    // iterate through the hashset and wait a second before going again
    @Override
    public void run() {

        // keep the thread running
        while (true) {
            // create a lock on the hashset so the other thread cannot add/alter the hashset
            synchronized (hashset) {
                // create an iterator for the hashset
                Iterator<Integer> iterator = hashset.iterator();
                // go through each item in the hashset
                while (iterator.hasNext()) {
                    int it = iterator.next();
                    // print out the item
                    System.out.println(it);
                }
            }

            // wait for a second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}