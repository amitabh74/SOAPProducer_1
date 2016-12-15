package com.concretepage;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.concretepage.soap.GetStudentRequest;
import com.concretepage.soap.GetStudentResponse;

@Endpoint
public class StudentEndpoint {
	
	private static final String NAMESPACE_URI = "http://concretepage.com/soap";
	@Autowired
	private StudentUtility studentUtility;
	
	
	@Resource
	WebServiceContext wsctx;
	
	private boolean isAuthenticated(){
		MessageContext mctx = wsctx.getMessageContext();
		
		Map httpHeaders = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
		List userList = (List) httpHeaders.get("userName");
		List pwdList = (List) httpHeaders.get("password");
		
		String username = null;
		String password = null;
		
		if(userList!=null){
        	//get username
        	username = userList.get(0).toString();
        }

        if(pwdList!=null){
        	//get password
        	password = pwdList.get(0).toString();
        }
		
      //Should validate username and password with database
        if (username.equals("amitabh") && password.equals("basu")){
        	return true;
        }else{
        	return false;
        }
		
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStudentRequest")
	@ResponsePayload
	public GetStudentResponse getCountry(@RequestPayload GetStudentRequest request) {
		
		GetStudentResponse response = null;
		if(isAuthenticated()){
		
			response = new GetStudentResponse();
			response.setStudent(studentUtility.getStudent(request.getStudentId()));
		}
			return response;
	}

}
