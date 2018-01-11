/**
 * \file p3secAPI.java
 * <h3>Protected Point to Point administrator interface file</h3>
 *
 * Copyright (C) Velocite 2011
 *
 * The adminstrator interface is used to send requests and responses
 * between Java and the P3 administration libraries.
 */
 
import java.io.UnsupportedEncodingException; 

public class p3secAPI {
	private static p3secAPI secAPI = new p3secAPI();
	native byte[] initAdmin(String args);
	native byte[] sendRequest(String jtokens);
	native byte[] getNextRequest();
	// Load the library
	static {
		System.loadLibrary("p3secadmin");
	}

	public void main() {
		secAPI = new p3secAPI();
	}

/**
 * Initialize the administration management.
 */
	public void initAdmin(String[] arglist) throws IllegalArgumentException {
		int idx;
		byte buf[];
		String args = "";
		IllegalArgumentException exception;

		for (idx=0; idx < arglist.length; idx++)
			args += arglist[idx] + " ";
		buf = secAPI.initAdmin(args);
		args = new String(buf);
		if (!args.equals("OK")) {
			exception = new IllegalArgumentException("Error initializing administration: " + args);
			throw exception;
		}
  }

/**
 * Get the response to a request.
 */
	public String getResponse(String request) {
		byte buf[];
		String response;
		
		// Call native method to get a message
		buf = secAPI.sendRequest(request);
		response = new String(buf);
		return response;
	}

/**
 * Get the next response in a list.  Note that the original
 * response to a request returns the number of items in a
 * list, so that the caller is responsible for calling this
 * method the appropriate number of times.  If there is no
 * response available, a null String is returned.
 */
	public String getNext(String request) {
		byte buf[];
		String response;
		
		// Call native method to get a message
		buf = secAPI.getNextRequest();
		response = new String(buf);
		return response;
	}

}

