# FIle Comparator
Application for detecting changes in files. Allows user to save snapshot of directory content and detect future changes in files basing on created snapshot.

# Build
These two steps create custom JRE with all modules required to run application.
1. Run
```mvn install```
**from project root directory**
2. Run
```mvn javafx:jlink```
**from app module directory "app"**

# Run application
Custom JRE will be created in `app/target/app` and `app/target/app.zip`. To run application, you need to double click `app/target/app/bin/app.bat`.
Or run `bin/java.exe -m app/com.drozd.app.App`

# Known issues
* Application may freeze for a while when snapshotting or reading directory with large number of files inside it. This is becasue everything is done synchronously. In future releases data processing should be done asynchronously with progress indicating.

# The content of the task

**EN (translated with [deepl](https://www.DeepL.com/Translator "DeepL translator")):**

Write an application to check specified directories for changes to the files they contain. 

The application should calculate the MD5 hash for each file examined in two steps: 1. preparing a "snapshot" of the current situation, and 2. verifying from the stored "snapshot" whether any changes have been made to the files.

The application should also be written using modules (introduced in Java since jdk 9). There should be a library module and an application module (using the library module). 

The resulting modules should be exported to jar files.

Using jlink you should prepare a minimal runtime environment to which the above mentioned modules will be plugged.

It should be possible to run the application from the command line, using only the generated runtime environment. The application itself should offer a user interface (preferably graphical, minimum - text).

Translated with www.DeepL.com/Translator (free version)

**PL (original)**

Napisz aplikację, która pozwoli na sprawdzania wskazanych katalogów pod kątem wystąpienia zmian w zawartych w nich plikach. 

Aplikacja powinna wyliczać skrót MD5 dla każdego badanego pliku w dwóch krokach: 1. przygotowując "snapshot" bieżącej sytuacji oraz 2. weryfikując na podstawie zapamiętanego "snapshotu", czy w plikach wprowadzono jakieś zmiany.

Aplikacja powinna być też napisana z wykorzystaniem modułów (wprowadzonych w Javie od jdk 9). Powstać ma moduł biblioteki oraz moduł samej aplikacji (korzystający z modułu biblioteki). 

Powstałe moduły należy wyeksportować do plików jar.

Używając jlink należy przygotować minimalne środowisko uruchomieniowe, do którego podpięte zostaną wymienione wyżej moduły.

Aplikację powinno dać się uruchomić z linii komend, korzystając tylko z wygenerowanego środowiska uruchomieniowego. Sama aplikacja powinna oferować interfejs użytkownika (najlepiej graficzny, minimum - tekstowy).



