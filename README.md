## Aplikacja do zarządzania kontami użytkowników z obsługą różnych walut

# Główne funkcjonalności:
- Tworzenie kont użytkowników z określonym saldem początkowym w PLN.
- Obsługa konwersji walut: konwersja PLN -> obca waluta oraz obca waluta -> PLN, oparta na bieżących kursach walut pobieranych z zewnętrznego API NBP.
- Walidacja danych w celu zapewnienia ich spójności.
- Testy jednostkowe: sprawdzające poprawność logiki konwersji walut.
- Scheduler: Regularne pobieranie tabeli C z kursami walut z API NBP do cache, co zapewnia, że aplikacja zawsze dysponuje najnowszymi kursami.
- Integracja z API Calendarific: Sprawdzanie, czy dzisiaj przypada dzień wolny od pracy (święto narodowe), w którym NBP nie aktualizuje kursów walut.

# Szczegóły implementacji:
- Scheduler: Dodano komponent NbpRatesScheduler, który co dzień rano (od poniedziałku do piątku) sprawdza, czy dzisiaj jest święto narodowe w Polsce, korzystając z zewnętrznego API Calendarific.
Jeśli dzisiaj nie jest święto, scheduler pobiera dane o kursach walut (tabela C) z API NBP i zapisuje je w cache, aby mogły być wykorzystywane w dalszej części aplikacji.
- HolidaysDownloader: Integracja z API Calendarific umożliwia pobranie świąt narodowych w Polsce na bieżący rok i sprawdzenie, czy dzisiaj jest jeden z tych dni.
- Obsługa API NBP i Cache: Kursy walut są pobierane z API NBP za pomocą komponentu NbpTableCRates przy użyciu klienta HTTP WebClient.
Użycie cache do przechowywania danych kursów walutowych optymalizuje wydajność aplikacji i redukuje liczbę zapytań do zewnętrznego API.
- Testy jednostkowe: Zaimplementowano testy jednostkowe dla funkcjonalności konwersji walut oraz schedulera, które mockują odpowiedzi z API oraz symulują święta narodowe w Polsce.

# Technologie:
- Język: Java 17
- Framework: Spring Boot
- Klient HTTP: Spring WebClient
- Testy: JUnit, Mockito
- Cache: ConcurrentHashMap
  
## Application for Managing User Accounts with Currency Support

# Main functionalities:
- Creating user accounts with a specified initial balance in PLN.
- Handling currency conversion: converting PLN to foreign currencies and vice versa, based on current exchange rates fetched from the external NBP API.
- Data validation to ensure consistency.
- Unit tests: Verifying the correctness of the conversion logic.
- Scheduler: A scheduled task that regularly fetches exchange rates from the NBP API and stores them in the cache.
- Integration with the Calendarific API: Checking for national holidays to ensure that the NBP does not update exchange rates on these days.

# Implementation details:
- Scheduler: The NbpRatesScheduler component runs every weekday morning to check if the day is a national holiday using the external Calendarific API.
If it's not a holiday, the scheduler fetches the latest exchange rates (Table C) from the NBP API and stores them in the cache for later use.
- HolidaysDownloader: Integration with the Calendarific API allows fetching national holidays in Poland for the current year and checking if today is one of those days.
NBP API and Cache Handling:
- Exchange rates are fetched from the NBP API using the NbpTableCRates component and Spring’s WebClient.
Cached exchange rates improve application performance by reducing the number of API calls to the NBP.
-  Unit Tests: Unit tests have been implemented for the currency conversion logic and scheduler functionality, mocking API responses and simulating national holidays in Poland.

# Technologies:
-  Language: Java 17
-  Framework: Spring Boot
-  HTTP Client: Spring WebClient
-  Testing: JUnit, Mockito
-  Cache: ConcurrentHashMap

  - add account
  ![image](https://github.com/user-attachments/assets/389c2aa4-00de-42f8-9338-696bcc0ca1fb)
  - convert pln to usd
  ![image](https://github.com/user-attachments/assets/23dc0829-d60e-42f3-b9a2-d38dc3a48f29)
  - convert pln to chf
  ![image](https://github.com/user-attachments/assets/bd960a28-9e62-47d5-9f25-8d5255b91220)
  - convert usd to pln
  ![image](https://github.com/user-attachments/assets/ec6d21ff-f347-46e4-8b5c-762ec0cd7f3b)
  - convert chf to pln
  ![image](https://github.com/user-attachments/assets/5ca4e88c-f8a6-4e90-a926-9aefcbd24dbd)
  - national holidays in Poland
  ![image](https://github.com/user-attachments/assets/501ea813-4d90-4484-9b7d-11d17e08d26b)


  




