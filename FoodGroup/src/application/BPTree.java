package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.HashMap;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author Peter Daly (pndaly@wisc.edu)
           Andrew Elert (elert3@wisc.edu)
           Huzaifa Sohail (hsohail@wisc.edu)
           Sasha Arkhagha (arkhagha@wisc.edu)
           Ethan Yeck (eyeck@wisc.edu)
 *
 * @param HashMap<K,V> nutrientvals - hashmap that stores key/value pairs for a specific nutrient
 * 
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

    // Root of the tree
    private HashMap<K,V> nutrientVals;
    


    
    
    /**
     * Public constructor
     *
     */
    public BPTree() {
       this.nutrientVals= new HashMap<K,V>();
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
        //Inserts key/value pair
     	double dubKey= (double)key;
    	nutrientVals.put(key, value);
    	
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        //Avoid errors
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=") ) {
            return new ArrayList<V>();
        }
        else {
        	List<V> returnList = new ArrayList<V>();
        	//uses correct comparator statement
        	if(comparator.contentEquals(">=")) {
        		//Checks each entry's key and compares it to the key
        		for(Entry<K, V> entry: nutrientVals.entrySet()) {
            		if(entry.getKey().compareTo(key)>0) {
            			returnList.add(entry.getValue());
            		}
            	}	
        		
        	}
        	if(comparator.contentEquals("<=")) {
        		for(Entry<K, V> entry: nutrientVals.entrySet()) {
        			
            		if(entry.getKey().compareTo(key)<0) {
            			returnList.add(entry.getValue());
            		}
            	}	
        	}
        	if(comparator.contentEquals("==")) {
        		for(Entry<K, V> entry: nutrientVals.entrySet()) {
        			
            		if(entry.getKey().compareTo(key)==0) {
            			returnList.add(entry.getValue());
            		}
            	}	
        	}
        	return returnList;
        }
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	String str = "";
        //Returns all items in string form
    	for(Entry<K, V> entry: nutrientVals.entrySet()) {
    		str+="{"+ entry.getKey() + ", " + entry.getValue()+ "}, ";
    	}
    	int g= str.length();
    	str=str.substring(0,g-2);
    	str+="\n";
    	return str;
    }
    
    
    
    

} // End of class BPTree
