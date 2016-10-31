package models;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.AbstractTableModel;

/**
 * The FilteredFilmListTableModel class serves as the background model for the
 * jtable view. The core attribute of this class is an ArrayList which holds all
 * the film objects in the current context and the column names for all the
 * columns.
 * 
 * @author Dong Huang 15920066
 */
@SuppressWarnings("serial")
public class FilteredFilmListTableModel extends AbstractTableModel {
	private String[] columnNames;
	private ArrayList<Film> filteredFilmList;

	/**
	 * The constructor of the FilteredFilmListTableModel class. It accepts an
	 * ArrayList to initialize its own attribute and sets default value for the
	 * column names.
	 * 
	 * @param An
	 *            ArrayList of the film.
	 * @return n/a
	 * @author Dong Huang 15920066
	 */
	public FilteredFilmListTableModel(ArrayList<Film> filteredFilmList) {
		this.filteredFilmList = filteredFilmList;
		columnNames = new String[] { "Title", "Release", "Genre", "Rating", "Director", "Cast" };
	}

	/**
	 * Get the ArrayList of the film objects.
	 * 
	 * @param void
	 * @return An ArrayList of the film objects.
	 * @author Dong Huang 15920066
	 */
	public ArrayList<Film> getFilteredFilmList() {
		return filteredFilmList;
	}

	/**
	 * Set the ArrayList of the film objects.
	 * 
	 * @param An
	 *            ArrayList of the film objects.
	 * @return void
	 * @author Dong Huang 15920066
	 */
	public void setFilteredFilmList(ArrayList<Film> filteredFilmList) {
		this.filteredFilmList = filteredFilmList;
	}

	/**
	 * Get the number of objects in the ArrayList.
	 * 
	 * @param void
	 * @return The number of objects in the ArrayList.
	 * @author Dong Huang 15920066
	 */
	@Override
	public int getRowCount() {
		return filteredFilmList.size();
	}

	/**
	 * Get the number of columns.
	 * 
	 * @param void
	 * @return The number of columns.
	 * @author Dong Huang 15920066
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/**
	 * Returns a string representation of the selected element in a film object.
	 * 
	 * @param The
	 *            row index and the column index.
	 * @return The string representation of the selected element in a film
	 *         object.
	 * @author Dong Huang 15920066
	 */
	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return filteredFilmList.get(rowIndex).getTitle();
		case 1:
			return String.valueOf(filteredFilmList.get(rowIndex).getReleaseYear());
		case 2:
			return filteredFilmList.get(rowIndex).getGenre().toString();
		case 3:
			return String.valueOf(filteredFilmList.get(rowIndex).getRating());
		case 4:
			return filteredFilmList.get(rowIndex).getDirector();
		case 5:
			return Arrays.toString(filteredFilmList.get(rowIndex).getCasts()).replace("[", "").replace("]", "");
		}

		return null;
	}

	/**
	 * Get the string column name.
	 * 
	 * @param The
	 *            index of the column.
	 * @return The string column name.
	 * @author Dong Huang 15920066
	 */
	@Override
	public String getColumnName(int index) {
		return columnNames[index];
	}
}