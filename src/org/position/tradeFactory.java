package org.position;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.position.objects.trade;

public class tradeFactory {
	
	private static int randomValue = 10000000;
	private static DateFormat dateFormat = new SimpleDateFormat("HHmmssSSS");

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		while (true) {
			System.out.println(getRandomID() + " " + getRandomBuySell() + " " +getRandomPrice() + " " + getRandomQuantity()+ " " +getRandomStock());
			Thread.sleep(1000);
		}
		
	}
	
	public static trade createRandomTrade() {
		
		return createTrade(getRandomID(), getRandomBuySell(), getRandomPrice() , getRandomQuantity(), getRandomStock(), getRandomCurrency());
	
	}
	
	
	public static trade createTrade(String ID, String BUYSELL, Double PRICE, int QUANTITY, String STOCK, String CURRENCY) {
		
		trade myTrade = new trade();
		
		myTrade.setID(ID);
		myTrade.setBUYSELL(BUYSELL);
		myTrade.setPRICE(PRICE);
		myTrade.setQUANTITY(QUANTITY);
		myTrade.setSTOCK(STOCK);
		myTrade.setCCY(CURRENCY);
		
		return myTrade;
		
	}
	
	
	
	public static int getRandomInt() {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(randomValue);
	}
	
	
	public static int getRandomQuantity() {
		int maxQuantity = 100; 
		
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(maxQuantity);
	}
	
	
	public static Double getRandomPrice() {
		
		Random randomGenerator = new Random();
		Double price = randomGenerator.nextDouble() * 10;
		return price;
	}

	
	public static String getRandomID() {
		
		Date date = new Date();
				
		List<String> list = new LinkedList<String>();
		list.add("TA");
		list.add("TB");
		list.add("TC");
		list.add("TD");
		list.add("TE");
		list.add("TF");

		Random rand = new Random();
		int choice = rand.nextInt(list.size());
		
		return String.valueOf(getRandomInt()) + list.get(choice) + dateFormat.format(date);

	}
	
	public static String getRandomBuySell() {
		List<String> list = new LinkedList<String>();
		list.add("BUY");
		list.add("SELL");

		Random rand = new Random();
		int choice = rand.nextInt(list.size());
		return list.get(choice);

	}

	public static String getRandomStock() {
		List<String> list = new LinkedList<String>();
		list.add("SOW");
		list.add("GOOG");
		list.add("FB");
		list.add("MS");
		list.add("SONY");
		list.add("ZNGA");

		Random rand = new Random();
		int choice = rand.nextInt(list.size());
		return list.get(choice);

	}
	
	public static String getRandomCurrency() {
		List<String> list = new LinkedList<String>();
		list.add("EUR");
		list.add("USD");
		list.add("JPY");
		list.add("GBP");

		Random rand = new Random();
		int choice = rand.nextInt(list.size());
		return list.get(choice);

	}

	public static String getRandomLastName() {
		List<String> regions = new LinkedList<String>();
		regions.add("Mustermann");
		regions.add("Huber");
		regions.add("Maier");
		regions.add("Hoffmann");
		regions.add("Berger");
		regions.add("Gruber");

		Random rand = new Random();
		int choice = rand.nextInt(regions.size());
		return regions.get(choice);

	}

}
