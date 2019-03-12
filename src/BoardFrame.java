
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BoardFrame extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BoardFrame()
	{
		Words words = new Words();
		wordList = words.generateList();
		colorList = words.setBoard();
		deathList = words.setDeathBoard();
		blueLeft = words.getBlueNumber();
		redLeft = words.getRedNumber();
		setTitle("Codenames");
		getContentPane().add(createMainPanel(), BorderLayout.CENTER);
		getContentPane().add(createBottomPanel(), BorderLayout.SOUTH);
	}
	
	public JPanel createMainPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 5));
		for (int i = 0; i<=24; i++)
		{
			panel.add(createButton(wordList.get(i), i));
		}
	//	modifyButtons();
		return panel;
	}
	
	public JButton createButton(String word, int i)
	{
		JButton button = new JButton(word);
		button.setBackground(Color.white);
		buttonList.add(button);
		class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				modifyButtons(i);
			}
		}
		ActionListener listener = new ButtonListener();
		button.addActionListener(listener);
		return button;
	}
	
	public JPanel createBottomPanel()
	{
		JPanel panel = new JPanel();
		JButton sMaster = new JButton("Key");
		blueRemaining = new JLabel("Blue: " + blueLeft);
		redRemaining = new JLabel("Red: " + redLeft);
		JButton ai = new JButton("AI");
		class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				JOptionPane.showMessageDialog(new JFrame(), "Please wait.");
				try {
					AI ai = new AI();
					JFrame frame = AIFrame(ai);
					frame.setSize(50, 50);
					frame.setVisible(true);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		ActionListener listener = new ButtonListener();
		ai.addActionListener(listener);
		
		sMaster.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	JFrame frame = new KeyBoard(colorList, wordList, RED, BLUE, YELLOW);
	     		frame.setSize(400, 400);
	     		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     		//frame.pack();
	     		frame.setVisible(true);
	         }          
	      });
		
		panel.add(blueRemaining);
		panel.add(ai);
		panel.add(sMaster);
		panel.add(redRemaining);
		return panel;
	}
	
	public JFrame AIFrame(AI ai)
	{
		JFrame frame = new JFrame();
	//	JPanel panel = new JPanel();
		JButton button = new JButton("Ask AI");
	//	JLabel label = new JLabel();
		
		class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				AI2 ai2 = new AI2(wordList);
				String clue = JOptionPane.showInputDialog("Give word: ");
				int number = Integer.parseInt(JOptionPane.showInputDialog("Give number: "));
				Stream<Integer> intList = ai.guess(wordList.stream().filter(word -> 
					isClicked(wordList.indexOf(word))).collect(Collectors.toList()
				), clue, number).stream().map(word -> wordList.indexOf(word));
				//intList.forEach(i -> modifyButtons(i));
				intList.forEach(i -> AIList += " " + wordList.get(i));
				
				JOptionPane.showMessageDialog(new JFrame(), "AI 1: " + AIList + "\n" + ai2.checkWord(clue, number));
			}
		}
		ActionListener listener = new ButtonListener();
		button.addActionListener(listener);
		//panel.add(button);	
		//panel.add(label);
		frame.add(button);
		return frame;
	}

	public void modifyButtons(int i)
	{
	//for (int i=0; i<=24; i++)
		{
			if (colorList.get(i) == 0)
			{
				buttonList.get(i).setBackground(RED);
				redLeft--;
				redRemaining.setText("Red: " + redLeft);
			}
			else if (colorList.get(i) == 1)
			{
				buttonList.get(i).setBackground(BLUE);
				blueLeft--;
				blueRemaining.setText("Blue: " + blueLeft);
			}
			else if (colorList.get(i) == 2)
			{
				buttonList.get(i).setBackground(YELLOW);
			}
			else
			{
				buttonList.get(i).setBackground(Color.black);
				JOptionPane.showMessageDialog(new JFrame(), "You lose!");
			}
			buttonList.get(i).setForeground(Color.white);
		}
	}
	
	public boolean isClicked(int i)
	{
		return buttonList.get(i).getBackground() == Color.white;
	}
	
	private String AIList = "";
	private ArrayList<JButton> buttonList = new ArrayList<JButton>(25);
	private ArrayList<String> wordList = new ArrayList<String>(25);
	private ArrayList<Integer> colorList = new ArrayList<Integer>(25); //RED 0, BLUE 1, YELLOW 2, death 3
	private int blueLeft;
	private int redLeft;
	private JLabel blueRemaining;
	private JLabel redRemaining;
	private final Color BLUE = new Color(0x1E88E5);
	private final Color YELLOW = new Color(0xFFEB3B);
	private final Color RED = new Color(0xDD2C00);	
	private int deathList;
}
