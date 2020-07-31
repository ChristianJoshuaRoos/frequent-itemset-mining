import java.security.SecureRandom;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PurchaseRecordGenerator{
	
	public static void makeBaskets()throws IOException{
		String[] fileNames = {"input.txt", "..\\Christian_Roos_HW3_Prob2\\input.txt"};
		FileWriter writer = null;
		
		for (String file : fileNames){
			writer = new FileWriter(file);
			SecureRandom randomNum = new SecureRandom();
			int numberOfBaskets = 1000;
			int itemsInBasketBetween1And100 = 5;
			int itemsInBasketBetween101And2000 = 5;
			ArrayList<Integer> basket;
			
			for (int i = 0; i < numberOfBaskets; i++){
				basket = new ArrayList<Integer>();
				
				for (int j = 0; j < itemsInBasketBetween1And100; j++){
					basket.add(1 + randomNum.nextInt(100));
				}
				
				for (int k = 0; k < itemsInBasketBetween101And2000; k++){
					basket.add(101 + randomNum.nextInt(1900));
				}
				
				try{
					for(Integer item : basket) {
						writer.append(item + " ");
					}
					writer.write(System.lineSeparator());
				}catch(IOException e){
					System.out.println("An IOException was caught: " + e.getMessage());
				}
			}
			writer.close();
		}
		
		
	}
	
	public static void main(String[] args)throws IOException{
		makeBaskets();
	}
}