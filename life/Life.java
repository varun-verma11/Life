package life;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Life extends JApplet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_COLUMN_SIZE = 30;
	public static final int DEFAULT_ROW_SIZE = 30;
	private static final int MAX_SPEED = 10;
	private static final int MIN_SPEED = 0;
	private static final int DEFAULT_SPEED = 0;
	private static final Colour dead_colour = Colour.GREY;
	private static int speed = 0;
	private JLabel turn_label;
	private Grid grid;
	private int number_of_turns = 0;

	public Life()
	{
		grid = new Grid();
	}

	// enum for managing the basic three colours of the cells
	// RED and GREEN cells are alive, GREY cells are dead
	public enum Colour
	{
		RED, GREEN, GREY;
	}

	// called by the html page to draw the applet
	public void paint(Graphics g)
	{
		// draw the applet
		grid.setLayout(new GridLayout(DEFAULT_ROW_SIZE, DEFAULT_COLUMN_SIZE));
		Display display = new Display(this);

//		JFrame frame = new JFrame();
//		frame.setLocationRelativeTo(null);
//		frame.setTitle("Game of Life");

		JSlider speed_slider = new JSlider(JSlider.VERTICAL, MIN_SPEED,
				MAX_SPEED, DEFAULT_SPEED);
		display.initialise_speed_slider(speed_slider);
		this.getContentPane().add(speed_slider, BorderLayout.EAST);

		this.getContentPane().add(grid, BorderLayout.CENTER);

		JPanel header = new JPanel();
		turn_label = new JLabel("Turns: " + this.number_of_turns);
		
		header.add(turn_label, BorderLayout.CENTER);
		this.getContentPane().add(header, BorderLayout.NORTH);

		JPanel buttons_footer = new JPanel();
		display.add_buttons_to_panel(buttons_footer);
		this.getContentPane().add(buttons_footer, BorderLayout.SOUTH);

//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(500, 500);
//		frame.setVisible(true);

		// g.drawString("ToDo: write a Life applet and a nice webpage", 25, 50);
	}

	/*
	 * The autotest will assume the following interfaces on the class life.Life
	 * You are free to implement these methods in any way you wish Note: we are
	 * assuming a standard coordinate system, that is with (0,0) referring to
	 * the bottom left cell, x being col no. and y being row no.
	 */

	// read the colour of the cell at coord (x,y)
	public Colour readCell(int x, int y)
	{
		return grid.getCellAtPosition(x, y).getColour();
	}

	public static void set_speed(int speed)
	{
		Life.speed = speed;
	}

	public static int get_speed()
	{
		return Life.speed;
	}

	// read the turn count
	public int readTurn()
	{
		return 0;
	}

	// kill the cell at coord (x,y)
	public void kill(int x, int y)
	{
		grid.getCellAtPosition(x, y).setColour(dead_colour);
	}

	// create a live cell with the specified colour at coord (x,y)
	public void resurrect(int x, int y, Colour c)
	{
		grid.getCellAtPosition(x, y).setColour(c);
	}

	// clear the board and reset the turn count to 0z
	public void clear()
	{
		for (int r = 0; r < Life.DEFAULT_ROW_SIZE; r++)
		{
			for (int c = 0; c < Life.DEFAULT_COLUMN_SIZE; c++)
			{
				grid.getCellAtPosition(r, c).setColour(dead_colour);
			}
		}
	}

	// step the simulation through one application of the rules and increment
	// the turn counter
	public void step()
	{
		number_of_turns++;
		turn_label.setText("Turns: " + number_of_turns);
		// update turns on the frame
		Grid previous = grid.clone();
		for (int r = 0; r < Life.DEFAULT_ROW_SIZE; r++)
		{
			for (int c = 0; c < Life.DEFAULT_COLUMN_SIZE; c++)
			{
				int number_of_live_neighbours = numberOfLiveNeigbours(r, c,
						previous);
				if (previous.getCellAtPosition(r, c).isDead())
				{
					if (number_of_live_neighbours == 3)
					{
						// need to get the colour here
						// for now set to RED
						resurrect(r, c, Colour.RED);
					}
				} else
				{
					if (number_of_live_neighbours != 2)
					{
						kill(r, c);
					}
				}
			}

		}
	}

	public void run()
	{
		while(!isGameFinished())
		{
			step();
			try
			{
				Thread.sleep(speed);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
				break;
			}
		}
		System.out.println(number_of_turns);
	}

	private int numberOfLiveNeigbours(int row, int column, Grid current_grid)
	{
		int count = 0;
		int start_row = (row > 0) ? row - 1 : row;
		int end_row = (row < DEFAULT_ROW_SIZE - 1) ? row + 1 : row;
		int start_column = (column < 0) ? column - 1 : column;
		int end_column = (row < DEFAULT_COLUMN_SIZE - 1) ? column + 1
				: column;

		for (int r = start_row; r < end_row; r++)
		{
			for (int c = start_column; c < end_column; c++)
			{
				if (!current_grid.getCellAtPosition(r, c).isDead())
					count++;
			}
		}
		return count;
	}

	private boolean isGameFinished()
	{
		for (int r = 0; r < Life.DEFAULT_ROW_SIZE; r++)
		{
			for (int c = 0; c < Life.DEFAULT_COLUMN_SIZE; c++)
			{
				if (!grid.getCellAtPosition(r, c).isDead())
				{
					return false;
				}
			}
		}
		return true;
	}

}