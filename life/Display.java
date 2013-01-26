package life;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Display
{
	
	private Life life_game;
	
	public Display(Life life_game)
	{
		this.life_game = life_game;
	}

	public JSlider initialise_speed_slider(JSlider speed_slider)
	{
		speed_slider.setMajorTickSpacing(2);
		speed_slider.setMinorTickSpacing(1);
		speed_slider.setPaintTicks(true);
		speed_slider.setPaintLabels(true);
		speed_slider.addChangeListener(new ChangeValue());
		return speed_slider;
	}

	public void add_buttons_to_panel(JPanel buttons_footer)
	{
		JButton clear = new JButton("Clear");
		JButton step = new JButton("Step");
		JButton run = new JButton("Run");
		buttons_footer.add(clear, BorderLayout.WEST);
		buttons_footer.add(step, BorderLayout.CENTER);
		buttons_footer.add(run, BorderLayout.EAST);
		clear.addActionListener(new ButtonListener(ButtonListener.CLEAR_BUTTON));
		step.addActionListener(new ButtonListener(ButtonListener.STEP_BUTTON));
		run.addActionListener(new ButtonListener(ButtonListener.RUN_BUTTON));
	}

	private class ChangeValue implements ChangeListener
	{

		public void stateChanged(final ChangeEvent expn)
		{
			final JSlider source = (JSlider) expn.getSource();
			if (!source.getValueIsAdjusting())
			{
				Life.set_speed(2000/(int) source.getValue());
			}
		}
	}

	private class ButtonListener implements ActionListener
	{
		private static final int RUN_BUTTON = 0;
		private static final int STEP_BUTTON = 1;
		private static final int CLEAR_BUTTON = 2;
		private int button;
		
		public ButtonListener(int button)
		{
			this.button = button;
		}
		
		public void actionPerformed(final ActionEvent event)
		{
			if (button==RUN_BUTTON)
			{
				life_game.run();
			} else if (button==STEP_BUTTON)
			{
				life_game.step();
			} else
			{
				life_game.clear();
			}
		}
	}
}
