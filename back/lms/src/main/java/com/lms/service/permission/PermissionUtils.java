package com.lms.service.permission;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {

	public static List<String> getAllPermissionNames() {
		List<String> permissionNames = new ArrayList<>();

		for(Permissions.Library permission : Permissions.Library.values()) {
			permissionNames.add(permission.getName());
		}

		for(Permissions.Book permission : Permissions.Book.values()) {
			permissionNames.add(permission.getName());
		}

		for(Permissions.Author permission : Permissions.Author.values()) {
			permissionNames.add(permission.getName());
		}

		for(Permissions.Publisher permission : Permissions.Publisher.values()) {
			permissionNames.add(permission.getName());
		}

		for(Permissions.Category permission : Permissions.Category.values()) {
			permissionNames.add(permission.getName());
		}

		for(Permissions.Checkout permission : Permissions.Checkout.values()) {
			permissionNames.add(permission.getName());
		}

		for(Permissions.Fine permission : Permissions.Fine.values()) {
			permissionNames.add(permission.getName());
		}

		for(Permissions.User permission : Permissions.User.values()) {
			permissionNames.add(permission.getName());
		}

		for(Permissions.Reservation permission : Permissions.Reservation.values()) {
			permissionNames.add(permission.getName());
		}

		for(Permissions.Role permission : Permissions.Role.values()) {
			permissionNames.add(permission.getName());
		}

		for(Permissions.Permission permission : Permissions.Permission.values()) {
			permissionNames.add(permission.getName());
		}

		return permissionNames;
	}

}
