//Saif Jame and Philip Aquilina
package application;
	
import java.awt.Button; 
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;


public class SongLib extends Application {
	Stage window;
	Stage scene;
	Button button;
	 @FXML
	 public ListView<String> songList;
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root= FXMLLoader.load(getClass().getResource("design.fxml"));
		primaryStage.setTitle("Song List");
		primaryStage.setScene(new Scene(root,300,700));
		primaryStage.show();
	}
	
	public static void main(String[] args,ListView <String> songList) {
		launch(args);
	}
}
