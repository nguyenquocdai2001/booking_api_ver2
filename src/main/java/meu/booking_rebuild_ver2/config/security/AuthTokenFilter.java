package meu.booking_rebuild_ver2.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.service.abstractions.IUserService;
import meu.booking_rebuild_ver2.service.abstractions.Passanger.ICustomerService;
import meu.booking_rebuild_ver2.service.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
author: Nguyen Minh Tam
Component: It will validate token, validate user
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICustomerService customerService;
    @Override
    // Function to check valid token with token
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                String pattern = "^\\d{10}$";
                Pattern regex = Pattern.compile(pattern);
                Matcher matcher = regex.matcher(username);
                if(matcher.matches()){
                    UserDetails customerDetails = customerService.loadCustomerByPhone(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            customerDetails,
                            null,
                            customerDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println(authentication);
                }
                else{
                    UserDetails userDetails = userService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}" + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
    // Function to get token,
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
