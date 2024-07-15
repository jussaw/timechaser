package com.timechaser.security;

public enum UserRole {
    ADMIN("admin"),
	MANAGER("manager"),
	USER("user");

	public final String label;

	private UserRole(String label) {
		this.label = label;
	}
}
