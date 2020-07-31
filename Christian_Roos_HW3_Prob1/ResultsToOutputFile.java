import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashSet;

public class ResultsToOutputFile{

	private static FileWriter writer;

	public static void sendFrequentPairsToFile(Map<Integer, HashSet> frequentPairs)throws IOException{
		writer = new FileWriter("output.txt");
		
		try{
			for(Integer key : frequentPairs.keySet()) {
				writer.append(frequentPairs.get(key) + System.lineSeparator());
			}
			writer.write(System.lineSeparator());
		}catch(IOException e){
			System.out.println("An IOException was caught: " + e.getMessage());
		}
	}
	
	public static void sendExecutionTimeToFile(Long executionTime)throws IOException{
		try{
			writer.append("Execution Time: " + executionTime + " secs\n");
			writer.write(System.lineSeparator());
		}catch(IOException e){
			System.out.println("An IOException was caught: " + e.getMessage());
		}
	}
	
	public static void sendUsedMemoryToFile(Double usedMemoryInMB)throws IOException{
		try{
			writer.append("Used Memory: " + usedMemoryInMB + " MB\n");
			writer.write(System.lineSeparator());
			writer.close();
		}catch(IOException e){
			System.out.println("An IOException was caught: " + e.getMessage());
		}
	}
}