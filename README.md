# Generator ewolucyjny
Pierwszy projekt z Programowania Obiektowego zrealizowany w Javie przy użyciu biblioteki JavaFX

![Zrzut ekranu 2023-02-21 142629](https://user-images.githubusercontent.com/80721230/220357227-bc5ec548-83e9-4112-bf84-f7ffbc182307.jpg)

## Cel Projektu
Celem projektu jest stworzenie symulacji podobnej do *game of life*.
## Opis
* Świat gry składa się z prostokątnej planszy podzielonej na kwadratowe pola. W centrum planszy znajduje się dżungla, na której  rosną rośliny. Poza dżunglą znajduje się
step, na którym rośliny pojawiają się w wolniejszym tempie. Planszę przemierzają zwierzęta, które żywią się roślinami.
* Każde zwierzę ma określoną energię, która zmniejsza się co dnia. Znalezienie i zjedzenie rośliny zwiększa poziom energii o pewną wartość. Ilość energii zwierzęcia jest wskazywana przez jego kolor
* Każde zwierzę posiada pulę genów, która determinuje jego zachowanie. Zwierzęta posiadające wystarczająco dużą ilość energii mogą się rozmnażać.
Urodzone zwierzę otrzymuje genotyp będący krzyżówką genotypów rodziców.
* Aplikacja na bierząco generuje i przezentuje na wykresach statystyki dotyczące symulacji, które na koniec są zapisywane do plików csv
