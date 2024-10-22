package com.lms.service.permission;

public class Permissions {

	public interface EntityPermission {
		String getName();
	}

	public enum Library implements EntityPermission {
		READ("can_view_library"),
		UPDATE("can_update_library"),
		CREATE("can_create_library"),
		DELETE("can_delete_library");

		Library(String name) {
			this.name = name;
		}

		private final String name;

		@Override
		public String getName() {
			return name;
		}
	}

	public enum Book implements EntityPermission {
		READ("can_view_book"),
		UPDATE("can_update_book"),
		CREATE("can_create_book"),
		DELETE("can_delete_book");

		Book(String name) {
			this.name = name;
		}

		private final String name;

		@Override
		public String getName() {
			return name;
		}
	}

	public enum Author implements EntityPermission {
		READ("can_view_author"),
		UPDATE("can_update_author"),
		CREATE("can_create_author"),
		DELETE("can_delete_author");

		Author(String name) {
			this.name = name;
		}

		private final String name;

		@Override
		public String getName() {
			return name;
		}
	}

	public enum Publisher implements EntityPermission {
		READ("can_view_publisher"),
		UPDATE("can_update_publisher"),
		CREATE("can_create_publisher"),
		DELETE("can_delete_publisher");

		Publisher(String name) {
			this.name = name;
		}

		private final String name;

		@Override
		public String getName() {
			return name;
		}
	}

	public enum Category implements EntityPermission {
		READ("can_view_category"),
		UPDATE("can_update_category"),
		CREATE("can_create_category"),
		DELETE("can_delete_category");

		Category(String name) {
			this.name = name;
		}

		private final String name;

		@Override
		public String getName() {
			return name;
		}
	}

	public enum Checkout implements EntityPermission {
		READ("can_view_checkout"),
		UPDATE("can_update_checkout"),
		CREATE("can_create_checkout"),
		DELETE("can_delete_checkout");

		Checkout(String name) {
			this.name = name;
		}

		private final String name;

		@Override
		public String getName() {
			return name;
		}
	}

	public enum Fine implements EntityPermission {
		READ("can_view_fine"),
		UPDATE("can_update_fine"),
		CREATE("can_create_fine"),
		DELETE("can_delete_fine");

		Fine(String name) {
			this.name = name;
		}

		private final String name;

		@Override
		public String getName() {
			return name;
		}
	}

	public enum User implements EntityPermission {
		READ("can_view_user"),
		UPDATE("can_update_user"),
		CREATE("can_create_user"),
		DELETE("can_delete_user");

		User(String name) {
			this.name = name;
		}

		private final String name;

		@Override
		public String getName() {
			return name;
		}
	}

	public enum Reservation implements EntityPermission {
		READ("can_view_reservation"),
		UPDATE("can_update_reservation"),
		CREATE("can_create_reservation"),
		DELETE("can_delete_reservation");

		Reservation(String name) {
			this.name = name;
		}

		private final String name;

		@Override
		public String getName() {
			return name;
		}
	}

	public enum Role implements EntityPermission {
		READ("can_view_role"),
		UPDATE("can_update_role"),
		CREATE("can_create_role"),
		DELETE("can_delete_role");

		Role(String name) {
			this.name = name;
		}

		private final String name;

		@Override
		public String getName() {
			return name;
		}
	}

	public enum Permission implements EntityPermission {
		READ("can_view_permission"),
		UPDATE("can_update_permission"),
		CREATE("can_create_permission"),
		DELETE("can_delete_permission");

		Permission(String name) {
			this.name = name;
		}

		private final String name;

		@Override
		public String getName() {
			return name;
		}
	}

}
