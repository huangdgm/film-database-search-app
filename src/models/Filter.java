package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * The Filter class serves as the filter to search for the film objects that
 * meet the criteria.
 * 
 * The film database search application implements the searching by the
 * following criteria:
 * 
 * 1. genre; 2. release year range; 3. star rating; 4. keyword search 4.1 search
 * by title 4.2 search by director 4.3 search by casts 4.4 search by story line
 * 4.5 search by any attributes
 * 
 * @author Dong Huang 15920066
 */
public class Filter {
	/**
	 * The filterByMultipleCriteria method is to return an ArrayList from the
	 * passed in filmList ArrayList that meets all the criteria which are
	 * specified by the parameter list.
	 * 
	 * @param ArrayList<Film>
	 *            filmList Genre genre Object releaseYearFrom Object
	 *            releaseYearTo boolean[] ratings String keywordSearchBy String
	 *            keyword
	 * @return A Film ArrayList that meets the criteria.
	 * @author Dong Huang 15920066
	 */
	public static ArrayList<Film> filterByMultipleCriteria(ArrayList<Film> filmList, Genre genre, Object releaseYearFrom, Object releaseYearTo, boolean[] ratings, String keywordSearchBy,
			String keyword) {

		ArrayList<ArrayList<Film>> list = new ArrayList<ArrayList<Film>>();

		ArrayList<Film> filmsFilteredByGenre;
		ArrayList<Film> filmsFilteredByReleaseYear;
		ArrayList<Film> filmsFilteredByRating;
		ArrayList<Film> filmsFilteredByKeyword;

		filmsFilteredByGenre = filterByGenre(filmList, genre);
		filmsFilteredByReleaseYear = filterByReleaseYear(filmList, releaseYearFrom, releaseYearTo);
		filmsFilteredByRating = filterByRating(filmList, ratings);
		filmsFilteredByKeyword = filterByKeyword(filmList, keywordSearchBy, keyword);

		list.add(filmList); // add the original full film list
		list.add(filmsFilteredByGenre);
		list.add(filmsFilteredByReleaseYear);
		list.add(filmsFilteredByRating);
		list.add(filmsFilteredByKeyword);

		return getCommonElements(list);
	}

	/**
	 * The filterByGenre method filters from the film objects that are specified
	 * by the filmList to return an ArrayList that meets the genre criteria.
	 * 
	 * @param ArrayList<Film>
	 *            film list ArrayList and the genre of the film.
	 * @return A Film ArrayList that meets the criteria.
	 * @author Dong Huang 15920066
	 */
	private static ArrayList<Film> filterByGenre(ArrayList<Film> filmList, Genre genre) {
		Iterator<Film> iterator = filmList.iterator();
		ArrayList<Film> filteredFilmList = new ArrayList<Film>();
		Film film = null;

		while (iterator.hasNext()) {
			film = iterator.next();

			if (genre == Genre.ANY) {
				filteredFilmList.addAll(filmList);
			} else if (film.getGenre() == genre) {
				filteredFilmList.add(film);
			}
		}

		return filteredFilmList;
	}

	/**
	 * The filterByReleaseYear method filters from the film objects that are
	 * specified by the filmList to return an ArrayList that meets the release
	 * year criteria.
	 * 
	 * @param The
	 *            film list ArrayList and the release year range of the film.
	 * @return A Film ArrayList that meets the criteria.
	 * @author Dong Huang 15920066
	 */
	private static ArrayList<Film> filterByReleaseYear(ArrayList<Film> filmList, Object releaseYearFrom, Object releaseYearTo) {
		Iterator<Film> iterator = filmList.iterator();
		ArrayList<Film> filteredFilmList = new ArrayList<Film>();
		Film film = null;
		int releaseYear = -1;

		while (iterator.hasNext()) {
			film = iterator.next();
			releaseYear = film.getReleaseYear();

			// little tricky here! releaseYearFrom is of type Object, it points
			// to the same area in where the string "ANY" is stored.
			if (releaseYearFrom.equals("ANY")) {
				if (releaseYearTo.equals("ANY")) {
					filteredFilmList.addAll(filmList);
				} else if (releaseYearTo.equals("Before 1950")) {
					if (releaseYear < 1950) {
						filteredFilmList.add(film);
					}
				} else {
					if (releaseYear <= (Integer) releaseYearTo) {
						filteredFilmList.add(film);
					}
				}
			} else if (releaseYearFrom.equals("Before 1950")) {
				if (releaseYearTo.equals("ANY")) {
					filteredFilmList.addAll(filmList);
				} else if (releaseYearTo.equals("Before 1950")) {
					if (releaseYear < 1950) {
						filteredFilmList.add(film);
					}
				} else {
					if (releaseYear <= (Integer) releaseYearTo) {
						filteredFilmList.add(film);
					}
				}
			} else {
				if (releaseYearTo.equals("ANY")) {
					if (releaseYear >= (Integer) releaseYearFrom) {
						filteredFilmList.add(film);
					}
				} else if (releaseYearTo.equals("Before 1950")) {
					// do nothing
				} else {
					if (releaseYear >= (Integer) releaseYearFrom && releaseYear <= (Integer) releaseYearTo) {
						filteredFilmList.add(film);
					}
				}
			}
		}

		return filteredFilmList;
	}

