package processor;

import javax.servlet.http.HttpServletRequest;

public final class Request {
	String protocol = "";
	String useCase = "";
	String item = "";
	String item_step = "";
	
	public static String getPathForData(HttpServletRequest req) {
	    int ignoreAmt = req.getContextPath().length() + req.getServletPath().length();
	    if( ignoreAmt < 0  || ignoreAmt ==  req.getRequestURI().length()) return "";
	    return req.getRequestURI().substring(ignoreAmt+1);
	}

	Request(HttpServletRequest request){
		String[] pathElemForData = getPathForData(request).split("/");
		switch (pathElemForData.length) {
		case 4: item_step = pathElemForData[3];
		case 3: item = pathElemForData[2];
		case 2: useCase = pathElemForData[1];
		case 1: protocol = pathElemForData[0];
				break;
		}
	}
	Request(String protocol, String useCase){
		this.protocol = protocol;
		this.useCase = useCase;
	}

}
