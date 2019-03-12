

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class KeyBoard extends JFrame
{
	private static final long serialVersionUID = 1L;
	public KeyBoard(ArrayList<Integer> a, ArrayList<String> b, final Color r, final Color bl, final Color y)
	{
		BLUE = bl;
		RED = r;
		YELLOW = y;
		wordList = b;
		colorList = a;
		setTitle("Key");
		getContentPane().add(createPanel(), BorderLayout.CENTER);
	}
	
	public JPanel createPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 5));
		for (int i=0; i<=24; i++)
		{
			panel.add(createPanels(i));
		}
		return panel;
	}
	
	public JPanel createPanels(int num)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		JLabel label = new JLabel(wordList.get(num));
		label.setForeground(Color.white);
		
		panel.add(label);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		if (colorList.get(num) == 0)
		{
			panel.setBackground(RED);
		}
		else if (colorList.get(num) == 1)
		{
			panel.setBackground(BLUE);
		}
		else if (colorList.get(num) == 2)
		{
			panel.setBackground(YELLOW);
		}
		else
		{
			panel.setBackground(Color.black);
		}
		return panel;
	}
	private final Color YELLOW;
	private final Color BLUE;
	private final Color RED;
	private ArrayList<String> wordList = new ArrayList<String>(25);
	private ArrayList<Integer> colorList = new ArrayList<Integer>(25);
}
