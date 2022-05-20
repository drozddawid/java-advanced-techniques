# Geo Tester
Multilanguage application which allows creating questions and checking the answers basing on [Teleport API](https://api.teleport.org/api/). 

# Build
Unfortunately `mvn javafx:jlink` won't work for this project, because `httpcomponents` library is an automatic module (module with no module-info) and it can't be used with jlink unless the module-info is added. Adding module-info to automatic module jar can be done with [ModiTect - Tooling for the Java Module System](https://github.com/moditect/moditect).


# Run application
Despite of previously described building problems, application can be run easily by calling `mvn javafx:run` from project root directory.


# The content of the task

**EN (translated with [deepl](https://www.DeepL.com/Translator "DeepL translator")):**

Write an application to consume data extracted from a service that offers a public rest API. An interesting list of services can be found at:
https://rapidapi.com/collection/list-of-free-apis
On that list is the GeoDB Cities API (https://rapidapi.com/wirefreethought/api/geodb-cities?endpoint=59908d68e4b075a0d1d6d9ac). And let this API be used to implement the exercise.

Let the designed application be used to test geographic knowledge. Queries and answers should be displayed on a graphical interface, which will allow changing language (localization) settings. Both Polish and English languages are to be supported. The implementation should be based on so called bundles (files containing keys and values) and using classes from java.text and java.util packages.

It is enough to implement a few sample questions, e.g. "How many cities in the country .... has a population greater than ....".
Let the user parameterize those questions (replace the dots with values from the selection lists - if they can be obtained from the service, or let them be values entered in free text) and declare the answers.

Let the system check the answers entered by the user and present correct answers. The correct answers can take the form, for example:
"In the country .... there are 3 such towns in this country", "In the country .... there are 5 such cities".
Let the variation by numbers be handled in these answers. In general, the idea is to test variant retrieval of texts from bundles. The ChoiceFormat class will be useful for this.

Translated with www.DeepL.com/Translator (free version)

**PL (original)**

Napisz aplikację, która pozwoli skonsumować dane pozyskiwane z serwisu oferującego publiczne restowe API. Ciekawą listę serwisów można znaleźć pod adresem:
https://rapidapi.com/collection/list-of-free-apis
Na tej liście jest GeoDB Cities API (https://rapidapi.com/wirefreethought/api/geodb-cities?endpoint=59908d68e4b075a0d1d6d9ac). I niech to API posłuży do realizacji ćwiczenia.

Projektowana aplikacja niech służy do testów z wiedzy z geograficznej. Zapytania i odpowiedzi powinny być wyświetlane na graficznym interfejsie, który umożliwi zmianę ustawień językowych (lokalizacji). Wspierane mają być język polski i język angielski. Implementacja opierać się ma na tzw. bundlach (plikach zawierających klucze i wartości) i z wykorzystaniem klas z pakietów java.text, java.util.

Wystarczy zaimplementować kilka przykładowych pytań, np. "Ile miast w kraju .... ma liczbę mieszkańców większą niż ....".
Niech użytkownik ma możliwość parametryzowania tych zapytania (w miejsce kropek niech wpisywane będą wartości z list wyboru - jeśli da się je pozyskać z serwisu, lub niech będą to wartości wprowadzone wolnym tekstem) oraz zadeklarowanie odpowiedzi.

Niech system sprawdza odpowiedzi wprowadzone przez użytkownika oraz prezentuje poprawne odpowiedzi. Odpowiedź poprawne mogą przyjąć postać, np.:
"W kraju .... istnieją 3 takie miasta", "W kraju .... istnieje 5 takich miast".
Niech w tych odpowiedziach obsłużona będzie odmiana przez liczny. Generalnie chodzi o przetestowanie wariantowego pobierania tekstów z bundli. Do tego przyda się klasa ChoiceFormat. 



