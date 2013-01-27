package life;

import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JApplet;

public class Life extends JApplet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_COLUMN_SIZE = 30;
	public static final int DEFAULT_ROW_SIZE = 30;
	private static final Colour dead_colour = Colour.GREY;
	private static int speed = 0;
	private Grid grid;
	private int number_of_turns = 0;
	private Display display;
	private boolean pause = false;

	public Life()
	{
		grid = new Grid();
		display = new Display(this);
	}

	public void set_pause(boolean bool)
	{
		pause = bool;
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
		grid.setLayout(new GridLayout(DEFAULT_ROW_SIZE, DEFAULT_COLUMN_SIZE));
		display.initialise_display();
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
		display.update_turn_label(number_of_turns);
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
						resurrect(r, c, get_birth_colour(r, c, previous));
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
		display.disable_controls_for_running();
		while (!pause && !isGameFinished())
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
		display.enable_controls_after_running();
	}

	private int numberOfLiveNeigbours(int row, int column, Grid current_grid)
	{
		int count = 0;
		int[] columns = { (column == 0) ? DEFAULT_COLUMN_SIZE - 1 : column,
				column, (column + 1) % (DEFAULT_COLUMN_SIZE - 1) };
		int[] rows = { (row == 0) ? DEFAULT_ROW_SIZE - 1 : row, row,
				(row + 1) % (DEFAULT_ROW_SIZE - 1) };
		for (int r : rows)
		{
			for (int c : columns)
			{
				if (!current_grid.getCellAtPosition(r, c).isDead())
					count++;
			}
		}
		return count;
	}

	private Colour get_birth_colour(int row, int column, Grid current_grid)
	{
		int[] columns = { (column == 0) ? DEFAULT_COLUMN_SIZE - 1 : column,
				column, (column + 1) % (DEFAULT_COLUMN_SIZE - 1) };
		int[] rows = { (row == 0) ? DEFAULT_ROW_SIZE - 1 : row, row,
				(row + 1) % (DEFAULT_ROW_SIZE - 1) };
		int green = 0;
		int red = 0;

		for (int r : rows)
		{
			for (int c : columns)
			{
				if (current_grid.getCellAtPosition(r, c).getColour() == Colour.RED)
				{
					red++;
				} else if (current_grid.getCellAtPosition(r, c).getColour() == Colour.GREEN)
				{
					green++;
				}
			}
		}

		return (green > red) ? Colour.GREEN : Colour.RED;
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

	public Grid get_grid()
	{
		return grid;
	}

}