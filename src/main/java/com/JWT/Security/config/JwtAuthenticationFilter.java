package com.JWT.Security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// service annotation or component annotation or also repository three of them are the same as bean
// the repository and the service extend the component

@Component // Spring will automatically detect it during component scanning and register it as a bean in the application context.
@RequiredArgsConstructor // it will create a constructor using any final field that we declare right here

// this extends for the filter of the authentication
public class JwtAuthenticationFilter extends OncePerRequestFilter
{

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException
    {
        // this is the header that contains the JWT token
        final String authHeader = request.getHeader("Authorization");

        final String jwt;

        final String userEmail;

        // the token should start with bearer
        if(authHeader == null || !authHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request, response);
            return;
        }

        // 7 because after the bearer word
        jwt = authHeader.substring(7);

        // now we should extract the email from jwt token
        userEmail = jwtService.extractUsername(jwt);

        // if the second one is null means that the user is not yet authenticated
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            // so we should get the user details from the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // we check if the user is valid or not
            if(jwtService.isTokenValid(jwt, userDetails))
            {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }
}