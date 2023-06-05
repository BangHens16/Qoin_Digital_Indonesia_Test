import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import groovy.json.JsonSlurper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObject
import java.time.LocalDate
import java.time.LocalTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

listUser().toString()
WS.delay(3)
singleUser().toString()
WS.delay(3)
updateUser().toString()
WS.delay(5)
registerUser().toString()
	
String listUser() {
	
	def page = GlobalVariable.pageSelected
	
	def response = WS.sendRequest(findTestObject('Object Repository/LIST USERS', 
		['page' : page]))
	
		def jsonResponse = new JsonSlurper().parseText(response.getResponseBodyContent())
		
		def idSelected = GlobalVariable.idSelectedListUser
		
			GlobalVariable.idResponse  = jsonResponse.data[idSelected].id
				GlobalVariable.emailResponse = jsonResponse.data[idSelected].email
					GlobalVariable.fistNameResponse = jsonResponse.data[idSelected].first_name
						GlobalVariable.lastNameResponse = jsonResponse.data[idSelected].last_name

						println GlobalVariable.idResponse
						
	def expectedPage = 1
	def expectedPerPage = 6
	def expectedTotal = 12
	def expectedTotalPage = 2
	
		WS.verifyResponseStatusCode(response, 200)
			WS.verifyElementPropertyValue(response, 'page', expectedPage)
				WS.verifyElementPropertyValue(response, 'per_page', expectedPerPage)
					WS.verifyElementPropertyValue(response, 'total', expectedTotal)
						WS.verifyElementPropertyValue(response, 'total_pages', expectedTotalPage)
						
}

String singleUser() {
	
	def userNumber = GlobalVariable.idResponse
	
	def response = WS.sendRequest(findTestObject('Object Repository/SINGLE USER', 
		['userNumber' : userNumber]))
	
		WS.verifyResponseStatusCode(response, 200)
			WS.verifyElementPropertyValue(response, 'data.id', GlobalVariable.idResponse)
				WS.verifyElementPropertyValue(response, 'data.email', GlobalVariable.emailResponse)
					WS.verifyElementPropertyValue(response, 'data.first_name', GlobalVariable.fistNameResponse)
						WS.verifyElementPropertyValue(response, 'data.last_name', GlobalVariable.lastNameResponse)

}

String updateUser() {
	
	def userNumber = GlobalVariable.idResponse
	def nameUpdate =  GlobalVariable.nameUpdate
	def jobUpdate =  GlobalVariable.jobUpdate
		
	def response = WS.sendRequest(findTestObject('Object Repository/UPDATE USER', 
		['nameUpdate' : nameUpdate 
		, 'jobUpdate' : jobUpdate
		, 'userNumber' : userNumber ]))
	
		WS.verifyResponseStatusCode(response, 200)
			WS.verifyElementPropertyValue(response, 'name', GlobalVariable.nameUpdate)
				WS.verifyElementPropertyValue(response, 'job', GlobalVariable.jobUpdate)
					
}

String registerUser() {
	
	def emailToRegister = GlobalVariable.emailResponse
	def passwordToRegister = GlobalVariable.passwordToRegister
	
	def response = WS.sendRequest(findTestObject('Object Repository/REGISTER - SUCCESSFUL',
		['emailToRegister' : emailToRegister
		, 'passwordToRegister' : passwordToRegister]))
	
		def jsonResponse = new JsonSlurper().parseText(response.getResponseBodyContent())
			GlobalVariable.token  = jsonResponse.token
			
		WS.verifyResponseStatusCode(response, 200)
			WS.verifyElementPropertyValue(response, 'id', GlobalVariable.idResponse)
				WS.verifyElementPropertyValue(response, 'token', GlobalVariable.token)
}
