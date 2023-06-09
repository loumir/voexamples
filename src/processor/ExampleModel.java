package processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExampleModel {
	public final String exampleDir;
	public final File exampleFile;
	public final String exampleName;
	public final Request params;
	
	ExampleModel(String baseDir , Request params) throws Exception{
		this.exampleDir = baseDir+ "/" + params.protocol+ "/" + params.useCase;
		this.exampleFile = new File(this.exampleDir);
		if( !exampleFile.exists()) {
			throw new Exception("Directory " + this.exampleDir + " does not exist");
		}
		if( !exampleFile.isDirectory()) {
			throw new Exception(this.exampleDir + " is not 	a directory");
		}
		this.exampleName = this.exampleFile.getName();
		this.params = params;
	}
	
	public String getName()throws Exception {
		return this.exampleName;
	}
	public String getTitle() throws Exception{
		return getItem("title.txt");
	}
	public String getDescription() throws Exception {
		return getItem("description.txt");
	}
	public String getQuery()throws Exception  {
		return getItem("query.txt");
	}
	public String getUsage() throws Exception {
		return getItem("usage.txt");
	}

	public String getImage() throws Exception {
		return getItem("image.txt");
	}

	public List<String> getIndex(String subpath) throws Exception {
		List<String> index = new ArrayList<>();

		File f = new File(exampleDir + "/index.txt");
		if(subpath != null){
			f = new File(exampleDir + "/" + subpath + "/index.txt");
		}
		if( !f.exists()) {
			return null;
		}
		BufferedReader br = new BufferedReader(new FileReader(f));
		String boeuf;
		while( (boeuf =  br.readLine()) != null ) {
			index.add(boeuf);
		}
		br.close();

		return index;
	}

	public String getByName(String name) throws Exception {
		if(name.contains("png") || name.contains("jpg") || name.contains("jpeg")) {
			return "";
		}
		return getItem(name);
	}

	public boolean match(String filter) throws Exception {
		Pattern pattern = Pattern.compile(".*" + filter + ".*", Pattern.CASE_INSENSITIVE);
		System.out.println("@@@@@@@@@@@ " + this.getTitle() + " " + filter + " " + this.getTitle().indexOf(filter));
		Matcher matcher= pattern.matcher(this.getTitle());
		return matcher.matches();
	}
	private String getItem(String itemName) throws Exception {
		File f = new File(exampleDir + "/" + itemName);
		if( !f.exists() || f.isDirectory()) {
			return "";
		}
		BufferedReader br = new BufferedReader(new FileReader(f));
		String boeuf, retour = "";
		while( (boeuf =  br.readLine()) != null ) {
			retour += boeuf + "\n";
		}
		br.close();
		return retour.trim();
	}
}
