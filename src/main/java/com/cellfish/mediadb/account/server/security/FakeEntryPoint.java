package com.cellfish.mediadb.account.server.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class FakeEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2) throws IOException,
			ServletException {
		throw new IllegalStateException(
				"This implementation is a dummy class, created purely so that spring security namespace tags can be used in application context, and this method should never be called");
	}
}