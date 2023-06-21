/**
 * 
 */
package processor;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author michel
 *
 */
public class GetIndex extends Processor {

	public GetIndex(HttpServletRequest request, ServletContext servletContext) {
		super(request, servletContext);
	}

	@Override
	public String getContent() throws Exception{
		String myfile = servletContext.getRealPath(ROOTDIR);
		File fls = new File(myfile);
		String retour = "";
		retour += "<h2>List of Documented Protocols or Models</h2><UL>\n";

		for( String f: fls.list() ) {
			File fl = new File(fls.getAbsolutePath() + "/" + f);
			if( fl.isDirectory()){
				retour += "<li><a href=\"" + DALIURL  + f + "\">" + f +  "</a></li>\n";
			}
		}
		return getXhtmlHead() + "<body><p>" + retour + "</p></body></html>";
	}
	@Override
	public String getNiceContent() throws Exception{
		String myfile = servletContext.getRealPath(ROOTDIR);
		File fls = new File(myfile);

		String retour = XhtmlBuilder.getNiceHead() + XhtmlBuilder.openNiceHeading("Protocols and Langages") + "\n<strong><ul class=\"contentlist\">";
		for( String f: fls.list() ) {
			File fl = new File(fls.getAbsolutePath() + "/" + f);
			if( fl.isDirectory()){
				retour += "<li><form action=\"" + SHOWURL  + f + "\">\n" +
						"<input type=\"submit\" value=\"" + f + "\"/>\n" +
						"</form></li>\n";
			}
		}
		return retour + "</ul></strong>\n"+ XhtmlBuilder.closeNiceHeading() + XhtmlBuilder.getNiceFooter();
	}

	@Override
	public String getContentType() {
		return "text/html";
	}

}
