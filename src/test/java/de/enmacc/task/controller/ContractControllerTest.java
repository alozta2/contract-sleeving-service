package de.enmacc.task.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.junit.Assert.assertEquals;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractControllerTest {

    @LocalServerPort
    protected int port;

    @Before
    public void beforeTest() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void addContract() {
        //language=JSON
        String json = "{\n" +
                "  \"aCompany\": \"company_5\",\n" +
                "  \"anotherCompany\": \"company_2\",\n" +
                "  \"material\": { \"name\" : \"gas\" },\n" +
                "  \"amount\": 2000\n" +
                "}";

        String response =
        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("contracts")
        .then()
        	.extract()
        		.jsonPath()
        			.getString("response");
        assertEquals(response, "Success");					//create a new contract
    }
    
    @Test
    public void addExistingContract() {
        //language=JSON
        String json = "{\n" +
                "  \"aCompany\": \"company_5\",\n" +
                "  \"anotherCompany\": \"company_6\",\n" +
                "  \"material\": { \"name\" : \"gas\" },\n" +
                "  \"amount\": 2000\n" +
                "}";

        String response =
        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("contracts")
        .then()
        	.extract()
        		.jsonPath()
        			.getString("response");
        
        String response2 =
        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("contracts")
        .then()
        	.extract()
        		.jsonPath()
        			.getString("response");
        assertEquals(response2, "Contract is not valid.");		//same contract cannot be created again
    }
    
    @Test
    public void addContractWithNoCompanies() {
        //language=JSON
        String json = "{\n" +
                "  \"material\": { \"name\" : \"gas\" },\n" +
                "  \"amount\": 2000\n" +
                "}";

        String response =
        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("contracts")
        .then()
        	.extract()
        		.jsonPath()
        			.getString("response");
        assertEquals(response, "Contract is not valid.");
    }
    
    @Test
    public void addContractWithNoACompany() {
        //language=JSON
    	String json = "{\n" +
                "  \"anotherCompany\": \"company_6\",\n" +
                "  \"material\": { \"name\" : \"gas\" },\n" +
                "  \"amount\": 2000\n" +
                "}";

        String response =
        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("contracts")
        .then()
        	.extract()
        		.jsonPath()
        			.getString("response");
        assertEquals(response, "Contract is not valid.");
    }
    
    @Test
    public void addContractWithNoAnotherCompany() {
        //language=JSON
    	String json = "{\n" +
                "  \"aCompany\": \"company_5\",\n" +
                "  \"material\": { \"name\" : \"gas\" },\n" +
                "  \"amount\": 2000\n" +
                "}";

        String response =
        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("contracts")
        .then()
        	.extract()
        		.jsonPath()
        			.getString("response");
        assertEquals(response, "Contract is not valid.");
    }
    
    @Test
    public void addContractWithNoMaterial() {
        //language=JSON
    	String json = "{\n" +
                "  \"aCompany\": \"company_5\",\n" +
                "  \"anotherCompany\": \"company_6\",\n" +
                "  \"amount\": 2000\n" +
                "}";

        String response =
        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("contracts")
        .then()
        	.extract()
        		.jsonPath()
        			.getString("response");
        assertEquals(response, "Contract is not valid.");
    }
    
    @Test
    public void addContractWithNoMaterialName() {
        //language=JSON
    	String json = "{\n" +
                "  \"aCompany\": \"company_5\",\n" +
                "  \"anotherCompany\": \"company_6\",\n" +
                "  \"material\": {  },\n" +
                "  \"amount\": 2000\n" +
                "}";

        String response =
        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("contracts")
        .then()
        	.extract()
        		.jsonPath()
        			.getString("response");
        assertEquals(response, "Contract is not valid.");
    }
    
    @Test
    public void addContractWithNoAmount() {
        //language=JSON
    	String json = "{\n" +
                "  \"aCompany\": \"company_5\",\n" +
                "  \"anotherCompany\": \"company_6\",\n" +
                "  \"material\": { \"name\" : \"gas\" }\n" +
                "}";

        String response =
        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("contracts")
        .then()
        	.extract()
        		.jsonPath()
        			.getString("response");
        assertEquals(response, "Contract is not valid.");
    }
    
    @Test
    public void addContractWithZeroAmount() {
        //language=JSON
    	String json = "{\n" +
                "  \"aCompany\": \"company_5\",\n" +
                "  \"anotherCompany\": \"company_6\",\n" +
                "  \"material\": { \"name\" : \"gas\" },\n" +
                "  \"amount\": 0\n" +
                "}";

        String response =
        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("contracts")
        .then()
        	.extract()
        		.jsonPath()
        			.getString("response");
        assertEquals(response, "Contract is not valid.");
    }

    @Test
    public void getAllPossibleSleevesBetweenTwoCompanies() {

    	String response =
        given()
            .contentType(ContentType.JSON)
            .param("aCompany", "company_1")
            .param("anotherCompany", "company_7")
        .when()
            .get("sleeves")
        .then()
    		.extract()
    			.jsonPath()
    				.getString("response");
    	assertEquals(response, "Success");
    }

    @Test
    public void getAllPossibleSleevesOnlyOneCompanies1() {

    	String response =
        given()
            .contentType(ContentType.JSON)
            .param("aCompany", "company_1")
        .when()
            .get("sleeves")
        .then()
    		.extract()
    			.jsonPath()
    				.getString("response");
    	assertEquals(response, "Two companies have to be specified to calculate sleeves.");
    }

    @Test
    public void getAllPossibleSleevesOnlyOneCompanies2() {

    	String response =
        given()
            .contentType(ContentType.JSON)
            .param("anotherCompany", "company_7")
        .when()
            .get("sleeves")
        .then()
    		.extract()
    			.jsonPath()
    				.getString("response");
    	assertEquals(response, "Two companies have to be specified to calculate sleeves.");
    }

    @Test
    public void getAllPossibleSleevesOnlyOneCompanies3() {

    	String response =
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("sleeves")
        .then()
    		.extract()
    			.jsonPath()
    				.getString("response");
    	assertEquals(response, "Two companies have to be specified to calculate sleeves.");
    }

    @Test
    public void getAllPossibleSleevesOnlyTheLowestCost() {

    	int size =
        given()
            .contentType(ContentType.JSON)
            .param("aCompany", "company_1")
            .param("anotherCompany", "company_7")
            .formParam("option", "best")
        .when()
            .get("sleeves")
        .then()
    		.extract()
    			.jsonPath()
    				.getList("data")
    					.size();
    	assertEquals(size, 1);
    }
    
    @Test
    public void getLast2Contracts() {
    	
    	int size =
    	        given()
    	            .contentType(ContentType.JSON)
    	            .param("aCompany", "company_1")
    	            .param("limit", 2)
    	        .when()
    	            .get("history")
    	        .then()
    	    		.extract()
    	    			.jsonPath()
    	    				.getList("data")
    	    					.size();
    	    	assertEquals(size, 2);
    }
    
    @Test
    public void getLast2ContractsWithNoCompanyName() {
    	
    	String response=
    	        given()
    	            .contentType(ContentType.JSON)
    	            .param("limit", 2)
    	        .when()
    	            .get("history")
    	        .then()
    	    		.extract()
    	    			.jsonPath()
    	    				.getString("response");
    	    	assertEquals(response, "A company must be specified to retrieve its history.");
    }
}