package com.lms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.lms.model.Permission;
import com.lms.model.Role;
import com.lms.model.User;
import com.lms.repository.permission.PermissionRepository;
import com.lms.repository.role.RoleRepository;
import com.lms.repository.user.UserRepository;
import com.lms.service.permission.PermissionUtils;

@Component
public class DataSeeder implements CommandLineRunner {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;
	private final PasswordEncoder passwordEncoder;

	public DataSeeder(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.permissionRepository = permissionRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {
		for (String permissionName : PermissionUtils.getAllPermissionNames()) {
			if (permissionRepository.findByName(permissionName).isPresent()) continue;
			Permission permission = new Permission();
			permission.setName(permissionName);
			permissionRepository.save(permission);
		}
		if (!permissionRepository.findByName("Admin").isPresent()) {
			Permission permission = new Permission();
			permission.setName("Admin");
			permissionRepository.save(permission);
		}
		Role role;
		if (roleRepository.findByName("SuperUser").isPresent()) {
			role = roleRepository.findByName("SuperUser").get();
		} else {
			role = new Role();
			role.setName("SuperUser");
			role.setPermissionList(permissionRepository.findAll());
			roleRepository.save(role);
		}
		if (!userRepository.findByUsername("admin").isPresent()) {
			User user = new User();
			user.setUsername("admin");
			user.setEmail("testMail@gmail.com");
			String encodedPassword = this.passwordEncoder.encode("admin");
			user.setPassword(encodedPassword);
			user.setRole(role);

			userRepository.save(user);
		}
	}

}
