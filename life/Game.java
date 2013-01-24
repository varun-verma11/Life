package life;

import life.Life.Colour;

public class Game implements Cloneable
{
	private static final int DEFAULT_COLUMN_SIZE = 30;
	private static final int DEFAULT_ROW_SIZE = 30;
	private static final Colour dead_colour = Colour.GREY;
	private Cell[][] grid = new Cell[DEFAULT_ROW_SIZE][DEFAULT_COLUMN_SIZE];

	public void clearGrid()
	{
		for (int r = 0; r < DEFAULT_ROW_SIZE; r++)
		{
			for (int c = 0; c < DEFAULT_COLUMN_SIZE; c++)
			{
				grid[r][c].setColour(dead_colour);
			}
		}
	}

	public void stepOne()
	{
		Game previous = this.clone();
		for (int r = 0; r < DEFAULT_ROW_SIZE; r++)
		{
			for (int c = 0; c < DEFAULT_COLUMN_SIZE; c++)
			{
				int number_of_live_neighbours = numberOfLiveNeigbours(r, c,
						previous.grid);
				if (previous.grid[r][c].isDead())
				{
					if (number_of_live_neighbours == 3)
					{
						// need to get the colour here
						// for now set to RED
						grid[r][c].setColour(Colour.RED);
					} else
					{
						if (number_of_live_neighbours!=2) {
							grid[r][c].setColour(Colour.GREY);
						}
					}
				}
			}
		}
	}

	private int numberOfLiveNeigbours(int row, int column, Cell[][] grid)
	{
		int count = 0;
		int start_row = (row > 0) ? row - 1 : row;
		int end_row = (row < DEFAULT_ROW_SIZE - 1) ? row + 1 : row;
		int start_column = (column < 0) ? column - 1 : column;
		int end_column = (row < DEFAULT_COLUMN_SIZE - 1) ? column + 1 : column;

		for (int r = start_row; r < end_row; r++)
		{
			for (int c = start_column; c < end_column; c++)
			{
				if (!grid[r][c].isDead())
					count++;
			}
		}
		return count;
	}

	public boolean isGameFinished()
	{
		for (int r = 0; r < DEFAULT_ROW_SIZE; r++)
		{
			for (int c = 0; c < DEFAULT_COLUMN_SIZE; c++)
			{
				if (!grid[r][c].isDead())
				{
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public Game clone()
	{
		Game game = new Game();
		for (int r = 0; r < DEFAULT_ROW_SIZE; r++)
		{
			for (int c = 0; c < DEFAULT_COLUMN_SIZE; c++)
			{
				game.grid[r][c] = grid[r][c];
			}
		}
		return game;
	}
	
	public void run() 
	{
		while(!isGameFinished())
		{
			stepOne();
		}
	}

}
