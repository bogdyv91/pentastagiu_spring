package com.pentalog.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pentalog.model.User;
import com.pentalog.service.AuthentificationService;

@Component
public class TokenFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenFilter.class);

	@Autowired
	AuthentificationService authentificationService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String token = request.getParameter("token");
		if (token != null) {
			Optional<User> user = authentificationService.getUserByToken(token);
			if (user.isPresent()) {
				chain.doFilter(request, response);
			} else {
				LOGGER.error("Token invalid");
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"The token is not valid.");
			}
		} else {
			chain.doFilter(request, response);
		}
	}

}
