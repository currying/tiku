package com.toparchy.molecule.permission;

import javax.enterprise.event.Observes;

import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;

public class HttpSecurityConfiguration {

	public void onInit(@Observes SecurityConfigurationEvent event) {
		SecurityConfigurationBuilder builder = event.getBuilder();

		builder
			.http()
				.forGroup("basic")
					.authenticateWith()
						.form()
							.authenticationUri("/login.xhtml")
								.loginPage("/login.xhtml")
								.errorPage("/error.xhtml")
								.restoreOriginalRequest()
				.forPath("/logout").logout()
					.redirectTo("/index.xhtml")
				.forPath("/view/*", "basic")
				.forPath("/system/*", "basic")
					.authorizeWith()
					.group("adminstrators");
//					.authorizeWith()
//					.role("ADMINISTRATOR");
	}
}
