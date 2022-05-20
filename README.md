# Advertisement system
Customer: Allows to order advertisements.
Billboard: Shows advertisements ordered by customers.
Manager: Allows to manage billboards (turn on/off, change single advertisement duration).



# Build
Run `./build.sh`

# Run application
Run `./run.sh`

# Known issues
Closing Manager before Customer and Billboard is closed and running Customer and Billboard before Manager is not handled. Because of that, it is highly recommended to run Customer and Billboard instances after Manager instance. Also Customer and Billboard instances should be closed before closing Manager instance.

# The content of the task

**EN (translated with [deepl](https://www.DeepL.com/Translator "DeepL translator")):**

Implement a distributed system that imitates a network of advertising boards that periodically displays a set text (i.e., one advertising slogan is visible for a specified period of time, and then it changes).
Data exchange between system elements should be done via SSL sockets (using certificates), with the use of a security manager and policy files.
(Materials to study: 
 https://docs.oracle.com/javase/8/docs/technotes/guides/rmi/socketfactory/index.html
 https://docs.oracle.com/en/java/javase/11/security/java-secure-socket-extension-jsse-reference-guide.html
)

Three types of applications (classes with the main method) are to be distinguished in this system:
* Manager - responsible for taking orders from customers, displaying advertising slogans and sending these slogans to advertising boards
* Client - responsible for reporting orders to the manager or their withdrawal
* Billboard - responsible for displaying passwords, linking to a manager who can stop and start displaying passwords

During system startup, create: 1 instance of Manager, at least 2 instances of Client, at least 3 instances of Billboard. 
These must be separate applications (cannot use the same address space!!!). Applications should be parameterized on the interface or in the command line (so that they can be run on different computers).

Translated with www.DeepL.com/Translator (free version)

**PL (original)**

Zaimplementuj rozproszony system imitujący działanie sieci tablic reklamowych, na których cyklicznie wyświetlane są zadane teksty (tj. przez określony czas widać jedno hasło reklamowe, po czym następuje zmiana).
Wymiana danych pomiędzy elementami systemu powinna odbywać się poprzez gniazda SSL (z użyciem certyfikatów), z wykorzystaniem menadżera bezpieczeństwa i plików polityki.
(materiały do przestudiowania: 
 https://docs.oracle.com/javase/8/docs/technotes/guides/rmi/socketfactory/index.html
 https://docs.oracle.com/en/java/javase/11/security/java-secure-socket-extension-jsse-reference-guide.html
)

W systemie tym wyróżnione mają być trzy typy aplikacji (klas z metodą main):
* Manager (Menadżer) - odpowiedzialna za przyjmowanie od klientów zamówień wyświetlanie haseł reklamowych oraz przesyłanie tych haseł na tablice reklamowe
* Client (Klient) - odpowiedzialna za zgłaszanie menadżerowi zamówień lub ich wycofywanie
* Billboard (Tablica) - odpowiedzialna za wyświetlanie haseł, dowiązująca się do menadżera, który może zatrzymać i uruchomić wyświetlanie haseł

Podczas uruchomienia systemu należy utworzyć: 1 instancję Menadżera, przynajmniej 2 instancje Klienta, przynajmniej 3 instancje Tablicy. 
Muszą to być osobne aplikacje (nie mogą korzystać z tej samej przestrzeni adresowej!!!). Aplikacje powinny być parametryzowane na interfejsie lub w linii komend (by dało się je uruchomić na różnych komputerach).
