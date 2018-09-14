package utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Randomness {

    private static Random random = null;

    private static Randomness instance = new Randomness();

    private Randomness() {
        long seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    public <T> T choice(Collection<T> collection) {
        if (collection.isEmpty())
            return null;

        int position = random.nextInt(collection.size());
        Iterator<T> iterator = collection.iterator();
        int counter = 0;
        while(iterator.hasNext()){
            if(counter == position){
                return iterator.next();
            }
            counter++;
        }
        throw new IllegalStateException("Randomness choice - impossible to retrieve element with position " + position);
    }

    public int nextInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static Randomness getInstance(){
        return instance;
    }
}
