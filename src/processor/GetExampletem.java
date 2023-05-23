/**
 * 
 */
package processor;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author michel
 *
 */
public class GetExampletem extends Processor {

	public GetExampletem(HttpServletRequest request, ServletContext servletContext) {
		super(request, servletContext);
	}

	@Override
	public String getContent() throws Exception{
		File fls = setupFile();
		String retour = "";
		if( fls != null ) {
			Scanner myScanner = new Scanner(fls);
			retour = myScanner.useDelimiter("\\A").next(); 
			myScanner.close();
		} else {
			if(params.item.contains("image")){
				retour = getImagePath();
			}
			else{
				retour = "Protocol " + params.item + " not supported";
			}
		}
		return retour;

	}
	
	@Override
	public String getNiceContent() throws Exception{
		File fls = setupFile();
		String retour = "";
		if( fls != null ) {
			Scanner myScanner = new Scanner(fls);
			retour = myScanner.useDelimiter("\\A").next();
			myScanner.close();
		} else {
			if(params.item.contains("image")){
				retour = getImagePath();
			}
			else{
				retour = "Protocol " + params.item + " not supported";
			}
		}
		return retour;

	}

	@Override
	public String getContentType() {
		return "text/plain";
	}

	public String getImagePath(){
		String myfile = servletContext.getRealPath(ROOTDIR);
		File folder = new File(myfile + "/" + params.protocol + "/" + params.useCase);
		String[] listOfFiles = folder.list();

		String retour = null;

		if(listOfFiles != null){
			for(String file : listOfFiles){
				if(file.substring(0, file.indexOf('.')).equals(params.item)){
					retour = myfile + "/" + params.protocol + "/" + params.useCase+ "/" + file;
				}
			}
		}

		return retour;
	}

	public File setupFile(){
		String myfile = servletContext.getRealPath(ROOTDIR);
		String[] listOfFiles = new File(myfile + "/" + params.protocol + "/" + params.useCase).list();
		File retour = null;

		if(listOfFiles == null){
			return retour;
		}
		for(String file : listOfFiles){
			if(file.contains(".")) {
				if (file.substring(0, file.indexOf('.')).equals(params.item) && !file.contains("image")) {
					retour = new File(myfile + "/" + params.protocol + "/" + params.useCase + "/" + file);
				}
			}
			else{
				String[] listOfStepFiles = new File(myfile + "/" + params.protocol + "/" + params.useCase + "/" + file).list();
				if(listOfStepFiles == null){
					continue;
				}
				for(String stepFile : listOfStepFiles){
					if(stepFile.contains(".")){
						if (stepFile.substring(0, stepFile.indexOf('.')).equals(params.item_step)) {
							retour = new File(myfile + "/" + params.protocol + "/" + params.useCase + "/" + file + "/" + stepFile);
						}
					}
				}
			}
		}

		return retour;
	}
}
