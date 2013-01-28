package life;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.Timer;

public class Life extends JApplet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_COLUMN_SIZE = 30;
	public static final int DEFAULT_ROW_SIZE = 30;
	private static final Colour dead_colour = Colour.GREY;
	private static int speed = 2000;
	private Grid grid;
	private int number_of_turns = 0;
	private Display display;
	private boolean paused = false;

	public Life()
	{
		grid = new Grid();
		display = new Display(this);
	}

	/**
	 * This method updates the pause field in this class.
	 * 
	 * @param bool
	 *            : specifies the new value for pause
	 */
	public void set_pause(boolean bool)
	{
		paused = bool;
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
		return grid.getCellAtPosition(DEFAULT_ROW_SIZE - x, y).getColour();
	}

	/**
	 * This method is used to update the speed of the running. i.e. the amount
	 * of gap between each step when run is invoked.
	 * 
	 * @param speed
	 *            : specifies the new speed
	 */
	public static void set_speed(int speed)
	{
		Life.speed = 2000 / speed;
	}

	// read the turn count
	public int readTurn()
	{
		return number_of_turns;
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
					if (number_of_live_neighbours < 2
							|| number_of_live_neighbours > 3)
					{
						kill(r, c);
					}
				}
			}

		}

		if (isGameFinished())
		{
			number_of_turns = 0;
			display.update_turn_label(number_of_turns);
		}
	}

	/**
	 * This method runs the simulation. It uses a timer and invokes the step
	 * method at the defined speed.
	 */
	public void run()
	{
		display.disable_controls_for_running();
		Timer timer = new Timer(speed, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				Timer timer = (Timer) event.getSource();
				timer.setDelay(speed);
				if (!paused && !isGameFinished())
				{

					step();
				} else
				{
					display.enable_controls_after_running();
					timer.stop();
				}
			}
		});
		timer.start();
	}

	/**
	 * This method returns the number of live neighbours cell at given position
	 * has.
	 * 
	 * @param row
	 *            : specifies the row for the given cell
	 * @param column
	 *            : specifies the column for the given cell
	 * @param current_grid
	 *            : specifies the current grid to use while calculating number
	 *            of live neighbours
	 * @return
	 */
	private int numberOfLiveNeigbours(int row, int column, Grid current_grid)
	{
		int count = 0;
		for (int r = -1; r <= 1; r++)
		{
			for (int c = -1; c <= 1; c++)
			{
				if (!(r == 0 && c == 0)
						&& !current_grid.getCellAtPosition(row + r, column + c)
								.isDead())
				{
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * This method returns the colour which has more cells in the neighbours of
	 * cell at given location
	 * 
	 * @param row
	 *            : specifies the row number for the cell
	 * @param column
	 *            : specifies the column number for the cell
	 * @param current_grid
	 *            : specifies the current grid to look at
	 * @return : returns Colour.RED iff more neighbours with Colour.Red nearby
	 *         and Colour.GREEN otherwise
	 */
	private Colour get_birth_colour(int row, int column, Grid current_grid)
	{
		int green = 0;
		int red = 0;

		for (int r = -1; r <= 1; r++)
		{
			for (int c = -1; c <= 1; c++)
			{
				if (!(r == 0 && c == 0))
				{
					Colour cell_colour = current_grid.getCellAtPosition(
							row + r, column + c).getColour();
					if (cell_colour == Colour.RED)
					{
						red++;
					} else if (cell_colour == Colour.GREEN)
					{
						green++;
					}
				}
			}
		}

		return (green > red) ? Colour.GREEN : Colour.RED;
	}

	/**
	 * This method is used to check whether the game has been finished or not
	 * @return : returns true iff game is over and false otherwise
	 */
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

	/**
	 * This method is used to get the grid for the current game
	 * @return
	 */
	public Grid get_grid()
	{
		return grid;
	}

	/**
	 * This method is used to set the number of turns
	 * @param i
	 */
	public void set_turns(int i)
	{
		number_of_turns = i;
	}

}