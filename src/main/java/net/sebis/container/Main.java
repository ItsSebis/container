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
    private final HashMap<String, URL> urls = new HashMap<>();

    private final List<HashMap<Integer, Integer>> containers = new ArrayList<>();

    private final Scanner s = new Scanner(System.in);

    private final DecimalFormat df = new DecimalFormat("##.##");

    public static void main(String[] args) throws IOException, InterruptedException {
        new Main();
    }

    public Main() throws IOException, InterruptedException {
        instance = this;
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));

        collectURLMap();
        readWords();
        sort();
        output();
    }

    public void collectURLMap() {
        // Put all examples in the previously created HashMap
        urls.put("Examples0.txt", getExamples0());
        urls.put("Examples1.txt", getExamples1());
        urls.put("Examples2.txt", getExamples2());
        urls.put("Examples3.txt", getExamples3());
    }

    public void readWords() throws IOException {
        // Select word list
        System.out.println("Select word pallet: ");
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
            System.out.print("D: " + containers.size() + "\r");
        }
        System.out.println("Done - " + containers.size());
    }

    public void sort() throws InterruptedException {
        System.out.println("Sorting containers in the table...");

        // Start sorting thread
        Thread thread = new Thread(new Sorter(1, containers));
        threads.add(thread);
        thread.start();
        while (fin < threads.size()) {
            Thread.sleep(10);
        }
        System.out.println("Done");
    }

    public void output() {

        // Select output format
        System.out.println("Select output type: ");
        System.out.println("   1: Console");
        System.out.println("   2: table.md (recommended)");
        System.out.print("> ");
        String input = s.nextLine();
        while (input.isBlank() || (!input.equals("1") && !input.equals("2"))) {
            System.out.print("> ");
            input = s.nextLine();
        }

        // Generating Table in Github markdown format
        System.out.println("Generating output table...");

        StringBuilder table = new StringBuilder("| ");



        // Outputting table in console or table markdown file
        if (input.equals("1")) {
            System.out.println(table);
        } else {
            writeInFile(table.toString(), "table.md");
        }
    }

    public void writeInFile(String text, String fileName) {
        // outputting text to file with BufferedWriter
        if (text.equals("")) {
            return;
        }
        try {
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
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
