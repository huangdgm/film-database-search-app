package models;

import static java.nio.file.StandardOpenOption.CREATE;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Accept the filmDatabaseModel and convert it to a string stream and then save
 * it to an external file.
 */
public class FileSaver {
	private FilmDatabaseModel filmDatabaseModel;
	private String pathString;

	public FileSaver(FilmDatabaseModel filmDatabaseModel) {
		this.filmDatabaseModel = filmDatabaseModel;
	}
	
	public String getPathString() {
		return pathString;
	}
	
	public void setPathString(String pathString) {
		this.pathString = pathString;
	}

	/**
	 * Save the string stream to a external text file.
	 * 
	 * @param void
	 * @return void
	 * @author Dong Huang 15920066
	 */
	public void saveFile() {
		String stringStream;
		byte[] byteData;

		stringStream = createStringStream();
		byteData = stringStream.getBytes();

		DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		this.setPathString("./database/filmList - " + dateFormatter.format(date) + ".txt");
		Path path = Paths.get(pathString);

		try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(path, CREATE))) {
			outputStream.write(byteData, 0, byteData.length);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	/**
	 * Convert the modified database to a String stream which consists all the
	 * film information.
	 * 
	 * @param void
	 * @return The string stream representation of all the film information.
	 * @author Dong Huang 15920066
	 */
	private String createStringStream() {
		List<Film> filmList = filmDatabaseModel.getfilmList();
		String stringStream = String.valueOf(filmList.size()) + "\n\n";

		for (int i = 0; i < filmList.size(); i++) {
			stringStream = stringStream.concat(filmList.get(i).toString());
		}

		return stringStream;
	}
}
