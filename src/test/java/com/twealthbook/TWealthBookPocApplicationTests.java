package com.twealthbook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TWealthBookPocApplicationTests {

	@Test
	public void contextLoads() {
	}

	public void createUser(){
	    String JSONStringCreateClient = "{\n" +
                "  \"clientAadharCardNo\": \"\",\n" +
                "  \"clientActiveStatus\": 2,\n" +
                "  \"clientBrithDate\": \"1981-01-01\",\n" +
                "  \"clientCellNo\": 9819702056,\n" +
                "  \"clientFirstName\": \"Shalmi\",\n" +
                "  \"clientId\": 0,\n" +
                "  \"clientJoiningDate\": \"2017-04-01\",\n" +
                "  \"clientLastName\": \"Gawde\",\n" +
                "  \"clientMaritalStatus\": 2,\n" +
                "  \"clientMiddleName\": \"Yatin\",\n" +
                "  \"clientPanCardNo\": \"\"\n" +
                "}";
		String JSONStringCreateUser = "{\n" +
				"  \"active\": 1,\n" +
				"  \"password\": \"QUALTIY80\",\n" +
				"  \"userAddLine1\": \"10 Ramkunj\",\n" +
				"  \"userAddLine2\": \"Dr. Raut Road\",\n" +
				"  \"userAddLine3\": \"Shivaji Park, Dadar West\",\n" +
				"  \"userCity\": \"Mumbai\",\n" +
				"  \"userContactNo\": 9999999999,\n" +
				"  \"userContactNoAlternate\": 0,\n" +
				"  \"userEmailId\": \"praksashbhogle@rediffmail.com\",\n" +
				"  \"userFirstName\": \"Prakash\",\n" +
				"  \"userId\": 0,\n" +
				"  \"userJoiningDate\": \"2017-04-01\",\n" +
				"  \"userLastLoginTime\": \"2017-04-01\",\n" +
				"  \"userLastName\": \"Bhogle\",\n" +
				"  \"userLoginId\": \"9999999999\",\n" +
				"  \"userPIN\": \"400028\",\n" +
				"  \"userState\": \"Maharashtra\"\n" +
				"}";
	}

	public void getUserByUserByLoginID(){
		String JSONString = "{\n" +
				"  \"active\": 0,\n" +
				"  \"password\": \"\",\n" +
				"  \"userAddLine1\": \"\",\n" +
				"  \"userAddLine2\": \"\",\n" +
				"  \"userAddLine3\": \"\",\n" +
				"  \"userCity\": \"\",\n" +
				"  \"userContactNo\": 0,\n" +
				"  \"userContactNoAlternate\": 0,\n" +
				"  \"userEmailId\": \"\",\n" +
				"  \"userFirstName\": \"\",\n" +
				"  \"userId\": 0,\n" +
				"  \"userJoiningDate\": \"\",\n" +
				"  \"userLastLoginTime\": \"\",\n" +
				"  \"userLastName\": \"\",\n" +
				"  \"userLoginId\": \"9819980951\",\n" +
				"  \"userPIN\": \"\",\n" +
				"  \"userState\": \"\"\n" +
				"}";
	}

}
