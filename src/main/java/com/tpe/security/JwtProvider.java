package com.tpe.security;

import com.tpe.security.service.UserDetailsImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import java.util.Date;

@Component
public class JwtProvider {
    /*
        In this class:
            1. Generate JW Token
            2. Validate JW Token
            3. extract userName from JW Token
     */

    private String jwtSecretKey = "sbootasdfa65754sfgdfgrhtndfghnergz567sdfbsdgns6rthnd6f6gh6sdfgsgd76sfghfghg8bsdfg9bsdfbgsdfbsdfgb"; //secret key will be used to generate/parse token
    private long jwtExpiration = 86400000; //24*60*60*1000 = 24 hours

    //*********************** GENERATE JW TOKEN **************************
    /*
        to generate TOKEN we need 3 things
            1. userName
            2. expire duration
            3. secret key
     */

    public String createToken(Authentication authentication) {
        //getPrincipal method will give us currently logged-in user
        UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

        return  Jwts.builder().subject(userDetail.getUsername())// username of logged-in / authenticated user
                .issuedAt(new Date()) //when jwt is generated
                .expiration(new Date(new Date().getTime()+jwtExpiration)) //expire time
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes())) //encoding algorithm + secret key
                .compact(); //compact / zip everything
    }

    //*********************** VALIDATE JW TOKEN **************************

    public boolean validateToken(String token){
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes())).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    //*********************** EXTRACT USERNAME FROM JW TOKEN **************************

    public String extractUserNameFromJwToken(String token) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes())).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


}
