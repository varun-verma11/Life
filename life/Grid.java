package life;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;

public class Grid extends JPanel implements Cloneable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cell[][] grid = new Cell[Life.DEFAULT_ROW_SIZE][Life.DEFAULT_COLUMN_SIZE];

	public Grid()
	{
		for (int r = 0; r < Life.DEFAULT_ROW_SIZE; r++)
		{
			for (int c = 0; c < Life.DEFAULT_COLUMN_SIZE; c++)
			{
				grid[r][c] = new Cell();
				this.add(grid[r][c]);
			}
		}
	}

	public Cell getCellAtPosition(int r, int c)
	{
		return grid[r][c];
	}

	@Override
	public Grid clone()
	{
		Grid game = new Grid();
		for (int r = 0; r < Life.DEFAULT_ROW_SIZE; r++)
		{
			for (int c = 0; c < Life.DEFAULT_COLUMN_SIZE; c++)
			{
				Cell cell = new Cell();
				cell.setColour(grid[r][c].getColour());
				game.grid[r][c] = cell;
			}
		}
		return game;
	}

	public void saveToFile(String filename)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					new java.io.File("../.")
					.getCanonicalPath().toString()
					+"/patterns/" + filename + ".txt"));
			writer.write(this.toString());
			writer.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String toString()
	{
		String grid_rep = "";

		for (int r = 0; r < Life.DEFAULT_ROW_SIZE; r++)
		{
			for (int c = 0; c < Life.DEFAULT_COLUMN_SIZE; c++)
			{
				grid_rep += getCellAtPosition(r, c).getColour().ordinal() + " ";
			}
			grid_rep += "\n";
		}

		return grid_rep;
	}

}