package com.gsm.mat.configuration.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsm.mat.configuration.security.auth.MyUserDetailsService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final MyUserDetailsService memberService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken=request.getHeader("Authorization");
        String refreshToken=request.getHeader("RefreshToken");
        if(accessToken!=null && refreshToken != null && tokenProvider.getTokenType(accessToken).equals("accessToken")){
            if(tokenProvider.isTokenExpired(accessToken) && tokenProvider.getTokenType(refreshToken).equals("refreshToken") && !tokenProvider.isTokenExpired(refreshToken)){
                accessToken = generateNewAccessToken(refreshToken);
                writeResponse(response, accessToken);
                return;
            }
            String userEmail=accessTokenExtractEmail(accessToken);
            if(userEmail!=null) registerUserinfoInSecurityContext(userEmail, request);
        }
        filterChain.doFilter(request, response);
    }

    private void writeResponse(HttpServletResponse response, String accessToken) throws IOException {
        String bodyToJson = getBodyToJson();
        response.addHeader("accessToken", accessToken);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(bodyToJson);
    }

    private String getBodyToJson() throws JsonProcessingException {
        Map<String,Object> body = new HashMap<>();
        body.put("success", true);
        body.put("msg", "Token is regenerated");
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        String bodyToJson = objectMapper.writeValueAsString(body);
        return bodyToJson;
    }

    private String accessTokenExtractEmail(String accessToken){
        try{
            return tokenProvider.getUserEmail(accessToken);
        }catch(JwtException e){
            throw new RuntimeException();
        }
    }
    private void registerUserinfoInSecurityContext(String userEmail, HttpServletRequest req){
        try{
            UserDetails userDetails = memberService.loadUserByUsername(userEmail);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }catch (NullPointerException e){
            throw new RuntimeException();
        }
    }
    private String generateNewAccessToken(String refreshToken){
        try{
            return tokenProvider.generateAccessToken(tokenProvider.getUserEmail(refreshToken));
        }catch (JwtException | IllegalStateException e) {
            throw new RuntimeException();
        }
    }
}