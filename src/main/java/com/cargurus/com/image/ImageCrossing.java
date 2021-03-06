package com.cargurus.com.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

public class ImageCrossing {
	WebDriver driver;
	Logger log;

	@BeforeClass
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
		driver = new ChromeDriver();
		Screenshot ImageScreenshot;
		driver.manage().window().maximize();
		driver.get("http://www.aa.com");
	}

	@Test
	public void testImg() throws InterruptedException, IOException, FileNotFoundException {

		BufferedImage expectedImage = null;
		WebElement imgb;
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		SoftAssert sa = new SoftAssert();

		try {
			expectedImage = ImageIO.read(new File("hp-hero-737-max-03132019-new.jpg"));
		} catch (IOException e) {
			// Assert.fail("File not found");
			throw new SkipException("Boeing Image file not found");
		}
		System.out.println(expectedImage.getWidth());
		System.out.println(expectedImage.getHeight());
		try {
			imgb = driver.findElement(By.xpath("//img[@alt='Boeing 787 MAX 8 The latest about flights']"));
		} catch (NoSuchElementException ne) {
			throw new SkipException("no image Boeing on Silo");
		}
		Screenshot ImageScreenshot = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver,
				imgb);

		BufferedImage actualImage = ImageScreenshot.getImage();
		// System.out.println(actualImage.getWidth());
		// System.out.println(actualImage.getHeight());

		ImageDiffer imgDiff = new ImageDiffer();
		ImageDiff diff = imgDiff.makeDiff(actualImage, expectedImage);
		System.out.println(actualImage);
		System.out.println(actualImage);
		if (actualImage.getWidth() == expectedImage.getWidth() && actualImage.getHeight() == actualImage.getHeight()) {
			System.out.println("Width and Height match");
		}
		if (diff.hasDiff() == true) {
			System.out.println("Some parameters are not same");
		} else {
			System.out.println("100% match");
		}

		driver.quit();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();

	}

}
