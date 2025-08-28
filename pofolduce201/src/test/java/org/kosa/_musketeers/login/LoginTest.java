package org.kosa._musketeers.login;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoginTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserMapper mapper;
	
	@Test
	public void loginSuccessTest() throws Exception {
		
		//given
		String email = "user1@example.com";
		String password = "pass1234";
		
		//when
		mockMvc.perform(post("/login/processing")
				.param("email", email)
				.param("password", password)
				
		//then
		).andExpect(status().isFound())
		.andExpect(redirectedUrl("/home"));
	}
	
	@Test
	public void loginFailTest() throws Exception {
		String email = "wrongemail";
		String password = "pass1234";
		
		//when
		mockMvc.perform(post("/login/processing")
				.param("email", email)
				.param("password", password)
				
		//then
		).andExpect(status().isOk())
        .andExpect(view().name("pages/login/login"))
        .andExpect(model().attributeExists("loginFailMessage"));
	}
	
	@Test
	public void getUserIdByEmailPwdTest() {
		
		//given
		String email = "user1@example.com";
		String password = "pass1234";
		
		//when
		User user = mapper.getUserByEmail(email, password);
		
		//then
		assertThat(user).isNotNull();
		assertThat(user.getUserId()).isEqualTo(1);
	}

}
