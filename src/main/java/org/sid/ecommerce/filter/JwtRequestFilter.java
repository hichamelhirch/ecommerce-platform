package org.sid.ecommerce.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sid.ecommerce.services.UserDetailsServiceImp;
import org.sid.ecommerce.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImp userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        String token=null;
        String username=null;
        if (authHeader!=null && authHeader.startsWith("Bearer ")){
            token=authHeader.substring(7);
            username=jwtUtil.extractUsername(token);
        }
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails= userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            }
        }
        filterChain.doFilter(request,response);
    }
}
