package controllers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import models.FileSaver;
import models.Film;
import models.FilmDatabaseModel;
import models.FilteredFilmListTableModel;
import models.Genre;
import views.FilmDatabaseView;

/**
 * The FilmDatabaseSearchApp class acts as a JFrame which also implements the
 * ActionLister interface. This class utilizes the models as well as the JPanel
 * which are defined in the views package and the models package, to instantiate
 * the models and the JPanel. It adds event listener to the JComponents if
 * needed.
 * 
 * Note: All the film data information are copied from http://www.imdb.com
 * 
 * @author Dong Huang 15920066
 */
@SuppressWarnings("serial")
public class FilmDatabaseSearchApp extends JFrame implements ActionListener {
	private FilmDatabaseView filmDatabaseView;

	private FilmDatabaseModel filmDatabaseModel;
	private FilteredFilmListTableModel filteredFilmListTableModel;

	private Film addedFilm;
	private File file = new File("database/filmList - original.txt");

	/**
	 * The constructor of the FilmDatabaseSearchApp class. It reads data from
	 * the text file, instantiates the model and the view, adds event listeners
	 * for JFrame, JButton, JComboBox, JCheckBox and so on, and sets the default
	 * attributes of the JFrame, such as the location, size and the default
	 * close operation.
	 * 
	 * @param title
	 *            The string title of the JFrame.
	 * @return n/a
	 * @author Dong Huang 15920066
	 */
	public FilmDatabaseSearchApp(String title) {
		super(title);

		try {
			filmDatabaseModel = new FilmDatabaseModel(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Initialize the filteredFilmListModel to contain no films
		filmDatabaseView = new FilmDatabaseView();
		filteredFilmListTableModel = new FilteredFilmListTableModel(new ArrayList<Film>());

		filmDatabaseView.getSearchButton().addActionListener(this);
		filmDatabaseView.getKeywordSearchTextField().addActionListener(this);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				handleExitPressed();
			}
		});

