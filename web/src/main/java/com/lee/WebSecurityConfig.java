/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

/**
 * Customizes Spring Security configuration.
 *
 * @author Rob Winch
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
				// See https://jira.springsource.org/browse/SPR-11496
				.headers()
				.addHeaderWriter(
						new XFrameOptionsHeaderWriter(
								XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
				.and()

				.formLogin().defaultSuccessUrl("/dak/index.jsp")
				.loginPage("/dak/login.jsp")
				.failureUrl("/dak/login.jsp?error").permitAll().and().logout()
				.logoutSuccessUrl("/dak/login.jsp?logout")
				.logoutUrl("/dak/logout.html").permitAll().and()
				.authorizeRequests().antMatchers("/dak/login.css").permitAll()
				.antMatchers("/dak/**").authenticated().and();

		// @formatter:on
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth.inMemoryAuthentication().withUser("fabrice").password("fab123")
				.roles("USER").and().withUser("paulson").password("bond")
				.roles("ADMIN", "USER");
		// @formatter:on
	}

}