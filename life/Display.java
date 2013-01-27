package life;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import life.Life.Colour;

public class Display
{
	private static final int MAX_SPEED = 10;
	private static final int MIN_SPEED = 0;
	private static final int DEFAULT_SPEED = 0;

	private Life life_game;
	private JLabel turn_label;
	private JSlider speed_slider;
	private JButton clear;
	private JButton step;
	private JButton run;
	private JComboBox<String> saved_patterns;
	private String[] patterns_name = { "Block", "Beehive", "Loaf", "Boat",
			"Blinker", "Toad", "Beacon", "Pulsar", "Glider", "Lightweight" };

	public Display(Life life_game)
	{
		this.life_game = life_game;

	}

	public void initialise_display()
	{
		life_game.getContentPane().add(
				initialise_speed_slider(MIN_SPEED, MAX_SPEED, DEFAULT_SPEED),
				BorderLayout.EAST);
		life_game.getContentPane().add(life_game.get_grid(),
				BorderLayout.CENTER);

		JPanel header = new JPanel();
		add_turn_label(header);
		add_drop_down_for_patterns();
		header.add(saved_patterns, BorderLayout.EAST);
		life_game.getContentPane().add(header, BorderLayout.NORTH);

		JPanel buttons_footer = new JPanel();
		add_buttons_to_panel(buttons_footer);
		life_game.getContentPane().add(buttons_footer, BorderLayout.SOUTH);
	}

	public JSlider initialise_speed_slider(int min, int max, int def)
	{
		speed_slider = new JSlider(JSlider.VERTICAL, min, max, def);
		speed_slider.setMajorTickSpacing(2);
		speed_slider.setMinorTickSpacing(1);
		speed_slider.setPaintTicks(true);
		speed_slider.setPaintLabels(true);
		speed_slider.addChangeListener(new ChangeValue());
		return speed_slider;
	}

	public void add_turn_label(JPanel header)
	{
		turn_label = new JLabel("Turns: 0");
		header.add(turn_label, BorderLayout.WEST);
	}

	public void update_turn_label(int number_of_turns)
	{
		turn_label.setText("Turns: " + number_of_turns);
	}

	public void add_buttons_to_panel(JPanel buttons_footer)
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

	@SuppressWarnings("deprecation")
	public void disable_slider()
	{
		speed_slider.disable();
	}

	@SuppressWarnings("deprecation")
	public void enable_slider()
	{
		speed_slider.enable();
	}

	private class ChangeValue implements ChangeListener
	{

		public void stateChanged(final ChangeEvent expn)
		{
			final JSlider source = (JSlider) expn.getSource();
			if (!source.getValueIsAdjusting())
			{
				Life.set_speed(2000 / (int) source.getValue());
			}
		}
	}

	private class ButtonListener implements ActionListener
	{
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

	@SuppressWarnings("deprecation")
	public void disable_controls_for_running()
	{
		clear.disable();
		step.disable();
		life_game.get_grid().disable();
	}

	private void add_drop_down_for_patterns()
	{
		saved_patterns = new JComboBox<String>(patterns_name);
		saved_patterns.addActionListener(new PatternSelecterActionListener());
	}

	public void change_run_button(String text)
	{
		run.setText(text);
	}

	@SuppressWarnings("deprecation")
	public void enable_controls_after_running()
	{
		change_run_button("Run");
		clear.enable();
		step.enable();
		life_game.get_grid().enable();
	}

	private class PatternSelecterActionListener implements ActionListener
	{
		public void actionPerformed(final ActionEvent event)
		{
			@SuppressWarnings("unchecked")
			final JComboBox<String> source = (JComboBox<String>) event
					.getSource();
			String filepath = (String) source.getSelectedItem() + ".txt";
			Grid grid = new Grid();
			grid.setLayout(new GridLayout(Life.DEFAULT_ROW_SIZE,
					Life.DEFAULT_COLUMN_SIZE));
			try
			{
				Scanner scanner = new Scanner(new File(filepath));
				for (int r = 0; r < Life.DEFAULT_ROW_SIZE; r++)
				{
					String[] row = scanner.nextLine().split(" ");
					for (int c = 0; c < Life.DEFAULT_COLUMN_SIZE; c++)
					{
						grid.getCellAtPosition(r, c).setColour(
								map_string_to_colour(row[c]));
					}
				}
				scanner.close();
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}

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
	}
}