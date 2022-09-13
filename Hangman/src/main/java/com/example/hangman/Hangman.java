package com.example.hangman;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Hangman extends Application {

    Scene main, game, instruction, editDB;
    private Integer sceneH = 600, sceneV = 800;
    private Integer space = 10;
    ObservableList<Word> data = FXCollections.observableArrayList();
    TableView<Word> table = new TableView<Word>();

    Label guessSmallLabel, wordLabel, livesLabel, showWordLabel, showLivesLabel;
    Label guessBigLabel;


    private DbManager dbManager;

    public DbManager getDbManager() {
        return dbManager;
    }

    private void start() throws SQLException {
        this.dbManager = new DbManager();
        this.dbManager.prepareDbIfNeeded();
        data.add(this.dbManager.insertWord(this.dbManager.getConnection(), "mama",
               "rodzina"));
        data.add(this.dbManager.insertWord(this.dbManager.getConnection(), "kot",
                "zwierzę"));
        data.add(this.dbManager.insertWord(this.dbManager.getConnection(), "ołówek",
                "przedmiot"));
    }

    private Button creatButton(String name){
        // Creates buttons of the same size and font
        Font buttonFont = new Font("System", 17);
        Button button = new Button(name);
        button.setPrefSize(170,30);
        button.setFont(buttonFont);
        return button;
    }

    private Label creatGameLabel(String text){
        Label label = new Label(text);
        label.setFont(new Font ("System", 17));
        label.setPrefSize(150, 30);
        return label;
    }

    private Label creatBigLabel(String text){
        Label label = new Label(text);
        label.setFont(new Font("System", 55));
        label.setLayoutX(300);
        label.setLayoutY(65);
        return label;
    }

    private Label creatInstructionLabel(String text){
        // Creates labels which make an instruction text
        Label label = new Label(text);
        label.setFont(new Font ("System", 17));
        label.setPrefSize(700, 30);
        return label;
    }

    private TextField creatTextField(String text){
        TextField temp = new TextField();
        temp.setPromptText(text);
        temp.setPrefSize(170,30);
        temp.setFont(new Font ("System", 17));
        return temp;
    }

    private Label creatWordLabel(String text){
        Label label = creatGameLabel(text);
        label.setFont(new Font("System", 30));
        label.setPrefSize(300, 30);
        return label;
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        // Prepares data base
        start();

        /*
            Main Scene
        */
        Pane mainPane = new Pane();
        // Labels
        Label hangman = new Label("Hangman");
        hangman.setFont(new Font("ArialBlack", 55));
        hangman.setLayoutX(300);
        hangman.setLayoutY(65);
        // Buttons
        VBox mainButtons = new VBox();
        mainButtons.setSpacing(space);
        mainButtons.setLayoutX(325);
        mainButtons.setLayoutY(250);
        mainButtons.setAlignment(Pos.CENTER);

        Button gameButton, instructionButton, editDBButton, endButton;
        Game gameSetup = new Game();

        gameButton = creatButton("Start!");

        instructionButton = creatButton("Instrukcja");
        instructionButton.setOnAction(e -> stage.setScene(instruction));

        editDBButton = creatButton("Edytuj bazę słów");
        editDBButton.setOnAction(e -> stage.setScene(editDB));

        endButton = creatButton("Zakończ");
        endButton.setOnAction(e -> System.exit(0));

        mainButtons.getChildren().addAll(gameButton, instructionButton, editDBButton, endButton);
        mainPane.getChildren().addAll(hangman, mainButtons);

        main  = new Scene(mainPane, sceneV, sceneH);
        stage.setScene(main);

        /*
            Game Scene
        */
        Pane gamePane = new Pane();

        // Labels

        gameButton.setOnAction(e -> {
            try {
                gameSetup.setWordList(dbManager.getWords(dbManager.getConnection()));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            gameSetup.setLivesNumber(5);
            //System.out.println((gameSetup.getHiddenWord()+ gameSetup.getName()));
            showWordLabel.setText(gameSetup.getHiddenWord());
            showLivesLabel.setText(gameSetup.getLives());
            stage.setScene(game);
        });

        //Label guessBigLabel;
        guessBigLabel = creatBigLabel("Zgadnij słowo");
        guessBigLabel.setLayoutX(250);

        //Label guessSmallLabel, wordLabel, livesLabel, showWordLabel, showLivesLabel;
        guessSmallLabel = creatGameLabel("Zgadnij literę");
        wordLabel = creatGameLabel("Słowo");
        livesLabel = creatGameLabel("Życia");
        //showWordLabel = creatGameLabel(gameSetup.getHiddenWord());
        //showWordLabel.setFont(new Font("System", 30));
        //showWordLabel.setPrefSize(300, 30);
        showLivesLabel = creatGameLabel("");
        showLivesLabel.setFont(new Font("System", 30));
        showWordLabel = creatWordLabel("");

        // Boxes

        VBox wordVB = new VBox();
        wordVB.setLayoutX(100);
        wordVB.setLayoutY(200);

        VBox livesVB = new VBox();
        livesVB.setLayoutX(500);
        livesVB.setLayoutY(200);
        livesVB.setSpacing(space);

        VBox guessVB = new VBox();
        guessVB.setLayoutX(100);
        guessVB.setLayoutY(400);
        guessVB.setSpacing(space);

        HBox guessHB = new HBox();
        guessHB.setSpacing(space);
        TextField guessText = new TextField();
        guessText.setPrefSize(170, 30);
        guessText.setFont(new Font("System", 17));
        Button guessButton = creatButton("Zgaduję!");
        guessButton.setOnAction(e -> {
            if (gameSetup.getLose() == false && gameSetup.getWin() == false){
                gameSetup.setLetter(guessText.getText());
                guessText.clear();
                showWordLabel.setText(gameSetup.getHiddenWord());
                showLivesLabel.setText(gameSetup.getLives());
            }
        }
        );

        guessHB.getChildren().addAll(guessText, guessButton);
        guessVB.getChildren().addAll(guessSmallLabel, guessHB);
        wordVB.getChildren().addAll(wordLabel, showWordLabel);
        livesVB.getChildren().addAll(livesLabel, showLivesLabel);

        // Buttons
        VBox gameButtons = new VBox();
        gameButtons.setSpacing(space);
        gameButtons.setLayoutX(600);
        gameButtons.setLayoutY(475);
        //gameButtons.setAlignment(Pos.CENTER);

        Button newWordButton, returnButton;

        newWordButton = creatButton("Nowe słowo");
        newWordButton.setOnAction(e -> {
            gameSetup.randomWord();
            showWordLabel.setText(gameSetup.getHiddenWord());
            showLivesLabel.setText(gameSetup.getLives());
        });

        returnButton = creatButton("Wróć do menu");
        returnButton.setOnAction(e -> stage.setScene(main));

        gameButtons.getChildren().addAll(newWordButton, returnButton);
        gamePane.getChildren().addAll(guessBigLabel, guessVB, wordVB, livesVB, gameButtons);

        game = new Scene(gamePane, sceneV, sceneH);


        /*
            Instruction Scene
        */
        Pane instructionPane = new Pane();

        Label instructionLabel;
        instructionLabel = creatBigLabel("Instrukcja");
        instructionLabel.setFont(new Font("System", 40));

        Button returnInstructionButton;
        returnInstructionButton = creatButton("Wróć do menu");
        returnInstructionButton.setOnAction(e -> stage.setScene(main));
        returnInstructionButton.setLayoutX(300);
        returnInstructionButton.setLayoutY(425);

        VBox instructionText = new VBox();
        instructionText.setLayoutX(100);
        instructionText.setLayoutY(200);

        Label line1, line2, line3, line4, line5, line6;
        line1 = creatInstructionLabel("Witaj, kandydacie na wisielca!");
        line2 = creatInstructionLabel("Twoim zadaniem jest zgadnąć jakie słowo zostało wylosowane z bazy słów wszelakich.");
        line3 = creatInstructionLabel("Możesz zgadnąć pojedynczą literę poprzez wpisanie jej w odpowiednie pole.");
        line4 = creatInstructionLabel("Uważaj! Masz ograniczoną liczbę żyć. Litera, której nie ma w słowie to utrata życia.");
        line5 = creatInstructionLabel("Na stronie głównej możesz edytować bazę słów - dodać nowe, usunąć już istniejące.");
        line6 = creatInstructionLabel("Miłej zabawy!");

        instructionText.getChildren().addAll(line1, line2, line3, line4, line5, line6);
        instructionPane.getChildren().addAll(instructionLabel, instructionText, returnInstructionButton);

        instruction = new Scene(instructionPane, sceneV, sceneH);

        /*
            Edit DB Scene
         */

        table.setEditable(true);
        table.setLayoutX(25);
        table.setLayoutY(100);

        Pane editDbPane = new Pane();

        Label wordList = creatBigLabel("Lista słów");
        wordList.setLayoutY(0);
        HBox deleteHB = new HBox();
        deleteHB.setSpacing(space);
        deleteHB.setLayoutY(450);
        deleteHB.setLayoutX(425);
        HBox addHB = new HBox();
        addHB.setSpacing(space);
        addHB.setLayoutX(25);
        addHB.setLayoutY(525);
        HBox searchByWordHB = new HBox();
        searchByWordHB.setSpacing(space);
        HBox searchByLengthHB = new HBox();
        searchByLengthHB.setSpacing(space);
        HBox searchByCategoryHB = new HBox();
        searchByCategoryHB.setSpacing(space);

        VBox searchVB = new VBox();
        searchVB.setSpacing(space);
        searchVB.setLayoutY(100);
        searchVB.setLayoutX(425);

        Button deleteButton, addButton, searchWordButton, searchLengthButton, searchCategoryButton, returnEditButton, clearSearchButton;
        returnEditButton = creatButton("Wróć do menu");
        returnEditButton.setOnAction(e -> stage.setScene(main));
        deleteButton = creatButton("Usuń");

        clearSearchButton = creatButton("Wyczyść");
        clearSearchButton.setLayoutX(425);
        clearSearchButton.setLayoutY(300);

        addButton = creatButton("Dodaj");
        searchWordButton= creatButton("Szukaj");
        searchLengthButton = creatButton("Szukaj");
        searchCategoryButton = creatButton("Szukaj");

        // Text Fields

        TextField searchWordTF, searchLengthTF, searchCategoryTF, addWordTF, addCategoryTF;
        searchWordTF = creatTextField("Wyszukaj słowo");
        searchLengthTF = creatTextField("Wyszukaj długość");
        searchCategoryTF = creatTextField("Wyszukaj kategorię");
        addWordTF = creatTextField("Słowo");
        addCategoryTF = creatTextField("Kategoria");

        // Functions


        //Creating columns in table view and controlling updates

        TableColumn wordCol = new TableColumn("Słowo");
        wordCol.setMinWidth(100);
        wordCol.setCellValueFactory(
                new PropertyValueFactory<Word, String>("name"));
        wordCol.setCellFactory(TextFieldTableCell.forTableColumn());
        wordCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Word, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Word, String> t) {
                        Word temp = t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        temp.setName(t.getNewValue());
                        //System.out.println(temp.getId());
                        dbManager.updateWord(dbManager.getConnection(), temp);
                        ((Word) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    }
                }
        );

        TableColumn lenCol = new TableColumn("Długość");
        lenCol.setMinWidth(100);
        lenCol.setCellValueFactory(
                new PropertyValueFactory<Word, String>("length"));
        lenCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn categoryCol = new TableColumn("Kategoria");
        categoryCol.setMinWidth(100);
        categoryCol.setCellValueFactory(
                new PropertyValueFactory<Word, String>("category"));
        categoryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Word, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Word, String> t) {
                        Word temp = t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        temp.setCategory(t.getNewValue());
                        //System.out.println(temp.getId());
                        dbManager.updateWord(dbManager.getConnection(), temp);
                        ((Word) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCategory(t.getNewValue());
                    }
                }
        );

        TableColumn idCol = new TableColumn("ID");
        idCol.setCellValueFactory(
                new PropertyValueFactory<Word, Integer>("id"));


        table.getColumns().addAll(idCol, wordCol, lenCol, categoryCol);


        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
             public void handle(ActionEvent e) {
                try {
                    data.add(dbManager.insertWord(dbManager.getConnection(), addWordTF.getText(),
                            addCategoryTF.getText()));
                    addWordTF.clear();
                    addCategoryTF.clear();
                    table.setItems(data);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Word selectedItem = table.getSelectionModel().getSelectedItem();
                dbManager.deleteWord(dbManager.getConnection(), selectedItem);
                table.getItems().remove(selectedItem);
            }});

        searchWordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String searchText = searchWordTF.getText();
                searchWordTF.clear();
                table.setItems((ObservableList<Word>) dbManager.findWordByName(dbManager.getConnection(), searchText));
            }
        });

        searchLengthButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String searchText = searchLengthTF.getText();
                searchLengthTF.clear();
                table.setItems((ObservableList<Word>) dbManager.findWordByLength(dbManager.getConnection(), searchText));
            }
        });

        searchCategoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String searchText = searchCategoryTF.getText();
                searchCategoryTF.clear();
                table.setItems((ObservableList<Word>) dbManager.findWordByCategory(dbManager.getConnection(), searchText));
            }
        });


        clearSearchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                table.getItems().clear();
                try {
                    data = dbManager.showWords(dbManager.getConnection());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                table.setItems(data);
            }
        });

        try {
            data = dbManager.showWords(dbManager.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setItems(data);



        searchByWordHB.getChildren().addAll(searchWordTF, searchWordButton);
        searchByLengthHB.getChildren().addAll(searchLengthTF, searchLengthButton);
        searchByCategoryHB.getChildren().addAll(searchCategoryTF, searchCategoryButton);
        addHB.getChildren().addAll(addWordTF, addCategoryTF, addButton);
        deleteHB.getChildren().addAll(deleteButton, returnEditButton);

        searchVB.getChildren().addAll(searchByWordHB, searchByLengthHB, searchByCategoryHB);
        editDbPane.getChildren().addAll(wordList, searchVB, clearSearchButton, table, deleteHB, addHB);
        editDB = new Scene(editDbPane, sceneV, sceneH);

        stage.setTitle("Gra w wisielca");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}