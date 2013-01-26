package life;

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
	
}