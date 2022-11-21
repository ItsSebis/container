package net.sebis.container.runners;

import net.sebis.container.Main;

import java.util.List;

public record Sorter(List<Integer> lower, int count) implements Runnable {

    @Override
    public void run() {

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
    }
}
