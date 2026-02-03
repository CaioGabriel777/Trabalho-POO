package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.IUIController;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.BorderPane;
import javafx.beans.binding.Bindings;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.geometry.Side;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class UIController extends Application implements IUIController {
    private MenuBar menuBar;
    private TabPane tabPane;
    private VBox homeContent;
    private static UIController uiController;

    public UIController() {
    }

    @Override
    public void init() {
        uiController = this;
    }

    public static UIController getInstance() {
        return uiController;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Car Rental System");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Header branco
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(12, 25, 12, 25));
        header.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");

        // Logo e título
        Label headerTitle = new Label("CAR RENTAL");
        headerTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Menu Bar
        menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Botão Home
        Button homeButton = new Button("Home");
        homeButton.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #333; -fx-font-size: 13px; " +
                "-fx-padding: 6 16; -fx-background-radius: 15; -fx-cursor: hand;");
        homeButton.setOnAction(e -> showHome());
        homeButton.setOnMouseEntered(e -> homeButton
                .setStyle("-fx-background-color: #d0d0d0; -fx-text-fill: #333; -fx-font-size: 13px; " +
                        "-fx-padding: 6 16; -fx-background-radius: 15; -fx-cursor: hand;"));
        homeButton.setOnMouseExited(e -> homeButton
                .setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #333; -fx-font-size: 13px; " +
                        "-fx-padding: 6 16; -fx-background-radius: 15; -fx-cursor: hand;"));

        header.getChildren().addAll(headerTitle, menuBar, spacer, homeButton);

        // Área de conteúdo principal
        StackPane contentArea = new StackPane();
        contentArea.setStyle("-fx-background-color: #f5f5f5;");

        // TabPane
        tabPane = new TabPane();
        tabPane.setSide(Side.TOP);
        tabPane.setStyle("-fx-background-color: #f5f5f5;");

        // Conteúdo da Home
        homeContent = createHomeContent();

        contentArea.getChildren().addAll(homeContent, tabPane);

        // Mostrar home quando não há abas
        homeContent.visibleProperty().bind(Bindings.isEmpty(tabPane.getTabs()));
        tabPane.visibleProperty().bind(Bindings.isNotEmpty(tabPane.getTabs()));

        root.setTop(header);
        root.setCenter(contentArea);

        Scene scene = new Scene(root, 1280, 800);

        String css = ".menu{-fx-background-color:white;-fx-cursor:hand;}" +
                ".menu:hover{-fx-background-color:white;}" +
                ".menu:focused{-fx-background-color:white;}" +
                ".menu:showing{-fx-background-color:white;}" +
                ".menu .label{-fx-text-fill:#333;}" +
                ".context-menu{-fx-background-color:white;}" +
                ".menu-item{-fx-background-color:white;-fx-cursor:hand;}" +
                ".menu-item:hover{-fx-background-color:white;}" +
                ".menu-item:focused{-fx-background-color:white;}" +
                ".menu-item .label{-fx-text-fill:#333;}" +
                ".menu-item:hover .label{-fx-text-fill:#333;}" +
                ".menu-item:focused .label{-fx-text-fill:#333;}" +
                // Abas
                ".tab-pane .tab-header-area{-fx-background-color:white;}" +
                ".tab-pane .tab-header-background{-fx-background-color:white;}" +
                ".tab{-fx-background-color:white;-fx-background-radius:0 0 8 8;-fx-padding:10 20;-fx-opacity:0.5;}"
                +
                ".tab:selected{-fx-background-color:white;-fx-opacity:1;}" +
                ".tab .tab-label{-fx-text-fill:#888;}" +
                ".tab:selected .tab-label{-fx-text-fill:#333;-fx-font-weight:bold;}" +
                ".tab-content-area{-fx-background-color:white;}";

        scene.getStylesheets().add("data:text/css," + css);

        primaryStage.setScene(scene);
        primaryStage.show();

        Core.getInstance().getPluginController().init();
    }

    private VBox createHomeContent() {
        VBox home = new VBox(25);
        home.setAlignment(Pos.CENTER);
        home.setPadding(new Insets(60));
        home.setStyle("-fx-background-color: #f5f5f5;");

        // Ícone carro
        Label logo = new Label("🚗");
        logo.setStyle("-fx-font-size: 70px;");

        // Título
        Label title = new Label("Welcome to Car Rental");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Subtítulo
        Label subtitle = new Label("Your premium vehicle rental solution");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");

        // Cards
        HBox cardsBox = new HBox(25);
        cardsBox.setAlignment(Pos.CENTER);
        cardsBox.setPadding(new Insets(30, 0, 0, 0));

        home.getChildren().addAll(logo, title, subtitle, cardsBox);

        return home;
    }

    private void showHome() {
        tabPane.getTabs().clear();
    }

    public MenuItem createMenuItem(String menuText, String menuItemText) {
        Menu newMenu = null;
        for (Menu menu : menuBar.getMenus()) {
            if (menu.getText().equals(menuText)) {
                newMenu = menu;
                break;
            }
        }
        if (newMenu == null) {
            newMenu = new Menu(menuText);
            menuBar.getMenus().add(newMenu);
        }

        MenuItem menuItem = new MenuItem(menuItemText);
        newMenu.getItems().add(menuItem);

        return menuItem;
    }

    public boolean createTab(String tabText, Node contents) {

        ScrollPane scrollPane = new ScrollPane(contents);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white; -fx-background: white;");

        Tab tab = new Tab();
        tab.setText(tabText);
        tab.setContent(scrollPane);
        tabPane.getTabs().add(tab);

        tabPane.getSelectionModel().select(tab);

        return true;
    }
}
