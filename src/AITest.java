

//import java.io.IOException;

//import java.util.List;
import java.util.Scanner;
//import java.util.stream.Collectors;

public class AITest 
{
	public static void main(String[] args)
	{
		//AI ai = new AI("src/test3.vec");
		//AI ai = new AI();
		Words words = new Words();
		System.out.println(words.generateList());
	//	List<Integer> list = ai.guess(words.returnList(), "animal", 7);
		//System.out.println(list.stream().map(i -> words.returnList().get(i)).collect(Collectors.toList()));
		Scanner input = new Scanner(System.in);
		String bla = "animal";
		while (bla != "")
		{
			System.out.print("enter a clue for 7: ");
			bla = input.next();
		//	System.out.println(ai.guess(words.returnList(), bla, 7).stream().map(i -> words.returnList().get(i)).collect(Collectors.toList()));
		}
		input.close();
	}
}
