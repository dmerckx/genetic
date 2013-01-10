package main.crossover;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class HashIndex {

	public HashMap<Integer, Set<Integer>> edgeMap = new HashMap<Integer,Set<Integer>>();
	
	/**
	 * An indexKey index refers to one or more edgeMapkeys of the edgemap in which the given indexKey is within the value set of those edgeMapKeys
	 */
	public HashMap<Integer, Set<Integer>> index = new HashMap<Integer,Set<Integer>>();
	
	public void add(HashMap<Integer, Set<Integer>> edgeMap) {
		index = new HashMap<Integer, Set<Integer>>();
		for(Integer i: edgeMap.keySet()) {
			index.put(i, new HashSet<Integer>());
		}
		for(Integer i: edgeMap.keySet()) {
			for (Integer val: edgeMap.get(i)) {
				index.get(val).add(i);
			}
		}
//		System.out.println(edgeMap);
//		System.out.println(index);
//		System.out.println("------------------------------------------------");
	}
}
