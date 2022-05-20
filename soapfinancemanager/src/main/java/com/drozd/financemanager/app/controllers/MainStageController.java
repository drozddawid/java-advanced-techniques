package com.drozd.financemanager.app.controllers;

import com.drozd.financemanager.app.JFXFinanceManagerApplication;
import com.drozd.financemanager.app.model.Instalment;
import com.drozd.financemanager.app.model.Person;
import com.drozd.financemanager.app.service.EventDataService;
import com.drozd.financemanager.app.service.InstalmentDataService;
import com.drozd.financemanager.app.service.PersonDataService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainStageController {

    @FXML
    private Button addButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ListView<com.drozd.financemanager.app.model.Event> eventsListView;

    @FXML
    private Tab eventsTab;

    @FXML
    private ListView<Instalment> paymentsListView;

    @FXML
    private ListView<Instalment> delayedPaymentsListView;

    @FXML
    private VBox rightSideVBox;

    @FXML
    private VBox delayedPaymentsVBox;

    @FXML
    private Tab paymentsTab;

    @FXML
    private ListView<Person> peopleListView;

    @FXML
    private Tab peopleTab;

    @FXML
    private Button removeButton;

    @FXML
    private TabPane tabPane;

    @FXML
    private VBox addPaymentVBox;


    @FXML
    private Label addPaymentInfoLabel;

    @FXML
    private Label delayedPaymentsLabel;

    @FXML
    private ComboBox<com.drozd.financemanager.app.model.Event> eventComboBox;

    @FXML
    private TextField eventCostTextField;

    @FXML
    private TextField instalmentPeriodTextField;

    @FXML
    private TextField numberOfInstalmentsTextField;

    @FXML
    private ComboBox<Person> personComboBox;

    @FXML
    private VBox addEventVBox;

    @FXML
    private VBox addPersonVBox;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private TextField eventPlaceTextField;

    @FXML
    private TextField eventTitleTextField;

    @FXML
    private TextField personNameTextField;

    @FXML
    private TextField personSurameTextField;

    @FXML
    private Label addEventInfoLabel;

    @FXML
    private Label addPersonInfoLabel;


    private EventDataService eventDataService;
    private InstalmentDataService instalmentDataService;
    private PersonDataService personDataService;

    private com.drozd.financemanager.app.model.Event ele;

    @FXML
    void initialize(){
        eventDataService = JFXFinanceManagerApplication.getApplicationContext().getBean(EventDataService.class);
        instalmentDataService = JFXFinanceManagerApplication.getApplicationContext().getBean(InstalmentDataService.class);
        personDataService = JFXFinanceManagerApplication.getApplicationContext().getBean(PersonDataService.class);

        hideAddEventVBox();
        hideAddPersonVBox();
        hideRightSideVBox();
        hideDelayedPaymentsVBox();
        hidePaymentAddingVBox();
        fetchDataViews();
    }

    private void showDelayedPaymentsVBox() {
        delayedPaymentsVBox.setVisible(true);
        delayedPaymentsVBox.setManaged(true);
    }
    private void hideDelayedPaymentsVBox() {
        delayedPaymentsVBox.setVisible(false);
        delayedPaymentsVBox.setManaged(false);
    }

    private void showRightSideVBox() {
        rightSideVBox.setVisible(true);
        rightSideVBox.setManaged(true);
    }
    private void hideRightSideVBox() {
        rightSideVBox.setVisible(false);
        rightSideVBox.setManaged(false);
    }

    private void showAddEventVBox() {
        addEventVBox.setVisible(true);
        addEventVBox.setManaged(true);
    }
    private void hideAddEventVBox() {
        addEventVBox.setVisible(false);
        addEventVBox.setManaged(false);
    }

    private void showAddPersonVBox() {
        addPersonVBox.setVisible(true);
        addPersonVBox.setManaged(true);
    }
    private void hideAddPersonVBox() {
        addPersonVBox.setVisible(false);
        addPersonVBox.setManaged(false);
    }

    private void fetchDataViews() {
        eventsListView.setItems(FXCollections.observableArrayList(eventDataService.getAllEvents()));
        paymentsListView.setItems(FXCollections.observableArrayList(instalmentDataService.getAllInstalments()));
        peopleListView.setItems(FXCollections.observableArrayList(personDataService.getAllPersons()));
        eventsListView.refresh();
        paymentsListView.refresh();
        peopleListView.refresh();
    }

    @FXML
    void onAddButtonClick(ActionEvent event) {
        Tab selectedItem = tabPane.getSelectionModel().getSelectedItem();
        if (eventsTab.equals(selectedItem)) {
            showAddEventVBox();
        } else if (paymentsTab.equals(selectedItem)) {
            showRightSideVBox();
            showPaymentAddingVBox();
        } else if (peopleTab.equals(selectedItem)) {
            showAddPersonVBox();
        }
    }

    @FXML
    void onRemoveButtonClick(ActionEvent event) {
        Tab selectedItem = tabPane.getSelectionModel().getSelectedItem();
        if (eventsTab.equals(selectedItem)) {
            com.drozd.financemanager.app.model.Event selectedEvent = eventsListView.getSelectionModel().getSelectedItem();
            if (selectedEvent != null ) {
                eventDataService.removeEvent(selectedEvent);
            }
        } else if (paymentsTab.equals(selectedItem)) {
            Instalment selectedInstalment = paymentsListView.getSelectionModel().getSelectedItem();
            if(selectedInstalment != null) {
                instalmentDataService.removeInstalment(selectedInstalment);
            }
        } else if (peopleTab.equals(selectedItem)) {
            Person selectedPerson = peopleListView.getSelectionModel().getSelectedItem();
            if(selectedPerson != null) {
                personDataService.removePerson(selectedPerson);
            }
        }
        fetchDataViews();
        onDateChange(new ActionEvent());
    }

    @FXML
    void onDateChange(ActionEvent event) {
        LocalDate date = datePicker.getValue();
        if (date == null) return;
        System.out.println(date.toString());
        List<Instalment> delayedInstalments = instalmentDataService.getDelayedInstalments(date.atTime(12, 0));
        if(delayedInstalments.size() > 0){
            delayedPaymentsListView.setItems(FXCollections.observableArrayList(delayedInstalments));
            showRightSideVBox();
            showDelayedPaymentsVBox();
        }else{
            hideDelayedPaymentsVBox();
            if(!addPaymentVBox.isVisible()) hideRightSideVBox();
        }
    }

    @FXML
    void onTabSelectionChanged(Event event) {

    }

    void showPaymentAddingVBox(){
        personComboBox.setItems(peopleListView.getItems());
        eventComboBox.setItems(eventsListView.getItems());

        addPaymentVBox.setVisible(true);
        addPaymentVBox.setManaged(true);
    }
    void hidePaymentAddingVBox(){
        addPaymentVBox.setVisible(false);
        addPaymentVBox.setManaged(false);
    }

    @FXML
    void onAddPaymentAcceptButtonClick(ActionEvent event) {
        Person selectedPerson = personComboBox.getSelectionModel().getSelectedItem();
        com.drozd.financemanager.app.model.Event selectedEvent = eventComboBox.getSelectionModel().getSelectedItem();
        try {
            if (selectedPerson == null || selectedEvent == null) throw new NullPointerException("Event or Person combobox is not selected.");
            Double eventCost = Double.parseDouble(eventCostTextField.getText().strip());
            Integer numberOfInstalments = Integer.parseInt(numberOfInstalmentsTextField.getText());
            if(numberOfInstalments < 1) numberOfInstalments = 1;
            Integer instalmentPeriodDays = Integer.parseInt(instalmentPeriodTextField.getText());
            if (instalmentPeriodDays < 1) throw new IllegalArgumentException("Instalment period must be greater than 0.");
            Double instalmentCost = eventCost / numberOfInstalments;
            LocalDateTime paymentDeadline = LocalDateTime.now();
            for (int i = 0; i < numberOfInstalments; i++) {
                paymentDeadline = paymentDeadline.plusDays(instalmentPeriodDays.longValue());
                instalmentDataService.addInstalment(new Instalment(selectedEvent, selectedPerson, (i+1), paymentDeadline, instalmentCost, false, null));
            }
            fetchDataViews();
        }catch(NumberFormatException | NullPointerException e){
            addPaymentInfoLabel.setText("Please fill all fields to continue.");
        }catch(IllegalArgumentException e){
            addPaymentInfoLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    void onAddPaymentCancelButtonClick(ActionEvent event) {
        hidePaymentAddingVBox();
        if(!delayedPaymentsVBox.isVisible()) hideRightSideVBox();
    }

    @FXML
    void onPaymentClick(MouseEvent mouseEvent){
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2){
            Instalment selectedInstalment = paymentsListView.getSelectionModel().getSelectedItem();
            if(selectedInstalment.getWasPaid()){
                selectedInstalment.setWasPaid(false);
                selectedInstalment.setWhenWasPaid(null);
            }else{
                selectedInstalment.setWasPaid(true);
                selectedInstalment.setWhenWasPaid(LocalDateTime.now());
            }
            instalmentDataService.modifyInstalment(selectedInstalment);
            fetchDataViews();
        }

    }

    @FXML
    void onAddEventButtonClick(ActionEvent event) {
        String title = eventTitleTextField.getText();
        String place = eventPlaceTextField.getText();
        LocalDate date = eventDatePicker.getValue();
        if (title.length() > 0 && place.length() > 0 && date != null){
            LocalDateTime dateTime = date.atTime(12, 00);
            com.drozd.financemanager.app.model.Event e = new com.drozd.financemanager.app.model.Event(title, place, dateTime, new ArrayList<>());
            eventDataService.addEvent(e);
            fetchDataViews();
        }else{
            addEventInfoLabel.setText("Please fill all fields.");
        }
    }

    @FXML
    void onAddPersonButtonClick(ActionEvent event) {
        String name = personNameTextField.getText();
        String surname = personSurameTextField.getText();

        if (name.length() > 0 && surname.length() > 0){
            Person p = new Person(name, surname, new ArrayList<>());
            personDataService.addPerson(p);
            fetchDataViews();
        }else{
            addPersonInfoLabel.setText("Please fill all fields.");
        }
    }

    @FXML
    void onCancelAddEventButtonClick(ActionEvent event) {
        hideAddEventVBox();
    }

    @FXML
    void onCancelAddPersonButtonClick(ActionEvent event) {
        hideAddPersonVBox();
    }

    @FXML
    void onRefreshButtonClick(ActionEvent event){ fetchDataViews();}
}
