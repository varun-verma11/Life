package life;

import javax.swing.JPanel;

/**
 * This class is used to represent grid which is JPanel object. This consists
 * of all the cells for the grid.
 * 
 * @author varun
 *
 */
public class Grid extends JPanel implements Cloneable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The field grid is used to store a 2-D representation of cells which are 
	 * used to represent the grid.
	 */
	private Cell[][] grid = new Cell[Life.DEFAULT_ROW_SIZE][Life.DEFAULT_COLUMN_SIZE];

	/**
	 * The constructor initialises each of the cells in the grid field
	 * with a new cell 
	 */
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

	/**
	 * This method is used to get a cell at certain position in the grid
	 * 
	 * @param r : specifies the row 
	 * @param c : specifies the column
	 * @return  : returns a reference to the cell at given position
	 */
	public Cell getCellAtPosition(int r, int c)
	{
		return grid[r][c];
	}

	/**
	 * This method is used to obtain a clone of the current grid. The method
	 * performs a deep clone.
	 */
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

	/**
	 * This method is used to obtain the string representation of the current 
	 * grid
	 */
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