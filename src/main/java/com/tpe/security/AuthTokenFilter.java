package com.tpe.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * in this class:
 *  validate Credentials (username password) of logged user
 *  Create Token
 *  or Validate
 */
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //getToken from header
        String jwtToken = getTokenFromHeader(request);

        //validate tokeb
        if (jwtToken != null && jwtProvider.validateToken(jwtToken)) {
            String userName = jwtProvider.extractUserNameFromJwToken(jwtToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);//user which is recognized by security
            UsernamePasswordAuthenticationFilter authentication =
                    new UsernamePasswordAuthenticationFilter(
//                            userDetails,
//                            null,
//                            userDetails.getAuthorities()
                    ); //TODO: resolve the issue 1:02:55
        }

    }

    //we are extracting Token from header using request.
    private String getTokenFromHeader(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        //TOKEN Format (Value "Authorization") Bearer 3k4hg5i342g5y3g53452yg32i4uh
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
