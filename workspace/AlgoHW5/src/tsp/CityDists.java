package tsp;

import dy.DYConsole;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by DYKim on 2015. 10. 28..
 */
public class CityDists {
    public static DYConsole console = new DYConsole();

    /**
     * CityDistSetKey : subset, target city
     * Integer : shortest distance from 1 to target city
     *
     * cityDist[CityDistSetKey] = d (d is shortest distance)
     */
    HashMap<CityDistSetKey, Integer> cityDistSet;

    public CityDists() {
        console.setClassPrefix("CityDists");

        try {
            this.cityDistSet = new HashMap<CityDistSetKey, Integer>();
        }
        catch(Exception e) {
            e.notify();
        }
    }

    /*
        Getter method (rarely used, almost not use)
     */
    public final HashMap<CityDistSetKey, Integer> getCityDistSet() {
        return cityDistSet;
    }

    /*
        Operations
    */
    public void addCityDist(HashSet<Integer> subset, int target, int dist) throws Exception {
        CityDistSetKey key = new CityDistSetKey(subset, target);
        cityDistSet.put(key, dist);
    }
    public void modifyCityDist(HashSet<Integer> subset, int target, int dist) throws Exception {
        CityDistSetKey key = new CityDistSetKey(subset, target);
        cityDistSet.replace(key, dist);
    }
    public void removeCityDist(HashSet<Integer> subset, int target) throws Exception {
        CityDistSetKey key = new CityDistSetKey(subset, target);
        cityDistSet.remove(key);
    }
    public int getCityDist(HashSet<Integer> subset, int target) throws Exception {
        CityDistSetKey key = new CityDistSetKey(subset, target);
        return cityDistSet.get(key);
    }
}
