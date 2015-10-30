package tsp;

import java.util.HashSet;

/**
 * Created by DYKim on 2015. 10. 28..
 */
class CityDistSetKey {
    // C(subset, target in subset) = shortest distance
    // Map<KEY: (subset, target), VALUE: shortest distance> m

    HashSet<Integer> subset;
    Integer target;

    /**
     * CityDistSetKey constructor
     * @param subset : subset (key 1)
     * @param target : target city (key 2)
     * @throws Exception
     *
     * key = (subset, target)
     */
    public CityDistSetKey(HashSet<Integer> subset, Integer target) throws Exception {
        if (!subset.contains(target))
            throw new Exception("there is no target in subset");

        this.subset = subset;
        this.target = target;
    }

    /*
        Getter method
     */
    public final HashSet<Integer> getSubset() {
        return subset;
    }

    public final int getTarget() {
        return target;
    }

    // No Setter method


    /*
        Override method
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CityDistSetKey))
            return false;
        CityDistSetKey ref = (CityDistSetKey) obj;
        return this.subset.equals(ref.subset) &&
                this.target.equals(ref.target);
    }

    @Override
    public int hashCode() {
        return (subset.hashCode() << 16) + target.hashCode();
//        return subset.hashCode() ^ target.hashCode();
    }
}
