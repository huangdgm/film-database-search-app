package views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableColumn;
import models.Film;
import models.FilteredFilmListTableModel;
import models.Genre;

/**
 * The FilmDatabaseView class includes all the JComponent that are required by
 * the film database search application. It defines the look and feel of all the
 * JComponent, from JMenuBar, JTable to JComboBox and JTextArea. Also, it design
 * two update() methods, where method overloading occurs. One update method is
 * used to refresh the view of the JTable, while the other is used to refresh
 * the view of the JTextArea. Apart from the update methods, all other
 * JComponent are accompanied with the getter and the setter methods.
 * 
 * @author Dong Huang 15920066
 */
public class FilmDatabaseView extends JPanel {
	private static final long serialVersionUID = 1L;
	private FilteredFilmListTableModel filteredFilmListTableModel;

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu helpMenu;
	private JMenuItem openFileMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem authorMenuItem;
	private JMenuItem versionMenuItem;

	private JTable searchResultTable;
	private JTextField keywordSearchTextField;
	private JTextArea filmDetailInfoTextArea = new JTextArea();
	private JButton searchButton;
	private JButton addButton;
	private JButton removeButton;
	private JScrollPane searchResultTableScrollPane;
	private JScrollPane filmDetailInfoTextFieldScrollPane;
	private JComboBox<Genre> genreComboBox;
	private JComboBox<Object> releaseYearFromComboBox;

	private JComboBox<Object> releaseYearToComboBox;
	private JComboBox<String> keywordSearchByComboBox;
	private JCheckBox[] ratingCheckBox = new JCheckBox[5];
	private JButton resetSearchCriteriaButton;

	private JLabel genreLabel;
	private JLabel releaseYearLabel;
	private JLabel toLabel;
	private JLabel searchByLabel;
	private JLabel keywordsLabel;
	private JLabel[] ratingLabels = new JLabel[5];

	/**
	 * The constructor of the FilmDatabaseView class. When the FilmDatabaseView
	 * class is instantiated, all the JComponent are assigned with the specified
	 * attributes.
	 * 
	 * @param void
	 * @return n/a
	 * @author Dong Huang 15920066
	 */
	public FilmDatabaseView() {
		setLayout(null);

		Font menuBarfont = new Font("Arial", Font.BOLD, 12);
		Font menuItemfont = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12);

		menuBar = new JMenuBar();

