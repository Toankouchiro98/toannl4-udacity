package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getUnauthorizedHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getNoteTab(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();
	}

	@Test
	public void createNote() {
		doMockSignUp("checkFirstName","checkLastName","default","1234");

		doLogIn("default", "1234");
		WebElement notesTab= driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes")));
		Assertions.assertTrue(driver.findElement(By.id("nav-notes")).isDisplayed());

		WebElement addNoteButton= driver.findElement(By.id("add-note"));
		addNoteButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputTitle = driver.findElement(By.id("note-title"));
		inputTitle.click();
		inputTitle.sendKeys("NoteTitle");

		WebElement inputDescription = driver.findElement(By.id("note-description"));
		inputDescription.click();
		inputDescription.sendKeys("NoteDescription.");

		WebElement submitNote = driver.findElement(By.id("save-note"));
		submitNote.click();

		getNoteTab();

	}

	@Test
	public void editNote()  {
		doMockSignUp("checkFirstName","checkLastName","default","1234");
		doLogIn("default", "1234");

		WebElement notesTab= driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();

		createNote();
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-note")));
		WebElement btnEditNote = driver.findElement(By.id("edit-note"));
		btnEditNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputTitle = driver.findElement(By.id("note-title"));
		inputTitle.click();
		inputTitle.clear();
		inputTitle.sendKeys("Test Update");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement inputDescription = driver.findElement(By.id("note-description"));
		inputDescription.click();
		inputDescription.clear();
		inputDescription.sendKeys("UpdateDescription");

		WebElement submitNote = driver.findElement(By.id("save-note"));
		submitNote.click();

		getNoteTab();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		Assertions.assertTrue(driver.findElement(By.id("noteDescription")).getText().contains("UpdateDescription"));

	}

	@Test
	public void deleteNote() {

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		createNote();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note")));
		WebElement btnDelNote = driver.findElement(By.id("delete-note"));
		btnDelNote.click();

		getNoteTab();

	}

	public void getCredentialsTab(){
		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-credentials-tab")).click();
	}

	@Test
	public void createCredentials(){
		doMockSignUp("checkFirstName","checkLastName","default","1234");
		doLogIn("default", "1234");

		WebElement CreTab= driver.findElement(By.id("nav-credentials-tab"));
		CreTab.click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials")));
		Assertions.assertTrue(driver.findElement(By.id("nav-credentials")).isDisplayed());

		WebElement addCreButton= driver.findElement(By.id("add-credentials"));
		addCreButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement inputurl = driver.findElement(By.id("credential-url"));
		inputurl.click();
		inputurl.sendKeys("Localhost");

		WebElement inputUserNm = driver.findElement(By.id("credential-username"));
		inputUserNm.click();
		inputUserNm.sendKeys("credentialName");

		WebElement inputPass = driver.findElement(By.id("credential-password"));
		inputPass.click();
		inputPass.sendKeys("credentialPassword");

		WebElement submitCre = driver.findElement(By.id("credential-save"));
		submitCre.click();

		getCredentialsTab();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		Assertions.assertTrue(driver.findElement(By.id("credentialUsername")).getText().contains("credentialName"));
	}

	@Test
	public void editCredentials(){

		createCredentials();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials")));
		Assertions.assertTrue(driver.findElement(By.id("nav-credentials")).isDisplayed());

		WebElement addCreButton= driver.findElement(By.id("edit-credential"));
		addCreButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement inputurl = driver.findElement(By.id("credential-url"));
		inputurl.click();
		inputurl.clear();
		inputurl.sendKeys("http://localhost:");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement inputUserNm = driver.findElement(By.id("credential-username"));
		inputUserNm.click();
		inputUserNm.clear();
		inputUserNm.sendKeys("New credential userName");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement inputPass = driver.findElement(By.id("credential-password"));
		inputPass.click();
		inputPass.clear();
		inputPass.sendKeys("pass");

		WebElement submitCre = driver.findElement(By.id("credential-save"));
		submitCre.click();

		getCredentialsTab();
	}

	@Test
	public void deleteCre() {
		createCredentials();
		WebElement btnDel = driver.findElement(By.id("delete-credential"));
		btnDel.click();
		getCredentialsTab();
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}



}
