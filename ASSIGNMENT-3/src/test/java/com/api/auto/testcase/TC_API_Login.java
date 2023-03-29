package com.api.auto.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.*;

import com.api.auto.utils.PropertiesFileUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class TC_API_Login {
	private String account;
	private String password;
	private Response response;
	private ResponseBody responseBody;
	private JsonPath jsonBody;
	PropertiesFileUtils pro = new PropertiesFileUtils();
	@BeforeClass
	public void init() {
		// Init data
		String baseUrl = pro.getProperty("baseurl");
		String loginPath = pro.getProperty("loginPath");
		account = pro.getProperty("account");
		password = pro.getProperty("password");
		RestAssured.baseURI = baseUrl;
		RestAssured.basePath = loginPath;
		// make body
				Map<String, Object> Body = new HashMap<String, Object>();
				Body.put("account",account);
				Body.put("password", password);
		RequestSpecification req = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(Body);
		response = req.post();
		responseBody = response.body();
		jsonBody = responseBody.jsonPath();
		System.out.println(" " + responseBody.asPrettyString());
		}
	@Test(priority = 0)
	public void TC01_Validate200Ok() {
		int actcode = response.getStatusCode();
		assertEquals(actcode,200, "Status Check Failed!");
	}
	@Test(priority = 1)
	public void TC02_ValidateMessage() {
              // kiểm chứng response body có chứa trường message hay không
		assertTrue(responseBody.asString().contains("message"),"Respone body không chứa trường message!");
		              // kiểm chứng trường message có = "Đăng nhập thành công
		String actmess = jsonBody.getString("message");
		String expmess = "Đăng nhập thành công";
		assertEquals(actmess, expmess, "Nội dung message sai");
		}
	@Test(priority = 2)
	public void TC03_ValidateToken() {
           // kiểm chứng response body có chứa trường token hay không
		assertTrue(responseBody.asString().contains("token"),"Respone body không chứa trường token!");
		String token = jsonBody.getString("token");
          // lưu lại token
		pro.saveToken("token", token);
	}
	@Test(priority = 3)
	public void TC05_ValidateUserType() {
         // kiểm chứng response body có chứa thông tin user và trường type hay không
		assertTrue(responseBody.asString().contains("user"),"Respone body không chứa thông tin user!");
		assertTrue(responseBody.asString().contains("type"),"Respone body không chứa trường type!");
         // kiểm chứng trường type có phải là “UNGVIEN”
		String actType = jsonBody.getString("user.type");
		assertEquals(actType, "UNGVIEN", "trường type không phải là “UNGVIEN”");
	}
	@Test(priority = 4)
	public void TC06_ValidateAccount() {
          // kiểm chứng response chứa trường account hay không
		assertTrue(responseBody.asString().contains("account"),"Respone body không chứa trường account!");
          // Kiểm chứng trường account có khớp với account đăng nhặp
		String actAccount = jsonBody.getString("user.account");
		assertEquals(actAccount, account, "trường account không khớp với account đăng nhặp");
          // kiểm chứng response chứa trường password hay không
		String actPass = jsonBody.getString("user.password");
        // Kiểm chứng trường password có khớp với password đăng nhặp
		assertEquals(actPass, password, "trường password không khớp với password đăng nhặp");
	}
}
