module lib {
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    opens com.drozd.lib.model to com.fasterxml.jackson.databind;

    exports com.drozd.lib.model;
    exports com.drozd.lib.service;
}