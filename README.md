# Catalog
Application for testing different VM options related with garbage collection. Allows user to browse catalog of people. One record in people catalog is represented by directory which name is record ID. Also there are two files (image.png and record.txt) inside the directory. Image is picture of a person, and record is some text data related to person on the picture. Person records which are loaded to memory when user clicks on the record ID are referenced using weak references after user picks another record. This avoids unnecessary loading data from file everytime user chooses another record, and then switches back for previously closed record. 

Example people catalog tree:

- people_catalog<br>
  - 0<br>
    - image.png<br>
    - record.txt<br>
  - 1<br>
      - image.png<br>
      - record.txt<br>
  - 2<br>
      - image.png<br>
      - record.txt<br>
  - ...

Example people catalogs are in TEST CATALOGS.

# Build
This command creates custom JRE with all modules required to run application.
* run `mvn javafx:jlink` from project root directory

# Run application
Custom JRE will be created in `target/catalog` and `target/catalog.zip`. To run application, you need to double click `target/catalog/bin/catalog.bat`.
Or `run target/catalog/bin/java.exe -m app/com.drozd.app.App`

# Known issues
* List of catalogs is not sorted.

# VM options testing conclusions
Options were tested using VisualVM with Visual GC plugin. 

-XX:-ShrinkHeapInSteps<br>
This makes GC run more often. It allows to reduce the amount of memory used by program, but due to more often garbage collecting application works slower.<br>
-XX:+ShrinkHeapInSteps<br>
This makes GC run less often. It allows the application work faster, because garbage collection is not performed so often. On the other hand the amount of memory used by program increases.

-XX:+UseSerialGC<br>
Single threaded concurrent GC. Freezes all application threads whenever it’s working.<br>
Can be used in applications which:
- work with small amount of data
- work on one CPU (often single threaded environment)
- are allowed to "freeze" (no pause-time requirements)

-XX:+UseParallelGC<br>
Parallel GC. This is default JVM collector. Uses multiple threads to scan through the heap. Stops application threads when performing either a minor or full GC collection.<br>
Can be used in applications which:
- highest priority is performance,
- are allowed to "freeze" (no pause-time requirements)
- "freeze" lasting for more than 1s is acceptable

-XX:+UseG1GC <br>
Parallel GC. Designed for better support for heaps larger than 4GB. Uses multiple threads to scan through the heap. Doesn't stop appliation threads when performing GC collection.
Can be used in applications in which:
- response time is more important than performance
- "freeze" lasting for more than 1s is **not** acceptable


# The content of the task

**EN (translated with [deepl](https://www.DeepL.com/Translator "DeepL translator")):**

Write an application to view personal data stored on disk. We assume that the personal data will be saved in folders with names corresponding to the identifiers of the persons concerned. More precisely, in the folders there should appear two files: record.txt (of free structure, in this file there should be: name, surname, age, ....) and image.png (with a photo of the person, but for testing purposes any picture can be used instead of a photo).

The graphic interface of this application can be realized by means of two panels - one intended for the list of browsed folders and the other one for displaying personal data and a picture corresponding to the folder selected from the list.

The application should be designed using weak references. We assume that when browsing folders, the contents of text files and files containing pictures will be loaded into the appropriate structure. Weak references should allow us to bypass the need to load the same content multiple times - which may happen while moving forward and backward through the list of folders.

The application should indicate whether the contents of the file have been reloaded or retrieved from memory. This indication can be realized by some marker presented on the interface.

In order to evaluate the correctness of the operation, the application should be started by passing to the virtual machine the parameters limiting the memory allocated to it. For example -Xms512m (which means minimum 512 MB of memory), -Xmx1024m (which means maximum 1GB).
You should also test the possibility of regulating the behavior of the garbage collection algorithm, for which the options -XX:+ShrinkHeapInSteps, -XX:-ShrinkHeapInSteps are useful. Please study what other attributes can be passed to the virtual machine, including the algorithm selections -XX:+UseSerialGC, -XX:+UseParNewGC (deprecated), -XX:+UseParallelGC, -XX:+UseG1GC.

Translated with www.DeepL.com/Translator (free version)

**PL (original)**

Napisz aplikację, która umożliwi przeglądanie danych osobowych zapisanych na dysku. Zakładamy, że dane osobowe zapisywane będą w folderach o nazwach odpowiadających identyfikatorom osób, których dotyczą. Dokładniej, w folderach pojawiać się mają dwa pliki: record.txt (o dowolnej strukturze, w pliku tym zapisane mają być: imię, nazwisko, wiek, ....) oraz image.png (ze zdjęciem danej osoby, przy czym do celów testowych można zamiast zdjęcia użyć dowolnego obrazka).

Interfejs graficzny tej aplikacji można zrealizować za pomocą dwóch paneli - jednego, przeznaczonego na listę przeglądanych folderów oraz drugiego, służącego do wyświetlania danych osobowych i zdjęcia odpowiedniego do folderu wybranego z listy.

Aplikację należy zaprojektować z wykorzystaniem słabych referencji (ang. weak references). Zakładamy, że podczas przeglądania folderów zawartość plików tekstowych i  plików zawierających obrazki będzie ładowana do odpowiedniej struktury. Słabe referencje powinny pozwolić na ominięcie konieczności wielokrotnego ładowania tej samej zawartości - co może nastąpić podczas poruszanie się wprzód i wstecz po liście folderów.

Aplikacja powinna wskazywać, czy zawartość pliku została załadowana ponownie, czy też została pobrana z pamięci. Wskazanie to może być zrealizowane za pomocą jakiegoś znacznika prezentowanego na interfejsie.

W celu oceny poprawności działania aplikację należy uruchamiać przekazując wirtualnej maszynie parametry ograniczające przydzielaną jej pamięć. Na przykład -Xms512m (co oznacza minimalnie 512 MB pamięci), -Xmx1024m (co oznacza maksymalnie 1GB).
Należy też przetestować możliwość regulowania zachowania się algorytmu odśmiecania, do czego przydają się opcje -XX:+ShrinkHeapInSteps, -XX:-ShrinkHeapInSteps. Proszę przestudiować, jakie inne atrybuty można przekazać do wirtualnej maszyny, w tym selekcji algorytmu -XX:+UseSerialGC, -XX:+UseParNewGC (deprecated), -XX:+UseParallelGC, -XX:+UseG1GC.



