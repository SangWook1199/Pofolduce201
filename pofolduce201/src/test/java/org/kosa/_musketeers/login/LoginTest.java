package org.kosa._musketeers.login;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
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
	public void loginTest() throws Exception {
		
		mockMvc.perform(post("/login/processing")
				.param("email", "email")
				.param("password", "1234")
		).andExpect(status().isFound())
		.andExpect(redirectedUrl("/home"));
	}
	
	@Test
	public void getUserIdByEmailPwdTest() {
		
		//given
		String email = "user1@example.com";
		String password = "pass1234";
		
		//when
		int id = mapper.getUserIdByEmailPwd(email, password);
		
		//then
		assertThat(id).isEqualTo(1);
	}

}
