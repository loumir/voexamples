package servlet;

import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import processor.GetExample;
import processor.GetExampletem;
import processor.GetIndex;
import processor.GetProtocol;
import processor.Processor;
import processor.Request;
import processor.SearchProtocol;

/**
 * Servlet implementation class Search
 */
public class Search extends RootServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/* (non-Javadoc)
	 * @see servlet.RootServlet#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected  void process(HttpServletRequest request, HttpServletResponse response)  {
		try {
			super.process(request, response);
			String rq = Request.getPathForData(request).trim();
			String[] pathElemForData =  (rq.length() > 0 )? rq.split("\\/") : new String[0];
			Processor processor;
			switch (pathElemForData.length) {
			case 2: processor = new SearchProtocol(request, this.getServletContext());break;
			default: throw new Exception("Not implented");
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType(processor.getContentType());
			response.getOutputStream().println(processor.getNiceContent());
		} catch(Exception e){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("text/plain");
			try {
				e.printStackTrace(new PrintStream(response.getOutputStream()));
			} catch (IOException e1) {}
		}

	}

}
