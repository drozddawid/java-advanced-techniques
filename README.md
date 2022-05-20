# Finance manager
Database application for managing instalments payment. Allows to add and remove events, people and instalments. After adding some instalments in `Payments` tab user can pick current date and the application shows unpaid instalments if there are any.

# Build
Run `mvn clean package spring-boot:repackage`

# Run application
Run `java -jar financemanager-0.0.1-SNAPSHOT-spring-boot.jar` from `target` directory after build.

# H2 database
H2 database interface is available at localhost:8080/h2/console
user: user
password: password
It can be changed in application.properies. It is also recommended to change spring.jpa.hibernate.ddl-auto to "validate" after running app for the first time. (update is needed at the first time to auto-generate h2 database scheme).

# The content of the task

**EN (translated with [deepl](https://www.DeepL.com/Translator "DeepL translator")):**

Write a program to simulate the operation of a tool for handling the finances of the parent committee of some school class. The main function of this tool is to handle the case of fundraising for organized events. The tool may work as a desktop application in console mode, it may also be implemented as an application with a graphical interface (desktop or web, but this would require more work).

The task should be completed using a relational database. You can use sqlite or h2 (recommended, because there is no need to install any additional service) or any other database (provided that you will be able to connect to this database while handing in the task).

During the assignment, you can use the DAO design pattern (and the possibilities provided by JDBC) or ORM mapping (and the possibilities provided by JPA together with the Hibernate framework).

When using DAO (with JDBC), please remember to parameterize your SQL queries (don't build queries by "gluing" consecutive strings together). Please remember about scrollability of the result set.

When using ORM mapping, please take care to automatically generate the database schema and use the service layer.

We assume that the following information will be stored in the database:
* Event - identifier, name, place, date.
* Person - identifier, name, surname
* Installments - identifier, event identifier, installment number, due date, amount?
* Deposits - identifier, deposit date, deposit amount, person identifier, event identifier, installment number
 
The program should:
* allow manual data entry (persons, events, installments, deposits).
* allow data browsing (in particular reviewing payments due and payments made)
* automatically check the timeliness and amount of payments and send reminders about next payments (it is enough to write to a file with logs of reminders, the passage of time should be simulated).
* Automatically escalate reminders in case of lack of timely payment (it is enough to write to a file with logs of escalated reminders, time lapse should be simulated)

Translated with www.DeepL.com/Translator (free version)

**PL (original)**

Napisz program, który pozwoli zasymulować działanie narzędzia do obsługi finansów komitetu rodzicielskiego jakiejś szkolnej klasy. Główną funkcją tego narzędzia ma być obsługa przypadku zbierania funduszy na organizowane wydarzenia. Narzędzie może działać jako aplikacja desktopowa w trybie konsolowym, może też być zaimplementowane jako aplikacja z graficznym interfejsem (desktopowa lub internetowa, ale to wymagałoby więcej pracy).

Zadanie należy zrealizować wykorzystując relacyjną bazę danych. Można skorzystać z sqlite czy też h2 (zalecane, bo nie ma potrzeby instalowania żadnego dodatkowego serwisu) lub innej bazy danych (pod warunkiem, że podczas oddawania zadania będzie można połączyć się z tą bazą danych).

Podczas realizacji zadania można skorzystać z wzorca projektowego DAO (oraz możliwości, jakie daje JDBC) lub mapowania ORM (oraz możliwości, jakie daje JPA razem z frameworkiem Hibernate).

W przypadku użycia DAO (z JDBC) proszę pamiętać, by parametryzować zapytania SQL (nie wolno budować zapytań poprzez "sklejanie" kolejnych ciągów znaków). Proszę pamiętać o przewijalności zbioru wynikowego.

W przypadku zastosowania mapowania ORM proszę zadbać o automatyczne wygenerowanie schematu bazy danych oraz zastosowanie warstwy serwisów.

Zakładamy, że w bazie danych będą przechowywane następujące informacje:
* Wydarzenie - identyfikator, nazwa, miejsce, termin
* Osoba - identyfikator, imię, nazwisko
* Raty - identyfikator, identyfikator wydarzenia, numer raty, termin płatności, kwota?
* Wpłaty - identyfikator, termin wpłaty, kwota wpłaty, identyfikator osoby, identyfikator wydarzenia, numer raty
 
Program powinien:
* umożliwiać ręczne wprowadzanie danych (osób, wydarzeń, rat, wpłat).
* umożliwiać przeglądanie danych (w szczególności przeglądanie należnych i dokonanych wpłat)
* automatycznie sprawdzać terminowość i wysokość wpłat oraz wysyłać monity o kolejnych płatnościach (wystarczy, że będzie pisał do pliku z logami monitów, upływ czasu należy zasymulować).
* automatycznie eskalować monity w przypadku braku terminowej wpłaty (wystarczy, że będzie pisał do pliku z logami ekalowanych monitów, upływ czasu należy zasymulować)

