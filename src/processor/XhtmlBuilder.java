/**
 * 
 */
package processor;

import java.util.ArrayList;
import java.util.List;
import scripts.GetScript;

/**
 * @author michel
 *
 */
public class XhtmlBuilder {

	public static String getExampleDiv(String baseDir , Request params) throws Exception{
		ExampleModel em = new ExampleModel(baseDir, params);
		StringBuilder retour = new StringBuilder();
		String content;
		retour.append("<div id=\"" + em.getName() + "\" resource=\"#" +em.getName() + "\" typeof=\"example\">\n");
		retour.append("  <fieldset>\n");
		retour.append("     <legend>" + params.protocol + "[" + params.useCase + "] " + em.getTitle() + "</legend>\n");

		List<String> index = em.getIndex(null);
		if(index != null) {
			for(String file : index){
				if (file.contains("stepquery")){
					for(String stepFile : em.getIndex(file)){
						boolean is_code = stepFile.contains("query") || stepFile.contains("xml");
						retour.append(addDaliDiv(file + "/" + stepFile, em.getByName(file + "/" + stepFile), is_code));
					}
				}
				else {
					boolean is_code = file.contains("query") || file.contains("xml");
					retour.append(addDaliDiv(file, em.getByName(file), is_code));
				}
			}
		} else {
			retour.append(addDaliDiv("description", em.getDescription(), false));
			retour.append(addDaliDiv("query", em.getQuery(), true));
			retour.append(addDaliDiv("usage", em.getUsage(), false));
			retour.append(addDaliDiv("image", em.getImage(), false));
		}

		retour.append("    </fieldset>\n");
		retour.append("</div>\n");
		return retour.toString();
	}

