/**
 * 
 */
package processor;

import java.io.File;
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
		String myfile = servletContext.getRealPath(ROOTDIR);
		File fls = new File(myfile + "/" + params.protocol + "/" + params.useCase+ "/" + params.item + ".txt");
		String retour = "";
		if( fls.exists() ) {
			Scanner myScanner = new Scanner(fls);
			retour = myScanner.useDelimiter("\\A").next(); 
			myScanner.close();
		} else {
			retour = "Protocol " + params.item + " not supported";
		}
		return retour;

	}
	
	@Override
	public String getNiceContent() throws Exception{
		String myfile = servletContext.getRealPath(ROOTDIR);
		File fls = new File(myfile + "/" + params.protocol + "/" + params.useCase+ "/" + params.item + ".txt");
		String retour = "";
		if( fls.exists() ) {
			Scanner myScanner = new Scanner(fls);
			retour = myScanner.useDelimiter("\\A").next(); 
			myScanner.close();
		} else {
			retour = "Protocol " + params.item + " not supported";
		}
		return retour;

	}


	@Override
	public String getContentType() {
		return "text/plain";
	}

}
