# Analyzer
Application which allows loading csv data and perform simple operations implemented with services which are loaded using Service Provider Interface.

# Build
Run `mvn package` from project root directory. After that you can find all jars necessary to run app in the `target` directory.


# Run application
Run `java --module-path "median-1.0.jar;Average-1.0.jar;libs/" --add-modules ALL-MODULE-PATH -jar app-1.0.jar` from `target` directory.

Example csv data file is `data.csv`.

Note that implemented services "process" data for 5s (`Thread.sleep(5000)` is done to emulate processing), so before you want to see the result, you need to wait for 5s until the end of processing.

# The content of the task

**EN (translated with [deepl](https://www.DeepL.com/Translator "DeepL translator")):**

Implement an application which allows performing statistical analysis on tabular data.
This analysis can consist of: determination of trend line, calculation of median, calculation of standard deviation.

The application should allow:
- displaying/editing tabular data;
- choosing the algorithm with which they will be processed (at least 2 algorithms of statistical analysis should be implemented);
- displaying the processing results.
  During implementation, the Service Provider Interface (SPI) should be used.
  More precisely, using the SPI approach, it is necessary to ensure that the application can load classes implementing the given interface
  interface after the application itself has been built.
  These classes (with selected cluster analysis algorithms implemented) are to be provided in jar files placed in the path.

Translated with www.DeepL.com/Translator (free version)

**PL (original)**

Zaimplementuj aplikację pozwalającą przeprowadzić analizę statystyczną na danych tabelarycznych.
Analiza ta może polegać na: wyznaczeniu linii trendu, obliczeniu mediany, obliczeniu odchylenia standardowego.

Aplikacja ta powinna umożliwiać:
- wyświetlanie/edytowanie danych tabelarycznych;
- wybranie algorytmu, jakim będą one przetwarzane (należy zailplementować przynajmniej 2 algorytmy analiz statystycznych);
- wyświetlenie wyników przetwarzania.
  W trakcie implementacji należy wykorzystać interfejs dostarczyciela serwisu (ang. Service Provider Interface, SPI).
  Dokładniej, stosując podejście SPI należy zapewnić aplikacji możliwość załadowania klas implementujących zadany interfejs
  już po zbudowaniu samej aplikacji.
  Klasy te (z zaimplementowanymi wybranymi algorytmami analizy skupień) mają być dostarczane w plikach jar umieszczanych w ścieżce. 

