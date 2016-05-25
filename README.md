# Ogame
Ogame clone created for coding practice

Planned technologies, standards, solutions (order is irrelevant):
- Java 8
- Spring Boot
- Maven
- JUnit
- Mockito
- REST API
- AngularJS
- MySQL/MongoDB/Cassandra (yet to chose)
- Grunt/Gulp (yet to chose)
- Jasmine/Cucumber (yet to chose)

Methodology
- Scrum-like more like Scrumban

Others
- Powered by ZenHub
- CI by Travis
- Cloud server by DigitalOcean

## API
### Zasoby API
* Player
  * Nazwa
  * Ranking
  * Punktacja
  * Aktywność
  * Przynależność do sojuszu
* Planet
  * Nazwa
  * Adres
  * Temperatura
  * Średnica
  * Stan zabudowy
  * Pola zniszczeń
* Building
  * Poziom
  * Produkcja
  * Zużycie energi
  * Wymagania 
  * Czas budowy
  * Czas wyburzania
* Ship
  * Nazwa
  * Rodzaj
  * Punkty strukturalne
  * Siła obrony
  * Siła ataku
  * Pojemność ładunkowa
  * Szybkość
  * Zużycie paliwa
* Defense
  * Nazwa
  * Punkty strukturalne
  * Siła obrony
  * Siła ataku
* Research
  * Nazwa
  * Poziom
  * Wymagania
  * Czas badania
* Resources
  * 
* Fleet
  * Nr
  * Misja
  * Liczba
  * Skąd
  * Godzina dolotu
  * Cel
  * Godzina powrotu
  * Pozostały czas
  * Rozkaz
* Message
  * Data
  * Autor
  * Temat
  * Treść
* Galaxy
  * 
* Alliance
  
### Enpoints (per view)

#### Common
* Resources on planet
* Messages count
* Planets owned

#### Dashboard
* Planet informations
  * coords
  * size
  * destruction fields
  * temperature

#### Buildings
* current building levels
* current building costs
* current building build times
* leveling a building up
* building preconditions
  
#### Laboratory
* researches available
* current research levels
* current research costs
* current research build times
* leveling a research up
* research preconditions
  
#### Shipyard
* ships available
* ships owned
* ship costs
* ship build time
* building ships
  
#### Defense
* defense available
* defense owned
* defense costs
* defense build time
* building defense

#### Capitol
#### Alliance
#### Fleet
#### Teleport
#### Galaxy
#### Technology
#### Statistics
#### High score
#### Exchange
#### Search
#### Simulator
