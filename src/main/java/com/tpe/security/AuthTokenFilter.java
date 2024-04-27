package com.tpe.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
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

        //validate token
        try {
            if (jwtToken != null && jwtProvider.validateToken(jwtToken)) {
                String userName = jwtProvider.extractUserNameFromJwToken(jwtToken); //extract user from token
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);//get security recognized user //user which is recognized by security

                //to place logged user into security context we need to use UsernamePasswordAuthenticationToken
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, //authenticated user
                                null, //if we need extra data about the user we can add here
                                userDetails.getAuthorities() //role of user
                        );
                SecurityContextHolder.getContext().setAuthentication(authentication); //place authenticated user in security context
            }
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }
        //This allows subsequent filters to perform their operations before reaching the final resource
        filterChain.doFilter(request,response);

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

    /**
     * --> "permitAll" is used to explicitly allow unauthenticated access to a resource or
     * endpoint, bypassing authentication and authorization checks.
     * --> "shouldNotFilter" is used to specify that a specific filter should not be applied to a particular request,
     * bypassing the execution of that filter for the matching request.
     *
     * @param request
     * @return
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher antMatcher = new AntPathMatcher();
        return antMatcher.match("/register",request.getServletPath()) ||
                antMatcher.match("/login",request.getServletPath());
    }
}
