package meu.booking_rebuild_ver2.service.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.service.impl.UserDetailsImplement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
/*
author: Nguuyenx Minh Tm
service: The service will create Bearer Token with userdeatil that will be created in UserService after we have
checked successfull
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value(value="${server.JWT.KEY}")
    private String jwtSecret;
    @Value(value="${server.JWT.EXP}")
    private long expirationTime;
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret));
    }
//    public String generateJwtToken(Authentication authentication){
//        UserDetailsImplement userDetails = (UserDetailsImplement) authentication.getPrincipal();
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + expirationTime);
//        return Jwts.builder()
//                .claim("name", userDetails.getFullname())
//                .claim("email", userDetails.getEmail())
//                .setSubject((userDetails.getEmail()))
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(key(), SignatureAlgorithm.HS256)
//                .compact();
//    }
    public String getUserNameFromJwtToken(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateJwtToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        }catch (MalformedJwtException e) {
            logger.error(Constants.MESSAGE_INVALID_TOKEN, e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error(Constants.MESSAGE_TOKEN_EXPIRED, e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error(Constants.MESSAGE_TOKEN_UNSUPPORTED, e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(Constants.MESSAGE_TOKEN_CLAIM_EMPTY, e.getMessage());
        }
        return false;
    }
    public String createToken(String email, String role){
        Date now = new Date();
        Date expiryTime = new Date(now.getTime() + expirationTime);
        return Jwts
                .builder()
                .setSubject(email)
                .setExpiration(expiryTime)
                .claim("role", role)
                .setIssuedAt(now)
                .signWith(key())
                .compact();

    }
//    public void validateToken(String token) throws Exception {
//        if(token.startsWith("Bearer ")){
//            token = token.substring(7);
//        }
//        try{
//            Jwts.parserBuilder()
//                    .setSigningKey(key())
//                    .build()
//                    .parseClaimsJws(token);
//        }
//        catch (ExpiredJwtException e){
//            throw new Exception("Expired token") ;
//        }
//        catch(JwtException | IllegalArgumentException e){
//            throw new Exception("Token is not valid") ;
//        }
//    }
}
