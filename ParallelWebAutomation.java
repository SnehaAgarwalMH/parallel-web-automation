package com.AutoProject.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import org.monte.media.VideoFormatKeys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;
import java.util.concurrent.*;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class performParallelLogin {
	
	 private static String BinanceBaseURL = "https://www.binance.com/en";
	 private static String BithumbBaseURL = "https://www.bithumb.com/react/";
	 private static ScreenRecorder screenRecorder1;
	 private static ScreenRecorder screenRecorder2;
	 private static WebDriverWait wait;
	 private static WebDriver driver;

	  public static void main(String[] args) {
		  
		  System.setProperty("webdriver.chrome.driver", "My_Path\chromedriver.exe");
		  	        
	        driver = new ChromeDriver();
		    driver.manage().window().maximize();
		    wait = new WebDriverWait(driver, Duration.ofSeconds(10));	
		    		  
		    // Open Binance website in the first tab/window
		    driver.get(BinanceBaseURL);
		    try {
				  Thread.sleep(2000); // wait for 2 seconds
			  } catch (InterruptedException e) {
				  e.printStackTrace();
			  }				  				  
			  
			  handleRewardPointPopUp(driver);
			  try {
				  Thread.sleep(2000); // wait for 2 seconds
			  } catch (InterruptedException e) {
				  e.printStackTrace();
			  }
			  
		    BinanceHandleCookieConsent(driver);
	        try {
	    		Thread.sleep(2000); // wait for 2 seconds
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	        
	        handleRewardPointPopUp(driver);
			  try {
				  Thread.sleep(2000); // wait for 2 seconds
			  } catch (InterruptedException e) {
				  e.printStackTrace();
			  }
	        
	        BinanceLogin(driver);	        	      

		    // Open Bithumb website in a new tab/window
		    ((JavascriptExecutor) driver).executeScript("window.open();");
		    driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
		    driver.get(BithumbBaseURL);
		    try {
	    		Thread.sleep(2000); // wait for 5 seconds
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    BithumbHandleGetStartedPopUp(driver);
	        try {
	    		Thread.sleep(2000); // wait for 2 seconds
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	        
	        BithumbHandlePopUp(driver);
	        try {
	    		Thread.sleep(2000); // wait for 2 seconds
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	        
	        BithumbLogin(driver);
	        try {
	    		Thread.sleep(2000); // wait for 5 seconds
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}

		    // Handle cookies and login concurrently
		    Thread binanceThread = new Thread(() -> {
		    	
		        try {
		        	
		        	// Start the scheduled tasks after login
			        startScheduledTasks1();
			        
			        // Add a wait to ensure that tasks have time to execute
			        try {
			            Thread.sleep(8 * 60 * 60 * 1000); // Sleep for 8 hours
			        } catch (InterruptedException e) {
			            e.printStackTrace();
			        }

		        } catch (Exception e) {
		        	
		            e.printStackTrace();
		        }
		    	
		    });

		    Thread bithumbThread = new Thread(() -> {
		    	
		        try {
		        	
		        	// Start the scheduled tasks after login
			        startScheduledTasks2();
			        
			        // Add a wait to ensure that tasks have time to execute
			        try {
			            Thread.sleep(8 * 60 * 60 * 1000); // Sleep for 8 hours
			        } catch (InterruptedException e) {
			            e.printStackTrace();
			        }
		        } catch (Exception e) {
		        	
		            e.printStackTrace();
		        }
		    	
		    });

		    binanceThread.start();
		    bithumbThread.start();

		    try {
		        binanceThread.join();
		        bithumbThread.join();
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    } finally {
		        // Close the browser when both website automations are completed
		        if (driver != null) {
		            driver.quit();
		        }
		    }

		    System.out.println("Both websites automation completed.");
	    }
	  
	  public static void startScheduledTasks1() {
	        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	        Calendar targetTime = getTargetTime1(14,42);
	        long initialDelay = targetTime.getTimeInMillis() - System.currentTimeMillis();

	        scheduler.schedule(() -> {
	            try {	            	
	            	automateBinanceWebsite();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }, initialDelay, TimeUnit.MILLISECONDS);
	    }
	  
	   
	 // Helper method to calculate the initial delay until the target time
	    public static Calendar getTargetTime1(int hourOfDay, int minute) {
	        Calendar targetTime = Calendar.getInstance();
	        targetTime.set(Calendar.HOUR_OF_DAY, 14);
	        targetTime.set(Calendar.MINUTE,42);
	        targetTime.set(Calendar.SECOND, 0);
	        targetTime.set(Calendar.MILLISECOND, 0);

	        // If the current time is already past the target time, schedule for the next day
	        if (System.currentTimeMillis() > targetTime.getTimeInMillis()) {
	            targetTime.add(Calendar.DAY_OF_MONTH, 1);
	        }
	        return targetTime;
	    }
	  
	   
	  public static void automateBinanceWebsite() throws Exception {
		  
	      // Configure the video recording using MonteMedia
	      File videoFile = new File("C:\\Users\\sneha\\Documents\\Binance1-video\\recorded_Binance_video.mp4");
	      GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	      Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	      screenRecorder1 = new ScreenRecorder(gc, captureSize,
	              new Format(VideoFormatKeys.MediaTypeKey, VideoFormatKeys.MediaType.FILE, VideoFormatKeys.MimeTypeKey, VideoFormatKeys.MIME_AVI),
	              new Format(VideoFormatKeys.MediaTypeKey, VideoFormatKeys.MediaType.VIDEO, VideoFormatKeys.EncodingKey, VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
	                      VideoFormatKeys.CompressorNameKey, VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
	                      VideoFormatKeys.DepthKey, 24, VideoFormatKeys.FrameRateKey, Rational.valueOf(15),
	                      VideoFormatKeys.QualityKey, 1.0f, VideoFormatKeys.KeyFrameIntervalKey, 15 * 60),
	              new Format(VideoFormatKeys.MediaTypeKey, VideoFormatKeys.MediaType.VIDEO, VideoFormatKeys.EncodingKey, "black",
	                      VideoFormatKeys.FrameRateKey, Rational.valueOf(30)),
	              null, videoFile);	

	       int retryIntervalMinutes = 5; // Interval between retries in minutes
	        
	       System.out.println("hello binance\n\n\n\n\n\n\n\n\n\n");
	       
	       try {
	    		Thread.sleep(5000); // wait for 5 seconds
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	       
	      
	       while (true) {
	        try {		        	
	        	WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(5));
	            BinanceSearchAndClickElement(driver);
	            BinanceStartVideoRecording(driver);
	            BinanceTakeScreenshotsAndScroll(driver);
	            BinanceStopMonteScreenRecorder(driver);
	            BinanceLogout(driver);
	            
	            // Exit the loop if tasks are successful
	               break;
	           } catch (Exception e) {
	               e.printStackTrace();
	               System.out.println("Task execution failed. Retrying in " + retryIntervalMinutes + " minutes...");

	               // Sleep for the specified retry interval before the next attempt
	               try {
	                   Thread.sleep(retryIntervalMinutes * 60 * 1000);
	               } catch (InterruptedException ex) {
	                   ex.printStackTrace();
	               }
	           }
	       }
	    }
	  
	  public static void startScheduledTasks2() {
	        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	        Calendar targetTime = getTargetTime2(14,42);
	        long initialDelay = targetTime.getTimeInMillis() - System.currentTimeMillis();

	        scheduler.schedule(() -> {
	            try {
	            	automateBithumbWebsite();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }, initialDelay, TimeUnit.MILLISECONDS);
	    }
	  	   
	 // Helper method to calculate the initial delay until the target time
	    public static Calendar getTargetTime2(int hourOfDay, int minute) {
	        Calendar targetTime = Calendar.getInstance();
	        targetTime.set(Calendar.HOUR_OF_DAY,14);
	        targetTime.set(Calendar.MINUTE,42);
	        // If the current time is already past the target time, schedule for the next day
	        if (System.currentTimeMillis() > targetTime.getTimeInMillis()) {
	            targetTime.add(Calendar.DAY_OF_MONTH, 1);
	        }
	        return targetTime;
	    }
	  
	  public static void automateBithumbWebsite() throws Exception {

		     // Configure the video recording using MonteMedia
	        File videoFile = new File("C:\\Users\\sneha\\Documents\\Bithumb1-video\\recorded_Bithumb_video.mp4"); 
	        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	        Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	        screenRecorder2 = new ScreenRecorder(gc, captureSize,
	                new Format(VideoFormatKeys.MediaTypeKey, VideoFormatKeys.MediaType.FILE, VideoFormatKeys.MimeTypeKey, VideoFormatKeys.MIME_AVI),
	                new Format(VideoFormatKeys.MediaTypeKey, VideoFormatKeys.MediaType.VIDEO, VideoFormatKeys.EncodingKey, VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
	                        VideoFormatKeys.CompressorNameKey, VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
	                        VideoFormatKeys.DepthKey, 24, VideoFormatKeys.FrameRateKey, Rational.valueOf(15),
	                        VideoFormatKeys.QualityKey, 1.0f, VideoFormatKeys.KeyFrameIntervalKey, 15 * 60),
	                new Format(VideoFormatKeys.MediaTypeKey, VideoFormatKeys.MediaType.VIDEO, VideoFormatKeys.EncodingKey, "black",
	                        VideoFormatKeys.FrameRateKey, Rational.valueOf(30)),
	                null, videoFile);	        
	     
	        System.out.println("Scheduled task executing at: " + new Date());
	        int retryIntervalMinutes = 5; // Interval between retries in minutes

	        System.out.println("hello bithum\n\n\n\n\n\n\n\n\n\n");
	       
	        while (true) {	        	
	        
	        try {
	        	 
	        	BithumbSearchAndClickElement(driver);
	        	BithumbStartVideoRecording(driver);
	        	BithumbTakeScreenshotsAndScroll(driver);
	        	BithumStopMonteScreenRecorder(driver);
	        	BithumLogout(driver);
	        	driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
	        	
	        	// Exit the loop if tasks are successful
                break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Task execution failed. Retrying in " + retryIntervalMinutes + " minutes...");

                // Sleep for the specified retry interval before the next attempt
                try {
                    Thread.sleep(retryIntervalMinutes * 60 * 1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
	 }	 	
	  
	  private static void BinanceHandleCookieConsent(WebDriver driver) {
	        // Check if the cookie consent pop-up is present
	        WebElement cookiePopup = null;
	        try {
	            cookiePopup = driver.findElement(By.id("onetrust-policy")); //cookie pop-up element
	        } catch (NoSuchElementException e) {
	            // If the element is not found, no cookie pop-up is present
	        }

	        // Handle the cookie consent pop-up if present
	        if (cookiePopup != null) {
	            // Click on the "Accept" button
	            WebElement acceptButton = cookiePopup.findElement(By.xpath("//*[@id=\"onetrust-accept-btn-handler\"]")); //"Accept" button
	            acceptButton.click();
	        }
	    }
	  
	  private static void handleRewardPointPopUp(WebDriver driver) {
			// Find all elements that match the reward point pop-up selector
			  java.util.List<WebElement> rewardPointPopups = driver.findElements(By.cssSelector(".css-y12ctw > div"));

			    // If there are any reward point pop-ups present, handle them
			    if (!rewardPointPopups.isEmpty()) {
			        // Handle each pop-up individually if needed
			        for (WebElement rewardPointPopup : rewardPointPopups) {
			            WebElement closeButton = rewardPointPopup.findElement(By.cssSelector(".css-zdq66x > svg > path"));
			            closeButton.click();
			           
			        }
			    }
	  } 

	  public static void BinanceLogin(WebDriver driver) {
		  
		  WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));

	    	// Create a scanner to read user input
	        Scanner scanner = new Scanner(System.in);        	    
	        
	        // Click the login button
	        WebElement login = driver.findElement(By.xpath("//*[@id=\"header_login\"]"));
	    	login.click();
	    	try {
	    		Thread.sleep(2000); // wait for 5 seconds
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}

	    	 // Get the logger associated with the AsyncHttpClient library
	        Logger asyncHttpClientLogger = (Logger) LoggerFactory.getLogger("org.asynchttpclient");
	        
	        // Set the logging level to INFO (or any desired level)
	        asyncHttpClientLogger.setLevel(Level.INFO);
	   	   
	    	// Enter username/email
	    	//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	        WebElement username = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));	 
	    	username.click();
	    	try {
	    		Thread.sleep(2000); // wait for 2 seconds
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }	 	    		    	
		
	    	System.out.print("Enter User name as Email (or 'exit' to quit): ");
	    	String uName = scanner.nextLine();
    	
	    	// Check if the input is "exit" to quit
	        if (uName.equalsIgnoreCase("exit")) {
	           scanner.close(); // Close the scanner if user wants to exit
	            return; // Exit the method
	        }

	        username.clear(); // Clear any existing text in the search box
	        username.sendKeys(uName);      
	        username.sendKeys(Keys.ENTER);

	        // Submit the user name
	        username.submit();
	    	try {
	    		Thread.sleep(2000); // wait for 2 seconds
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	    	
	    	WebElement NextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"click_login_submit\"]")));
	    	JavascriptExecutor executor = (JavascriptExecutor) driver;
	        executor.executeScript("arguments[0].click();", NextButton);		
	    	try {
	    		Thread.sleep(10000); // wait for 10 seconds
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }		    	
	    	
	    	//	scanner.nextLine();
	    	
	    	//	scanner.close();
	      
	    }  
	  

	  
	  public static void BinanceSearchAndClickElement(WebDriver driver)  {
		  		 
		    Actions actions = new Actions(driver);

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(5));
		    try {
		        Thread.sleep(4000); // Wait for 2 seconds to allow the page to load after clicking logout
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }

		    // Wait for the page to load completely
		    wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
		    ExpectedCondition<Boolean> walletClickableCondition = webDriver -> {
		        WebElement wallet = webDriver.findElement(By.xpath("//*[@id=\"header_menu_ba-wallet\"]/div[1]/div"));
		        return wallet.isEnabled();
		    };

		    try {
		        wait.until(walletClickableCondition);
		        WebElement wallet = driver.findElement(By.xpath("//*[@id=\"header_menu_ba-wallet\"]/div[1]/div"));
		        wallet.click();
		        try {
			        Thread.sleep(4000); // Wait for 2 seconds to allow the page to load after clicking logout
			    } catch (InterruptedException e) {
			        e.printStackTrace();
			    }
		        System.out.println("Clicked on Binance wallet element");
		    } catch (StaleElementReferenceException e) {
		        System.out.println("StaleElementReferenceException: Wallet element not clickable");
		    }

		    WebElement DepWit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"header_menu_item_ba-exchangeWallet\"]/div[2]/div[1]/div")));
		    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", DepWit);
		    try {
		        Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }

		    DepWit.click();

		    try {
		        Thread.sleep(4000); // Wait for 2 seconds to allow the page to load after clicking logout
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
	  }
	  
	  public static void BinanceStartVideoRecording(WebDriver driver) throws IOException {
		  		  
	        // Start the video recording
	    	screenRecorder1.start();
	  }
	    
	 
	    public static void BinanceTakeScreenshotsAndScroll(WebDriver driver) throws IOException, InterruptedException {
	    	
	    	 int pageNumber = 1;
		        		        
		        Actions actions = new Actions(driver);
		      //  JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		        
		        Date date = new Date();
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		        String datetime = dateFormat.format(date);
		        
		        while (true) {   
		        	
		        	 int page = pageNumber;
				        String Page = String.format("%02d", page);
		            	
		        	if (pageNumber == 1 ) {
			        		       
			            // Wait for a moment to load the content
			            Thread.sleep(4000);  	            	           
			            
			            // Take a screenshot of the current visible part of the page
			            File screenshotFile = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
			            BufferedImage fullScreen = ImageIO.read(screenshotFile);			         
			            ImageIO.write(fullScreen, "png", new File("C:\\Users\\sneha\\Documents\\Binace1-ScreenShot\\Binance_Master_Dashboard_" + Page + "_" + datetime + "_top.png"));
			
			            // Wait for a moment to load the content
			            Thread.sleep(2000);  	            	            
			            

			            WebElement SearchMid = driver.findElement(By.id("next-page"));
			        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);",  SearchMid);
			            try {
			                Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
			            } catch (InterruptedException e) {
			                e.printStackTrace();
			            } 
			            // Scroll down to capture the middle part of the first page
			            actions.sendKeys(Keys.PAGE_DOWN).perform();
			            Thread.sleep(2000);	
			            try {
		                    Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
		                } catch (InterruptedException ex) {
		                    ex.printStackTrace();
		                }  
			            
			            // Take a screenshot of the current visible part of the page (middle part of the first page)
			            File screenshotFileMiddle = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
			            BufferedImage fullScreenMiddle = ImageIO.read(screenshotFileMiddle);			            
			            ImageIO.write(fullScreenMiddle, "png", new File("C:\\Users\\sneha\\Documents\\Binace1-ScreenShot\\Binance_Master_Dashboard_" + Page + "_" + datetime + "_middle.png"));
			
			            // Wait for a moment to load the content after scrolling
			            Thread.sleep(2000);
				        
			            WebElement Search = driver.findElement(By.id("next-page"));
			        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",  Search);
			            try {
			                Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
			            } catch (InterruptedException e) {
			                e.printStackTrace();
			            } 
			            
			            // Scroll down to capture the bottom part of the first page
			            actions.sendKeys(Keys.END).perform();
			            Thread.sleep(2000);
			
			            // Take a screenshot of the current visible part of the page (bottom part of the first page)
			            File screenshotFileBottom = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
			            BufferedImage fullScreenBottom = ImageIO.read(screenshotFileBottom);
			            ImageIO.write(fullScreenBottom, "png", new File("C:\\Users\\sneha\\Documents\\Binace1-ScreenShot\\Binance_Master_Dashboard_" + Page + "_" + datetime + "_bottom.png"));
			            
			            Thread.sleep(2000);	            	            

		        	} else {
		        		
			        		// Scroll up to the top of the page for the next iteration
			                while (true) {
			                		                	
			                    actions.sendKeys(Keys.PAGE_UP).perform();
			                    Thread.sleep(1000);
			                    // Check if the top of the page is reached
			                    JavascriptExecutor jsExecutors = (JavascriptExecutor) driver;
			                    boolean isAtTop = (boolean) jsExecutors.executeScript(
			                            "return (window.pageYOffset === 0);");
			                    if (isAtTop) {
			                        break;
			                    }
			                }  
			                
			                // Scroll up to the top of the page to ensure it starts from the top
			                actions.sendKeys(Keys.HOME).perform();
			                Thread.sleep(2000);     
			                
				            // Take a screenshot of the current visible part of the page
				            File screenshotFile = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
				            BufferedImage fullScreen = ImageIO.read(screenshotFile);			         
				            ImageIO.write(fullScreen, "png", new File("C:\\Users\\sneha\\Documents\\Binace1-ScreenShot\\Binance_Master_Dashboard_" + Page + "_" + datetime + "_top.png"));
			
			                // Wait for a moment to load the content
			                Thread.sleep(2000);  
			                
			                // Scroll down to capture the middle part of the first page
			                actions.sendKeys(Keys.PAGE_DOWN).perform();
			                Thread.sleep(2000);
			
				            // Take a screenshot of the current visible part of the page (middle part of the first page)
				            File screenshotFileMiddle = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
				            BufferedImage fullScreenMiddle = ImageIO.read(screenshotFileMiddle);			            
				            ImageIO.write(fullScreenMiddle, "png", new File("C:\\Users\\sneha\\Documents\\Binace1-ScreenShot\\Binance_Master_Dashboard_" + Page + "_" + datetime + "_middle.png"));
			
			                // Wait for a moment to load the content after scrolling
			                Thread.sleep(2000);
			
			                // Scroll down to capture the bottom part of the first page
			                actions.sendKeys(Keys.END).perform();
			                Thread.sleep(2000);
			
				            // Take a screenshot of the current visible part of the page (bottom part of the first page)
				            File screenshotFileBottom = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
				            BufferedImage fullScreenBottom = ImageIO.read(screenshotFileBottom);
				            ImageIO.write(fullScreenBottom, "png", new File("C:\\Users\\sneha\\Documents\\Binace1-ScreenShot\\Binance_Master_Dashboard_" + Page + "_" + datetime + "_bottom.png"));
			                
			                Thread.sleep(2000);                       

		        	} 
		            
		        	 // Check if pagination element is present
		            WebElement paginationElement = driver.findElement(By.xpath("//*[@class='css-1bgd29k']"));

		            if (!paginationElement.isDisplayed()) {
		                // If pagination element is not displayed, break the loop (reached last page)
		                break;
		            }

		            // Find the element for the next button or page numbers
		            try {
		                WebElement nextButton = driver.findElement(By.id("next-page"));

		                if (!nextButton.isEnabled()) {
		                	
		                    // If next button is not enabled, break the loop (reached last page)
		                    break;
		                    
		                } else {
		                    nextButton.click();
		                    try {
		                        Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
		                    } catch (InterruptedException ex) {
		                        ex.printStackTrace();
		                    }                                                      
		                    
		                }
		                
		            } catch (NoSuchElementException e) {
		            	
		                // If next button is not found, try clicking on individual page numbers one by one
		                WebElement pageNumberElement = driver.findElement(By.xpath("//*[@id='page-" + (pageNumber + 1) + "']"));
		                
		                if (!pageNumberElement.isDisplayed()) {
		                	
		                    // If pagination element is not displayed, break the loop (reached last page)
		                    break;
		                    
		                } else {
		                    pageNumberElement.click();
		                    try {
		                        Thread.sleep(4000); // Wait for 2 seconds to ensure the element is clickable
		                    } catch (InterruptedException ex) {
		                        ex.printStackTrace();
		                    }                                    
		                    
		                }
		            }

		            // Wait for a moment to load the content of the next page
		            Thread.sleep(1000);

		            // Increment the page number for the next iteration
		            pageNumber++;
		            }
		    }

	  
	    public static void BinanceStopMonteScreenRecorder(WebDriver driver) throws IOException {
	    	
	        // Stop the video recording
	        screenRecorder1.stop();
	    }

	
	    public static void BinanceLogout(WebDriver driver) {	    		    	
	    	
	    	WebElement logoutSearch = driver.findElement(By.id("header_menu_cabinet"));
	    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", logoutSearch);
	        try {
	            Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        // Click on the logout element
	        logoutSearch.click();

	        try {
	            Thread.sleep(4000); // Wait for 2 seconds to allow the page to load after clicking logout
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    	
	        // Locate the logout button and click on it
	        WebElement logoutButton = driver.findElement(By.cssSelector(".menu-item.hidden-in-bnc-app"));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", logoutButton);
	        try {
	            Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        logoutButton.click();

	        try {
	            Thread.sleep(2000); // Wait for 2 seconds to allow the logout process to complete
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }    

	    private static void BithumbHandleGetStartedPopUp(WebDriver driver) {
	    	
	    	// Find all elements that match the reward point pop-up selector
			  java.util.List<WebElement> rewardPointPopups = driver.findElements(By.className("NoticePopupTitle_notice-title__u1ZAR"));

			    // If there are any reward point pop-ups present, handle them
			    if (!rewardPointPopups.isEmpty()) {
			        // Handle each pop-up individually if needed
			        for (WebElement rewardPointPopup : rewardPointPopups) {
			            WebElement closeButton = rewardPointPopup.findElement(By.xpath("//span[text()='닫기']"));
			            closeButton.click();
			            try {
				    		Thread.sleep(2000); // wait for 5 seconds
				    	} catch (InterruptedException e) {
				    		e.printStackTrace();
				    	}
			           
			        }
			    }	    	
	    }
	    
	    private static void BithumbHandlePopUp(WebDriver driver) {
	    	// Check if the Get Started pop-up is present
	    	
	    	// Find all elements that match pop-up selector
			  java.util.List<WebElement> rewardPointPopups = driver.findElements(By.className("FreezonModal_modal-freezon__image__gSgVV"));

			    // If there are any pop-ups present, handle them
			    if (!rewardPointPopups.isEmpty()) {
			        // Handle each pop-up individually if needed
			        for (WebElement rewardPointPopup : rewardPointPopups) {
			            WebElement closeButton = rewardPointPopup.findElement(By.xpath("//span[text()='오늘 그만 보기']"));
			            closeButton.click();
			            try {
				    		Thread.sleep(2000); // wait for 5 seconds
				    	} catch (InterruptedException e) {
				    		e.printStackTrace();
				    	}
			           
			        }
			    }
	    }

	    public static void BithumbLogin(WebDriver driver) {
	    	
	    	// Create a scanner to read user input
	        Scanner scanner = new Scanner(System.in);
	        
	        // Click the login button
	        WebElement login = driver.findElement(By.linkText("로그인"));
	    	login.click();
	    	try {
	    		Thread.sleep(2000); // wait for 5 seconds
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	   	   
	    /*	// Enter username/email
	        WebElement username = wait.until(ExpectedConditions.elementToBeClickable(By.className("InputBox_input-box__label__H37dX")));	 
	    	username.click();

	    	try {
	    		Thread.sleep(2000); // wait for 2 seconds
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		
	    	System.out.print("Enter User name as Email (or 'exit' to quit): ");
	    	String uName = scanner.nextLine();
	    	
	    	// Check if the input is "exit" to quit
	        if (uName.equalsIgnoreCase("exit")) {
	            scanner.close(); // Close the scanner if user wants to exit
	            return; // Exit the method
	        }

	    	username.clear(); // Clear any existing text in the search box
	    	username.sendKeys(uName);      
	    	username.sendKeys(Keys.ENTER);

	        // Submit the user name
	    	username.submit();
	    	try {
	    		Thread.sleep(2000); // wait for 2 seconds
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	    	
	    	WebElement password = driver.findElement(By.name("password"));
	    	password.click();
	    	try {
	    		Thread.sleep(2000); // wait for 2 seconds
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	    	System.out.print("Enter Password (or 'exit' to quit): ");
	        String Pwd = scanner.nextLine();       	
	    
	        // Check if the input is "exit" to quit
	        if (Pwd.equalsIgnoreCase("exit")) {
	        	scanner.close(); // Close the scanner if user wants to exit
	        	return; // Exit the method
	        }
	    
	        password.clear(); // Clear any existing text in the search box
	        password.sendKeys(Pwd);   
	        password.sendKeys(Keys.ENTER);

	        // Submit the password
	        password.submit();        
	        try {
	        	Thread.sleep(2000); // wait for 2 seconds
	        } catch (InterruptedException e) {
	        	e.printStackTrace();
	        }
	        	
	    	WebElement LoginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/main/div/div/div/div/form/div[3]/button")));
	    	JavascriptExecutor executor = (JavascriptExecutor) driver;
	        executor.executeScript("arguments[0].click();", LoginButton);		
	    	try {
	    		Thread.sleep(10000); // wait for 10 seconds
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }    */  
	    	
	    	try {
	    		Thread.sleep(8000); // wait for 10 seconds
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } 
	        
	        // Wait for a specific condition before continuing
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2)); // Adjust the waiting time as needed

	        // Assuming some condition that indicates the user has completed the login process
	        WebElement conditionElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("xyz@naver.com")));   
	    	       
	        //	scanner.nextLine();
	    	
	        //	scanner.close();
	      
	    }   

	    public static void BithumbSearchAndClickElement(WebDriver driver) {
	    	
	    	driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
	    	
	    	Actions actions = new Actions(driver);
	    	
	    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    	
	        // Locate the element to click
	        WebElement wallet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("지갑관리")));
	        actions.moveToElement(wallet).perform();
	       	      
	        // Click on the element
	        wallet.click();
	        try {	        	
	    		Thread.sleep(5000); // wait for 2 seconds
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        
	        System.out.println("Clicked on Bithumb wallet element");
	        
	        WebElement DepWit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("자산현황")));
	        actions.moveToElement(DepWit).perform();
	        DepWit.click();
	        try {
	    		Thread.sleep(5000); // wait for 2 seconds
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void BithumbStartVideoRecording(WebDriver driver) throws IOException {
	    	
	         // driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
	        // Start the video recording
	    	screenRecorder2.start();
	    }

	    public static void BithumbTakeScreenshotsAndScroll(WebDriver driver) throws IOException, InterruptedException {
	    	
	    //	driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
	    	int pageNumber = 1;
	        
	        int page = pageNumber;
	        String Page = String.format("%02d", page);
	        
	        Actions actions = new Actions(driver);
	        
	        Date date = new Date();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	        String datetime = dateFormat.format(date);        
		        		       
	        // Wait for a moment to load the content
            Thread.sleep(5000);  
            
            // Take a screenshot of the current visible part of the page
            File screenshotFile = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage fullScreen = ImageIO.read(screenshotFile);
            ImageIO.write(fullScreen, "png", new File("C:\\Users\\sneha\\Documents\\Bithumb1-ScreenShot\\Bithumb_New_Dashboard_01_" + Page + "_" + datetime + "_top.png"));

            // Wait for a moment to load the content
            Thread.sleep(2000);
            
            // Scroll down to capture the middle part of the first page
            actions.sendKeys(Keys.ARROW_DOWN).perform();
            Thread.sleep(4000);	
            try {
               Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
            } catch (InterruptedException ex) {
               ex.printStackTrace();
            }  
            
            // Take a screenshot of the current visible part of the page
            screenshotFile = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
            fullScreen = ImageIO.read(screenshotFile);
            ImageIO.write(fullScreen, "png", new File("C:\\Users\\sneha\\Documents\\Bithumb1-ScreenShot\\Bithumb_New_Dashboard_01_" + Page + "_" + datetime + "_middle1.png"));

            // Wait for a moment to load the content
            Thread.sleep(2000);
            
            // Scroll down to capture the middle part of the first page
            actions.sendKeys(Keys.PAGE_DOWN).perform();
            Thread.sleep(2000);
            try {
               Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
            } catch (InterruptedException ex) {
               ex.printStackTrace();
            }  
            
            // Take a screenshot of the current visible part of the page
            screenshotFile = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
            fullScreen = ImageIO.read(screenshotFile);
            ImageIO.write(fullScreen, "png", new File("C:\\Users\\sneha\\Documents\\Bithumb1-ScreenShot\\Bithumb_New_Dashboard_01_" + Page + "_" + datetime + "_middle2.png"));

            // Wait for a moment to load the content
            Thread.sleep(2000);
            
         // Scroll down to capture the bottom part of the first page
            actions.sendKeys(Keys.END).perform();
            Thread.sleep(2000);
            
         // Take a screenshot of the current visible part of the page
            screenshotFile = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
            fullScreen = ImageIO.read(screenshotFile);
            ImageIO.write(fullScreen, "png", new File("C:\\Users\\sneha\\Documents\\Bithumb1-ScreenShot\\Bithumb_New_Dashboard_01_" + Page + "_" + datetime + "_end.png"));

            // Wait for a moment to load the content
            Thread.sleep(2000);
	            	            	            	            	                       	            
	    }

	    public static void BithumStopMonteScreenRecorder(WebDriver driver) throws IOException {
	    	
	    	//driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
	        // Stop the video recording
	        screenRecorder2.stop();
	    }

	    public static void BithumLogout(WebDriver driver) {
	    	
	    //	driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
        
	    	WebElement logoutSearch = driver.findElement(By.linkText("xyz@naver.com"));
	    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", logoutSearch);
	        try {
	            Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        // Click on the logout element
	        logoutSearch.click();

	        try {
	            Thread.sleep(5000); // Wait for 2 seconds to allow the page to load after clicking logout
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	    	
	     // Locate the logout button and click on it
	        WebElement logoutButton = driver.findElement(By.linkText("로그아웃"));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", logoutButton);
	        try {
	            Thread.sleep(5000); // Wait for 2 seconds to ensure the element is clickable
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        logoutButton.click();

	        try {
	            Thread.sleep(5000); // Wait for 2 seconds to allow the logout process to complete
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }


}
