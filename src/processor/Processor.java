/**
 * 
 */
package processor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author michel
 *
 */
public abstract class Processor {
	protected Request params;
	ServletContext servletContext;
	static final String ROOTDIR = "examples";
	static final String DALIURL = "/voexamples/dali/";
	static final String SHOWURL = "/voexamples/show/";
	
	final String getXhtmlHead() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
		     + "<!DOCTYPE html \n" 
		     + " PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n" 
	         + " \"DTD/xhtml1-strict.dtd\">\n" 
	         + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n" 
	         + "  <head>\n" 
	         + "    <title>VO Example Server</title>\n" 
	         + "    <meta http-equiv=\"Content-Type\" content=\"text/html\" charset=\"UTF-8\" />\n" 
	         + "  </head>\n"; 
	}
	final String getNiceXhtmlHead() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
		     + "<!DOCTYPE html \n" 
		     + " PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n" 
	         + " \"DTD/xhtml1-strict.dtd\">\n" 
	         + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n" 
	         + "  <head>\n" 
	         + "    <title>VO Example Server</title>\n" 
	         + "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=\"UTF-8\" />\n" 
	         + "    <link rel=\"stylesheet\" href=\"//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.1.0/styles/default.min.css\">\n" 
	         + "    <script src=\"//code.jquery.com/jquery-2.1.1.min.js\"></script>\n" 
	         + "    <script src=\"//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.1.0/highlight.min.js\"></script>\n" 
	         + "     <script>\n" 
	         + "      $(document).ready(function() {\n" 
	         + "     	  $('pre code').each(function(i, block) {\n" 
	         + "     	    hljs.highlightBlock(block);\n" 
	         + "     	  });\n" 
	         + "     	});	         \n" 
	         + "     </script>\n" 

	         + "  </head>\n"; 
	}
	Processor(HttpServletRequest request, ServletContext servletContext){
		this.params = new Request(request);
		this.servletContext = servletContext;
	}
	public abstract String getContent() throws Exception;
	public abstract String getNiceContent() throws Exception;
	public abstract String getContentType();

}
