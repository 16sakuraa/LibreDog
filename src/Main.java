import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
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
	static MenuButton bookmark = new MenuButton("");
	
	

	@Override
	public void start(Stage stage) throws Exception 
	{
		WebView myWebView = new WebView();
		WebEngine engine = myWebView.getEngine();
		

		TextField urlBar = new TextField();
		urlBar.setPrefWidth(300.00);
		homePage = "https://www.duckduckgo.com";
		engine.load(homePage);
		urlBar.setText(homePage);

		EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {  //event เรียกเว็บจากบุ๊คมาค
            public void handle(ActionEvent e)
            {
				engine.load(((MenuItem)e.getSource()).getText());
				urlBar.setText(((MenuItem)e.getSource()).getText());
            }
        };
		
		LoadFav(event1);

		Image bookmarkIcon = new Image(getClass().getResource("icons/bookmark-new-symbolic.png").toExternalForm(), 20, 17, true, true);
		Image bookmarkMenuIcon = new Image(getClass().getResource("icons/user-bookmarks-symbolic.png").toExternalForm(), 20, 17, true, true);
		Image backIcon = new Image(getClass().getResource("icons/left-symbolic.png").toExternalForm(), 20, 17, true, true);
		Image fwIcon = new Image(getClass().getResource("icons/right-symbolic.png").toExternalForm(), 20, 17, true, true);
		Image reloadIcon = new Image(getClass().getResource("icons/refresh-symbolic.png").toExternalForm(), 20, 17, true, true);

		urlBar.setOnAction(e -> {
			String title = engine.getTitle()+" - LibreDog";
			
			if(urlBar.getText().length()> 11 &&(((urlBar.getText().substring(0,8).compareTo("https://")==0)||(urlBar.getText().substring(0,7).compareTo("http://")==0))&&(urlBar.getText().substring(urlBar.getText().length()-4,urlBar.getText().length()).compareTo(".com")==0||urlBar.getText().substring(urlBar.getText().length()-1,urlBar.getText().length()).compareTo("/")==0))){
				engine.load(urlBar.getText());
				urlBar.setText(urlBar.getText());
				stage.setTitle(title);
			}
			else if(urlBar.getText().substring(urlBar.getText().length()-4,urlBar.getText().length()).compareTo(".com")==0&&urlBar.getText().length()>3){
				engine.load("https://"+urlBar.getText());
				urlBar.setText("https://"+urlBar.getText());
				stage.setTitle(title);
			}
			else{
				engine.load("https://duckduckgo.com/?q="+urlBar.getText());
				urlBar.setText("https://duckduckgo.com/?q="+urlBar.getText());
				stage.setTitle(title);
			}
		});
		myWebView.setOnMouseClicked(e -> {
			urlBar.setText(engine.getLocation());
			stage.setTitle(engine.getTitle()+" - LibreDog");
		});

		Button fav = new Button("");
		fav.setGraphic(new ImageView(bookmarkIcon));
		bookmark.setGraphic(new ImageView(bookmarkMenuIcon));

		fav.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) 
			{
				MenuItem bookmarkName = new MenuItem(urlBar.getText());
				bookmark.getItems().add(bookmarkName);				
				
				
				try {
					Fav(urlBar.getText());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				bookmarkName.setOnAction(event1);
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
		
		VBox root = new VBox();

		VBox.setMargin(urlBar , new Insets(-56, 30, 30, 130));
		VBox.setMargin(back , new Insets(10, 10, 10, 10));
		VBox.setMargin(forward , new Insets(-36, 20, 30, 50));
		VBox.setMargin(reload , new Insets(-56, 20, 30, 90));

		root.scaleYProperty();
		root.getChildren().addAll(back, forward , reload , urlBar, fav , bookmark , myWebView);

		
		
		Scene scene = new Scene(root, 3840, 2160);
		stage.getIcons().add(new Image("icons/LibreDog.png"));
		stage.setTitle(engine.getTitle()+" - LibreDog");
		stage.setScene(scene);

		stage.widthProperty().addListener((obs, oldVal, newVal) -> {  //rescale wifth when resize window

			VBox.setMargin(bookmark , new Insets(-37 , 90 , 10 , stage.widthProperty().doubleValue()-70));  // button layout 
			VBox.setMargin(fav ,  new Insets(-56 , 90 , 10 , stage.widthProperty().doubleValue()-120));
			urlBar.setMaxWidth(stage.widthProperty().doubleValue()-260);	// urlBar layout 

		});
		stage.heightProperty().addListener((obs, oldVal, newVal) -> {  //rescale height when resize window

			myWebView.setMinHeight(stage.heightProperty().doubleValue()-100);

		});
		
		stage.show();
	}



	public static void Fav(String bookmarkURL) throws FileNotFoundException
	{
		
		String savestr = "bookmarkPage.txt"; 
		File f = new File(savestr);

		PrintWriter out = null;
		if ( f.exists() && !f.isDirectory() ) {
    		out = new PrintWriter(new FileOutputStream(new File(savestr), true));	//new write file
		}
		else {
    		out = new PrintWriter(savestr);
		}
		out.append(bookmarkURL+"\n");
		out.close();
	}

	public static void LoadFav(EventHandler<ActionEvent> event1) throws FileNotFoundException
	{
		try 
		{
            File myFile = new File("bookmarkPage.txt");
			Scanner input = new Scanner(myFile);
            
			while (input.hasNext()) 
            {
				String line = (input.nextLine());
				MenuItem bookmarkName = new MenuItem(line); 
				bookmark.getItems().add(bookmarkName);
				bookmarkName.setOnAction(event1);
			}
            input.close();
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("Bookmark file not found in default path, will create a new bookmark file.");
			
		}
	}
	

}


