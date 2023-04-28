/**
 * 
 */
package processor;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author michel
 *
 */
public class GetProtocol extends Processor {

	public GetProtocol(HttpServletRequest request, ServletContext servletContext) {
		super(request, servletContext);
	}

	@Override
	public String getContent() throws Exception{
		String myfile = servletContext.getRealPath(ROOTDIR);
		System.out.println(myfile+ "/" + params.protocol);
		File fls = new File(myfile + "/" + params.protocol);
		
		String retour = getXhtmlHead() + "<body>" + "<h2>Examples for Protocol " + params.protocol + "</h2>";
		
		if( fls.exists() ) {
			retour += "<ul>\n";
			Set<String> sef = new TreeSet<String>();			
			for( String f: fls.list() ) {
				sef.add(f);
			}

			for( String f: sef ) {
				File fl = new File(fls.getAbsolutePath() + "/" + f);
				if( fl.isDirectory()){
					retour += "<li>" + XhtmlBuilder.getExampleAnchor(myfile, new Request(params.protocol, f)) + "</li>";
				}
			}
			return retour + "</ul></body></html>";

		} else {
			throw new Exception("Protocol " + params.protocol + " not supported");
		}
	}
	@Override
	public String getNiceContent() throws Exception{
		String myfile = servletContext.getRealPath(ROOTDIR);
		File fls = new File(myfile + "/" + params.protocol);
		
		if( fls.exists() ) {
			String retour = XhtmlBuilder.getNiceHead() + XhtmlBuilder.openNiceHeading(params.protocol + " Use Cases and Examples");
			retour += "<ul>\n";
			Set<String> sef = new TreeSet<String>();			
			for( String f: fls.list() ) {
				sef.add(f);
			}

			for( String f: sef ) {
				File fl = new File(fls.getAbsolutePath() + "/" + f);
				if( fl.isDirectory()){
					retour += "<li>" + XhtmlBuilder.getExampleAnchor(myfile, new Request(params.protocol, f)) + "</li>";
				}
			}
			return retour + "</ul>\n"+ XhtmlBuilder.closeNiceHeading() + XhtmlBuilder.getNiceFooter();

		} else {
			throw new Exception("Protocol " + params.protocol + " not supported");
		}
	}

	@Override
	public String getContentType() {
		return "text/html";
	}

}
