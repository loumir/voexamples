/**
 * 
 */
package processor;

import java.io.File;

/**
 * @author michel
 *
 */
public class XhtmlBuilder {

	public static String getExampleDiv(String baseDir , Request params) throws Exception{
		ExampleModel em = new ExampleModel(baseDir, params);
		StringBuffer retour = new StringBuffer();
		String content;
		retour.append("<div id=\"" + em.getName() + "\" resource=\"#" +em.getName() + "\" typeof=\"example\">\n");
		retour.append("  <fieldset>\n");
		retour.append("     <legend>" + params.protocol + "[" + params.useCase + "] " + em.getTitle() + "</legend>\n");
		content = em.getDescription();
		if( content.length() > 0 ){
			retour.append("     <fieldset>\n");
			retour.append("       <legend>Description</legend>\n");
			retour.append("       <pre>" + content + "</pre>");
			retour.append("     </fieldset>\n");
		}
		content = em.getQuery();
		if( content.length() > 0 ){
			retour.append("     <fieldset>\n");
			retour.append("       <legend>Query</legend>\n");
			retour.append("       <pre>" + content.replaceAll("<","&lt;").replaceAll(">", "&gt;") + "</pre>");
			retour.append("     </fieldset>\n");
		}
		content = em.getUsage();
		if( content.length() > 0 ){
			retour.append("     <fieldset>\n");
			retour.append("       <legend>Usage</legend>\n");
			retour.append("       <pre>" + content + "</pre>");
			retour.append("     </fieldset>\n");
			retour.append("   </fieldset>\n");
		}
		retour.append("</div>\n");
		return retour.toString();
	}
	
	public static String getExampleNiceDiv(ExampleModel em) throws Exception{
		StringBuffer retour = new StringBuffer();
		String content;
		retour.append("     <p>");
		retour.append("[<a title='Get Dali resource' href='" + Processor.DALIURL + em.params.protocol+ "/" + em.params.useCase + "'>dali xhtml</a>]");
		if( em.getTitle().length() > 0 ){
			retour.append(" [<a title='Get title' href='" + Processor.DALIURL + em.params.protocol+ "/" + em.params.useCase + "/title'>title</a>]");
		}
		if( em.getDescription().length() > 0 ){
			retour.append(" [<a title='Get description' href='" + Processor.DALIURL + em.params.protocol+ "/" + em.params.useCase + "/description'>description</a>]");
		}
		if( em.getQuery().length() > 0 ){
			retour.append(" [<a title='Get query' href='" + Processor.DALIURL + em.params.protocol+ "/" + em.params.useCase + "/query'>query</a>]");
		}
		if( em.getUsage().length() > 0 ){
			retour.append(" [<a title='Get usage' href='" + Processor.DALIURL + em.params.protocol+ "/" + em.params.useCase + "/usage'>usage</a>]");
		}
		retour.append("     </p>\n");
		
		content = em.getDescription();
		if( content.length() > 0 ){
			retour.append("     <p><strong><pre>");
			retour.append(content);
			retour.append("      </strong></pre></p>\n");
		}
		content = em.getQuery();
		String cssClass="";
		if( content.length() > 0 ){
			if( content.indexOf("?xml") >= 0 ) {
				cssClass = "class=xml";
			}
			retour.append("     <p><strong>Query</strong>\n");
			retour.append("       <pre><code " + cssClass + ">" + content.replaceAll("<","&lt;").replaceAll(">", "&gt;") + "</code></pre>");
			retour.append("     </p>\n");
		}
		content = em.getUsage();
		if( content.length() > 0 ){
			if( !content.startsWith("#!")){
				retour.append("     <p><strong>Usage</strong>\n");
				retour.append("       <pre>" + content + "</pre>");
				retour.append("     </pre>\n");
			} else {
				retour.append("     <p><strong>Usage</strong>\n");
				retour.append("       <pre><code>" + content + "</code></pre>");
				retour.append("     </pre>\n");				
			}
		}
		return retour.toString();
	}
	
	public static String getExampleAnchor(String baseDir , Request params) throws Exception{
		ExampleModel em = new ExampleModel(baseDir, params);
		StringBuffer retour = new StringBuffer();
		retour.append("<div id=\"" + em.getName() + "\" resource=\"#" +em.getName() + "\" typeof=\"example\">\n");
		retour.append("[<a href=\"" + Processor.SHOWURL + params.protocol + "/" + params.useCase + "\">" + params.useCase + "</a>]\n");
		retour.append("[<a href=\"" + Processor.DALIURL + params.protocol + "/" + params.useCase + "\">dali xhtml</a>] \n");
		retour.append("<strong>" + em.getTitle() + " </strong>");

		String d = em.getDescription();
		if( d.length() > 100 ) d = d.substring(0,  100) + "... ";
		retour.append(d);
		retour.append("</div>\n");
		return retour.toString();
	}

	
	public static String getNiceHead() {
		return 
		  " <!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
		+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
		+ "<head>\n"

		+ "<title>IVOA examples</title>\n"
		+ "<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />\n"
		+ "<link rel=\"stylesheet\" href=\"/voexamples/css/ivoa.css\" type=\"text/css\" media=\"all\" />\n"
		+ "<link rel=\"icon\" type=\"image/jpeg\" href=\"/voexamples/images/ivoa_logoc2.jpg\" />\n"
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
		+ "</head>\n";

	}
	/**
	 * @return
	 */
	public static String getNiceFooter() {
		return 
		  "		<div id=\"footer\">\n"
		+ "			<div class=\"shell\">\n"
		+ "				<div class=\"cl\">&nbsp;</div>\n"
		+ "				<ul>\n"
		+ "					<li></li>\n"
		+ "				</ul>\n"
		+ "				<p class=\"copy\">\n"
		+ "					&copy; Laurent Michel - Daniel Durand - Mireille Louys. Contact the<a\n"
		+ "						href=\"laurent.michel@astro.unistra.fr\"> Webmaster</a>\n"
		+ "				</p>\n"
		+ "				<div class=\"cl\"></div>\n"
		+ "			</div>\n"
		+ "		</div>\n"
		+ "		<!-- End Footer -->\n"
		+ "	</body>\n"
		+ "	</html>\n";
	}
	
	public static String openNiceHeading(String title) {
		return
		  "	<body>\n"
		+ "			<div id=\"header\">\n"
		+ "				<div class=\"shell\">\n"
		+ "					<!-- Logo -->\n"
		+ "					<h1 id=\"logo\" class=\"notext\">\n"
		+ "						<a href=\"http://www.ivoa.net\"></a>\n"
		+ "					</h1>\n"
		+ "					<!-- End Logo -->\n"
		+ "					<div id=\"navigation\">\n"
		+ "						<ul>\n"
		+ "							<li class=\"active\"><a href=\"/voexamples\"><span>Home</span></a></li>\n"
		+ "							<li><a href=\"/voexamples/show\"><span>Protocols & Langages</span></a></li>\n"
		+ "						</ul>\n"
		+ "					</div>\n"
		+ "				</div>\n"
		+ "			</div>\n"
		+ "			<!-- Heading -->\n"
		+ "			<div id=\"heading\">\n"
		+ "				<div class=\"shell\">\n"
		+ "					<div class=\"cl\">&nbsp;</div>\n"
		+ "					<h2>" + title + " </h2>\n"
		+ "					<div class=\"heading-cnt\">\n";
	}	
	
	public static String closeNiceHeading() {
		return 
		  "		              <div class=\"cl\"></div>\n"
		+ "	               </div>\n"
		+ "	           </div>\n"
		+ "       </div>\n";
	}
}
