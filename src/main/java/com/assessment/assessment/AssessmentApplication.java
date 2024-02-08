package com.assessment.assessment;

import com.assessment.assessment.dto.Data;
import com.assessment.assessment.dto.Response;
import com.assessment.assessment.dto.UserRequest;
import com.assessment.assessment.entity.User;
import com.assessment.assessment.exceptions.customException.UserNotFoundException;
import com.assessment.assessment.repository.UserRepository;
import com.assessment.assessment.service.UserService;
import com.assessment.assessment.service.serviceImpl.UserServiceImpl;
import com.assessment.assessment.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssessmentApplication {


//	@LocalServerPort
//	private int port;

	@MockBean
	private UserRepository userRepository;
	//	@Autowired
//	private TestRestTemplate restTemplate;
	@InjectMocks
	private ResponseUtils responseUtils;

//	@InjectMocks
//	private UserServiceImpl userService;

//	public AppTests(UserRepository userRepository, ResponseUtils responseUtils) {
//		this.userRepository = userRepository;
//		this.responseUtils = responseUtils;
//	}

	@Autowired
	public AppTests(UserRepository userRepository, ResponseUtils responseUtils, UserServiceImpl userService) {
		this.userRepository = userRepository;
		this.responseUtils = responseUtils;
		this.userService = userService;
	}

	@InjectMocks
	private UserServiceImpl userService;


//	private URL base;
//
//
//	AppTests() {
//	}
//
//	@BeforeEach
//	public void setUp() throws Exception {
//		this.base = new URL("http://localhost:" + port + "/greeting");
//	}
//
//	@Test
//	@Description("/greeting endpoint returns expected response for default name")
//	public void greeting_ExpectedResponseWithDefaultName() {
//		ResponseEntity<String> response = restTemplate.getForEntity(base.toString(), String.class);
//
//		assertEquals(200, response.getStatusCode().value());
//		assertEquals("Hello World", response.getBody());
//	}
//
//	@Test
//	@Description("/greeting endpoint returns expected response for specified name parameter")
//	public void greeting_ExpectedResponseWithNameParam() {
//		ResponseEntity<String> response = restTemplate.getForEntity(base.toString() + "?name=John", String.class);
//
//		assertEquals(200, response.getStatusCode().value());
//		assertEquals("Hello John", response.getBody());
//	}


	@Test
	public void testRegisterUser_successfulRegistration() {
		// Set up expected user data
		String email = "test@example.com";
		String firstName = "John";
		String lastName = "Doe";
		String otherName = "Middle";
		BigDecimal accountBalance = BigDecimal.valueOf(10000);
		// ... other user details

		// Mock user existence check
		when(userRepository.existsByEmail(email)).thenReturn(false);

		// Mock account number generation (if applicable)
		String generatedAccountNumber = "1234567890";
		int lengthOfAccNumber = 10;
		when(responseUtils.generateAccountNumber(lengthOfAccNumber)).thenReturn(generatedAccountNumber);

		// Create user request
		UserRequest userRequest = UserRequest.builder()
				.email(email)
				.firstName(firstName)
				.lastName(lastName)
				.otherName(otherName)
				.accountBalance(accountBalance)
				// ... other user details
				.build();

		// Perform the test
		Response response = userService.registerUser(userRequest);

		// Verify user creation and saving
//		Mockito.verify(userRepository).save(argThat(user ->
//				user.getEmail().equals(email) &&
//						user.getFirstName().equals(firstName) &&
//						user.getLastName().equals(lastName) &&
//						user.getOtherName().equals(otherName) &&
//						user.getAccountBalance().equals(accountBalance) &&
//						// ... other expected user details
//						user.getAccountNumber().equals(generatedAccountNumber)
//		));

		// Verify response structure and data
		assertEquals(responseUtils.SUCCESS, response.getResponseCode());
		assertEquals(responseUtils.USER_REGISTERED_SUCCESS, response.getResponseMessage());
		Data data = (Data) response.getData();
		assertEquals(userRequest.getAccountBalance(), data.getAccountBalance());
		assertEquals(generatedAccountNumber, data.getAccountNumber());
		assertEquals(firstName + " " + lastName + " " + otherName, data.getAccountName());
	}

	@Test
	public void testRegisterUser_emailAlreadyExists() {
		String email = "test@example.com";
		when(userRepository.existsByEmail(email)).thenReturn(true);

		// Create user request
		UserRequest userRequest = UserRequest.builder()
				.email(email)
				.build();

		// Perform the test
		try {
			userService.registerUser(userRequest);
//				fail("Expected UserNotFoundException");
		} catch (UserNotFoundException e) {
			// Expected exception
			assertEquals("User not found", e.getMessage());
		}

		// Verify no user creation (optional)
		verify(userRepository, never()).save(any(User.class));
	}

	// ... other potential test cases (optional)

}
