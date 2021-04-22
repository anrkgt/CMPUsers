package com.campaign.user.campaignuser;

import com.campaign.user.campaignuser.dto.GetUserResponseDTO;
import com.campaign.user.campaignuser.dto.UserRequestDTO;
import com.campaign.user.campaignuser.dto.UserUpdateRequestDTO;
import com.campaign.user.campaignuser.entity.User;
import com.campaign.user.campaignuser.repository.EmbeddedMongoDbIntegrationTest;
import com.campaign.user.campaignuser.userenum.StateType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(EmbeddedMongoDbIntegrationTest.class)
@Profile("test")
class CampaignUserApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	private int port;

	@Test
	void testCreateUser() {
		//Given
		UserRequestDTO usrRequestDto = new UserRequestDTO();
		usrRequestDto.setPhoneNumber("1234567890");
		usrRequestDto.setName("Anisa");
		usrRequestDto.setAge(20);
		usrRequestDto.setEmail("gmail@anisa.com");
		usrRequestDto.setAddress("USA, Park Street");
		usrRequestDto.setState("Active");
		HttpEntity<UserRequestDTO> userEntity = new HttpEntity<>(usrRequestDto);
		//When
		ResponseEntity<String> createUser_Response = this.testRestTemplate.
				withBasicAuth("admin", "test123").exchange("/api/v1/users", HttpMethod.POST,userEntity, String.class);
		//Then
		assertEquals(HttpStatus.CREATED, createUser_Response.getStatusCode());
		assertThat(createUser_Response).isNotNull();
		assertThat(createUser_Response.getBody()).isNotNull();
		assertThat(createUser_Response.getBody()).isNotBlank();
	}

	@Test
	void testBadRequest_For_CreateUser() {
		//Given
		User user = new User();
		HttpEntity<User> userEntity = new HttpEntity<User>(user);
		//When
		ResponseEntity<String> createUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users", HttpMethod.POST,userEntity, String.class);
		//Then
		assertEquals(HttpStatus.BAD_REQUEST, createUser_Response.getStatusCode());
	}

	@Test
	void testGetUser() {
		//Given
		UserRequestDTO usrRequestDto = getUserRequestDTO();
		HttpEntity<UserRequestDTO> userEntity = new HttpEntity<>(usrRequestDto);
		ResponseEntity<String> createUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users", HttpMethod.POST,userEntity, String.class);
		String userId = createUser_Response.getBody().trim();

		//When
		ResponseEntity<User> getUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users/" +userId , HttpMethod.GET,null, User.class);

		//Then
		User db_user = getUser_Response.getBody();
		assertEquals(HttpStatus.CREATED, createUser_Response.getStatusCode());
		assertThat(createUser_Response).isNotNull();
		assertThat(createUser_Response.getBody()).isNotNull();
		assertEquals(HttpStatus.OK, getUser_Response.getStatusCode());
		assertThat(getUser_Response).isNotNull();
		assertThat(getUser_Response.getBody()).isNotNull();
		assertEquals(db_user.getPhoneNumber(), usrRequestDto.getPhoneNumber());
		assertEquals(db_user.getName(), usrRequestDto.getName());
		assertEquals(db_user.getAge(), usrRequestDto.getAge());
		assertEquals(db_user.getEmail(), usrRequestDto.getEmail());
		assertEquals(db_user.getState(), usrRequestDto.getState());
		assertEquals(db_user.getId(), userId);
	}

	@Test
	void testDeleteUser() {
		//Given
		UserRequestDTO usrRequestDto = getUserRequestDTO();
		HttpEntity<UserRequestDTO> userEntity = new HttpEntity<>(usrRequestDto);
		ResponseEntity<String> createUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users", HttpMethod.POST,userEntity, String.class);
		String userId = createUser_Response.getBody().trim();

		//When
		ResponseEntity<Void> deleteUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users/" +userId , HttpMethod.DELETE,null, Void.class);

		//Then
		assertEquals(HttpStatus.OK, deleteUser_Response.getStatusCode());
	}

	@Test
	void testUpdateUser() {
		//Given
		UserRequestDTO usrRequestDto = getUserRequestDTO();
		HttpEntity<UserRequestDTO> userEntity = new HttpEntity<UserRequestDTO>(usrRequestDto);
		ResponseEntity<String> createUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users", HttpMethod.POST,userEntity, String.class);
		String userId = createUser_Response.getBody().trim();

		UserUpdateRequestDTO userUpdateDto = new UserUpdateRequestDTO();
		userUpdateDto.setAddress("USA");
		userUpdateDto.setPhoneNumber("0987654321");
		userUpdateDto.setEmail("facebook@anisa.com");
		userUpdateDto.setAge(35);
		HttpEntity<UserUpdateRequestDTO> user_http_entity = new HttpEntity<>(userUpdateDto);
		//When
		ResponseEntity<Void> updateUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users/" +userId , HttpMethod.PUT,user_http_entity, Void.class);
		//Then
		ResponseEntity<User> getUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users/" +userId , HttpMethod.GET,null, User.class);
		User db_user = getUser_Response.getBody();

		assertEquals(HttpStatus.CREATED, createUser_Response.getStatusCode());
		assertEquals(HttpStatus.OK, updateUser_Response.getStatusCode());
		assertEquals(HttpStatus.OK, getUser_Response.getStatusCode());

		assertEquals(userUpdateDto.getPhoneNumber(), db_user.getPhoneNumber());
		assertEquals(usrRequestDto.getName(), db_user.getName());
		assertEquals(userUpdateDto.getAge(), db_user.getAge());
		assertEquals(userUpdateDto.getEmail(), db_user.getEmail());
		assertEquals(userUpdateDto.getAddress(), db_user.getAddress());
		assertEquals(userId, db_user.getId());
	}

	@Test()
	void testUpdateUser_With_Invalid_Id() {
		//Given
		UserRequestDTO usrRequestDto = getUserRequestDTO();
		HttpEntity<UserRequestDTO> userEntity = new HttpEntity<>(usrRequestDto);
		ResponseEntity<String> createUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users", HttpMethod.POST,userEntity, String.class);

		UserUpdateRequestDTO userUpdateDto = new UserUpdateRequestDTO();
		userUpdateDto.setAddress("USA");
		userUpdateDto.setPhoneNumber("0987654321");
		userUpdateDto.setEmail("facebook@anisa.com");
		userUpdateDto.setAge(35);
		HttpEntity<UserUpdateRequestDTO> user_http_entity = new HttpEntity<>(userUpdateDto);
		//When
		ResponseEntity<Void> updateUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users/" +"999999999" , HttpMethod.PUT,user_http_entity, Void.class);
		//Then
		assertEquals(HttpStatus.CREATED, createUser_Response.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, updateUser_Response.getStatusCode());
	}

	private UserRequestDTO getUserRequestDTO() {
		UserRequestDTO usrRequestDto = new UserRequestDTO();
		usrRequestDto.setPhoneNumber("1234567890");
		usrRequestDto.setName("Anisa");
		usrRequestDto.setAge(20);
		usrRequestDto.setEmail("gmail@anisa.com");
		usrRequestDto.setState("Active");
		return usrRequestDto;
	}

	@Test()
	void testActivateUser_Invalid() {
		//Given
		UserRequestDTO usrRequestDto = getUserRequestDTO();
		HttpEntity<UserRequestDTO> userEntity = new HttpEntity<>(usrRequestDto);
		ResponseEntity<String> createUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users", HttpMethod.POST,userEntity, String.class);
		String userId = createUser_Response.getBody().trim();

		//When
		ResponseEntity<GetUserResponseDTO> activateUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users/" +userId+"/activate" , HttpMethod.PUT,null, GetUserResponseDTO.class);
		//Then
		assertEquals(HttpStatus.CREATED, createUser_Response.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, activateUser_Response.getStatusCode());
	}

	@Test()
	void testActivateUser_Terminated() {
		//Given
		UserRequestDTO usrRequestDto = getUserRequestDTO();
		usrRequestDto.setState(StateType.TERMINATED.getType());
		HttpEntity<UserRequestDTO> userEntity = new HttpEntity<>(usrRequestDto);
		ResponseEntity<String> createUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users", HttpMethod.POST,userEntity, String.class);
		String userId = createUser_Response.getBody().trim();

		//When
		ResponseEntity<GetUserResponseDTO> activateUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users/" +userId+"/activate" , HttpMethod.PUT,null, GetUserResponseDTO.class);
		//Then
		assertEquals(HttpStatus.CREATED, createUser_Response.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, activateUser_Response.getStatusCode());
	}

	@Test()
	void testActivateUser_Valid() {
		//Given
		UserRequestDTO usrRequestDto = getUserRequestDTO();
		usrRequestDto.setState(StateType.SUSPENDED.getType());
		HttpEntity<UserRequestDTO> userEntity = new HttpEntity<>(usrRequestDto);
		ResponseEntity<String> createUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users", HttpMethod.POST,userEntity, String.class);
		String userId = createUser_Response.getBody().trim();

		//When
		ResponseEntity<GetUserResponseDTO> activateUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users/" +userId+"/activate" , HttpMethod.PUT,null, GetUserResponseDTO.class);
		//Then

		assertEquals(HttpStatus.CREATED, createUser_Response.getStatusCode());
		assertEquals(HttpStatus.OK, activateUser_Response.getStatusCode());
		assertEquals(StateType.ACTIVE.getType(), activateUser_Response.getBody().getState());
	}

	@Test()
	void testSuspendUser_Terminated() {
		//Given
		UserRequestDTO usrRequestDto = getUserRequestDTO();
		usrRequestDto.setState(StateType.TERMINATED.getType());
		HttpEntity<UserRequestDTO> userEntity = new HttpEntity<>(usrRequestDto);
		ResponseEntity<String> createUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users", HttpMethod.POST,userEntity, String.class);
		String userId = createUser_Response.getBody().trim();

		//When
		ResponseEntity<GetUserResponseDTO> suspendUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users/" +userId+"/suspend" , HttpMethod.PUT,null, GetUserResponseDTO.class);
		//Then
		assertEquals(HttpStatus.CREATED, createUser_Response.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, suspendUser_Response.getStatusCode());
	}

	@Test()
	void testSuspendUser_Valid() {
		//Given
		UserRequestDTO usrRequestDto = getUserRequestDTO();
		HttpEntity<UserRequestDTO> userEntity = new HttpEntity<>(usrRequestDto);
		ResponseEntity<String> createUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users", HttpMethod.POST,userEntity, String.class);
		String userId = createUser_Response.getBody().trim();

		//When
		ResponseEntity<GetUserResponseDTO> activateUser_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
				.exchange("/api/v1/users/" +userId+"/suspend" , HttpMethod.PUT,null, GetUserResponseDTO.class);
		//Then

		assertEquals(HttpStatus.CREATED, createUser_Response.getStatusCode());
		assertEquals(HttpStatus.OK, activateUser_Response.getStatusCode());
		assertEquals(StateType.SUSPENDED.getType(), activateUser_Response.getBody().getState());
	}
}
