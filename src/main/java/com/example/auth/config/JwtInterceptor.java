package com.example.auth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.FajarService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FajarService fajarService;

    @Autowired
    private MyUserDetails myUserDetails;
    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("Request URL: " + request.getRequestURL());
        final String authHeader = request.getHeader("Authorization");
        String tokenBearier = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7);
            logger.debug("Token: " + token);
            String username = jwtTokenUtil.getUsernameFromToken(token);

         
                UserDetails userDetails = myUserDetails.loadUserByUsername(username);
                User user = userRepository.findUserByEmail(userDetails.getUsername());

                if (jwtTokenUtil.validateToken(token, userDetails)) {
                   if (jwtTokenUtil.isTokenExpired(token)) {
                        tokenBearier = fajarService.refreshToken(token);
                        user.setTokenJwt(tokenBearier);
                        userRepository.save(user);
                   }
                  
                } else {
                    logger.debug("Token is invalid");
                    
                }
            
        }
        return true;
    }
}
