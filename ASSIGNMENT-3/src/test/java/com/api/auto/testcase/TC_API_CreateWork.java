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

public class TC_API_CreateWork {
	private String token;
	private Response response;
	private ResponseBody responseBody;
	private JsonPath jsonBody;
	//Chúng ta có thể tự tạo data
	private String myWork = "Freelancer";
	private String myExperience = "8 năm";
	private String myEducation = "Đại học";
	
	@BeforeClass
	public void init() {

	RestAssured.baseURI = PropertiesFileUtils.getProperty("baseurl");
	RestAssured.basePath = PropertiesFileUtils.getProperty("createWorkPath");
	// make body
	Map<String, Object> work = new HashMap<String, Object>();
    work.put("nameWork", myWork);
    work.put("experience", myExperience);
    work.put("education", myEducation);
    
    RequestSpecification req = RestAssured.given()
    		.contentType(ContentType.JSON)
    		.header("token", PropertiesFileUtils.getToken("token"))
			.body(work);
    response = req.post();
	responseBody = response.body();
	jsonBody = responseBody.jsonPath();
	System.out.println(" " + responseBody.asPrettyString());
    }
	@Test(priority = 0)
	public void TC01_Validate201Created() {
              // kiểm chứng status code
		assertEquals(response.getStatusCode(),201, "Status Check Failed!");
	}
	@Test(priority = 1)
	public void TC02_ValidateWorkId() {
              // kiểm chứng id
		assertTrue(response.asString().contains("id"), "Respone body không chứa trường id!");
	}
	@Test(priority = 2)
	public void TC03_ValidateNameOfWorkMatched() {
        // kiểm chứng response body có chứa trường nameWork
		assertTrue(response.asString().contains("nameWork"), "Respone body không chứa trường nameWork!");
              // kiểm chứng tên công việc nhận được có giống lúc tạo
		String actWork = jsonBody.getString("nameWork");
		assertEquals(actWork, myWork, "Giá trị trường nameWork không khớp với giá trị nameWork lúc tạo");
	}
	@Test(priority = 3)
	public void TC03_ValidateExperienceMatched() {
        // kiểm chứng response body có chứa trường experience
		assertTrue(response.asString().contains("experience"), "Respone body không chứa trường experience!");
              // kiểm chứng kinh nghiệm nhận được có giống lúc tạo
		String actEX = jsonBody.getString("experience");
		assertEquals(actEX, myExperience, "Giá trị trường experience không khớp với giá trị experience lúc tạo");
	}
	@Test(priority = 4)
	public void TC03_ValidateEducationMatched() {
        // kiểm chứng response body có chứa trường education
		assertTrue(response.asString().contains("education"), "Respone body không chứa trường education!");
              // kiểm chứng học vấn nhận được có giống lúc tạo
		String actEdu = jsonBody.getString("education");
		assertEquals(actEdu, myEducation, "Giá trị trường education không khớp với giá trị education lúc tạo");

	}
}
