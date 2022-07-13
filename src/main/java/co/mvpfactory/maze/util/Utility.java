/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.mvpfactory.maze.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utility {

    private static AtomicReference<Long> currentTime = new AtomicReference<>(System.currentTimeMillis());

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public static synchronized String requestId(String serverId) {
        Long prev;
        Long next = System.currentTimeMillis();
        String uid = "";
        do {
            prev = currentTime.get();
            next = next > prev ? next : prev + 1;
        } while (!currentTime.compareAndSet(prev, next));
        uid = serverId.trim() + next;
        return uid.trim();
    }

    public static void printGrid(String[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            System.out.print("| ");
            for (int col = 0; col < grid[row].length; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println(" |");
        }
    }

    public static void printGrid(int[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            System.out.print("| ");
            for (int col = 0; col < grid[row].length; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println(" |");
        }
    }

    public static boolean gridHasValue(String[][] grid, String element) {
        boolean result = false;
        String[] elementArray = {element};
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (Arrays.stream(elementArray).anyMatch(grid[row][col]::equals)) {
                    return true;
                }
            }

        }
        return result;
    }

    public static boolean isValidWall(String[][] grid, String[] walls) {
        boolean result = true;
        for (String wall : walls) {
            if (!gridHasValue(grid, wall)) {
                return false;

            }
        }
        return result;
    }

}
