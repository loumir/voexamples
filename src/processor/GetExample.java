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
public class GetExample extends Processor {

	public GetExample(HttpServletRequest request, ServletContext servletContext) {
		super(request, servletContext);
	}

	@Override
	public String getContent() throws Exception {
		String myfile = servletContext.getRealPath(ROOTDIR);
		return getXhtmlHead() + "<body>" + XhtmlBuilder.getExampleDiv(myfile, this.params) + "</body></html>";
	}
	@Override
	public String getNiceContent() throws Exception {
		String myfile = servletContext.getRealPath(ROOTDIR);
		ExampleModel em = new ExampleModel(myfile, this.params);
		String retour = XhtmlBuilder.getNiceHead() + XhtmlBuilder.openNiceHeading("<a href='" + SHOWURL + params.protocol + "'>" + params.protocol + "</a>" /* + " [" + params.useCase + "]: "*/ + ": " + em.getTitle());
		retour += XhtmlBuilder.getExampleNiceDiv(em);
		return retour + "</ul>\n"+ XhtmlBuilder.closeNiceHeading() + XhtmlBuilder.getNiceFooter();
	}
	@Override
	public String getContentType() {
		return "text/html";
	}

}
