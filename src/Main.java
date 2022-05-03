import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.web.WebHistory;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	private String homePage;
	private WebHistory history;
	//private String bookmarkName;
	


	@Override
	public void start(Stage stage) throws Exception 
	{
		WebView myWebView = new WebView();
		WebEngine engine = myWebView.getEngine();

		TextField urlBar = new TextField();
		urlBar.setPrefWidth(300.00);
		homePage = "https://www.google.com";
		engine.load(homePage);
		urlBar.setText(homePage);

		

		Image bookmarkIcon = new Image(getClass().getResource("star.png").toExternalForm(), 20, 17, true, true);
		Image backIcon = new Image(getClass().getResource("back.png").toExternalForm(), 20, 17, true, true);
		Image fwIcon = new Image(getClass().getResource("forward.png").toExternalForm(), 20, 17, true, true);
		Image reloadIcon = new Image(getClass().getResource("reload.png").toExternalForm(), 20, 17, true, true);

		//urlBar.textProperty().bind(engine.locationProperty()); // for detecting URL change - work perfectly but unable to type in url.

		urlBar.setOnAction(e -> {
			engine.load("http://"+urlBar.getText());
			});


		MenuButton bookmark = new MenuButton("Bookmarks");
		Button fav = new Button("");

		/* try (ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(new FileInputStream("bookmarkPage.dat")))) 
		 {
			

			MenuItem bookmarkName = new MenuItem(input.readObject().toString()); 
			bookmark.getItems().add(bookmarkName);
            
         } */


		 try  
		 {  
		 	File file = new File("bookmarkPage.dat");    //creates a new file instance  
		 	FileReader fr = new FileReader(file);   //reads the file  
		 	BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
		 	StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters  
		 	String line;  
		 while((line=br.readLine())!=null)  
		 {  
			MenuItem bookmarkName = new MenuItem(line);
			bookmark.getItems().add(bookmarkName);
		 }  
		 fr.close(); 
		} catch(IOException e)  
		{  
				e.printStackTrace();  
		}  









		
		fav.setGraphic(new ImageView(bookmarkIcon));
		fav.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) 
			{
				MenuItem bookmarkName = new MenuItem(urlBar.getText()); 
				bookmark.getItems().add(bookmarkName);					// add bookmark page to drop down menu



			 try (ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("bookmarkPage.dat")))) 
			{
         			output.writeObject(urlBar.getText());
         			
      		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 






				bookmarkName.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						
						urlBar.setText(bookmarkName.getText());
						engine.load("http://"+urlBar.getText());    // load bookmark page on click
						
					}
				});
			}
			
		});
		
		
		Button back = new Button("");
		back.setGraphic(new ImageView(backIcon));
		back.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				history = engine.getHistory();
				ObservableList<WebHistory.Entry> entries = history.getEntries();
				history.go(-1);

				urlBar.setText(entries.get(history.getCurrentIndex()).getUrl());
			}

			
		});
		
		 Button forward = new Button("");
		 forward.setGraphic(new ImageView(fwIcon));
		forward.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				history = engine.getHistory();
				ObservableList<WebHistory.Entry> entries = history.getEntries();
				history.go(1);

				urlBar.setText(entries.get(history.getCurrentIndex()).getUrl());
			}
		}); 
		
		Button reload = new Button("");
		reload.setGraphic(new ImageView(reloadIcon));
		reload.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				engine.reload();
			}
		});

		
		engine.setUserAgent("Mozilla/99.0 (Linux; U; Android 7.0; en-us; Xoom Build/HRI39) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13");
		//engine.setUserAgent("Mozilla/5.0 (Linux; U; Android 2.2.1; en-us; Nexus One Build/FRG83) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
		
		
		VBox root = new VBox();

		/*VBox.setMargin(urlBar , new Insets(10, 10, 10, 10));
		VBox.setMargin(back , new Insets(0, 0, 10, 10));
		VBox.setMargin(forward , new Insets(-35, 90, 10, 60));		// old button layout
		VBox.setMargin(reload , new Insets(-35, 90, 10, 130));
		VBox.setMargin(bookmark , new Insets(-35 , 90 , 10 , 850));
		VBox.setMargin(fav ,  new Insets(-35 , 90 , 10 , 810)); */



		VBox.setMargin(urlBar , new Insets(-55, 30, 30, 130));
		VBox.setMargin(back , new Insets(10, 10, 10, 10));
		VBox.setMargin(forward , new Insets(-35, 20, 30, 50));		// new button layout 
		VBox.setMargin(reload , new Insets(-55, 20, 30, 90));
		VBox.setMargin(bookmark , new Insets(-35 , 90 , 10 , 50));
		VBox.setMargin(fav ,  new Insets(-25 , 90 , 10 , 10));

		

		//root.getChildren().addAll(urlBar , back, forward , reload , fav , bookmark , myWebView);
		root.getChildren().addAll(back, forward , reload , urlBar, fav , bookmark , myWebView);

	
		
		Scene scene = new Scene(root, 1080, 720);
		stage.getIcons().add(new Image("dog.png"));
		stage.setTitle("Firedog");
		stage.setScene(scene);
		
		stage.show();
	}

}