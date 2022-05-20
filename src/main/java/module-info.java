module com.drozd.tester {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.compiler;
    requires java.prefs;


    opens com.drozd.tester to javafx.fxml;
    exports com.drozd.tester;
    exports com.drozd.tester.service.deserializer to com.fasterxml.jackson.databind;
    exports com.drozd.tester.model.list to com.fasterxml.jackson.databind;
    exports com.drozd.tester.model.entity to com.fasterxml.jackson.databind;
}