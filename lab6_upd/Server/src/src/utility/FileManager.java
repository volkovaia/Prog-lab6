package src.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.LabWork;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public class FileManager implements Serializable{
    /**
     * Аргумент командной строки
     */

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateDerializer())
            .create();


//    private ConsoleManager consoleManager;

    public FileManager(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    String pathToFile;

    public void saveCollection(Collection<LabWork> collection) {
        LocalDate saveTime = LocalDate.now();
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(pathToFile))) {

            LabWorkArray labworkArray = new LabWorkArray();

            labworkArray.array.addAll(collection);

            String fileString = gson.toJson(labworkArray, LabWorkArray.class);

            bufferedOutputStream.write(fileString.getBytes(StandardCharsets.UTF_8));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Collection<LabWork> readCollection() {
        //LocalDate lastInitTime = LocalDate.now();

        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {

            String stringFile = reader.lines().collect(Collectors.joining());

            LabWorkArray labWorkArray = gson.fromJson(stringFile, LabWorkArray.class);

            return labWorkArray.array;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
