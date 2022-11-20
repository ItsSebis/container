# Container - Junioaufgabe 2

## Lösungsidee
Ein Programm, das alle zweitgenannten Zahlen durch geht und guckt welcher Container in diesen nicht vorkommt, welches diesen zu dem schwersten Container macht.

## Implementierung in ein Java Programm
Zuerst wird man vom Programm nach der Quelle der Container Vergleich Liste gefragt:
https://github.com/ItsSebis/container/blob/1419969515fc983943ec9d9f145aeb40d1dcd1df/src/main/java/net/sebis/container/Main.java#L59

Dann liest ein Scanner die txt Datei/URI und speichert die Anzahl der Container und die Vergleiche als Listen in die schwereren und die leichteren Container aufgeteilt:
https://github.com/ItsSebis/container/blob/1419969515fc983943ec9d9f145aeb40d1dcd1df/src/main/java/net/sebis/container/Main.java#L92

Darauf startet ein neuer Thread, welcher dafür zuständig ist den schwersten Container zu finden.
In einer for-Schleife geht er alle Container durch und testet, ob sie in der Liste mit den leichteren Containern auftauchen, wenn nicht fügt er sie einer Liste hinzu:
https://github.com/ItsSebis/container/blob/1419969515fc983943ec9d9f145aeb40d1dcd1df/src/main/java/net/sebis/container/runners/Sorter.java#L13

Wenn die Liste mit jenen Containern, die nicht in der Liste mit den leichteren Containern auftauchen, genau einen Eintrag hat, wird dieser als der schwerste Container ausgegeben.
Wenn sie mehr als einen Eintrag hat, werden alle dieser Einträge als mögliche schwerste Container ausgegeben.
Wenn die Liste keine Einträge hat ist die am Anfang gewählte Datei nicht lösbar, weil ein loop entsteht.
https://github.com/ItsSebis/container/blob/1419969515fc983943ec9d9f145aeb40d1dcd1df/src/main/java/net/sebis/container/Main.java#L125