	/**
	 * The filterByRating method filters from the film objects that are
	 * specified by the filmList to return an ArrayList that meets the rating
	 * criteria.
	 * 
	 * @param The
	 *            film list ArrayList and the rating of the film.
	 * @return A Film ArrayList that meets the criteria.
	 * @author Dong Huang 15920066
	 */
	private static ArrayList<Film> filterByRating(ArrayList<Film> filmList, boolean[] ratings) {
		Iterator<Film> iterator = filmList.iterator();
		ArrayList<Film> filteredFilmList = new ArrayList<Film>();
		Film film = null;
		int index = 0;

		while (iterator.hasNext()) {
			film = iterator.next();
			index = film.getRating() - 1;

			if (ratings[index] == true) {
				filteredFilmList.add(film);
			}
		}

		return filteredFilmList;
	}

	/**
	 * The filterByKeyword method filters from the film objects that are
	 * specified by the filmList to return an ArrayList that meets the keyword
	 * criteria.
	 * 
	 * @param The
	 *            film list ArrayList and the keyword of the film.
	 * @return A Film ArrayList that meets the criteria.
	 * @author Dong Huang 15920066
	 */
	private static ArrayList<Film> filterByKeyword(ArrayList<Film> filmList, String keywordSearchBy, String keyword) {
		Iterator<Film> iterator = filmList.iterator();
		ArrayList<Film> filteredFilmList = new ArrayList<Film>();
		Film film = null;

		switch (keywordSearchBy) {
		case "ANY":
			while (iterator.hasNext()) {
				film = iterator.next();

				if (film.toString().toUpperCase().contains(keyword.toUpperCase())) {
					filteredFilmList.add(film);
				}
			}

			break;
		case "Title":
			while (iterator.hasNext()) {
				film = iterator.next();

				if (film.getTitle().toUpperCase().contains(keyword.toUpperCase())) {
					filteredFilmList.add(film);
				}
			}

			break;
		case "Director":
			while (iterator.hasNext()) {
				film = iterator.next();

				if (film.getDirector().toUpperCase().contains(keyword.toUpperCase())) {
					filteredFilmList.add(film);
				}
			}

			break;
		case "Casts":
			while (iterator.hasNext()) {
				film = iterator.next();

				if (Arrays.toString(film.getCasts()).toUpperCase().contains(keyword.toUpperCase())) {
					filteredFilmList.add(film);
				}
			}

			break;
		case "Storyline":
			while (iterator.hasNext()) {
				film = iterator.next();

				if (film.getStoryline().toUpperCase().contains(keyword.toUpperCase())) {
					filteredFilmList.add(film);
				}
			}

			break;
		}

		return filteredFilmList;
	}

	/**
	 * The getCommonElements methods implements the function of getting the
	 * common elements from a number of ArrayList<Film>.
	 * 
	 * @param An
	 *            ArrayList<ArrayList<Film>> of an ArrayList<Film>.
	 * @return A common ArrayList<Film>
	 * @author Dong Huang 15920066
	 */
	private static ArrayList<Film> getCommonElements(ArrayList<ArrayList<Film>> arrayListOfArrayList) {

		ArrayList<Film> common = new ArrayList<Film>();

		if (!arrayListOfArrayList.isEmpty()) {
			Iterator<ArrayList<Film>> it = arrayListOfArrayList.iterator();
			common.addAll(it.next());

			while (it.hasNext()) {
				common.retainAll(it.next());
			}
		}

		return common;
	}
}