		fileMenu = new JMenu("File");
		fileMenu.setFont(menuBarfont);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);

		helpMenu = new JMenu("Help");
		helpMenu.setFont(menuBarfont);
		helpMenu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(helpMenu);

		openFileMenuItem = new JMenuItem("Open file...", KeyEvent.VK_O);
		openFileMenuItem.setFont(menuItemfont);
		fileMenu.add(openFileMenuItem);

		exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_E);
		exitMenuItem.setFont(menuItemfont);
		fileMenu.add(exitMenuItem);

		authorMenuItem = new JMenuItem("About author", KeyEvent.VK_U);
		authorMenuItem.setFont(menuItemfont);
		helpMenu.add(authorMenuItem);

		versionMenuItem = new JMenuItem("About version", KeyEvent.VK_V);
		versionMenuItem.setFont(menuItemfont);
		helpMenu.add(versionMenuItem);

		menuBar.setLocation(0, 0);
		menuBar.setSize(70, 20);
		add(menuBar);

		genreLabel = new JLabel("Genre");
		genreLabel.setLocation(10, 30);
		genreLabel.setSize(40, 30);
		add(genreLabel);

		releaseYearLabel = new JLabel("Release Year");
		releaseYearLabel.setLocation(130, 30);
		releaseYearLabel.setSize(100, 30);
		add(releaseYearLabel);

		toLabel = new JLabel("To");
		toLabel.setLocation(238, 60);
		toLabel.setSize(20, 20);
		add(toLabel);

		toLabel = new JLabel("Storyline");
		toLabel.setLocation(1040, 120);
		toLabel.setSize(60, 20);
		add(toLabel);

		searchByLabel = new JLabel("Search By");
		searchByLabel.setLocation(650, 30);
		searchByLabel.setSize(60, 30);
		add(searchByLabel);

		keywordsLabel = new JLabel("Keywords");
		keywordsLabel.setLocation(760, 30);
		keywordsLabel.setSize(60, 30);
		add(keywordsLabel);

		genreComboBox = new JComboBox<Genre>(Genre.values());
		genreComboBox.setSelectedIndex(0);
		genreComboBox.setLocation(10, 60);
		genreComboBox.setSize(100, 20);
		add(genreComboBox);

		keywordSearchTextField = new JTextField();
		keywordSearchTextField.setLocation(760, 60);
		keywordSearchTextField.setSize(120, 20);
		add(keywordSearchTextField);

		searchButton = new JButton("Search");
		searchButton.setLocation(910, 40);
		searchButton.setSize(80, 40);
		add(searchButton);

		addButton = new JButton("Add");
		addButton.setLocation(1040, 779);
		addButton.setSize(80, 40);
		add(addButton);

		removeButton = new JButton("Remove");
		removeButton.setLocation(1140, 779);
		removeButton.setSize(80, 40);
		add(removeButton);

		String[] searchByKeywords = new String[] { "ANY", "Title", "Director", "Casts", "Storyline" };
		keywordSearchByComboBox = new JComboBox<String>(searchByKeywords);
		keywordSearchByComboBox.setSelectedIndex(0);
		keywordSearchByComboBox.setLocation(650, 60);
		keywordSearchByComboBox.setSize(100, 20);
		add(keywordSearchByComboBox);

		releaseYearFromComboBox = new JComboBox<Object>(getReleaseYearFromArray());
		releaseYearFromComboBox.insertItemAt("ANY", 0);
		releaseYearFromComboBox.insertItemAt("Before 1950", getReleaseYearFromArray().length + 1);
		releaseYearFromComboBox.setSelectedIndex(0);
		releaseYearFromComboBox.setLocation(130, 60);
		releaseYearFromComboBox.setSize(100, 20);
		add(releaseYearFromComboBox);

		releaseYearToComboBox = new JComboBox<Object>(getReleaseYearToArray());
		releaseYearToComboBox.insertItemAt("ANY", 0);
		releaseYearToComboBox.insertItemAt("Before 1950", getReleaseYearToArray().length + 1);
		releaseYearToComboBox.setSelectedIndex(0);
		releaseYearToComboBox.setLocation(260, 60);
		releaseYearToComboBox.setSize(100, 20);
		add(releaseYearToComboBox);

		resetSearchCriteriaButton = new JButton("Reset Search");
		resetSearchCriteriaButton.setSize(120, 40);
		resetSearchCriteriaButton.setLocation(1000, 40);
		resetSearchCriteriaButton.setSelected(false);
		resetSearchCriteriaButton.setMnemonic(KeyEvent.VK_R);
		add(resetSearchCriteriaButton);

		for (int i = 0; i < ratingCheckBox.length; i++) {
			ratingCheckBox[i] = new JCheckBox();
			ratingCheckBox[i].setSize(20, 20);
			ratingCheckBox[i].setLocation(380 + i * 50, 60);
			ratingCheckBox[i].setSelected(true);

			ratingLabels[i] = new JLabel((i + 1) + "Star");
			ratingLabels[i].setSize(40, 30);
			ratingLabels[i].setLocation(383 + i * 50, 30);
			add(ratingLabels[i]);

			add(ratingCheckBox[i]);
		}

		// Initial the search result table to empty.
		searchResultTable = new JTable(new FilteredFilmListTableModel(new ArrayList<Film>()));
		searchResultTable.setFillsViewportHeight(true);
		searchResultTableScrollPane = new JScrollPane(searchResultTable);
		searchResultTableScrollPane.setLocation(10, 120);
		searchResultTableScrollPane.setSize(1020, 700);
		searchResultTable.setPreferredScrollableViewportSize(new Dimension(500, 80));
		// enable row sorting functionality
		searchResultTable.setAutoCreateRowSorter(true);
		// Stops the JTable from recreating the TableColumnModel, which in turn
		// preserve the attributes of the TableColumn, such as the preferred
		// width of the columns.
		searchResultTable.setAutoCreateColumnsFromModel(false);

		TableColumn column = null;

		for (int i = 0; i < 6; i++) {
			column = searchResultTable.getColumnModel().getColumn(i);

			switch (i) {
			case 1:
			case 2:
			case 3:
				column.setPreferredWidth(50);

				break;
			case 0:
			case 4:
			case 5:
				column.setPreferredWidth(150);

				break;
			}
		}

		add(searchResultTableScrollPane);

		filmDetailInfoTextFieldScrollPane = new JScrollPane(filmDetailInfoTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		filmDetailInfoTextFieldScrollPane.setLocation(1040, 150);
		filmDetailInfoTextFieldScrollPane.setSize(400, 610);
		filmDetailInfoTextArea.setLineWrap(true);
		filmDetailInfoTextArea.setEditable(false);
		filmDetailInfoTextArea.setWrapStyleWord(true);
		add(filmDetailInfoTextFieldScrollPane);

		setSize(1100, 680);
	}

	/**
	 * Get the Integer array representation of the range of the release year
	 * from.
	 * 
	 * @param void
	 * @return The Integer array representation of the range of the release year
	 *         from.
	 * @author Dong Huang 15920066
	 */
	public static Integer[] getReleaseYearFromArray() {
		Integer[] releaseYearFromArray = new Integer[67];

		for (int i = 0; i < releaseYearFromArray.length; i++) {
			releaseYearFromArray[i] = 2016 - i;
		}

		return releaseYearFromArray;
	}

	/**
	 * Get the Integer array representation of the range of the release year to.
	 * 
	 * @param void
	 * @return The Integer array representation of the range of the release year
	 *         to.
	 * @author Dong Huang 15920066
	 */
	private Integer[] getReleaseYearToArray() {
		Integer[] getReleaseYearToArray = new Integer[67];

		for (int i = 0; i < getReleaseYearToArray.length; i++) {
			getReleaseYearToArray[i] = 2016 - i;
		}

		return getReleaseYearToArray;
	}

	public JTextField getKeywordSearchTextField() {
		return keywordSearchTextField;
	}

	public JTextArea getFilmDetailInfoTextField() {
		return filmDetailInfoTextArea;
	}

	public JButton getSearchButton() {
		return searchButton;
	}

	public JScrollPane getSearchResultListScrollPane() {
		return searchResultTableScrollPane;
	}

	public JScrollPane getFilmDetailInfoTextFieldScrollPane() {
		return filmDetailInfoTextFieldScrollPane;
	}

	public JComboBox<Genre> getGenreComboBox() {
		return genreComboBox;
	}

	public JComboBox<Object> getReleaseYearFromComboBox() {
		return releaseYearFromComboBox;
	}

	public JComboBox<Object> getReleaseYearToComboBox() {
		return releaseYearToComboBox;
	}

	public JCheckBox[] getRatingCheckBox() {
		return ratingCheckBox;
	}

	public JComboBox<String> getKeywordSearchByComboBox() {
		return keywordSearchByComboBox;
	}

	public JTable getSearchResultTable() {
		return searchResultTable;
	}

	public void setSearchResultTable(JTable searchResultTable) {
		this.searchResultTable = searchResultTable;
	}

	public JButton getAddButton() {
		return addButton;
	}

	public void setAddButton(JButton addButton) {
		this.addButton = addButton;
	}

	public JButton getRemoveButton() {
		return removeButton;
	}

	public void setRemoveButton(JButton removeButton) {
		this.removeButton = removeButton;
	}

	public JButton getResetSearchCriteriaButton() {
		return resetSearchCriteriaButton;
	}

	public void setResetSearchCriteriaButton(JButton resetSearchCriteriaCheckBox) {
		this.resetSearchCriteriaButton = resetSearchCriteriaCheckBox;
	}

	public JMenuItem getOpenFileMenuItem() {
		return openFileMenuItem;
	}

	public void setOpenFileMenuItem(JMenuItem openFileMenuItem) {
		this.openFileMenuItem = openFileMenuItem;
	}

	public JMenuItem getExitMenuItem() {
		return exitMenuItem;
	}

	public void setExitMenuItem(JMenuItem exitMenuItem) {
		this.exitMenuItem = exitMenuItem;
	}

	public JMenuItem getVersionMenuItem() {
		return versionMenuItem;
	}

	public void setVersionMenuItem(JMenuItem versionMenuItem) {
		this.versionMenuItem = versionMenuItem;
	}

	public JMenuItem getAuthorMenuItem() {
		return authorMenuItem;
	}

	public void setAuthorMenuItem(JMenuItem authorMenuItem) {
		this.authorMenuItem = authorMenuItem;
	}

	public FilteredFilmListTableModel getFilteredFilmListTableModel() {
		return filteredFilmListTableModel;
	}

	public void setFilteredFilmListTableModel(FilteredFilmListTableModel filteredFilmListTableModel) {
		this.filteredFilmListTableModel = filteredFilmListTableModel;
	}

	public void update() {
		searchResultTable.setModel(filteredFilmListTableModel);
	}

	public void update(String filmDetailInfo) {
		filmDetailInfoTextArea.setText(filmDetailInfo);
	}
}
