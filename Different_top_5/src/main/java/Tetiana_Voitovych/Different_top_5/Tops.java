package Tetiana_Voitovych.Different_top_5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;
import com.opencsv.CSVReader;

/**
*
* @author Tetiana Voitovych
* 2016
*/

public class Tops {

	List<String[]> myEntries;
	TreeMap<Double, String> avg;
	TreeMap<Double, String> users_amount;
	TreeMap<Double, String> similar_movie;
	String text;

	Tops(String filename) throws IOException {
		readCVSfile(filename);
		this.avg = new TreeMap<Double, String>();
		this.users_amount = new TreeMap<Double, String>();
		this.similar_movie = new TreeMap<Double, String>();
		calculate();
		this.text = "";
		avgTop5();
		rate4();
		userAmountTop5();
		similarToStarWar();
		write(text);
	}

	private void readCVSfile(String filename) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(filename));
		myEntries = reader.readAll();

	}

	private int whichNumber(String s) {
		if (s.equals("1"))
			return 1;
		if (s.equals("2"))
			return 2;
		if (s.equals("3"))
			return 3;
		if (s.equals("4"))
			return 4;
		if (s.equals("5"))
			return 5;
		return 0;
	}

	private void calculate() {
		double avgrating = 0;
		double sum = 0;
		double star_war_user_amout = 0;
		// averange rating
		for (int j = 1; j < myEntries.get(0).length; j++) {
			for (int i = 1; i < myEntries.size(); i++) {
				if (whichNumber(myEntries.get(i)[j]) != 0) {
					sum += whichNumber(myEntries.get(i)[j]);
					avgrating += 1;

				}
			}
			if (j == 1) {
				star_war_user_amout = avgrating;
			}
			users_amount.put(avgrating, myEntries.get(0)[j]);
			avgrating = sum / avgrating;
			avg.put(avgrating, myEntries.get(0)[j]);
			if (j != 1) {
				similar_movie
						.put(((star_war_user_amout + avgrating) / star_war_user_amout),
								myEntries.get(0)[j]);
			}
			avgrating = 0;
			sum = 0;
		}

	}

	private void avgTop5() {
		text+="Avg Top 5 \n";
		int N = 5;
        int i = 0;
        for (Entry<Double, String> entry : avg.descendingMap().entrySet()) {
            if (i++ < N) {
                text+=entry+"\n";
            }
        }
	}

	private void rate4() {
		text+="Rate >4 \n";
		if(avg.lastEntry().getKey()<4.0){ text+="rate >4 = 0% \n";}
        else{
      	  int rate=0;
      	  for(Entry<Double, String> entry : avg.entrySet()){
      		  
      		    if(entry.getKey()>=4.0){rate++;}
      		}  
      	 text+="rate >4 = "+(rate/avg.size())+" % \n";
        }

	}

	private void userAmountTop5() {
		text+="User Amount Top 5 \n";
		int N = 5;
        int i = 0;
        for (Entry<Double, String> entry : users_amount.descendingMap().entrySet()) {
            if (i++ < N) {
                text+=entry+"\n";
            }
        }
	}

	private void similarToStarWar() {
	    text+="Similar to Star War: Episode IV - A New Hope (1977)  Top 5 \n";
	    int N = 5;
        int i = 0;
        for (Entry<Double, String> entry : similar_movie.descendingMap().entrySet()) {
            if (i++ < N) {
                text+=entry+"\n";
            }
        }
	}

	private void write(String text) {
		String fileName = "Tops.txt";
		File file = new File(fileName);

		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			PrintWriter out = new PrintWriter(file.getAbsoluteFile());

			try {
				out.print(text);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
