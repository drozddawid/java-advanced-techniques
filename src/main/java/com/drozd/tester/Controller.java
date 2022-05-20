package com.drozd.tester;

import com.drozd.tester.model.entity.Link;
import com.drozd.tester.model.list.Admin1DivisionList;
import com.drozd.tester.service.Internationalization;
import com.drozd.tester.service.TeleportApi;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.ChoiceFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.*;

public class Controller {
    private Internationalization i18n;
    private MessageFormat messageFormat;
    private ChoiceFormat choiceFormat;
    private ResourceBundle resourceBundle;
    private TeleportApi api;
    @FXML
    private AnchorPane root;

    @FXML
    private Button plButton;
    @FXML
    private Button enButton;
    //Question 1
    @FXML
    private Label q1QuestionTag;
    @FXML
    private Label q1QuestionLabel;
    @FXML
    private ComboBox<Link> q1CountryComboBox;
    @FXML
    private TextField q1AnswerTextField;
    @FXML
    private Label q1QuestionCheckLabel;

    //Question2
    @FXML
    private ComboBox<Link> q2CountryComboBox;
    @FXML
    private TextField q2AnswerTextField;
    @FXML
    private Label q2QuestionCheckLabel;
    //Question3
    @FXML
    private TextField q3AnswerTextField;
    @FXML
    private Label q3QuestionCheckLabel;

    @FXML
    public void initialize() {
        i18n = Internationalization.getInstance();
        messageFormat = i18n.getMessageFormat();
        choiceFormat = i18n.getChoiceFormat();
        resourceBundle = i18n.getResourceBundle();
        api = TeleportApi.getInstance();
        if (Objects.equals(i18n.getLocale().getLanguage(), "pl")) {
            plButton.setDisable(true);
            enButton.setDisable(false);
        } else {
            plButton.setDisable(false);
            enButton.setDisable(true);
        }

        Link[] continentLinks = api.getContinentList().getContinentLinks();
        q1CountryComboBox.setItems(FXCollections.observableList(Arrays.asList(continentLinks)));

        //        for(Link continentLink : continentLinks){
//            countryLinks.addAll(Arrays.asList(api.getContinentCountriesList(api.getContinent(continentLink)).getContinentCountriesList()));
//        }
        List<Link> countryLinks = new LinkedList<>(Arrays.asList(api.getCountries().getContinentCountriesList()));
        q2CountryComboBox.setItems(FXCollections.observableList(countryLinks));
    }

    private void reloadScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"), Internationalization.getInstance().getResourceBundle());
            root.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSetLanguagePL(ActionEvent e) {
        i18n.setLocale(new Locale("pl", "PL"));
        reloadScene();
    }

    @FXML
    public void onSetLanguageEN(ActionEvent e) {
        i18n.setLocale(new Locale("en", "US"));
        reloadScene();
    }

    @FXML
    public void onQ1AnswerKeyClicked(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getCode().equals(KeyCode.ENTER)) {

                try {
                    Integer answer = Integer.parseInt(q1AnswerTextField.getText());
                    Link selectedContinent = q1CountryComboBox.getSelectionModel().getSelectedItem();
                    if(selectedContinent != null) {
                        Link countriesListLink = api.getContinent(selectedContinent.getHref()).getCountriesListLink();
                        Integer correctAnswer = api.getContinentCountriesList(countriesListLink).getCount();
                        if (answer.equals(correctAnswer)) {
                            messageFormat.applyPattern(resourceBundle.getString("q1CorrectAnswer"));
                        } else {
                            messageFormat.applyPattern(resourceBundle.getString("q1WrongAnswer"));
                        }
                        Object[] formatters = new Object[]{selectedContinent.getName(), correctAnswer};
                        q1QuestionCheckLabel.setText(messageFormat.format(formatters));
                    }else{
                        q1QuestionCheckLabel.setText(resourceBundle.getString("q1NoContinentSelected"));
                    }
                } catch (NumberFormatException e) {
                    q1QuestionCheckLabel.setText(resourceBundle.getString("q1WrongInputMessage"));

                }
            }
        }
    }
    @FXML
    public void onQ2AnswerKeyClicked(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                try {
                    Integer answer = Integer.parseInt(q2AnswerTextField.getText());
                    Link selectedCountry = q2CountryComboBox.getSelectionModel().getSelectedItem();
                    if(selectedCountry != null) {
                        Admin1DivisionList admin1divisionsList = api.getCountryAdmin1Divisions(api.getCountry(selectedCountry));
                        Integer correctAnswer = admin1divisionsList.getCount();
                        if (answer.equals(correctAnswer)) {
                            messageFormat.applyPattern(resourceBundle.getString("q2CorrectAnswer"));
                        } else {
                            messageFormat.applyPattern(resourceBundle.getString("q2WrongAnswer"));
                        }
                        Object[] formatters = new Object[]{selectedCountry.getName(), correctAnswer};
                        q2QuestionCheckLabel.setText(messageFormat.format(formatters));
                    }else{
                        q2QuestionCheckLabel.setText(resourceBundle.getString("q2NoCountrySelected"));
                    }
                } catch (NumberFormatException e) {
                    q2QuestionCheckLabel.setText(resourceBundle.getString("q1WrongInputMessage"));
                }
            }
        }
    }

    @FXML
    public void onQ3AnswerKeyClicked(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                try {
                    Integer answer = Integer.parseInt(q3AnswerTextField.getText());
                    double[] limits = {0,1,2,5};
                    String[] roseFormats = {
                            resourceBundle.getString("noRoses"),
                            resourceBundle.getString("oneRose"),
                            resourceBundle.getString("2-4Roses"),
                            resourceBundle.getString("5-xRoses")
                    };
                    choiceFormat.setChoices(limits, roseFormats);
                    String pattern = resourceBundle.getString("michaelHasRoses");
                    Format[] formats = {choiceFormat, NumberFormat.getInstance()};
                    messageFormat.applyPattern(pattern);
                    messageFormat.setFormats(formats);
                    Object[] args = new Object[]{answer, answer};
                    q3QuestionCheckLabel.setText(messageFormat.format(args));

                } catch (NumberFormatException e) {
                    q3QuestionCheckLabel.setText(resourceBundle.getString("q1WrongInputMessage"));
                }
            }
        }
    }

}