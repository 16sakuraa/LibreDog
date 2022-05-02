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
	private String bookmarkName;
	TextField urlBar = new TextField();


	@Override
	public void start(Stage stage) throws Exception {
		WebView myWebView = new WebView();
		WebEngine engine = myWebView.getEngine();
		homePage = "https://www.google.com";
		engine.load(homePage);
		urlBar.setText(homePage);

		urlBar.setOnAction(e -> {
			engine.load("http://"+urlBar.getText());
			});

		MenuButton bookmark = new MenuButton("Bookmarks");

		Button fav = new Button("");
		Image image = new Image(getClass().getResource("star.png").toExternalForm(), 20, 17, true, true);
		fav.setGraphic(new ImageView(image));

		fav.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) 
			{
				MenuItem bookmarkName = new MenuItem(urlBar.getText());
				bookmark.getItems().add(bookmarkName);

				bookmarkName.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						urlBar.setText(bookmarkName.getText());
						engine.load("http://"+urlBar.getText());
						
					}
				});
			}
			
		});
		
		
		Button back = new Button("Back");
		back.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				history = engine.getHistory();
				ObservableList<WebHistory.Entry> entries = history.getEntries();
				history.go(-1);

				urlBar.setText(entries.get(history.getCurrentIndex()).getUrl());
			}

			
		});
		
		 Button forward = new Button("Forward");
		forward.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				history = engine.getHistory();
				ObservableList<WebHistory.Entry> entries = history.getEntries();
				history.go(1);

				urlBar.setText(entries.get(history.getCurrentIndex()).getUrl());
			}
		}); 
		
		Button reload = new Button("Reload");
		reload.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				engine.reload();
			}
		});

		
		engine.setUserAgent("Mozilla/99.0 (Linux; U; Android 7.0; en-us; Xoom Build/HRI39) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13");
		//engine.setUserAgent("Mozilla/5.0 (Linux; U; Android 2.2.1; en-us; Nexus One Build/FRG83) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
		
		
		VBox root = new VBox();

		VBox.setMargin(urlBar , new Insets(10, 10, 10, 10));
		VBox.setMargin(back , new Insets(0, 0, 10, 10));
		VBox.setMargin(forward , new Insets(-35, 90, 10, 60));
		VBox.setMargin(reload , new Insets(-35, 90, 10, 130));
		VBox.setMargin(bookmark , new Insets(-35 , 90 , 10 , 850));
		VBox.setMargin(fav ,  new Insets(-35 , 90 , 10 , 800));

		root.getChildren().addAll(urlBar , back, forward , reload , fav , bookmark , myWebView);

	
		
		Scene scene = new Scene(root, 1080, 720);
		stage.setTitle("Firedog");
		stage.setScene(scene);
		
		stage.show();
	}

}