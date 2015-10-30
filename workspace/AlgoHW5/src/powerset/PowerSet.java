package powerset;

import dy.DYConsole;

import java.util.HashSet;
import java.util.HashMap;

/**
 * Created by DYKim on 2015. 10. 28..
 */
public class PowerSet {
    public static DYConsole console = new DYConsole();

    /**
     * Integer : size of subset
     * HashSet<subset>: subsets of this size
     * subset = HashSet<Integer>()
     *
     * Each subset has to contain 'city 1'
     */
    HashMap< Integer, HashSet<HashSet<Integer>> > subsetDictBySize;
    int numberOfCity;   // no of city

    public PowerSet(int numberOfCity) {
        console.setClassPrefix("PowerSet");

        this.numberOfCity = numberOfCity;
        if(numberOfCity == 1) {
            this.subsetDictBySize = null;
        }
        else {
            this.subsetDictBySize = new HashMap<Integer, HashSet<HashSet<Integer>>>();
            initSetDictionary(numberOfCity); // must contain 'city 1'
        }
    }

    /*
        static constructor method
     */
    /**
     * initSetDictionary subconstructor method
     * @param numberOfCity: no of city
     *
     * each subset in subsetDictBySize must contain 'city 1'
     */
    void initSetDictionary(int numberOfCity) {
        console.setMethodPrefix("initSetDictionary");
        for(int size=1; size<numberOfCity+1; size++) {
            makeSubsets(size, numberOfCity);
            console.println("size: " + size + ", subsets: " + subsetDictBySize.get(size));
        }
        // subsetDictBySize completed
    }

    /**
     * makeSubSets subconstructor method
     * @param size : size which of subset made
     * @param numberOfCity : no of city
     */
    void makeSubsets(int size, int numberOfCity) {
        HashSet<HashSet<Integer>> subsets = new HashSet<HashSet<Integer>>();

        // base case
        if (size==1) {  // only city 1 is in
            HashSet<Integer> subset = new HashSet<Integer>();
            subset.add(1);
            subsets.add(subset);
        }
        // recursive case (size >= 2)
        else {
            for(HashSet<Integer> subset: subsetDictBySize.get(size-1)) {    // for each size-1 subsets
                for(int i=2; i<numberOfCity+1; i++) {
                    if(subset.contains(i))
                        continue;
                    HashSet<Integer> localSubset = new HashSet<Integer>(subset);
                    localSubset.add(i);

                    subsets.add(localSubset);
                }
            }
        }

        subsetDictBySize.put(size, subsets);
    }

    /*
        Getter method
     */
    final public HashMap< Integer, HashSet<HashSet<Integer>> > getSetDictionary() {
        return subsetDictBySize;
    }

    /*
        Tool method
     */

    /**
     * findSubSetExcludeCity method
     * @param size : target subset's size
     * @param subset : target subset
     * @param city : city which will be removed from subset
     * @return : subset - {city}
     * @throws Exception
     *
     * find subset-{city} from subsetDictBysize, and return it
     */
    public HashSet<Integer> findSubSetExcludeCity(int size, HashSet<Integer> subset, int city) throws Exception {
        if (!subset.contains(city))
            throw new Exception("there is no city in that subset");

        HashSet<Integer> compareSubSet = new HashSet<Integer>(subset);
        compareSubSet.remove(city);

        HashSet<HashSet<Integer>> parents = subsetDictBySize.get(size-1);
        for(HashSet<Integer> parent: parents) {
            if(parent.containsAll(compareSubSet)) {
                return parent;
            }
        }

        throw new Exception("There is no subset contain subset-{city}");
    }
}
