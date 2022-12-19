# Container - Junioaufgabe 2

## Lösungsidee
Ein Programm, das alle zweitgenannten Zahlen durch geht und guckt welcher Container in diesen nicht vorkommt, welches diesen zu dem schwersten Container macht.

## Implementierung in ein Java Programm
Zuerst wird man vom Programm nach der Quelle der Container Vergleich Liste gefragt:
```
// Select container load
System.out.println("Select container load: ");
int n = 0;
for (String example : urls.keySet().stream().toList()) {
    System.out.println("   " + n + ": " + example);
    n++;
}
System.out.println("   " + n + ": Custom URL to text file");
System.out.print("> ");
String input = s.nextLine();
while (input.isBlank() || (!input.equals("0") && !input.equals("1") && !input.equals("2")) && !input.equals("3") && !input.equals("4") && !input.equals("5")) {
    System.out.print("> ");
    input = s.nextLine();
}
int c = Integer.parseInt(input);
URL url;
if (c < urls.values().stream().toList().size()) {
    url = urls.values().stream().toList().get(c);
} else {
    System.out.println("Give a custom .txt URL");
    System.out.print("> ");
    input = s.nextLine();
    while (input.isBlank() || !input.endsWith(".txt")) {
        System.out.print("> ");
        input = s.nextLine();
    }
    url = new URL(input);
}
```

Dann liest ein Scanner die txt Datei/URI, speichert die Anzahl der Container und die Vergleiche als Listen in die schwereren und die leichteren Container aufgeteilt:
```
// Read URL line by line with Scanner
System.out.println("Downloading...");
Scanner s = new Scanner(url.openStream());
while (s.hasNextLine()) {
    // add to array
    String pair = s.nextLine();
    int h = Integer.parseInt(pair.split(" ")[0]); // get heavier container
    int l = Integer.parseInt(pair.split(" ")[1]); // get lighter container
    higher.add(h);// add heavier container to lists
    lower.add(l); // add lighter container to lists

    // count number of containers
    if (h > cCount) {
        cCount = h;
    }
    if (l > cCount) {
        cCount = l;
    }
    System.out.print("D: " + higher.size() + "\r");
}
System.out.println("Done - " + higher.size());
```

Darauf startet ein neuer Thread, welcher dafür zuständig ist den schwersten Container zu finden.
In einer for-Schleife geht er alle Container durch und testet, ob sie in der Liste mit den leichteren Containern auftauchen, wenn nicht fügt er sie einer Liste hinzu:
```
float done = 0;
for (float i = 1; i <= count; i ++) {

    // analyse containers
    if (!lower.contains((int) i)) {
        Main.getInstance().nothingHigher.add((int) i);
    }

    done++;
    float percent = done / count * 100;
    System.out.print("|" + "=".repeat(Math.round(percent)) + " ".repeat(100 - Math.round(percent)) + "| " + Main.getInstance().getDf().format(percent) + "% " + done + "\r");
}

Main.getInstance().setFin(Main.getInstance().getFin() + 1);
```

Wenn die Liste mit jenen Containern, die nicht in der Liste mit den leichteren Containern auftauchen, genau einen Eintrag hat, wird dieser als der schwerste Container ausgegeben,
wenn sie mehr als einen Eintrag hat, werden alle dieser Einträge als mögliche schwerste Container ausgegeben,
wenn die Liste keine Einträge hat, ist die am Anfang gewählte Datei nicht lösbar, weil ein loop entsteht.
```
// Output
if (nothingHigher.size() == 1) {
    System.out.println("Container " + nothingHigher.get(0) + " is the heaviest.");
} else if (nothingHigher.size() == 0) {
    System.out.println("This input is unsolvable, it ends in one loop.");
} else {
    System.out.println("One of the following containers is the heaviest: " + nothingHigher);
}
```
