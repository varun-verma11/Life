package life;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import life.Life.Colour;

/**
 * The Display class deals with all GUI interaction and setting up GUI for the
 * game of life
 * 
 * @author varun
 *
 */
public class Display
{
	/**
	 * The following three fields MAX_SPEED, MIN_SPEED, DEFAULT_SPEED are
	 * used for the speed slider.
	 */
	private static final int MAX_SPEED = 10;
	private static final int MIN_SPEED = 1;
	private static final int DEFAULT_SPEED = 1;

	/**
	 * The field life_game is a reference to the game for which this display
	 * is currently being used
	 */
	private Life life_game;
	
	/**
	 * The field turn_label is used to display the numbers of steps it has been
	 * from the initial state. This is set to 0 once all cells on the grid are 
	 * dead 
	 */
	private JLabel turn_label;
	
	/**
	 * The field speed_slider is the slider used to adjust the speed of the 
	 * run method
	 */
	private JSlider speed_slider;
	
	/**
	 * The field clear is the button used to clear the current grid
	 */
	private JButton clear;
	
	/**
	 * The field step is the button used to perform one transition on the
	 * grid
	 */
	private JButton step;
	
	/**
	 * The field run is the button used to run the simulation for starting
	 * from the initial state
	 */
	private JButton run;
	
	/**
	 * The saved_patterns is a drop-down menu which displays the patters user
	 * can run
	 */
	private JComboBox<String> saved_patterns;

	/**
	 * The field patters_name is an array of patterns which are currently saved
	 * on the server and user can select one of these.
	 */
	private String[] patterns_name = {};

	/**
	 * The constructor initialises the current display with the game it is being
	 * used for
	 * 
	 * @param life_game : specifies the game the display is being used for
	 */
	public Display(Life life_game)
	{
		this.life_game = life_game;

	}

	/**
	 * This method uses helper methods to initialises the display. i.e links all
	 * the objects on the GUI with there ActionListeners
	 */
	public void initialise_display()
	{
		initialise_speed_slider();
		life_game.getContentPane().add(speed_slider, BorderLayout.EAST);
		life_game.getContentPane().add(life_game.get_grid(),
				BorderLayout.CENTER);

		JPanel header = new JPanel();
		add_turn_label(header);
		add_drop_down_for_patterns();
		JPanel patterns_panel = new JPanel();
		patterns_panel.add(new JLabel("Patterns"), BorderLayout.WEST);
		patterns_panel.add(saved_patterns, BorderLayout.EAST);

		header.add(patterns_panel, BorderLayout.EAST);
		life_game.getContentPane().add(header, BorderLayout.NORTH);

		JPanel buttons_footer = new JPanel();
		add_buttons_to_panel(buttons_footer);
		life_game.getContentPane().add(buttons_footer, BorderLayout.SOUTH);
	}

