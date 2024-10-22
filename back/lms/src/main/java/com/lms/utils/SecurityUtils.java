package com.lms.utils;

import com.lms.model.User;
import com.lms.repository.user.UserRepository;
import com.lms.service.permission.Permissions;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    private final UserRepository userRepository;

	public SecurityUtils(UserRepository userRepository){
		super();
		this.userRepository = userRepository;
	}

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                return userDetails.getUsername();
            } else {
                return principal.toString();
            }
        }
        return null;
    }

    public User getCurrentUser() {
        String username = getCurrentUsername();
        if (username == null) return null;
        return userRepository.findByUsername(username).orElse(null);
    }

    public static void checkAuthority(String authority) {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(element -> element.getAuthority().equals("Admin")))
            return;
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().noneMatch(element -> element.getAuthority().equals(authority)))
            throw new AccessDeniedException("User does not have the necessary authority.");
    }

    public static void checkAuthority(Permissions.EntityPermission permission) {
        checkAuthority(permission.getName());
    }

    public static boolean hasAdminPermission(User user) {
        return user.getRole().getPermissionList().stream().anyMatch(e -> e.getName().equals("Admin"));
    }
}