	public static String getExampleNiceDiv(ExampleModel em) throws Exception{
		StringBuilder retour = new StringBuilder();
		retour.append("<button type=\"button\" class=\"collapsible\">Show DALI Resources</button>\n");
		retour.append("     <p class=\"content\">");
		retour.append("[<a title='Get Dali resource' href='" + Processor.DALIURL + em.params.protocol+ "/" + em.params.useCase + "'>dali xhtml</a>]");

		List<String> index = em.getIndex(null);
		if(index != null) {
			for(String file : index){
				if(file.contains(".")){
					String el = file.substring(0, file.indexOf("."));
					retour.append(" [<a title='Get " + el + " resource' href='" + Processor.DALIURL + em.params.protocol+ "/" + em.params.useCase + "/" + el + "'>" + el + "</a>]");
				}
				else{
					for (String stepFile : em.getIndex(file)){
						String el = stepFile.substring(0, stepFile.indexOf("."));
						retour.append(" [<a title='Get " + file + "/" + el + " resource' href='" + Processor.DALIURL + em.params.protocol+ "/" + em.params.useCase + "/" + file + "/" + el + "'>" + file + "/" + el + "</a>]");
					}
				}
			}
			retour.append("     </p>\n");

			for(String file : index){
				String el = em.getByName(file);
				if(file.contains("txt") && !file.contains("query")){
					if(file.contains("description")) {
						retour.append(addDescription(el));
					}
					else if(file.contains("usage")){
						retour.append(addUsage(el));
					}
					else if(file.contains("subtitle")){
						retour.append(addSubtitle(el));
					}
					else if(file.contains("uri")){
						retour.append(addURI(el, file));
					}

				}
				else if(file.contains("query") && file.contains(".")){
					if(file.contains(".xml")){
						retour.append(addQuery(el, "xml"));

					} else {
						retour.append(addQuery(el, null));
					}
				}
				else if(file.contains("png") || file.contains("jpg")){
					retour.append(addImage(file, em));
				}
				else if(file.contains("stepquery") && !file.contains(".")){
					retour.append(addStepQuery(file, em));
				}
				else{
					retour.append("     <strong><p>Link</p></strong>\n");
					retour.append("     <a href=\"../../examples/" +  em.params.protocol + "/" + em.params.useCase + "/" + file + "\"\n");
					retour.append("       <p>" + file + "</p>\n");
					retour.append("     </a>\n");
				}
			}
		}
		else {
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
			if( em.getImage().length() > 0 ){
				retour.append(" [<a title='Get image' href='" + Processor.DALIURL + em.params.protocol+ "/" + em.params.useCase + "/image'>image</a>]");
			}
			retour.append("     </p>\n");

			retour.append(addDescription(em.getDescription()));
			retour.append(addQuery(em.getQuery(), null));
			retour.append(addUsage(em.getUsage()));
			retour.append(addImage(em.getImage(), em));
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
						+ "<link rel=\"icon\" type=\"image.txt/jpeg\" href=\"/voexamples/images/ivoa_logoc2.jpg\" />\n"
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
						+ "<script>\n"
						+ GetScript.get("query-step")
						+ "</script>\n"
						+ "<script>\n"
						+ GetScript.get("show-image")
						+ "</script>\n"
						+ "<script>\n"
						+ GetScript.get("collapsible")
						+ "</script>\n"
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

	public static String addSubtitle(String subtitle){
		return "<p><strong>" + subtitle + "</strong></p>\n";
	}
	public static String addDescription(String description) {
		StringBuilder html = new StringBuilder();
		if( description.length() > 0 ){
			html.append("     <p id=\"desc\"><pre>");
			html.append(description);
			html.append("      </pre></p>\n");
		}

		return html.toString();
	}

	public static String addUsage(String usage) {
		StringBuilder html = new StringBuilder();
		if( usage.length() > 0 ) {
			if (!usage.startsWith("#!")) {
				html.append("     <p><strong>Usage</strong>\n");
				html.append("       <pre>" + usage + "</pre>");
				html.append("     </pre>\n");
			} else {
				html.append("     <p><strong>Usage</strong>\n");
				html.append("       <pre><code>" + usage + "</code></pre>");
				html.append("     </pre>\n");
			}
		}

		return html.toString();
	}

	public static String addQuery(String query, String type) {
		StringBuilder html = new StringBuilder();
		String cssClass="";

		if( query.length() > 0 ){

			if(type != null) {
				cssClass = "class=" + type;
			} else {
				if (query.contains("?xml")) {
					cssClass = "class=xml";
				}
			}

			html.append("     <p><strong>Query or Code</strong>\n");
			html.append("       <pre><code " + cssClass + ">" + query.replaceAll("<","&lt;").replaceAll(">", "&gt;") + "</code></pre>");
			html.append("     </p>\n");
		}

		return html.toString();
	}

	public static String addImage(String image, ExampleModel em) {
		StringBuilder html = new StringBuilder();
		if( image.length() > 0 ){
			String route = em.params.protocol + "/" + em.params.useCase;
			html.append("<div class=\"show-image\">\n");
			html.append("<input type=\"checkbox\" id=\"toggle\"><label for=\"toggle\"> Click to show " + image +": </label>");
			html.append("       <img id=\"toggled\" src=\"../../examples/" + route + "/" + image + "\"/>\n");
			html.append("     </p>\n");
			html.append("</div>\n");
		}

		return html.toString();
	}

	public static String addURI(String uri, String file){
		StringBuilder html = new StringBuilder();
		if( uri.length() > 0 ){
			if (file.contains("_")) {
				file = file.substring(file.indexOf("_") + 1, file.length() - 4);
			}
			else{
				file = "Link";
			}
			html.append("     <strong><p>" + file + "</p></strong>\n");
			html.append("     <a href=\"" + uri + "\"\n");
			html.append("       <p>" + uri + "</p>\n");
			html.append("     </a>\n");
		}

		return html.toString();
	}

	public static String addStepQuery(String file, ExampleModel em) throws Exception{
		StringBuilder html = new StringBuilder();
		html.append("<div class=\"stepquery\">");
		html.append("<div id=\"buttons\">");
		html.append("   <button id=\"info-btn\" title=\"Click on the next button to browse to the next step\">i</button>\n");
		html.append("	<button id=\"prev-btn\" disabled><<</button>\n");
		html.append("	<button id=\"next-btn\">>></button>");
		html.append("</div>");

		List<String> index = em.getIndex(file);
		List<String> descriptions = new ArrayList<>();
		List<String> queries = new ArrayList<>();

		for(String f : index) {
			String el = em.getByName(file + "/" + f);
			if(el.length() > 0) {
				if(f.contains("description")){
					descriptions.add(el);
				}
				else if(f.contains("query") && f.contains(".")){
					queries.add(el);
				}
			}
		}

		for(int i = 0 ; i < queries.size() ; i++) {
			html.append("  <div class=\"step\">");
			html.append(addDescription(descriptions.get(i)));
			if (index.get(i).contains(".xml")) {
				html.append(addQuery(queries.get(i), "xml"));

			} else {
				html.append(addQuery(queries.get(i), null));
			}
			html.append("  </div>");
		}

		html.append("</div>");

		return html.toString();
	}

	public static String addDaliDiv(String file_name, String content, boolean is_code){
		StringBuilder html = new StringBuilder();
		if( content.length() > 0 ){
			if(file_name.contains(".")){
				file_name = file_name.substring(0, file_name.indexOf('.'));
			}
			html.append("     <fieldset>\n");
			html.append("       <legend>" + file_name.substring(0,1).toUpperCase() + file_name.substring(1) + "</legend>\n");
			if(is_code) {
				html.append("       <pre>" + content.replaceAll("<","&lt;").replaceAll(">", "&gt;") + "</pre>");
			} else {
				html.append("       <pre>" + content + "</pre>\n");
			}
			html.append("     </fieldset>\n");
		}

		return html.toString();
	}
}
