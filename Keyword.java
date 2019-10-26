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

enum Classification { BULLY, THREAT, DISCRIMINATION, HARASSMENT}
/** 
 * @author Matthew
 * 
 *
 */
public class Keyword {
	public Keyword(String offphrase) {
		this.offphrase = offphrase;
	}
	
	public String getWord() {
		return offphrase;
	}
	
	/*
	 */
	public Classification getClassification() {
		return classification;
	}
	
	public void setClassification(Classification s) {
		classification = s;
	}
	
	public void setSeverity(int s) {
		severity = s;
	}
	
	public Keyword createEntry(String word, Classification c) {
		Keyword kw =  new Keyword(word);
		kw.setClassification(c);
		kw.setSeverity(1);
		return kw;
	}

	public static ArrayList<Keyword> createKeywordList(File f){
		
		if(!f.getParentFile().exists())
			f.mkdirs();
		
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
	    	f.mkdirs();
	    	try {
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
	
	public static void main(String args[]) {
		
		File f = new File("./database/database.json");
		ArrayList<Keyword> list = createKeywordList(f);
		
		for(Keyword kw: list) {
			System.out.println(kw.getWord());
			kw.setSeverity(1000);
		}
		
		saveKeywordList(f, list);
	}
}