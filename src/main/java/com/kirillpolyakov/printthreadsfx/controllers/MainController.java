package com.kirillpolyakov.printthreadsfx.controllers;

import com.kirillpolyakov.printthreadsfx.model.ComparatorFactory;
import com.kirillpolyakov.printthreadsfx.model.Dispatcher;
import com.kirillpolyakov.printthreadsfx.model.Document;
import com.kirillpolyakov.printthreadsfx.model.DocumentFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.Comparator;
import java.util.List;

public class MainController {
    @FXML
    public volatile ListView<Document> listViewToPrint;
    @FXML
    public ListView<Document> listViewNotPrinted;
    @FXML
    public ListView<Document> listViewPrinted;
    @FXML
    public ComboBox<String> comboBoxType;
    @FXML
    public ComboBox<String> comboBoxSortBy;

    @FXML
    public TextField textFieldAVR;

    private Dispatcher dispatcher;

    @FXML
    public void initialize(){
        dispatcher = new Dispatcher(listViewToPrint);
        dispatcher.createThread();
        comboBoxType.getItems().addAll("TXT", "PNG", "JPG");
        comboBoxSortBy.getItems().addAll("in order", "type", "duration", "paper size");
    }

    @FXML
    public void buttonAdd(ActionEvent actionEvent) {
        try {
            String type = comboBoxType.getSelectionModel().getSelectedItem();
            Document document = new DocumentFactory().create(type);
            dispatcher.addDocumentToPrint(document);
        } catch (NullPointerException ignored) {
        }
    }

    @FXML
    public void buttonCancel(ActionEvent actionEvent) {
        Document document = listViewToPrint.getSelectionModel().getSelectedItem();
        dispatcher.cancelPrintDocument(document);
    }

    @FXML
    public void buttonGet(ActionEvent actionEvent) {
        try {
            String by = comboBoxSortBy.getSelectionModel().getSelectedItem();
            Comparator<Document> comparator = new ComparatorFactory().comparator(by);
            List<Document> res = dispatcher.getSortedListOfPrintedDocuments(comparator);
            listViewPrinted.setItems(FXCollections.observableList(res));
        } catch (NullPointerException ignored) {
        }
    }

    @FXML
    public void buttonStop(ActionEvent actionEvent) {
        dispatcher.stopPrint();
        listViewNotPrinted.setItems(FXCollections.observableList(dispatcher.getNotPrintedDocuments()));
        Thread.currentThread().interrupt();
    }

    public void buttonAVR(ActionEvent actionEvent) {
        textFieldAVR.setText(String.valueOf(dispatcher.getAvrTimePrintedDocuments()));
    }
}
