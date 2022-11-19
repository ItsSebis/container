package net.sebis.container.runners;

import net.sebis.container.Main;

import java.util.HashMap;
import java.util.List;

public record Sorter(int threadID, List<HashMap<Integer, Integer>> containers) implements Runnable {

    @Override
    public void run() {
        float done = 0;
        for (float i = 0; i < containers.size(); i += threadID) {

            // analyse container pair

            done++;
            float percent = done / containers.size() * 100;
            System.out.print("|" + "=".repeat(Math.round(percent)) + " ".repeat(100 - Math.round(percent)) + "| " + Main.getInstance().getDf().format(percent) + "% " + done + "\r");
        }

        Main.getInstance().setFin(Main.getInstance().getFin() + 1);
    }
}
