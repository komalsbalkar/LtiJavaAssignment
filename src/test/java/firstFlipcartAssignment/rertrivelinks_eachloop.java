package firstFlipcartAssignment;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class rertrivelinks_eachloop {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver=new ChromeDriver();
	    driver.get("https://www.flipkart.com/?");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		List<WebElement> links=driver.findElements(By.tagName("a"));
		System.out.println(".....................forEach......................");
		//1 way normal each forloop
		for(WebElement link:links) {
			System.out.println(link.getText());
		}
		System.out.println(".....................lambda expression......................");
		//2 way --------------lambda expression-----------------------
		links.forEach(ele -> System.out.println(ele.getText()));

		System.out.println(".....................stream......................");
		//3 way------------stream------------------------------
		List<String> linklist=links.stream().
		filter(ele->!ele.getText().equals("")).
		map(ele->ele.getText()).
		collect(Collectors.toList());
		
		linklist.forEach(ele -> System.out.println(ele));
		
		System.out.println(".....................parellel stream......................");
		//4 way--------------------parellel stream------------------------------
		
		List<String> linklist1=links.parallelStream().
				filter(ele->!ele.getText().equals("")).
				map(ele->ele.getText()).
				collect(Collectors.toList());
				
				linklist1.forEach(ele -> System.out.println(ele));
		
		driver.close();
	}

}
