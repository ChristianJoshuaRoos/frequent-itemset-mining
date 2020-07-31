import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AprioriAlgorithm{
	
	private static Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
	private static Map<Integer, HashSet<Integer>> baskets = new HashMap<Integer, HashSet<Integer>>();
	private static int numberOfdistinctItems = 2000;
	private static Integer support = 50;
	
	public static Map<Integer, List<Integer>> getFile(String fileName) throws FileNotFoundException{
		
		Scanner input = new Scanner(new File(fileName));
	
		while (input.hasNext()){
			Integer item = input.nextInt();
			
			if (map.containsKey(item)){
				map.get(item).add(1);
			}else{
				map.put(item, new ArrayList());
				map.get(item).add(1);
			}
		}
		input.close();
		
		Scanner inputForBaskets = new Scanner(new File(fileName));
		Integer index = 0;
		
		while (inputForBaskets.hasNextLine()){
			baskets.put(index, new HashSet());
			
			while (inputForBaskets.hasNext()){
				Integer item = inputForBaskets.nextInt();
					
				baskets.get(index).add(item);
			}
			index++;
		}
		inputForBaskets.close();
		
		System.out.println("\nIndex : Baskets\n");
		for (Integer key : baskets.keySet() ){
			System.out.println(key + ": " + baskets.get(key).toString());
		}
		System.out.println();
		
		int numberOfItems = map.size();
		System.out.println("\nNumber of items: " + numberOfItems + "\n");
		
		System.out.println("\nItem : Occurrences\n");
		for (Integer key : map.keySet() ){
			System.out.println(key + ": " + map.get(key).toString());
		}
		System.out.println();
		
		return map;
	}	
	
	public static Map<Integer, List<Integer>> getCountOfEachIndividualItem(Map<Integer, List<Integer>> map){
		for (Integer key : map.keySet()){
			int countOfItem = map.get(key).size();
			map.get(key).clear();
			map.get(key).add(countOfItem);
		}
		
		System.out.println("\nItem : Count\n");
		for (Integer key : map.keySet() ){
			System.out.println(key + ": " + map.get(key).toString());
		}
		System.out.println();
		
		return map;
	}
	
	public static Map<Integer, List<Integer>> getFrequentItems(Map<Integer, List<Integer>> items){
		Map<Integer, List<Integer>> frequentItems = new HashMap<Integer, List<Integer>>();
		
		for (int key = 1; key <= numberOfdistinctItems; key++){
			if (!items.containsKey(key)){
				continue;
			}
			if (items.get(key).get(0) >= support){
				frequentItems.put(key, new ArrayList());
				frequentItems.get(key).add(items.get(key).get(0));
			}
		}
		
		System.out.println("\nFrequent Item : Count > " + support + "\n");
		for (Integer key : frequentItems.keySet() ){
			System.out.println(key + ": " + frequentItems.get(key).toString());
		}
		System.out.println();
		
		return frequentItems;
	}
	
	public static Map<Integer, HashSet> constructAllPairsOfFrequentItems(Map<Integer, List<Integer>> items){
		List<Integer> itemsArray = new ArrayList<Integer>(items.keySet());
		
		System.out.println("\nFrequent Items:\n");
		System.out.print("[");
		for (Integer item : itemsArray){
			System.out.print(item + " ");
		}
		System.out.print("]");
		
		Map<Integer, HashSet> frequentPairs = new HashMap<Integer, HashSet>();
		Integer index = 0;
		
		for (int item = 0; item < itemsArray.size() - 1; item++){
			for (int item2 = item + 1; item2 < itemsArray.size(); item2++){
				frequentPairs.put(index, new HashSet());
				frequentPairs.get(index).add(itemsArray.get(item));
				frequentPairs.get(index).add(itemsArray.get(item2));
				index++;
			}
		}
		
		System.out.println("\nIndex : Set of Frequent Pairs \n");
		for (Integer key : frequentPairs.keySet() ){
			System.out.println(key + ": " + frequentPairs.get(key).toString());
		}
		System.out.println();
		
		Integer numberOfFrequentPairs = frequentPairs.size();
		System.out.printf("%nNumber of Frequent Pairs with Support = %d: %d%n", support, numberOfFrequentPairs);
		
		return frequentPairs;
	}
	
	public static Map<Integer, HashSet> checkFrequentPairsAgainstBaskets(Map<Integer, HashSet<Integer>> baskets, Map<Integer, HashSet> frequentPairs){
		Map<Integer, HashSet> checkedFrequentPairs = new HashMap<Integer, HashSet>();
		Integer index = 0;
		
		for (int keyInFP = 0; keyInFP < frequentPairs.size(); keyInFP++){			
			if (baskets.containsValue(frequentPairs.get(keyInFP))){
				checkedFrequentPairs.put(index, new HashSet());
				checkedFrequentPairs.get(index).addAll(frequentPairs.get(keyInFP));
			}
		}
		
		System.out.println("\nIndex : Checked Frequent Pairs \n");
		for (Integer key : checkedFrequentPairs.keySet() ){
			System.out.println(key + ": " + checkedFrequentPairs.get(key).toString());
		}
		System.out.println();
		
		return checkedFrequentPairs;
	}

	public static void main(String[] args)throws IOException{
		Long startTime = System.currentTimeMillis();

		PurchaseRecordGenerator.makeBaskets();
		Map<Integer, List<Integer>> mapOfItems = getFile("input.txt");
		Map<Integer, List<Integer>> mapOfItemsWithCounts = getCountOfEachIndividualItem(mapOfItems);
		Map<Integer, List<Integer>> frequentItems = getFrequentItems(mapOfItemsWithCounts);
		Map<Integer, HashSet> pairsOfFrequentItems = constructAllPairsOfFrequentItems(frequentItems);
		Map<Integer, HashSet> checkedPairs = checkFrequentPairsAgainstBaskets(baskets, pairsOfFrequentItems);
		
		Long endTime = System.currentTimeMillis();
		Long executionTime = (endTime - startTime)/1000;
		System.out.printf("%nExecution Time: %d secs %n", executionTime);
		
		ResultsToOutputFile.sendFrequentPairsToFile(checkedPairs);
		ResultsToOutputFile.sendExecutionTimeToFile(executionTime);
		
		Double usedMemoryInMB = getUsedMemory() * 0.000001;
		ResultsToOutputFile.sendUsedMemoryToFile(usedMemoryInMB);
	}
	
	public static Long getUsedMemory(){
		return getMaxMemory() - getFreeMemory();
	}
	
	public static Long getMaxMemory(){
		return Runtime.getRuntime().maxMemory();
	}
	
	public static Long getFreeMemory(){
		return Runtime.getRuntime().freeMemory();
	}
}