		filmDatabaseView.getExitMenuItem().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handleExitPressed();
			}
		});

		filmDatabaseView.getAuthorMenuItem().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(FilmDatabaseSearchApp.this, "Name:  Dong Huang\n\nStudent ID:  15920066\n\nhuangdgm@gmail.com\n\nAUT South Campus");
			}
		});

		filmDatabaseView.getVersionMenuItem().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(FilmDatabaseSearchApp.this, "Film Database Search Application\n\nv1.0");
			}
		});

		filmDatabaseView.getOpenFileMenuItem().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Create a file chooser
				final JFileChooser fc = new JFileChooser();
				// In response to a button click
				fc.setCurrentDirectory(new File("./database"));
				int returnVal = fc.showOpenDialog(FilmDatabaseSearchApp.this);
				File fileTry;

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fileTry = fc.getSelectedFile();

					try {
						if (!fileTry.getName().startsWith("filmList")) {
							throw new Exception("File format not match");
						}

						filmDatabaseModel = new FilmDatabaseModel(fileTry);
					} catch (Exception excep) {
						// If the file format not match, pop up a message box.
						JOptionPane.showMessageDialog(FilmDatabaseSearchApp.this, excep);
					}
				}
			}
		});

		filmDatabaseView.getReleaseYearToComboBox().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object releaseYearFromSelectedItem = filmDatabaseView.getReleaseYearFromComboBox().getSelectedItem();

				// If the range of the release year is invalid, then a message
				// window will be popped up.
				if (!(releaseYearFromSelectedItem.equals("ANY")) && !(releaseYearFromSelectedItem.equals("Before 1950"))) {
					if (filmDatabaseView.getReleaseYearToComboBox().getSelectedIndex() > filmDatabaseView.getReleaseYearFromComboBox().getSelectedIndex()) {
						JOptionPane.showMessageDialog(FilmDatabaseSearchApp.this, "Please select a value greater than or equal to " + (Integer) releaseYearFromSelectedItem);

						filmDatabaseView.getReleaseYearToComboBox().setSelectedIndex(0);
					}
				}
			}
		});

		filmDatabaseView.getRemoveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// If no film record is selected, do nothing, otherwise, remove
				// the selected rows.
				if (filmDatabaseView.getSearchResultTable().getSelectedRow() != -1) {
					removeSelectedRows(filmDatabaseView.getSearchResultTable());
				}
			}
		});

		filmDatabaseView.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String title = JOptionPane.showInputDialog("Please enter the title:", "film title");

				if (title != null) {
					Genre genre = (Genre) JOptionPane.showInputDialog(null, "Please select the genre", "add a new film", 1, null, Genre.values(), Genre.ACTION);

					if (genre != null) {
						String releaseYear;

						do {
							releaseYear = JOptionPane.showInputDialog("Please enter the release year:", "release year");
						} while (releaseYear != null && !releaseYear.matches("[0-9]+"));

						if (releaseYear != null) {
							String rating = (String) JOptionPane.showInputDialog(null, "Please select the rating", "add a new film", 1, null, new String[] { "1", "2", "3", "4", "5" }, null);

							if (rating != null) {
								String director = JOptionPane.showInputDialog("Please enter the director:", "director");

								if (director != null) {
									String[] casts = JOptionPane.showInputDialog("Please enter the casts: (split with comma with no whitespace)", "casts").split(",");

									if (casts != null) {
										addedFilm = new Film(title, Integer.parseInt(releaseYear), genre, Integer.parseInt(rating), director, casts, null);

										filmDatabaseModel.addFilm(addedFilm);
									}
								}
							}
						}
					}
				}
			}
		});

		filmDatabaseView.getSearchResultTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				ArrayList<Film> filmList = getFilteredFilmList();
				int indexOfSelectedRow;
				String filmDetailInfo;

				if (filmDatabaseView.getSearchResultTable().getSelectedRow() != -1) {
					indexOfSelectedRow = filmDatabaseView.getSearchResultTable().getSelectedRow();
					filmDetailInfo = filmList.get(filmDatabaseView.getSearchResultTable().convertRowIndexToModel(indexOfSelectedRow)).getStoryline();
					filmDatabaseView.update(filmDetailInfo);
				}
			}
		});

		filmDatabaseView.getResetSearchCriteriaButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				filmDatabaseView.getKeywordSearchByComboBox().setSelectedIndex(0);
				filmDatabaseView.getGenreComboBox().setSelectedIndex(0);
				filmDatabaseView.getReleaseYearFromComboBox().setSelectedIndex(0);
				filmDatabaseView.getReleaseYearToComboBox().setSelectedIndex(0);

				for (int i = 0; i < 5; i++) {
					filmDatabaseView.getRatingCheckBox()[i].setSelected(true);
				}

				filmDatabaseView.getKeywordSearchTextField().setText(null);
			}
		});

		setSize(1460, 860);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		// set the initial location on the screen in the middle horizontally and
		// vertically
		this.setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);

		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		add(filmDatabaseView);
	}

	/**
	 * Invoke the subsequent operation based on the user input.
	 * 
	 * @param void
	 * @return void
	 * @author Dong Huang 15920066
	 */
	private void handleExitPressed() {
		int response = JOptionPane.showConfirmDialog(FilmDatabaseSearchApp.this, "Save changes?", "Save database", JOptionPane.YES_NO_CANCEL_OPTION);

		switch (response) {
		case JOptionPane.YES_OPTION:
			FileSaver fs;

			fs = new FileSaver(filmDatabaseModel);

			fs.saveFile();

			JOptionPane.showMessageDialog(FilmDatabaseSearchApp.this, "The updated database is saved as:\n\n" + fs.getPathString() + "\n\nYou can load your own database under the above directory.");

			System.exit(0);
		case JOptionPane.NO_OPTION:
			System.exit(0);
		case JOptionPane.CANCEL_OPTION:
			break;
		}
	}

	/**
	 * Delete the selected row(s) in the view perspective of a JTable. It
	 * supports the following selection mode:
	 * 
	 * 1. single selection. 2. single interval selection, which means the
	 * selection is in one continuous block. 3. multiple interval selection, it
	 * means you can select as many as rows you are interested, even they are
	 * not in a continuous block.
	 * 
	 * What's more, after delete, the rows keep sorted as the view before the
	 * delete.
	 * 
	 * @param searchResultTable
	 * @return void
	 * @author Dong Huang 15920066
	 * 
	 */
	protected void removeSelectedRows(JTable searchResultTable) {
		/**
		 * At the beginning of the removing operation, a TableRowSorter object
		 * is retrieved from the current JTable. We use this object to get the
		 * SortKeys which will be used to restore the ordering of the JTable to
		 * the last view ordering.
		 */
		@SuppressWarnings("unchecked")
		TableRowSorter<FilteredFilmListTableModel> sorter = (TableRowSorter<FilteredFilmListTableModel>) searchResultTable.getRowSorter();
		List<? extends RowSorter.SortKey> sortKeys = sorter.getSortKeys();

		ArrayList<Film> filteredFilmList = getFilteredFilmList();
		Film filmToBeRemoved = null;

		int[] rowsSelectedInTheView = searchResultTable.getSelectedRows();
		int[] rowsSelectedInTheModel = new int[rowsSelectedInTheView.length];

		int shift = 0;
		int index = 0;

		for (int i : rowsSelectedInTheView) {
			rowsSelectedInTheModel[index++] = searchResultTable.convertRowIndexToModel(i);
		}

		Arrays.sort(rowsSelectedInTheModel);

		int theFirstSelectedIndex = rowsSelectedInTheModel[0];

		for (int i : rowsSelectedInTheModel) {
			if (i == theFirstSelectedIndex) {
				filmToBeRemoved = filteredFilmList.remove(i);
			} else {
				filmToBeRemoved = filteredFilmList.remove(i - shift);
			}

			filmDatabaseModel.deleteFilm(filmToBeRemoved);

			shift++;
		}

		filteredFilmListTableModel = new FilteredFilmListTableModel(filteredFilmList);

		filmDatabaseView.setFilteredFilmListTableModel(filteredFilmListTableModel);

		filmDatabaseView.update();

		/**
		 * Since a new TableRowSorter will be created each time the TableModel
		 * is updated, we need to get a reference to the new TableRowSorter
		 * object, and then apply the old SortKeys to the current TableRowSorter
		 * object to retain the order.
		 */
		@SuppressWarnings("unchecked")
		TableRowSorter<FilteredFilmListTableModel> sorterAfter = (TableRowSorter<FilteredFilmListTableModel>) searchResultTable.getRowSorter();
		sorterAfter.setSortKeys(sortKeys);
	}

	/**
	 * Get the genre of the film.
	 * 
	 * @param void
	 * @return The genre of the film.
	 * @author Dong Huang 15920066
	 */
	private Genre getGenreSelection() {
		JComboBox<Genre> genreComboBox;
		int selectedIndex;
		Genre genre;

		genreComboBox = filmDatabaseView.getGenreComboBox();
		selectedIndex = genreComboBox.getSelectedIndex();
		genre = genreComboBox.getItemAt(selectedIndex);

		return genre;
	}

	/**
	 * Get the release year from selsection.
	 * 
	 * @param void
	 * @return The release year from selection.
	 * @author Dong Huang 15920066
	 */
	private Object getReleaseYearFromSelection() {
		JComboBox<Object> releaseYearFromComboBox;
		int selectedIndex;
		Object releaseYearFrom;

		releaseYearFromComboBox = filmDatabaseView.getReleaseYearFromComboBox();
		selectedIndex = releaseYearFromComboBox.getSelectedIndex();
		releaseYearFrom = releaseYearFromComboBox.getItemAt(selectedIndex);

		return releaseYearFrom;
	}

	/**
	 * Get the relsease yar to selection.
	 * 
	 * @param void
	 * @return The release year to selection.
	 * @author Dong Huang 15920066
	 */
	private Object getReleaseYearToSelection() {
		JComboBox<Object> getReleaseYearToComboBox;
		int selectedIndex;
		Object releaseYearTo;

		getReleaseYearToComboBox = filmDatabaseView.getReleaseYearToComboBox();
		selectedIndex = getReleaseYearToComboBox.getSelectedIndex();
		releaseYearTo = getReleaseYearToComboBox.getItemAt(selectedIndex);

		return releaseYearTo;
	}

	/**
	 * Get the rating selection of the films.
	 * 
	 * @param void.
	 * @return The rating selection of the films.
	 * @author Dong Huang 15920066
	 */
	private boolean[] getRatingCheckBoxSelection() {
		JCheckBox[] checkBoxes = filmDatabaseView.getRatingCheckBox();
		boolean[] checkBoxSelections = new boolean[5];

		for (int i = 0; i < checkBoxes.length; i++) {
			checkBoxSelections[i] = checkBoxes[i].isSelected() ? true : false;
		}

		return checkBoxSelections;
	}

	/**
	 * Get he keyword search by selection.
	 * 
	 * @param void
	 * @return The keyword search by selection.
	 * @author Dong Huang 15920066
	 */
	private String getKeywordSearchBySelection() {
		JComboBox<String> keywordSearchByComboBox;
		int selectedIndex;
		String keywordSearchBy;

		keywordSearchByComboBox = filmDatabaseView.getKeywordSearchByComboBox();
		selectedIndex = keywordSearchByComboBox.getSelectedIndex();
		keywordSearchBy = keywordSearchByComboBox.getItemAt(selectedIndex);

		return keywordSearchBy;
	}

	/**
	 * Get the search keywords.
	 * 
	 * @param void
	 * @return The search keywords.
	 * @author Dong Huang 15920066
	 */
	private String getSearhKeywordEntered() {
		String searchKeyword;

		searchKeyword = filmDatabaseView.getKeywordSearchTextField().getText().trim();

		return searchKeyword;
	}

	/**
	 * Get the Film ArrayList that meets the criteria.
	 * 
	 * @param void
	 * @return The Film ArrayList that meets the criteria.
	 * @author Dong Huang 15920066
	 */
	private ArrayList<Film> getFilteredFilmList() {
		ArrayList<Film> filteredFilmList = models.Filter.filterByMultipleCriteria(filmDatabaseModel.getfilmList(), getGenreSelection(), getReleaseYearFromSelection(), getReleaseYearToSelection(),
				getRatingCheckBoxSelection(), getKeywordSearchBySelection(), getSearhKeywordEntered());

		return filteredFilmList;
	}

	/**
	 * Defines the action performed when the search button is pressed. Both the
	 * JTable and the JTextArea will be updated.
	 * 
	 * @param ActionEvent
	 * @return void
	 * @author Dong Huang 15920066
	 */
	public void actionPerformed(ActionEvent e) {
		FilteredFilmListTableModel filteredFilmListTableModel;
		ArrayList<Film> filteredFilmList = new ArrayList<Film>();

		filteredFilmList = getFilteredFilmList();
		filteredFilmListTableModel = new FilteredFilmListTableModel(filteredFilmList);
		filmDatabaseView.setFilteredFilmListTableModel(filteredFilmListTableModel);

		/**
		 * The update methods is overloading here. The first update refresh the
		 * JTable while the second update update the text area view. The film
		 * detail info text area is cleared each time the search button is
		 * pressed. Following the update method is the conditional statement
		 * which prompt the user with the message dialog window.
		 */
		filmDatabaseView.update();
		filmDatabaseView.update("");

		if (filteredFilmList.size() == 0) {
			JOptionPane.showMessageDialog(FilmDatabaseSearchApp.this, "No result found. Please try again.");
		}
	}

	/**
	 * The entry point of the application. It instantiated a
	 * FilmDatabaseSearchApp object and set visible to the end user.
	 * 
	 * @param args
	 * @return void
	 * @author Dong Huang 15920066
	 */
	public static void main(String[] args) {
		FilmDatabaseSearchApp app = new FilmDatabaseSearchApp("Film Database Search Application");

		app.setVisible(true);
	}
}