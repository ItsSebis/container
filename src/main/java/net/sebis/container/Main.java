package net.sebis.container;

import net.sebis.container.runners.Sorter;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;

public class Main {

    private static Main instance;

    private final List<Thread> threads = new ArrayList<>();
    private int fin = 0;

    private final URL Examples0 = new URL("https://bwinf.de/fileadmin/bundeswettbewerb/41/container0.txt");
    private final URL Examples1 = new URL("https://bwinf.de/fileadmin/bundeswettbewerb/41/container1.txt");
    private final URL Examples2 = new URL("https://bwinf.de/fileadmin/bundeswettbewerb/41/container2.txt");
    private final URL Examples3 = new URL("https://bwinf.de/fileadmin/bundeswettbewerb/41/container3.txt");
    private final URL Examples4 = new URL("https://bwinf.de/fileadmin/bundeswettbewerb/41/container4.txt");
    private final HashMap<String, URL> urls = new HashMap<>();

    private final List<Integer> higher = new ArrayList<>();
    private final List<Integer> lower = new ArrayList<>();
    private int cCount = 0;

    public List<Integer> nothingHigher = new ArrayList<>();

    private final Scanner s = new Scanner(System.in);

    private final DecimalFormat df = new DecimalFormat("##.##");

    public static void main(String[] args) throws IOException, InterruptedException {
        new Main();
    }

    public Main() throws IOException, InterruptedException {
        instance = this;
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));

        collectURLMap();
        readContainers();
        search();
        output();
    }

    public void collectURLMap() {
        // Put all examples in the previously created HashMap
        urls.put("Examples0.txt", getExamples0());
        urls.put("Examples1.txt", getExamples1());
        urls.put("Examples2.txt", getExamples2());
        urls.put("Examples3.txt", getExamples3());
        urls.put("Examples4.txt", getExamples4());
    }

    public void readContainers() throws IOException {
        // Select word list
        System.out.println("Select container load: ");
        int n = 0;
        for (String example : urls.keySet().stream().toList()) {
            System.out.println("   " + n + ": " + example);
            n++;
        }
        System.out.println("   " + n + ": Custom URL to text file");
        System.out.print("> ");
        String input = s.nextLine();
        while (input.isBlank() || (!input.equals("0") && !input.equals("1") && !input.equals("2")) && !input.equals("3") && !input.equals("4")) {
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

        // Read URL line by line with Scanner
        System.out.println("Downloading...");
        Scanner s = new Scanner(url.openStream());
        while (s.hasNextLine()) {
            // add to array
            String pair = s.nextLine();
            int h = Integer.parseInt(pair.split(" ")[0]);
            int l = Integer.parseInt(pair.split(" ")[1]);
            higher.add(h);
            lower.add(l);
            if (h > cCount) {
                cCount = h;
            }
            if (l > cCount) {
                cCount = l;
            }
            System.out.print("D: " + higher.size() + "\r");
        }
        System.out.println("Done - " + higher.size());
    }

    public void search() throws InterruptedException {
        System.out.println("Searching for heaviest container...");

        // Start sorting thread
        Thread thread = new Thread(new Sorter(lower, cCount));
        threads.add(thread);
        thread.start();
        while (fin < threads.size()) {
            Thread.sleep(10);
        }
        System.out.println("Done");
    }

    public void output() {
        // Output
        if (nothingHigher.size() == 1) {
            System.out.println("Container " + nothingHigher.get(0) + " is the heaviest.");
        } else if (nothingHigher.size() == 0) {
            System.out.println("This input is unsolvable, it ends in one loop.");
        } else {
            System.out.println("One of the following containers is the heaviest: " + nothingHigher);
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public URL getExamples0() {
        return Examples0;
    }

    public URL getExamples1() {
        return Examples1;
    }

    public URL getExamples2() {
        return Examples2;
    }

    public URL getExamples3() {
        return Examples3;
    }

    public URL getExamples4() {
        return Examples4;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public DecimalFormat getDf() {
        return df;
    }
}
