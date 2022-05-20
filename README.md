# File crypto
Application for RSA and AES file encryption.
Supports only pkcs12 keystores.

Note: RSA isn't recommended for block encryption of file.

# Build
It is highly recommended to build the project with JDK 17, because it supports PKCS12 keystores (for example JDK11 doesn't support it and jarsigner will give an error). JDK 17 should be included in PATH environment variable because `build.sh` uses `java` and `jarsigner`. 

To build the application run `build.sh`.

# Run application
Run `build/run.sh`

Password to keystore_for_testing.pfx is "password". It contains AES and RSA keys which can be used in the application. Enjoy encrypting ;)

# Known issues
* NullPointerException after clicking `Swap files` when files aren't chosen.

# The content of the task

**EN (translated with [deepl](https://www.DeepL.com/Translator "DeepL translator")):**

Implement an application that allows encryption/decryption of files (such an application could act as a tool for encryption/decryption of email attachments). 
On the application's graphical interface the user should be able to indicate input and output files, as well as the encryption/decryption algorithm and the keys used: private (for encryption) and public (for decryption).
All logic related to encryption/decryption should be provided in a separate library, packed in a digitally signed jar file.
The application itself should also be exported to a digitally signed executable jar file (and it should run a security manager using the provided policy file).
The project should be based on techniques belonging to Java Cryptography Architecture (JCA) and/or Java Cryptography Extension (JCE).
Please pay attention to the size limitation of the encrypted data imposed by the selected algorithms (we want to be able to encrypt files of any size).
During the lab you will need to use key and certificate repositories.  Also, please familiarize yourself with the rules of using the jarsigner tool. 



**PL (original)**

Zaimplementuj aplikację pozwalającą na szyfrowanie/deszyfrowanie plików (taka aplikacja mogłaby pełnić rolę narzędzia służącego do szyfrowania/odszyfrowywania załączników do e-maili). 
Na interfejsie graficznym aplikacji użytkownik powinien mieć możliwość wskazania plików wejściowych oraz wyjściowych, jak również algorytmu szyfrowania/deszyfrowania oraz wykorzystywanych kluczy: prywatnego (do szyfrowania) i publicznego (do deszyfrowania).
Cała logika związana z szyfrowaniem/deszyfrowaniem powinna być dostarczona w osobnej bibliotece, spakowanej do podpisanego cyfrowo pliku jar.
Sama zaś aplikacja powinna również być wyeksportowana do wykonywalnego pliku jar podpisanego cyfrowo (i działać w niej ma menadżer bezpieczeństwa korzystający z dostarczonego pliku polityki).
Projekt opierać ma się na technogiach należących do Java Cryptography Architecture (JCA) i/lub Java Cryptography Extension (JCE).
Proszę zwrócić uwagę na ograniczenia związane z rozmiarem szyfrowanych danych narzuczane przez wybrane algorytmu (zależy nam, by zaszyfrować dało się pliki o dowolnym rozmiarze).
W trakcie realizacji laboratorium będzie trzeba skorzystać z repozytoriów kluczy i certyfikatów.  Ponadto proszę zapoznać się z zasadami korzystania z narzędzia jarsigner. 





