/*Επωνυμο:	Τασούλας  
  Ονομα:	  Θεοφάνης
  ΑΜ:		 Π2015092
  Ημερομηνια: 28/1/2017
  Λειτουργια: Η παρακατω κλαση παρέχει τη μέθοδο exportMediaListToJson, με την οποια εξαγουμε τα χαρακτηριστικα των αντικειμενων media (title, type, κλπ) σε μορφη Json.
  */
import java.io.FileWriter;	//Εισαγωγη απαραίτητων βιβλιοθηκων
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class MediaExport 
{
	static void exportMediaListToJson(java.util.List<Media> list, String name) throws IOException   //Η μεθοδος μας παιρνει ως παραμετρο μια λιστα και ενα ονομα για να ονομασουμε το αρχειο που θα δημιουργηθει
	{
		Writer out = null;												//Δημιοργουμε ενα αντικειμενο Writer για να δημιουργησουμε το αρχειο εγγραφου που θελουμε
		Gson gson = new	GsonBuilder().create();							//Δημιοργουμε ενα αντικειμενο Gson για να γραφθουν τα χαρακτηριστικα (title, type, κτλ) με μορφη json
		try	
		{
			out = new FileWriter(name);									//Δημιοργουμε νεο αρχειο με ονομα name
			gson.toJson(list, out);										//και βαζουμε μεσα σε αυτο τα χαρακτηριστικα απο τα αντικειμενα media που βρισκονται μεσα τη λιστα list
		}
		catch (IOException e)
		{
			System.out.printf("An error occured");						//Εαν προκυψει καποιο προβλημα εμφανιζουμε καταλληλο μηνυμα
		}
		finally 
		{
			if (out != null)											//Εαν το αντικειμενο Writer εχει δημιουργησει το αρχειο που θελαμε
			{
				out.close();											//κλεινουμε το αρχειο
			}
		}
	}

	static void exportJsonToMediaList(java.util.List<Media> list, String jsonfile) throws IOException   //Η μεθοδος μας παιρνει ως παραμετρο μια λιστα και το ονομα για να ονομασουμε το αρχειο που θα δημιουργηθει
	{
		FileReader jsonFile = null;										//Δημιοργουμε ενα αντικειμενο Reader για να διαβασουμε το αρχειο json που θελουμε
		Gson gson = new	GsonBuilder().create();							//Δημιοργουμε ενα αντικειμενο Gson για να γραφθουν τα χαρακτηριστικα (title, type, κτλ) από το json αρχείο σε μια λίστα από media αρχεία
		try	
		{
			jsonFile = new FileReader(jsonfile);					//Δημιοργουμε νεο αντικειμενο με ονομα που διαβαζει το json αρχειο
			//gson.fromJson(jsonFile, list);								//και βαζουμε μεσα σε αυτο τα χαρακτηριστικα απο τα αντικειμενα media που βρισκονται μεσα τη λιστα list
			Type type = new TypeToken<java.util.List<Media> >(){}.getType();
			java.util.List<Media> newMediaList = gson.fromJson(jsonFile, type);

			for(Media mediaVariable : newMediaList) {
				list.add(mediaVariable);
			}
			
		}
		catch (IOException e)
		{
			System.out.printf("An error occured");						//Εαν προκυψει καποιο προβλημα εμφανιζουμε καταλληλο μηνυμα
		}
		finally 
		{
			if (jsonFile != null)											//Εαν το αντικειμενο Writer εχει δημιουργησει το αρχειο που θελαμε
			{
				jsonFile.close();											//κλεινουμε το αρχειο
			}
		}
	}	
}