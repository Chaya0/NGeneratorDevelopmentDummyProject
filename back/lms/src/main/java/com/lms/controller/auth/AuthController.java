package com.lms.controller.auth;

import com.lms.config.security.JwtUtil;
import com.lms.dto.GenericResponse;
import com.lms.dto.LoginRequest;
import com.lms.dto.UserDataDTO;
import com.lms.exceptions.*;
import com.lms.model.User;
import com.lms.service.user.UserService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseCookie;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService service;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService service) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.service = service;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
        if (refreshToken == null || jwtUtil.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(401).body(GenericResponse.getErrorResponse("401", "Token expired!"));
        }

        String refreshedToken = jwtUtil.refreshToken(refreshToken);
        ResponseCookie cookie = ResponseCookie.from("jwt", refreshedToken).httpOnly(true).secure(false).path("/").maxAge(jwtUtil.getJwtExpiration() / 1000).build();

        response.addHeader("Set-Cookie", cookie.toString());
        User user = service.findByUsername(jwtUtil.extractUsername(refreshedToken));
        UserDataDTO userData = new UserDataDTO(user.getUsername(), user.getRole().getPermissionList().stream().map(GrantedAuthority::getAuthority).toList());
        return ResponseEntity.ok(GenericResponse.getSuccessfulResponse(userData));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest authRequest, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            UserDataDTO userData = createUserData(response, authentication);
            return ResponseEntity.ok(GenericResponse.getSuccessfulResponse(userData));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    private UserDataDTO createUserData(HttpServletResponse response, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
        ResponseCookie cookie = ResponseCookie.from("jwt", jwt).httpOnly(true).secure(false).path("/").maxAge(jwtUtil.getJwtExpiration() / 1000).build();
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken).httpOnly(true).secure(false).path("/auth/").maxAge(jwtUtil.getRefreshTokenExpiration() / 1000).build();

        response.addHeader("Set-Cookie", cookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());
        return new UserDataDTO(userDetails.getUsername(), userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response, @CookieValue(name = "jwt", required = false) String jwtCookie,
                                    @CookieValue(name = "refreshToken", required = false) String refreshTokenCookie) {
        if (jwtCookie != null) {
            ResponseCookie cookie = ResponseCookie.from("jwt", null).httpOnly(true).secure(false).path("/").maxAge(0).build();
            response.addHeader("Set-Cookie", cookie.toString());
        }
        if (refreshTokenCookie != null) {
            ResponseCookie cookie = ResponseCookie.from("refreshToken", null).httpOnly(true).secure(false).path("/auth/").maxAge(0).build();
            response.addHeader("Set-Cookie", cookie.toString());
        }

        return ResponseEntity.ok(GenericResponse.getSuccessfulResponse("User logged out successfully!"));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@CookieValue(name = "jwt", required = false) String jwtCookie, @CookieValue(name = "refreshToken", required = false) String refreshToken, @RequestBody User user, HttpServletResponse response) {
        if (jwtCookie == null || refreshToken == null || jwtUtil.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(401).body(GenericResponse.getErrorResponse("401", "Invalid or expired token"));
        }
        try {
            logout(response, jwtCookie, refreshToken);
            String password = user.getPassword();
            service.update(user);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), password));
            UserDataDTO userData = createUserData(response, authentication);
            return ResponseEntity.ok(GenericResponse.getSuccessfulResponse(userData));
        } catch (LmsException e) {
            return ResponseEntity.status(400).body(GenericResponse.getErrorResponse("400", "Cannot save user."));
        }
    }

    @GetMapping("/validate-session")
    public ResponseEntity<?> validateSession(@CookieValue(name = "jwt", required = false) String jwtCookie, @CookieValue(name = "refreshToken", required = false) String refreshToken,
                                             HttpServletResponse response) {
        if (refreshToken == null || jwtUtil.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(401).body(GenericResponse.getErrorResponse("401", "Invalid or expired token"));
        }
        if (jwtCookie == null) {
            String refreshedToken = jwtUtil.refreshToken(refreshToken);
            ResponseCookie cookie = ResponseCookie.from("jwt", refreshedToken).httpOnly(true).secure(false).path("/").maxAge(jwtUtil.getJwtExpiration() / 1000).build();
            response.addHeader("Set-Cookie", cookie.toString());

            User user = service.findByUsername(jwtUtil.extractUsername(refreshedToken));
            UserDataDTO userData = new UserDataDTO(user.getUsername(), user.getRole().getPermissionList().stream().map(GrantedAuthority::getAuthority).toList());

            return ResponseEntity.ok(GenericResponse.getSuccessfulResponse(userData));
        }

        User user = service.findByUsername(jwtUtil.extractUsername(jwtCookie));
        UserDataDTO userData = new UserDataDTO(user.getUsername(), user.getRole().getPermissionList().stream().map(GrantedAuthority::getAuthority).toList());

        return ResponseEntity.ok(GenericResponse.getSuccessfulResponse(userData));
    }

}
