package com.drozd.tester.service;

import com.drozd.tester.App;

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Internationalization {
    private static final Internationalization internationalization = new Internationalization();
    private final String bundleBaseName;

    private final Preferences preferences;

    private Locale locale;
    private ResourceBundle messages;
    private ChoiceFormat choiceFormat;
    private MessageFormat messageFormat;

    private Internationalization() {
        this.preferences = Preferences.userNodeForPackage(App.class);
        this.bundleBaseName = preferences.get("bundleBaseName", "LanguageBundle");
        this.locale = new Locale(preferences.get("language", "en"), preferences.get("country", "US"));
        this.messages = ResourceBundle.getBundle(bundleBaseName, locale);
        this.messageFormat = new MessageFormat("", locale);
        this.choiceFormat = new ChoiceFormat("");
    }
    public static Internationalization getInstance(){
        return internationalization;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        messages = ResourceBundle.getBundle(bundleBaseName, locale);
        messageFormat.setLocale(locale);
        preferences.put("language", locale.getLanguage());
        preferences.put("country", locale.getCountry());
    }

    public ResourceBundle getResourceBundle() {
        return messages;
    }

    public ChoiceFormat getChoiceFormat() {
        return choiceFormat;
    }

    public MessageFormat getMessageFormat() {
        return messageFormat;
    }
}
