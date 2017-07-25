package com.gcit.lms;


import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("token");
		String role = request.getHeader("role");
		//check if token is valid
		
		if(token != null) {
			HandlerMethod m = (HandlerMethod) handler;
			if(m.getMethodAnnotation(RolesAllowed.class) != null) {
				RolesAllowed rolesAllowed = (RolesAllowed) m.getMethodAnnotation((RolesAllowed.class));
				String[] roles = rolesAllowed.value();
				for(String r : roles) {
					if(r.equals(role))
						return super.preHandle(request, response, handler);
				}
				
				throw new Exception("Method not allowed for role");
			}
		} else {
			throw new Exception("Authentication token Required");
		}
		
		return false;
	}

}
