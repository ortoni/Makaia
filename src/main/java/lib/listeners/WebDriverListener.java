package lib.listeners;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.Reporter;

public class WebDriverListener extends
Reporter implements WebDriverEventListener {

	public RemoteWebDriver webdriver;
	public EventFiringWebDriver driver;
	public int i = 1;
	WebDriverWait wait;


	public void beforeAlertAccept(WebDriver driver) {
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.alertIsPresent());
	}

	public void afterAlertAccept(WebDriver driver) {
		//System.out.println("The alert is accepted");
		reportStep("The alert is accepted", "pass");

	}

	public void afterAlertDismiss(WebDriver driver) {
		//System.out.println("The alert is dismissed");
		reportStep("The alert is dismissed", "pass");

	}

	public void beforeAlertDismiss(WebDriver driver) {	
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.alertIsPresent());
	}

	public void beforeNavigateTo(String url, WebDriver driver) {
	}

	public void afterNavigateTo(String url, WebDriver driver) {
		//System.out.println("The browser loaded the URL "+url);
		reportStep("The browser loaded the URL "+url, "pass");
		takeSnap();
	}

	public void beforeNavigateBack(WebDriver driver) {
	}

	public void afterNavigateBack(WebDriver driver) {
		//System.out.println("The browser has loaded the previous page from the history");
		reportStep("The browser has loaded the previous page from the history",	"pass");
		takeSnap();
	}

	public void beforeNavigateForward(WebDriver driver) {

	}

	public void afterNavigateForward(WebDriver driver) {
		reportStep("The browser has loaded the next page from the history", "pass");
		//	System.out.println("The browser has loaded the next page from the history");
		takeSnap();
	}

	public void beforeNavigateRefresh(WebDriver driver) {


	}

	public void afterNavigateRefresh(WebDriver driver) {
		reportStep("The browser has reloaded successfully", "pass");
		//System.out.println("The browser has reloaded successfully");
		takeSnap();
	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) {
	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(element));		
	}

	public void afterClickOn(WebElement element, WebDriver driver) {
		reportStep("The element "+element +" is clicked successfully", "pass");
		//System.out.println("The element "+element +" is clicked successfully");		
		takeSnap();
	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		reportStep("The value "+keysToSend[0]+" is entered successfully in element "+element, "pass");
		//System.out.println("The value "+keysToSend[0]+" is entered successfully in element "+element);	
		takeSnap();

	}

	public void beforeScript(String script, WebDriver driver) {

	}

	public void afterScript(String script, WebDriver driver) {

	}

	public void beforeSwitchToWindow(String windowName, WebDriver driver) {

	}

	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		System.out.println("The driver is moved to the window with title "+driver.getTitle());		
		takeSnap();

	}

	public void onException(Throwable throwable, WebDriver driver) {
		
		if(throwable instanceof NoSuchSessionException) {
			reportStep("Failed"+throwable.getMessage(), "fail");
			throw new RuntimeException();
		}
		else if(throwable instanceof NullPointerException) {
			reportStep("Failed"+throwable.getMessage(), "fail");
			throw new RuntimeException();
		}
		else if(throwable instanceof NoSuchElementException) {
			reportStep("Failed"+throwable.getMessage(), "fail");
			throw new RuntimeException();
		}
	}

	public <X> void beforeGetScreenshotAs(OutputType<X> target) {

	}

	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {

	}

	public long takeSnap(){
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L; 
		try {
			FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE) , new File("./reports/images/"+number+".jpg"));
		} catch (WebDriverException e) {
			System.out.println("The browser has been closed.");
		} catch (IOException e) {
			System.out.println("The snapshot could not be taken");
		}
		return number;
	}

}
