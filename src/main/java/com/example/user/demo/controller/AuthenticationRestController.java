package com.example.user.demo.controller;

import com.example.user.demo.binding.JwtAuthenticationPayload;
import com.example.user.demo.exception.InvalidRequestException;
import com.example.user.demo.model.JwtUser;
import com.example.user.demo.response.JwtAuthenticationResponse;
import com.example.user.demo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("/")
public class AuthenticationRestController {
    @Value("${header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "auth", method = RequestMethod.POST)
    public JwtAuthenticationResponse createAuthenticationToken(@RequestBody JwtAuthenticationPayload authenticationPayload,
                                                               Device device) throws AuthenticationException {
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationPayload.getUsername(),
                        authenticationPayload.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationPayload.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails, device);

        // Return the token
        return new JwtAuthenticationResponse(token);
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public JwtAuthenticationResponse refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return new JwtAuthenticationResponse(refreshedToken);
        } else {
            throw new InvalidRequestException("unauthorized", null, HttpStatus.BAD_REQUEST);
        }
    }
}