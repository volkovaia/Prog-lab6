package src.data;

import exception.IllegalValueException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Scanner;

/**
 * The type Lab work.
 */
@Data
@NoArgsConstructor
public class LabWork implements Comparator<LabWork>, Serializable {


    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long minimalPoint; //Значение поля должно быть больше 0
    private Float personalQualitiesMinimum; //Поле не может быть null, Значение поля должно быть больше 0
    private float averagePoint; //Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле может быть null
    private Discipline discipline; //Поле может быть null


    /**
     * Check all variables.
     *
     * @throws IllegalValueException the illegal value exception
     */
    public void checkAllVariables() throws IllegalValueException {
        if (id < 0) {
            throw new IllegalValueException("ID меньше нуля");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalValueException("Введите имя");
        }
        if (coordinates == null) {
            throw new IllegalValueException("Поле не может быть null");
        }
        if (creationDate == null) {
            throw new IllegalValueException("Поле не может быть пустым");
        }
        if (minimalPoint <= 0) {
            throw new IllegalValueException("Значение поля должно быть больше 0");
        }
        if (personalQualitiesMinimum == null || personalQualitiesMinimum <= 0) {
            throw new IllegalValueException("Поле должно быть больше 0, не может быть пустым");
        }
        if (averagePoint <= 0) {
            throw new IllegalValueException("Значение поля должно быть больше 0");
        }
    }

    /**
     * Instantiates a new Lab work.
     *
     * @param name                     the name
     * @param coordinates              the coordinates
     * @param creationDate             the creation date
     * @param minimalPoint             the minimal point
     * @param personalQualitiesMinimum the personal qualities minimum
     * @param averagePoint             the average point
     * @param difficulty               the difficulty
     * @param discipline               the discipline
     */
    public LabWork(String name, Coordinates coordinates, LocalDate creationDate, long minimalPoint, Float personalQualitiesMinimum, float averagePoint, Difficulty difficulty, Discipline discipline) {
        try {
            this.name = name;
            this.coordinates = coordinates;
            this.creationDate = creationDate;
            this.minimalPoint = minimalPoint;
            this.personalQualitiesMinimum = personalQualitiesMinimum;
            this.averagePoint = averagePoint;
            this.difficulty = difficulty;
            this.discipline = discipline;

            checkAllVariables();

        } catch (IllegalValueException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String toString() {
        return "{\n\"id\": " + id + ",\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"coordinates\": " + coordinates + ",\n" +
                "\"creationDate\": \"" + creationDate + "\",\n" +
                "\"minimalPoint\": " + minimalPoint + ",\n" +
                "\"personalQualitiesMinimum\": " + personalQualitiesMinimum + ",\n" +
                "\"averagePoint\": " + averagePoint + ",\n" +
                "\"difficulty\": " + (difficulty == null ? "null" : "\"" + difficulty + "\"") + ",\n" +
                "\"discipline\": " + discipline + "\n" + "}\n";
    }


    @Override
    public int compare(LabWork o1, LabWork o2) {
        return Integer.compare(o1.id, o2.id);
    }

}