	/**
	 * This method initialises the speed_slider with the default values for 
	 * the minimum, max and default speed
	 */
	private void initialise_speed_slider()
	{
		speed_slider = new JSlider(JSlider.VERTICAL, MIN_SPEED, MAX_SPEED, DEFAULT_SPEED);
		speed_slider.setMajorTickSpacing(1);
		speed_slider.setPaintTicks(true);
		speed_slider.setPaintLabels(true);
		speed_slider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent event)
			{
				final JSlider source = (JSlider) event.getSource();
				if (!source.getValueIsAdjusting())
				{
					Life.set_speed(2000 / (int) source.getValue());
				}
			}
		});
	}

	/**
	 * This method initialises the label to display the turns
	 * @param header : specifies where to display the turn label
	 */
	private void add_turn_label(JPanel header)
	{
		turn_label = new JLabel("Turns: 0");
		header.add(turn_label, BorderLayout.WEST);
	}

	/**
	 * This method is used to update the text in the turns label
	 * @param number_of_turns : specifies the number of turns to be displayed
	 */
	public void update_turn_label(int number_of_turns)
	{
		turn_label.setText("Turns: " + number_of_turns);
	}

	/**
	 * This method is used to add the function buttons to the given panel.
	 * This method also links up the button with a single instance of the
	 * 
	 * @param buttons_footer : specifies the panel to add the buttons
	 */
	private void add_buttons_to_panel(JPanel buttons_footer)
	{
		clear = new JButton("Clear");
		step = new JButton("Step");
		run = new JButton("Run");
		buttons_footer.add(clear, BorderLayout.WEST);
		buttons_footer.add(step, BorderLayout.CENTER);
		buttons_footer.add(run, BorderLayout.EAST);
		ButtonListener buttonListener = new ButtonListener();
		clear.addActionListener(buttonListener);
		step.addActionListener(buttonListener);
		run.addActionListener(buttonListener);
	}

	/**
	 * This method is used to disable the speed slider
	 */
	@SuppressWarnings("deprecation")
	public void disable_slider()
	{
		speed_slider.disable();
	}

	/**
	 * This method is used to enable the speed slider
	 */
	@SuppressWarnings("deprecation")
	public void enable_slider()
	{
		speed_slider.enable();
	}

	/**
	 * The class ButtonListener is used for ActionListener for the the function
	 * buttons clear, run and step. Each button uses single instance of this
	 * class
	 * 
	 * @author varun
	 *
	 */
	private class ButtonListener implements ActionListener
	{
		/**
		 * This function is called when any of the buttons on the applet are
		 * clicked. The method uses the text from the button to perform
		 * a specific action
		 */
		public void actionPerformed(final ActionEvent event)
		{
			final JButton source = (JButton) event.getSource();
			if (source.getText().equals("Step"))
			{
				life_game.step();
			} else if (source.getText().equals("Run"))
			{
				life_game.set_pause(false);
				change_run_button("Pause");
				life_game.run();
			} else if (source.getText().equals("Pause"))
			{
				change_run_button("Run");
				life_game.set_pause(true);
			} else
			{
				life_game.clear();
			}
		}
	}

	/**
	 * This method disables all the controls while the game of life is running.
	 */
	@SuppressWarnings("deprecation")
	public void disable_controls_for_running()
	{
		clear.disable();
		step.disable();
		life_game.get_grid().disable();
		life_game.get_grid().disable();
	}

	/**
	 * This method adds a drop down menu for the patterns which are saved
	 * already.
	 */
	private void add_drop_down_for_patterns()
	{
		try
		{
			URL url = new URL(life_game.getCodeBase(), "pattern_names.txt");
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					url.openStream()));
			patterns_name = bf.readLine().split(" ");
			saved_patterns = new JComboBox<String>(patterns_name);
			saved_patterns.addActionListener(new ActionListener()
			{
				/**
				 * This method loads the pattern on the current grid. It loads
				 * the file from the server on which this applet is currently 
				 * being run.
				 */
				@Override
				public void actionPerformed(ActionEvent event)
				{
					@SuppressWarnings("unchecked")
					final JComboBox<String> source = (JComboBox<String>) event
							.getSource();
					if (((String) source.getSelectedItem())
							.equals(patterns_name[0]))
					{
						return;
					}
					String filepath = (String) source.getSelectedItem()
							+ ".txt";
					Grid grid = life_game.get_grid();
					grid.setLayout(new GridLayout(Life.DEFAULT_ROW_SIZE,
							Life.DEFAULT_COLUMN_SIZE));
					try
					{
						URL url = new URL(life_game.getCodeBase(), "patterns/"
								+ filepath);
						BufferedReader bf = new BufferedReader(
								new InputStreamReader(url.openStream()));
						for (int r = 0; r < Life.DEFAULT_ROW_SIZE; r++)
						{
							String[] row = bf.readLine().split(" ");
							for (int c = 0; c < Life.DEFAULT_COLUMN_SIZE; c++)
							{
								grid.getCellAtPosition(r, c).setColour(
										map_string_to_colour(row[c]));
							}
						}
						bf.close();
					} catch (IOException exp)
					{
						exp.printStackTrace();
					}

				}

				/**
				 * This method maps a string to a colour. It is a helper method 
				 * for loading the grid. The string passed in integer value of 
				 * the ordinal value. 
				 * 
				 * @param string : specifies the ordinal value as a string for 
				 * the colour
				 * @return : returns a colour mapping for the given string
				 */
				private Colour map_string_to_colour(String string)
				{
					try
					{
						Integer ord = Integer.parseInt(string);
						return Life.Colour.values()[ord];
					} catch (NumberFormatException e)
					{
						return null;
					}
				}

			});
			bf.close();
		} catch (MalformedURLException e)
		{

		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to set the text of the given 
	 * @param text
	 */
	public void change_run_button(String text)
	{
		run.setText(text);
	}

	@SuppressWarnings("deprecation")
	public void enable_controls_after_running()
	{
		change_run_button("Run");
		clear.enable();
		life_game.get_grid().enable();
		step.enable();
		life_game.get_grid().enable();
	}
}