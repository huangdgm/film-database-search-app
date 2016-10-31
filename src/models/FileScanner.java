package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The FileScanner class serves as the 'database builder' for the film database
 * search application. The main function of the FileScanner class is to read
 * from a predefined text file, which includes all the film data information
 * that the application needs.
 * 
 * This class parses the text file line by line and get out the strings that the
 * film database needs, such as the title, release year, rating, director, casts
 * and the story line. All these data are constructed into a number of Film
 * objects, and all the Film objects are collected together into an ArrayList.
 * This class also provides method to access this ArrayList.
 * 
 * @author Dong Huang 15920066
 */
public class FileScanner {
	private int numberOfFilms;
	private Scanner fileScan;

	private String title;
	private int releaseYear;
	private Genre genre;
	private int rating;
	private String director;
	private String[] casts;
	private String storyline = new String();

	private Film film;
	private ArrayList<Film> filmList = new ArrayList<Film>();

	/**
	 * The constructor of the FileScanner class. It use a File object as the
	 * input stream. As the file may be not exist, so this constructor may throw
	 * the FileNotFoundException..
	 * 
	 * @param File
	 *            file
	 * @return n/a
	 * @author Dong Huang 15920066
	 * @throws FileNotFoundException
	 */
	public FileScanner(File file) throws FileNotFoundException {
		fileScan = new Scanner(file);

		numberOfFilms = fileScan.nextInt();
		fileScan.nextLine();
		fileScan.nextLine();

		String[] titleAndReleaseYear;
		String nextLine;

		for (int i = 0; i < numberOfFilms; i++) {
			titleAndReleaseYear = fileScan.nextLine().split(" \\(");
			title = titleAndReleaseYear[0];
			releaseYear = Integer.parseInt(titleAndReleaseYear[1].replaceAll("[\\D]", ""));
			genre = Genre.valueOf(fileScan.nextLine().split(" \\| ")[2].split(", ")[0].toUpperCase());

			nextLine = fileScan.nextLine();
			storyline = "";

			while (!nextLine.startsWith("Director: ")) {
				storyline += nextLine;
				nextLine = fileScan.nextLine();
			}

			director = nextLine.split(": ")[1];
			casts = fileScan.nextLine().split(": ")[1].split(", ");

			rating = fileScan.nextInt();
			fileScan.nextLine();
			fileScan.nextLine();

			film = new Film(title, releaseYear, genre, rating, director, casts, storyline);
			filmList.add(film);
		}
	}

	/**
	 * The getFilmList() methods can be used to return the film list, which
	 * consists of a number of Film objects that are read from the text file.
	 * 
	 * @param void
	 * @return ArrayList<Film>
	 * @author Dong Huang 15920066
	 */
	public ArrayList<Film> getFilmList() {
		return filmList;
	}
}
