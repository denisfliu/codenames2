

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.HirstStOnge;
import edu.cmu.lti.ws4j.impl.JiangConrath;
import edu.cmu.lti.ws4j.impl.LeacockChodorow;
import edu.cmu.lti.ws4j.impl.Lesk;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.Path;
import edu.cmu.lti.ws4j.impl.Resnik;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

public class AI2 
{
	public AI2(ArrayList<String> wordList)
	{
		words = wordList;
		addLists();
	}
	private static ILexicalDatabase db = new NictWordNet();
	private static RelatednessCalculator[] rcs = {
			new HirstStOnge(db), new LeacockChodorow(db), new Lesk(db),  new WuPalmer(db), 
			new Resnik(db), new JiangConrath(db), new Lin(db), new Path(db)
			};
	
	public void run(String word1) 
	{
		WS4JConfiguration.getInstance().setMFS(true);
	
		for (int j = 0; j < words.size(); j++ ) 
		{
			int i = 0;
			for (RelatednessCalculator rc : rcs )
			{
				double s = rc.calcRelatednessOfWords(word1, words.get(j));
				lists.get(i).put(j, s);
				i++;
			}	
		}
	}
	
	public void addLists()
	{
		for (int i = 0; i < 8; i++)
		{
			lists.add(new LinkedHashMap<Integer, Double>());
		}
	}
	
	public LinkedHashMap<Integer, Double> sortMap(LinkedHashMap<Integer, Double> hm)
	{
		List<Map.Entry<Integer, Double> > list = 
				new LinkedList<Map.Entry<Integer, Double> >(hm.entrySet()); 

			
			Collections.sort(list, new Comparator<Map.Entry<Integer, Double> >() { 
				public int compare(Map.Entry<Integer, Double> o1, 
								Map.Entry<Integer, Double> o2) 
				{ 
					return (o1.getValue()).compareTo(o2.getValue()); 
				} 
			}); 
			
			LinkedHashMap<Integer, Double> temp = new LinkedHashMap<Integer, Double>(); 
			for (Map.Entry<Integer, Double> aa : list) { 
				temp.put(aa.getKey(), aa.getValue()); 
			} 
			return temp; 
	}
	
	public void sortAll()
	{
		int i = 0;
		for (LinkedHashMap<Integer, Double> a : lists)
		{
			lists.set(i, sortMap(a));
			i++;
		}
	}
	
	public String getWords(LinkedHashMap<Integer, Double> a, int num)
	{
		String str = "";
		Integer[] ints = new Integer[a.size()];
		int i = 0;
		for (Map.Entry<Integer, Double> entry : a.entrySet())
		{
			ints[i] = entry.getKey();
			i++;
		}
		for (int j = ints.length - 1; j > ints.length - num - 1; j--)
		{
			str += " " + words.get(ints[j]);
		}
		return str;
	}
	public String checkWord(String word, int num)
	{
		run(word);
		sortAll();
		String results = "";
		int i = 1;
		for (LinkedHashMap<Integer, Double> a : lists)
		{
			results += i + ":\t" + getWords(a, num) + "\n";
			i++;
		}
		return results;
	}
	/*
	 * public static void main(String[] args) {
		long t0 = System.currentTimeMillis();
		run( "dog","cat" );
		long t1 = System.currentTimeMillis();
		System.out.println( "Done in "+(t1-t0)+" msec." );
	}
	 */
	private ArrayList<String> words = new ArrayList<String>();
	private ArrayList<LinkedHashMap<Integer, Double>> lists = new ArrayList<LinkedHashMap<Integer,Double>>();

	
}
