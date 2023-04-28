package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import processor.Request;


/**
 * RootServlet super class for all servlets
 * Contains mainly utilities
 * 
 * @author michel
 *
 */
public abstract class RootServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	public static void getErrorPage(HttpServletRequest req, HttpServletResponse res, String msg) {
		try {
			res.setContentType("text/html");
			res.getOutputStream().println(msg);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void printAccess(HttpServletRequest request) {
			System.out.println(accessMessage(request) );
	}

	
	/**
	 * @param req
	 * @return
	 */
	public static String accessMessage(HttpServletRequest req) {
		String full_url = req.getRequestURL().toString();
		String queryString = req.getQueryString();   
		if (queryString != null) {
			full_url += "?"+queryString;
		}		
		return req.getMethod() + " from " + req.getRemoteAddr() + ": " + full_url  + " " + Request.getPathForData(req);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	
	protected  void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		printAccess(request);
	}
	
}
