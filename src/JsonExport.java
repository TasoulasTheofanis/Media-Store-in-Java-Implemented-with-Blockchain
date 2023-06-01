import java.io.FileWriter;	//Εισαγωγη απαραίτητων βιβλιοθηκων
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public class JsonExport 
{
	static void exportMediaListToJson(java.util.List<Media> list, String name) throws IOException{   //Η μεθοδος μας παιρνει ως παραμετρο μια λιστα και ενα ονομα για να ονομασουμε το αρχειο που θα δημιουργηθει
		Writer out = null;							//Δημιουργουμε ενα αντικειμενο Writer για να δημιουργησουμε το αρχειο εγγραφου που θελουμε
		Gson gson = new	GsonBuilder().create();		//Δημιουργουμε ενα αντικειμενο Gson για να γραφθουν τα χαρακτηριστικα (title, type, κτλ) με μορφη json
		try	{
			out = new FileWriter(name);				//Δημιουργουμε νεο αρχειο με ονομα name
			gson.toJson(list, out);					//και βαζουμε μεσα σε αυτο τα χαρακτηριστικα απο τα αντικειμενα media που βρισκονται μεσα τη λιστα list
		}
		catch (IOException e){
			System.out.printf("An error occured");	//Εαν προκυψει καποιο προβλημα εμφανιζουμε καταλληλο μηνυμα
		}
		finally {
			if (out != null){
				out.close();
			}
		}
	}

	static void exportJsonToMediaList(java.util.List<Media> list, String jsonfile) throws IOException{
		FileReader jsonFile = null;
		Gson gson = new	GsonBuilder().create();
		try{
			jsonFile = new FileReader(jsonfile);
			Type type = new TypeToken<java.util.List<Media> >(){}.getType();
			java.util.List<Media> newMediaList = gson.fromJson(jsonFile, type);

			for(Media mediaVariable : newMediaList){
				list.add(mediaVariable);
			}
		}
		catch (IOException e){
			System.out.printf("An error occured");
		}
		finally{
			if (jsonFile != null){
				jsonFile.close();
			}
		}
	}
	
	static void exportUserToJson(User newUser) throws Exception {
        Gson gson = new	GsonBuilder().create();	        
        FileReader fileReader = new FileReader("JSON/users.json");
        Type listType = new TypeToken<List<User>>() {}.getType();
        List<User> users = gson.fromJson(fileReader, listType);
        fileReader.close();
        users.add(newUser);
        // Write the updated list of users to the file
        FileWriter fileWriter = new FileWriter("JSON/users.json", false); // true to append
        gson.toJson(users, fileWriter);
        fileWriter.write("\n"); // add a newline character
        fileWriter.close();
    }
	
	static List<User> exportJsonToUser() throws Exception {
		Gson gson = new	GsonBuilder().create();
        FileReader fileReader = new FileReader("JSON/users.json");
        Type listType = new TypeToken<List<User>>() {}.getType();
        List<User> users = gson.fromJson(fileReader, listType);
        fileReader.close();
        return users;
    }
	
	static void exportBlocksToJson(List<Block> newBlocks) throws IOException{
		Writer out = null;
		Gson gson = new	GsonBuilder().create();
		try	{
			out = new FileWriter("JSON/blocks.json");
			gson.toJson(newBlocks, out);
		}
		catch (IOException e){
			System.out.printf("An error occured");
		}
		finally {
			if (out != null){
				out.close();
			}
		}
	}
	
	static void exportJsonToBlocks(List<Block> blockList) throws IOException{
		FileReader jsonFile = null;
		Gson gson = new	GsonBuilder().create();
		try{
			jsonFile = new FileReader("JSON/blocks.json");
			Type type = new TypeToken<List<Block>>(){}.getType();
			List<Block> newBlockList = gson.fromJson(jsonFile, type);
			for(Block blockVariable : newBlockList){
				blockList.add(blockVariable);
			}
		}
		catch (IOException e){
			System.out.printf("A system error occured on reading blocks.");
		}
		catch (NullPointerException e) {
			System.out.println("No blocks exists.");
		}
		finally{
			if (jsonFile != null){
				jsonFile.close();
			}
		}
	}	
	
	static void exportUsersToJson(List<User> newUsers) throws IOException{
		Writer out = null;
		Gson gson = new	GsonBuilder().create();
		try	{
			out = new FileWriter("JSON/users.json");
			gson.toJson(newUsers, out);
		}
		catch (IOException e){
			System.out.printf("An error occured on writing users.");
		}
		finally {
			if (out != null){
				out.close();
			}
		}
	}
}