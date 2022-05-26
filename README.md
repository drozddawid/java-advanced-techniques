# Programming in Java - advanced techniques.
My solutions for the tasks performed during the course "Java programming - advanced techniques" conducted at the Wrocław University of Science and Technology.

# Repository structure
So as to make the repository structure more transparent, I created separate branch for every task solution. Every task is described more precisely in its README.md (on its branch).

# Summary
Below are short descriptions of every task in this repository.
* **lab1 - File Comparator <br>**
Application for detecting changes in files. Allows user to save snapshot of directory content and detect future changes in files basing on created snapshot.
* **lab2 - Catalog <br>**
Application for testing different VM options related with garbage collection. Allows user to browse catalog of people. Uses weak references to cache people records read from files. Shows if record was loaded from file or from memory.
* **lab3 - Geo Tester <br>**
Multilanguage application which allows creating questions and checking the answers basing on [Teleport API](https://api.teleport.org/api/).
* **lab4 - Process Loader <br>**
Application which allows loading, using and unloading compiled classes which implement `Processor` interface specified by the lecturer.
* **lab5 - Analyzer <br>**
Application which allows loading csv data and perform very simple operations implemented with services which are loaded using Service Provider Interface.
* **lab6 - Advertisement System <br>**
Advertisement system consisting of three applications:
  * Billboard: Shows advertisements ordered by customers.
  * Customer: Allows to order advertisements.
  * Manager: Allows to manage billboards (turn on/off, change single advertisement duration).
Applications use Remote Method Invocation with SSL to communicate between each other.
* **lab7 - Finance Manager <br>**
Database application for managing instalments payment. Allows to add and remove events, people and instalments. After adding some instalments in `Payments` tab user can pick current date and the application shows unpaid instalments if there are any.
* **lab8 - SOAP Finance Manager <br>**
It is the same application as in lab7, but it's extended with SOAP endpoints allowing to make changes in the database.
* **lab9 - File Crypto <br>**
Application for RSA and AES file encryption. Supports only pkcs12 keystores.

# Suggestions
If you have suggestions how to improve my solutions, don't hesitate to contact me. I would like to improve my skills and I am open to any constructive criticism.
