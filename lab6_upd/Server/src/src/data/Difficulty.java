package src.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The enum Difficulty.
 */
public enum Difficulty implements Serializable {
    /**
     * Easy difficulty.
     */
    EASY,
    /**
     * Normal difficulty.
     */
    NORMAL,
    /**
     * Insane difficulty.
     */
    INSANE;
    private static HashMap<Integer, String> putByNumber = new HashMap<>();


    /**
     * Enter difficulty difficulty.
     *
     * @param scanner the scanner
     * @return the difficulty
     */

    /**
     * Enter difficulty from file difficulty.
     *
     * @param line the line
     * @return the difficulty
     */
    public static Difficulty enterDifficultyFromFile(String line) {
        putByNumber.put(1, "EASY");
        putByNumber.put(2, "NORMAL");
        putByNumber.put(3, "INSANE");

        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (Difficulty difficulty1 : Difficulty.values()) {
                stringBuilder.append(difficulty1).append(" ");
            }
            int in = Integer.parseInt(line);
            return Difficulty.valueOf(Difficulty.putByNumber.get(in));

        } catch (NullPointerException e) {
            System.out.println("Нет такого уровня сложности!");
            return enterDifficultyFromFile(line);
        }
    }
}