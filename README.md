# Process loader
Application which allows loading, using and unloading compiled classes which implement `Processor` interface.

# Build
1. Run `mvn compile` from `processors` project root directory. After that you can find compiled classes implementing interface `Processor` in directory `processors/target/classes/processors`. These compiled classes can be loaded by the application.
2. Run `mvn javafx:jlink` from `processLoader` root directory. This will create custom JRE which allows you to run the application.


# Run application
Custom JRE will be created in `processLoader/target/app` and `processLoader/target/app.zip`. It contains all modules required to run the application. To run the application, you need to double-click `processLoader/target/app/bin/app.bat`.
Or run `processLoader/target/app/bin/java -m com.drozd.processloader/com.drozd.processloader.App`.<br>
Alternatively you can also run `mvn javafx:run` from `processLoader/`

# Testing
As mentioned before, the classes which we can load with the application will be compiled into `processors/target/classes/processors`, so you need to select this directory in the app and then click `Load` button. Then you can use functionalities provided by loaded classes. You can also unload classes using `Unload` button. To check if classes were actually unloaded you can use `jconsole` tool which is bundled with JDK.

# The content of the task

**EN (translated with [deepl](https://www.DeepL.com/Translator "DeepL translator")):**

Write an application that allows you to delegate tasks to class instances that are dynamically loaded with your own class loader. This task should be implemented using a reflection API.

The application should provide an interface where you can, repeatedly, enter the text (data to process), select a class to load (whose instance will process the text), order the processing task and monitor its progress, display the result of the processing, unload loaded classes.

Classes that process text should implement the processing.Processor interface with code as follows. This code documents the meaning of the included methods and their parameters. In addition, the documented source code of the other required classes is provided.

At least 3 different text processing classes should be provided. These classes should be compiled separately from the application itself (the application should not know them during the build). It can be assumed that the byte code of these classes will be placed in a directory that the application will have access to. The path to this directory should be a parameter set in the application during its runtime. Let the default value for the path be the directory where the application was started. The application should read the contents of this directory and load the found classes with its own loader. We assume that during the application runtime, it will be possible to "add" new classes to this directory (so we should think about a kind of "refreshing").

The selection of classes should be done through a list that displays the names of loaded classes. These names should be accompanied by descriptions obtained using the getInfo() method of the created instances of those classes.

The task delegation to class instances should be performed by the submitTask(String task, StatusListner sl) method.
In this method you should provide a listener of type StatusListener, which will receive information about the change of processing status.
The processing.Status class is used to represent the processing status (this class is declared so that when a status is created, its attributes are read-only).

Please implement processing in a thread with delays so that you can monitor the current processing status.
You can use a separate list (task monitor) displayed on the application interface to monitor the processing statuses and display the results.

Please write your own (!) class loader. This loader can be a class to which you pass the location path of the byte codes of the loaded classes with processing algorithms. The custom loader must not extend the URLClassLoader class.

**PL (original)**

Napisz aplikację, która umożliwi zlecanie wykonywania zadań instancjom klas ładowanym dynamicznie własnym ładowaczem klas. Zadanie to należy zrealizować za pomocą reflection API.

Aplikacja powinna udostępniać interfejs, na którym będzie można, wielokrotnie, wprowadzić tekst (dane do przetworzenia), wybrać klasę do załadowania (której instancja ten tekst przetworzy),  zlecić zadanie przetwarzania i monitorować jego przebiec, wyświetlić wynik przetwarzania, wyładować załadowane klasy.

Klasy przetwarzające tekst powinny implementować interfejs processing.Processor o kodzie jak niżej. W kodzie tym zdokumentowano znaczenie zawartych w nim metod oraz ich parametrów. Ponadto dostarczono zdokumentowany kod źródłowy innych wymaganych klas.

Należy dostarczyć przynajmniej 3 różne klasy przetwarzające tekst. Klasy te powinny być skompilowane odrębnie od samej aplikacji (podczas budowania aplikacja nie powinna ona ich znać). Można założyć, że kod bajtowy tych klas będzie umieszczany w katalogu, do którego aplikacja będzie miała dostęp. Ścieżka do tego katalogu powinna być parametrem ustawianym w aplikacji w trakcie jej działania. Wartością domyślną dla ścieżki niech będzie katalog, w którym uruchomiono aplikację. Aplikacja powinna odczytać zawartość tego katalogu i załadować własnym ładowaczem odnalezione klasy. Zakładamy, że podczas działania aplikacji będzie można "dorzucić" nowe klasy do tego katalogu (należy więc pomyśleć o pewnego rodzaju "odświeżaniu").

Wybieranie klas powinno odbywać się poprzez listę wyświetlającą nazwy załadowanych klas. Nazwom tym niech towarzyszą opisy pozyskane metodą getInfo() utworzonych instancji tych klas.

Zlecanie zadań instancjom klas powinno odbywać się poprzez metodę submitTask(String task, StatusListner sl).
W metodzie tej należy podać słuchacza typu StatusListener, który będzie otrzymywał informacje o zmianie statusu przetwarzania.
Do reprezentacji statusu przetwarzania służy klasa processing.Status (klasę tę zadeklarowano tak, aby po utworzeniu statusu jego atrybuty były tylko do odczytu).

Proszę przetwarzanie zaimplementować w wątku z opóźnieniami, by dało się monitorować bieżący status przetwarzania.
Do monitorowania statusów przetwarzania i wyświetlania wyników można użyć osobnej listy (monitora zadań) wyświetlanej na interfejsie aplikacji.

Proszę napisać własny (!) ładowacz klas. Ładowacz ten może być klasą, do której przekazana zostanie ścieżka położenia kodów bajtowych ładowanych klas z algorytmami przetwarzania. Własny ładowacz nie może rozszerzać klasy URLClassLoader.



