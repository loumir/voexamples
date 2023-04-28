package servlet;

import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import processor.GetIndex;
import processor.GetProtocol;
import processor.GetExample;
import processor.GetExampletem;
import processor.Processor;
import processor.Request;

/**
 * Servlet implementation class Example
 */
public class Dali extends RootServlet {
	private static final long serialVersionUID = 1L;

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
			case 0: processor = new GetIndex(request, this.getServletContext());break;
			case 1: processor = new GetProtocol(request, this.getServletContext());break;
			case 2: processor = new GetExample(request, this.getServletContext());break;
			default: processor = new GetExampletem(request, this.getServletContext());break;
			}
			System.out.println(processor);
			response.setCharacterEncoding("UTF-8");
			response.setContentType(processor.getContentType());
			response.getOutputStream().println(processor.getContent());
		} catch(Exception e){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("text/plain");
			try {
				e.printStackTrace(new PrintStream(response.getOutputStream()));
			} catch (IOException e1) {}
		}

	}

}
