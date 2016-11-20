package models;

import java.util.Arrays;

/**
 * The Film class serves as the fundamental unit, which consists of a variety of
 * elements that an actual film needs, such as the title, release year, rating,
 * director, casts and the story line. The film objects will be used in the
 * table model later.
 * 
 * @author Dong Huang 15920066
 */
public class Film implements Comparable<Film> {

	private String title;
	private int releaseYear;
	private Genre genre;
	private int rating;
	private String director;
	private String[] casts;
	private String storyline;

	/**
	 * The constructor of the Film class. It accepts all the elements to
	 * construct a film object.
	 * 
	 * @param
	 * @return
	 * @author Dong Huang 15920066
	 */
	public Film(String title, int releaseYear, Genre genre, int rating, String director, String[] casts, String storyline) {
		this.title = title;
		this.releaseYear = releaseYear;
		this.genre = genre;
		this.rating = rating;
		this.setDirector(director);
		this.setCasts(casts);
		this.setStoryline(storyline);
	}

	/**
	 * Define the natural sorting of the film if needed.
	 * 
	 * @param Film
	 *            film
	 * @return integer value which is used in sorting
	 * @author Dong Huang 15920066
	 */
	@Override
	public int compareTo(Film film) {
		return film.getReleaseYear() - this.getReleaseYear();
	}

	/**
	 * Get the rating of the film.
	 * 
	 * @param void
	 * @return The rating of the film
	 * @author Dong Huang 15920066
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Set the rating of the film.
	 * 
	 * @param rating
	 * @return void
	 * @author Dong Huang 15920066
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * Get the genre of the film.
	 * 
	 * @param void
	 * @return The genre of the film.
	 * @author Dong Huang 15920066
	 */
	public Genre getGenre() {
		return genre;
	}

	/**
	 * Set the genre of the film.
	 * 
	 * @param The
	 *            genre of the film.
	 * @return void
	 * @author Dong Huang 15920066
	 */
	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	/**
	 * Get the release year of the film.
	 * 
	 * @param void
	 * @return The release year of the film.
	 * @author Dong Huang 15920066
	 */
	public int getReleaseYear() {
		return releaseYear;
	}

	/**
	 * Set the release year of the film.
	 * 
	 * @param The
	 *            release year of the film.
	 * @return void
	 * @author Dong Huang 15920066
	 */
	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	/**
	 * Get the title of the film.
	 * 
	 * @param void
	 * @return The title of the film.
	 * @author Dong Huang 15920066
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of the film.
	 * 
	 * @param The
	 *            title of the film.
	 * @return void
	 * @author Dong Huang 15920066
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the director of the film.
	 * 
	 * @param void
	 * @return The director of the film.
	 * @author Dong Huang 15920066
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Set the director of the film.
	 * 
	 * @param The
	 *            director of the film.
	 * @return void
	 * @author Dong Huang 15920066
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * Get the string array representation of the casts of the film.
	 * 
	 * @param void
	 * @return The string array representation of the casts of the film.
	 * @author Dong Huang 15920066
	 */
	public String[] getCasts() {
		return casts;
	}

	/**
	 * Set string array representation of the casts of the film.
	 * 
	 * @param The
	 *            string array representation of the casts of the film.
	 * @return void
	 * @author Dong Huang 15920066
	 */
	public void setCasts(String[] casts) {
		this.casts = casts;
	}

	/**
	 * Get the story line of the film.
	 * 
	 * @param void
	 * @return The story line of the film.
	 * @author Dong Huang 15920066
	 */
	public String getStoryline() {
		return storyline;
	}

	/**
	 * Set the story line of the film.
	 * 
	 * @param The
	 *            story line of the film.
	 * @return void
	 * @author Dong Huang 15920066
	 */
	public void setStoryline(String storyline) {
		this.storyline = storyline;
	}

	/**
	 * Get the string representation of the film object. To conform to the
	 * format of the text file, here we use extra strings to construct the
	 * string representation of a film object.
	 * 
	 * @param void
	 * @return The string representation of the film.
	 * @author Dong Huang 15920066
	 */
	@Override
	public String toString() {
		return title + " (" + releaseYear + ")\n" + "X | Xh XXmin | " + genre + "\n" + storyline + "\n" + "Director: " + director + "\n" + "Stars: "
				+ Arrays.toString(casts).replace("[", "").replace("]", "") + "\n" + rating + "\n\n";
	}
}