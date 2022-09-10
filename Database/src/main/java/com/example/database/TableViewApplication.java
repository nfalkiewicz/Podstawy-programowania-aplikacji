package com.example.database;

/*
Uwaga: domyślam się, że powinny być osobne metody, które zajmują się wszystkimi komentarzami,
ale przerosło mnie ustawienie odpowiedniej dostępności obiektów
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;

public class TableViewApplication extends Application {

    private TableView<Book> table = new TableView<Book>();
    private ObservableList<Book> data = FXCollections.observableArrayList();

    final HBox delete = new HBox();
    final HBox add = new HBox();
    final HBox searchByTitle = new HBox();

    private DbManager dbManager;

    public DbManager getDbManager() {
        return dbManager;
    }

    private void start() {
        this.dbManager = new DbManager();
        this.dbManager.prepareDbIfNeeded();
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage){
        start();

        Scene scene = new Scene(new Group());
        stage.setTitle("Books database");
        stage.setWidth(1000);
        stage.setHeight(700);

        final Label label = new Label("Library");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        /*
        Creating columns in table view and controlling updates
         */
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Book, String> t) {
                        Book temp = t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        temp.setLastName(t.getNewValue());
                        //System.out.println(temp.getId());
                        dbManager.updateBook(dbManager.getConnection(), temp);
                        ((Book) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLastName(t.getNewValue());
                    }
                }
        );

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Book, String> t) {
                        Book temp = t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        temp.setFirstName(t.getNewValue());
                        //System.out.println(temp.getId());
                        dbManager.updateBook(dbManager.getConnection(), temp);
                        ((Book) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFirstName(t.getNewValue());
                    }
                }
        );

        TableColumn titleCol = new TableColumn("Title");
        titleCol.setMinWidth(100);
        titleCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("title"));
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        titleCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Book, String> t) {
                        Book temp = t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        temp.setTitle(t.getNewValue());
                        //System.out.println(temp.getId());
                        dbManager.updateBook(dbManager.getConnection(), temp);
                        ((Book) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTitle(t.getNewValue());
                    }
                }
        );


        TableColumn yearCol = new TableColumn("Year");
        yearCol.setMinWidth(100);
        yearCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("year"));
        yearCol.setCellFactory(TextFieldTableCell.forTableColumn());
        yearCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Book, String> t) {
                        Book temp = t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                        temp.setYear(t.getNewValue());
                        //System.out.println(temp.getId());
                        dbManager.updateBook(dbManager.getConnection(), temp);

                       ((Book) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setYear(t.getNewValue());

                    }
                }
        );

        TableColumn printCol = new TableColumn("Print");
        printCol.setMinWidth(100);
        printCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("print"));
        printCol.setCellFactory(TextFieldTableCell.forTableColumn());
        printCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Book, String> t) {
                        Book temp = t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        temp.setPrint(t.getNewValue());
                        //System.out.println(temp.getId());
                        dbManager.updateBook(dbManager.getConnection(), temp);
                        ((Book) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPrint(t.getNewValue());
                    }
                }
        );

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setMinWidth(200);
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("description"));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Book, String> t) {
                        Book temp = t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        temp.setDescription(t.getNewValue());
                        //System.out.println(temp.getId());
                        dbManager.updateBook(dbManager.getConnection(), temp);
                        ((Book) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDescription(t.getNewValue());
                    }
                }
        );

        TableColumn idCol = new TableColumn("ID");
        idCol.setMinWidth(200);
        idCol.setCellValueFactory(
                new PropertyValueFactory<Book, Integer>("id"));


        table.getColumns().addAll(idCol, lastNameCol, firstNameCol, titleCol, yearCol, printCol, descriptionCol);

        /*
        Creating TextFields
         */

        final TextField searchByTitleText = new TextField();
        searchByTitleText.setMaxWidth(600);
        searchByTitleText.setPromptText("What's my title?");

        final TextField addLastName = new TextField();
        addLastName.setPromptText("Last Name");
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());

        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());

        final TextField addTitle = new TextField();
        addTitle.setPromptText("Title");
        addTitle.setMaxWidth(titleCol.getPrefWidth());

        final TextField addYear = new TextField();
        addYear.setPromptText("Year");
        addYear.setMaxWidth(yearCol.getPrefWidth());

        final TextField addPrint = new TextField();
        addPrint.setPromptText("Print");
        addPrint.setMaxWidth(printCol.getPrefWidth());

        final TextField addDescription = new TextField();
        addDescription.setPromptText("Description");
        addDescription.setMaxWidth(descriptionCol.getPrefWidth());

        /*
        Creating buttons
         */

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    data.add(dbManager.insertData(dbManager.getConnection(), addLastName.getText(),
                        addFirstName.getText(),
                        addTitle.getText(),
                        addYear.getText(),
                        addPrint.getText(),
                        addDescription.getText()));
                    addLastName.clear();
                    addFirstName.clear();
                    addTitle.clear();
                    addYear.clear();
                    addPrint.clear();
                    addDescription.clear();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            Book selectedItem = table.getSelectionModel().getSelectedItem();
            dbManager.deleteBook(dbManager.getConnection(), selectedItem);

               /* try {
                    data = dbManager.showBooks(dbManager.getConnection(), data);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                table.setItems(data);*/
            table.getItems().remove(selectedItem);
        }});

        final Button searchByTitleButton = new Button("Search title");
        searchByTitleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String searchText = searchByTitleText.getText();
                table.setItems((ObservableList<Book>) dbManager.findBooksByTitle(dbManager.getConnection(), searchText));
            }
        });

        final Button clearSearchButton = new Button("Clear filter");
        clearSearchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                table.getItems().clear();
                try {
                    data = dbManager.showBooks(dbManager.getConnection());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                table.setItems(data);
            }
        });

        try {
            data = dbManager.showBooks(dbManager.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setItems(data);

        delete.getChildren().addAll(deleteButton);
        searchByTitle.getChildren().addAll(searchByTitleText, searchByTitleButton, clearSearchButton);
        searchByTitle.setSpacing(3);
        add.getChildren().addAll(addLastName, addFirstName, addTitle, addYear, addPrint, addDescription, addButton);
        add.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, searchByTitle, table, delete, add);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}