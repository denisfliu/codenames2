

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Words 
{
	public Words()
	{
		Scanner scanner;
		try {
			scanner = new Scanner(new File("src/words.txt"));
			while (scanner.hasNextLine())
			{
				list.add(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<String> generateList()
	{
		
		idNum = rand.nextInt(2);
		for (int i=0; i<=24; i++) //pulls 25 random words and also sets up numList
		{
			numList.add(i);
			
			int num = rand.nextInt(673-i);
			words.add(list.get(num));
			list.remove(num);
		}
		return words;
	}
	
	public ArrayList<String> getList()
	{
		return words;
	}
	
	public void setBlueBoard()
	{
		if (idNum == 1)
		{
			for (int i=0; i<=8; i++)
			{
				randomizeColor(blueList);
			}
		}
		else
		{
			for (int i=0; i<=7; i++)
			{
				randomizeColor(blueList);
				
			}
		}
	}
	
	public void setRedBoard()
	{
		if (idNum == 0)
		{
			for (int i=0; i<=8; i++)
			{
				randomizeColor(redList);
			}
		}
		else
		{
			for (int i=0; i<=7; i++)
			{
				randomizeColor(redList);			
			}
		}
	}
	
	public int setDeathBoard()
	{
		int num = (rand.nextInt(counter - 1));
		int number = numList.get(num);
		numList.remove(num);
		return number;		
	}
	
	public ArrayList<Integer> setBoard()
	{
		setBlueBoard();
		setRedBoard();
		//System.out.println(blueList);
	//	System.out.println(redList);
		ArrayList<Integer> boardList = new ArrayList<Integer>(25);
		for (int i=0; i<=24; i++)
		{
			boardList.add(i);
		}
		for (int i=0; i<=blueList.size()-1; i++)
		{
			boardList.set(blueList.get(i), 1);
		}
		for (int i=0; i<=redList.size()-1; i++)
		{
			boardList.set(redList.get(i), 0);
		}
		boardList.set(setDeathBoard(), 3);
		for (int i=0; i<=numList.size()-1; i++)
		{
			boardList.set(numList.get(i), 2);
		}
		//System.out.println(boardList);
		return boardList;
	}
	
	public void randomizeColor(ArrayList<Integer> cList)
	{
		
		int num = rand.nextInt(counter);
		cList.add(numList.get(num));
		numList.remove(num);
		counter--;
	}
	
	public int getBlueNumber()
	{
		return blueList.size();
	}
	
	public int getRedNumber()
	{
		return redList.size();
	}
	
	int counter = 25; //counts how many numbers remaining in the numList
	Random rand = new Random();
	private ArrayList<String> words = new ArrayList<String>();
	private ArrayList<Integer> blueList = new ArrayList<Integer>();
	private ArrayList<Integer> redList = new ArrayList<Integer>();
	private ArrayList<Integer> numList = new ArrayList<Integer>();
	private ArrayList<String> list = new ArrayList<String>();
	private int idNum; //says which color has more, blue = 1 red = 0
}
