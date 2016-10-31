package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * The FilmDatabaseModel class serves as the background model for the
 * application view. The core attribute of this class is an ArrayList which
 * holds all the film objects in the current context. We can add or delete film
 * objects from this model, and the getter method for the film ArrayList is
 * available as well.
 * 
 * @author Dong Huang 15920066
 */
public class FilmDatabaseModel {
	private ArrayList<Film> filmList = new ArrayList<Film>();

	/**
	 * The constructor of the FilmDatabaseModel. It accepts a File object and
	 * utilize the FileScanner class to get an ArrayList of the film list. If
	 * the file is not found or null, a FileNotFoundException will be thrown
	 * out.
	 * 
	 * @param File
	 *            file
	 * @return n/a
	 * @author Dong Huang 15920066
	 * @throws FileNotFoundException
	 */
	public FilmDatabaseModel(File file) throws FileNotFoundException {
		setfilmList(new FileScanner(file).getFilmList());
	}

	/**
	 * Get the ArrayList of the film list.
	 * 
	 * @param void
	 * @return The ArrayList of the film list.
	 * @author Dong Huang 15920066
	 */
	public ArrayList<Film> getfilmList() {
		return filmList;
	}

	/**
	 * Set the ArrayList of the film list.
	 * 
	 * @param The
	 *            ArrayList of the film list.
	 * @return void
	 * @author Dong Huang 15920066
	 */
	public void setfilmList(ArrayList<Film> filmList) {
		this.filmList = filmList;
	}

	/**
	 * Add a new Film object to the current film ArrayList.
	 * 
	 * @param A
	 *            Film object.
	 * @return void
	 * @author Dong Huang 15920066
	 */
	public void addFilm(Film film) {
		filmList.add(film);
	}

	/**
	 * Delete a new Film object to the current film ArrayList.
	 * 
	 * @param A
	 *            Film object.
	 * @return void
	 * @author Dong Huang 15920066
	 */
	public void deleteFilm(Film film) {
		filmList.remove(film);
	}
}