
import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;


public class Main extends Application {
	private static Button[][] btns;
	private static Board board;
	private static int SIZE;
	static private ImageView onBtn, offBtn; 
	static private final String onFilePath = new File("").getAbsolutePath() + "\\bin\\img\\on.png";
	static private final String offFilePath = new File("").getAbsolutePath() + "\\bin\\img\\off.png";
	static private final String helpFilePath = new File("").getAbsolutePath() + "\\bin\\help\\HowToPlay.html";
	static private final String[] diffs = {"3", "4", "5", "6"};
	static boolean answer;
	static Stage stage;
	static VBox[] cols;
	static HBox rows;
	
	
	//In JavaFX, main is only used to call the start method via launch()
	public static void main(String[] args) {launch(args);}
	
	
	//The overridden start method can only be called ONCE (via the launch() in main())
	//This means that in order to have a restart capability,
	//we need to call a secondary start method to actually perform
	//start's intended objective.
	@Override
	public void start(Stage primaryStage) {
		startGame(primaryStage);
	}
	//The true start method
	public void startGame(Stage primaryStage) {
		
		try {
			//set difficulty
			try {
				SIZE = Integer.parseInt(DiffSelect(diffs, "Select Difficulty", "Flippy Squares"));
			} catch (NumberFormatException e) {
				System.exit(0); //If they don't wanna play, they don't wanna play.
			}
			
			
			//load images
			File onFile = new File(onFilePath);
			Image onImg = new Image(onFile.toURI().toString());
			onBtn = new ImageView(onImg);
			
			File offFile = new File(offFilePath);
			Image offImg = new Image(offFile.toURI().toString());
			offBtn = new ImageView(offImg);
			
			
			board = new Board(SIZE);
			btns = new Button[SIZE][SIZE];
			cols = new VBox[SIZE];
			
			Button reset = new Button();
			reset.setText("Reset");
			reset.setOnMouseClicked(e -> {
				try {
					reset(SIZE);
					startGame(primaryStage);
				} catch (NumberFormatException cancelledBoxOrDidntSelect) {
					return;
				}
			});
			
			HBox rows = new HBox();
			
			VBox root = new VBox(20);
			
			//Menu Bar
			Menu m = new Menu("Game");
			MenuItem newGame = new MenuItem("New Game");
			newGame.setOnAction(e -> {
				try {
					reset(SIZE);
					startGame(primaryStage);
				} catch (NumberFormatException cancelledBoxOrDidntSelect) {
					return;
				}
			
				
				
				
			});
			MenuItem quitGame = new MenuItem("Quit");
			quitGame.setOnAction(e -> {
				System.exit(0);
			});
			m.getItems().addAll(newGame, quitGame);
			
			Menu m2 = new Menu("Help");
			MenuItem aboutGame = new MenuItem("About");
			aboutGame.setOnAction(h -> {
				AboutBox();
			});
			MenuItem helpGame = new MenuItem("How to Play");
			helpGame.setOnAction(h -> {
				howToPlay();
			});
			m2.getItems().addAll(aboutGame, helpGame);
			
			MenuBar menu = new MenuBar();
			menu.getMenus().addAll(m,m2);
			
			//button setup
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					btns[i][j] = new Button();
					btns[i][j].setMinWidth(80);
					btns[i][j].setGraphic(new ImageView(onBtn.getImage()));
					int k = i;
					int l = j;
					btns[i][j].setOnMouseClicked(e -> {
						board.pushButton(k, l);
						updateLabels(k,l);
						if (board.checkFinished()) {
							WinBox();
						}
					});
				}
			}
			for (int i = 0; i < SIZE; i++) {
				cols[i] = new VBox();
				cols[i].getChildren().addAll(btns[i]);
			}
			
			
			
			
			
			
			rows.getChildren().addAll(cols);
			root.getChildren().addAll(menu, rows, reset);
			root.setAlignment(Pos.CENTER);
			root.setMinWidth(btns.length*80);
			root.setMinHeight(btns.length*50);
			

			
			
			
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void reset(int size) {
		board = new Board(size);
		btns = new Button[size][size];
		cols = new VBox[SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				btns[i][j] = new Button();
				btns[i][j].setMinWidth(80);
				btns[i][j].setGraphic(new ImageView(onBtn.getImage()));
				int k = i;
				int l = j;
				btns[i][j].setOnMouseClicked(e -> {
					board.pushButton(k, l);
					updateLabels(k,l);
					if (board.checkFinished()) {
						WinBox();
					}
				});
			}
		}
		for (int i = 0; i < SIZE; i++) {
			cols[i] = new VBox();
			cols[i].getChildren().addAll(btns[i]);
		}
		for (int i = 0; i < btns.length; i++) {
			for (int j = 0; j < btns[i].length; j++) {
				updateLabels(i,j);
			}
		}
		
	}
	
	private void updateLabels(int x, int y) {
		if (board.getButton(x, y)) {
			btns[x][y].setGraphic(new ImageView(onBtn.getImage()));
		}
		else {
			btns[x][y].setGraphic(new ImageView(offBtn.getImage()));
		}
		if (x > 0) {
			if (board.getButton(x-1, y)) {
				btns[x-1][y].setGraphic(new ImageView(onBtn.getImage()));
			}
			else {
				btns[x-1][y].setGraphic(new ImageView(offBtn.getImage()));
			}
		}
		if (x < SIZE-1) {
			if (board.getButton(x+1, y)) {
				btns[x+1][y].setGraphic(new ImageView(onBtn.getImage()));
			}
			else {
				btns[x+1][y].setGraphic(new ImageView(offBtn.getImage()));
			}
		}
		if (y > 0) {
			if (board.getButton(x, y-1)) {
				btns[x][y-1].setGraphic(new ImageView(onBtn.getImage()));
			}
			else {
				btns[x][y-1].setGraphic(new ImageView(offBtn.getImage()));
			}
		}
		if (y < SIZE-1) {
			if (board.getButton(x, y+1)) {
				btns[x][y+1].setGraphic(new ImageView(onBtn.getImage()));
			}
			else {
				btns[x][y+1].setGraphic(new ImageView(offBtn.getImage()));
			}
		}
	}
	
	public static String DiffSelect(String[] radiolabels, String message, String title) {
		
		RadioButton[] radios = new RadioButton[radiolabels.length];
		ToggleGroup radioGroup = new ToggleGroup();
		for (int i = 0; i < radiolabels.length; i++) {
			 radios[i] = new RadioButton();
			 radios[i].setText(radiolabels[i]);
			 radios[i].setToggleGroup(radioGroup);
		}
		radios[0].setSelected(true);
		radios[0].requestFocus();
		
		Label lbl = new Label();
		lbl.setText(message);
		
		VBox radiobox = new VBox();
		radiobox.setAlignment(Pos.CENTER_LEFT);
		radiobox.getChildren().addAll(radios);
		
		Button yes = new Button();
		yes.setText("Submit");
		yes.setOnMouseClicked(e -> yesClicked());
		
		Button no = new Button();
		no.setText("Cancel");
		no.setOnMouseClicked(e -> noClicked());
		
		HBox btnContainer = new HBox(20);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.setPadding(new Insets(10,0,0,0));
		btnContainer.getChildren().addAll(yes, no);
		
		VBox pane = new VBox();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(10,0,10,0));
		
		pane.getChildren().addAll(lbl, radiobox, btnContainer);
		
		Scene scene = new Scene(pane);
		
		
		stage = new Stage();
		stage.setTitle(title);
		stage.setMinWidth(250);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.showAndWait();
		
		try {
			if (answer) return ((RadioButton) radioGroup.getSelectedToggle()).getText();
		} catch (NullPointerException n) {
			System.exit(0);
		}
				
		return null;
	}
	
	private static void WinBox() {
		String message = "You win!";
		String title = "Chicken Dinner!";
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(250);
		stage.setResizable(false);
		
		Label lbl = new Label();
		lbl.setText(message);
		
		Button btn = new Button();
		btn.setText("OK");
		btn.setOnAction(e -> stage.close());
		
		VBox pane = new VBox(20);
		pane.getChildren().addAll(lbl, btn);
		pane.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.showAndWait();
		}
	private static void AboutBox() {
		String line1 = "Flippy Squares";
		String line2 = "April 2020";
		String line3 = "Steve Cina";
		String line4 = "cinas54@my.sunyulster.edu";
		String title = "About";
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(250);
		stage.setResizable(false);
		
		Label lbl1 = new Label(line1);
		Label lbl2 = new Label(line2);
		Label lbl3 = new Label(line3);
		Label lbl4 = new Label(line4);
		
		
		Button btn = new Button();
		btn.setText("OK");
		btn.setOnAction(e -> stage.close());
		
		VBox pane = new VBox(20);
		pane.getChildren().addAll(lbl1, lbl2, lbl3, lbl4, btn);
		pane.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.showAndWait();
		}
	
	private static void howToPlay() {
		stage = new Stage();
		stage.setTitle("How to Play");
		stage.setMinWidth(400);
		stage.setResizable(true);
		
		WebView helpPanel = new WebView();
		
		helpPanel.getEngine().load( (new File(helpFilePath)).toURI().toString());
		
		
		stage.setScene(new Scene(helpPanel));
		stage.showAndWait();
	}
	
	
	
	//Private methods to support public methods.
		private static void noClicked() {
			answer = false;
			stage.close();
		}
		
		private static void yesClicked() {
			answer = true;
			stage.close();
		}
}
