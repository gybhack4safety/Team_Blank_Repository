import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

enum Classification { THREAT, DISCRIMINATION, HARASSMENT}


/** 
 * @author Matthew
 *   
 * Keyword.java
 * 
 * The keyword class contains a severity level(1-5), a classification (enums) for the keyword,
 * and the keyword itself, also known as offphrase or offensive phrase.
 * 
 * The keyword class can read and write to the local keyword database.
 * Through the methods createKeywordList and saveKeywordList.
 * 
 * New entries are made through the createEntry method.
 * 
 * This class uses the gson library to handle json the json file.
 */
public class Keyword {
	private Keyword(String offphrase) {
		this.offphrase = offphrase;
		this.setSeverity(1);
	}
	
	public String getWord() {
		return offphrase;
	}
	
	public Classification getClassification() {
		return classification;
	}
	
	public int getSeverity() {
		return severity;
	}
	
	public void setClassification(Classification s) {
		classification = s;
	}
	
	public void setSeverity(int s) {
		severity = s;
	}
	
	public static Keyword createEntry(String word, Classification c) {
		Keyword kw =  new Keyword(word);
		kw.setClassification(c);
		kw.setSeverity(1);
		return kw;
	}

	public static ArrayList<Keyword> createKeywordList(File f){
		if(f.exists()) {
			try (BufferedReader br = new BufferedReader(new FileReader(f));) {
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();
				Type type = new TypeToken<List<Keyword>>(){}.getType();
				return gson.fromJson(br, type);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return new ArrayList<Keyword>();
	}
	
	public static void saveKeywordList(File f, ArrayList<Keyword> kl){
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting();  
	    Gson gson = builder.create();  
	    
	    if(!f.exists()) {
	    	try {
	    		f.getParentFile().mkdir();
				f.createNewFile();
			} catch (IOException e) { e.printStackTrace(); }
	    }
	    
	    try (FileWriter fw = new FileWriter(f)) {
	    	 gson.toJson(kl, fw);
	         fw.close();
	    } catch (Exception e) { e.printStackTrace(); }
	}
	
	private Classification classification;
	private String offphrase;
	private int severity;
}