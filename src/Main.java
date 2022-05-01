import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
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
	TextField urlBar = new TextField();


	@Override
	public void start(Stage stage) throws Exception {
		WebView myWebView = new WebView();
		WebEngine engine = myWebView.getEngine();
		homePage = "https://www.google.com";
		engine.load(homePage);

		urlBar.setOnAction(e -> {
			engine.load("http://"+urlBar.getText());
			});
		
	/*	Button btn = new Button("Load Page");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				engine.load("http://"+urlBar.getText());
			}
		}); */
		
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

		
		
		engine.setUserAgent("Mozilla/5.0 (Linux; U; Android 2.2.1; en-us; Nexus One Build/FRG83) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
		
		
		VBox root = new VBox();
		root.getChildren().addAll(myWebView, back, forward , reload , urlBar);
		
		Scene scene = new Scene(root, 800, 500);
		stage.setTitle("a quick brown fox jumps over the lazy fox");
		stage.setScene(scene);
		
		stage.show();
	}

}