
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jscience.mathematics.vector.Float64Vector;

public class AI
{
	public AI() throws NumberFormatException, IOException
	{
		this("src/wiki-news-300d-1M.vec", 400000);
	}
	
	public AI(String path)
	{
		this(path, 400000);
	}
	
	public AI(String path, int limit)
	{
		int dimensionSize = 300;
		int counter = 0;
		//Scanner scanner;
		try (BufferedReader br = new BufferedReader(new FileReader(path)))
		{
			boolean first = true;
			for (String line; (line = br.readLine()) != null; )
			{
				if (first)
				{
					dimensionSize = Integer.parseInt(line.split(" ")[1]);
					first = false;
					continue;
				}
				if (counter > limit)
				{
					break;
				}
				String arr[] = line.split(" ", 2);
				String stuff = arr[1];
				String word = arr[0];
				wordMap.put(word.toLowerCase(), populateFloat(dimensionSize, stuff));
				counter++;
			}
		}
		catch (IOException e)
		{
			System.out.println("noob");
			return;
		}
		/*
		 * while (scanner.hasNext())
		{	
			String word = scanner.next();
			String nextLine = scanner.nextLine();
			System.out.println(word);
			wordMap.put(word, populateFloat(dimensionSize, nextLine));
		}
		scanner.close();
		 */
		
	}
	
	public Float64Vector populateFloat(int dimensionSize, String line)
	{
 		double [] numbers = new double [dimensionSize];
		String [] strings = line.split(" ");
		for (int i = 0; i < dimensionSize; i++)
		{
			if (i + 1 > strings.length)
			{
				numbers [i] = 0.0;
				continue;
			}
			numbers [i] = Double.parseDouble(strings [i]);
		}
		return Float64Vector.valueOf(numbers);
	}
	
	public List<String> guess(List<String> wordList, String clue, int num)
	{
		//ArrayList<Integer> numbers = new ArrayList<Integer>();
		 List<Double> distances = wordList.stream().map(word -> 
		// 	wordMap.get(clue.toLowerCase()).minus(wordMap.get(word.toLowerCase())).normValue()
		 	cosDistance(wordMap.get(clue.toLowerCase()), wordMap.get(word.toLowerCase()))
		).collect(Collectors.toList());
		
		List<String> sorted = IntStream.range(0,wordList.size()).boxed().sorted((i, j) -> 
			Double.compare(distances.get(i), distances.get(j))
		).map(i -> wordList.get(i)).collect(Collectors.toList());
		return sorted.subList(0, num);
	}
	
	public double cosDistance(Float64Vector a, Float64Vector b)
	{
		return 1 - dot(a, b) / (a.normValue() * b.normValue());
	}
	
	public double dot(Float64Vector a, Float64Vector b)
	{
		double result = 0;
		for (int i = 0; i < a.getDimension(); i++)
		{
			result += a.getValue(i) * b.getValue(i);
		}
		return result;
	}
	
	private HashMap<String, Float64Vector> wordMap = new HashMap<String, Float64Vector>();
}
