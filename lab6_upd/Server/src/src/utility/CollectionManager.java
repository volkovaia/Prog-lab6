package src.utility;

import data.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


public class CollectionManager implements CollectionManagerInterface, Serializable {
    private static LocalDate lastInitTime;
    private final Vector<LabWork> labWorkCollection = new Vector<>();

    private final FileManager fileManager;

    @Override
    public Vector<LabWork> getLabWorkVector() {
        return labWorkCollection;
    }

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;


    }

    public Vector<LabWork> checkUniqueId(Vector<LabWork> toCheck) {
        Vector<LabWork> uniqueLabWork = new Vector<>();
        Set<Integer> uniqueIDs = new HashSet<>();
        for (LabWork labWork : toCheck) {
            int oldID = labWork.getId();
            if (!uniqueIDs.add(oldID)) {
                System.out.println("Проверьте id " + oldID + ", он не является уникальным!");
                int newID = getId();
                while (!uniqueIDs.add(newID)) {
                    newID = getId();
                }
                labWork.setId(newID);
                System.out.println("Заменён на уникальный id: " + newID);
            }
            uniqueLabWork.add(labWork);
        }
        return uniqueLabWork;
    }

    public LabWork addFromInput(String args) {
        List<String> arguments = new ArrayList<>();
        while (args.contains(";")) {
            arguments.add(args.split(";")[0]);
            args = args.substring(args.split(";")[0].length() + 1, args.length());
        }
        arguments.add(args);
        System.out.println(arguments);
        try {
            if (arguments.get(0).isBlank()) {
                throw new RuntimeException("Имя не может быть пустым");
            }
            if (arguments.get(1).isBlank()) {
                throw new RuntimeException("Координата X не может быть пустой");
            }
            try {
                Integer.parseInt(arguments.get(1));
            } catch (NumberFormatException e){
                throw new RuntimeException("Координата X должна быть типа int. Попробуйте снова!");
            }

            if (arguments.get(2).isBlank()) {
                throw new RuntimeException("Координата Y не может быть пустой");
            }
            try {
                Integer.parseInt(arguments.get(2));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Координата Y должна быть типа Float. Попробуйте снова!");
            }
            if (arguments.get(3).isBlank()){
                throw new RuntimeException("Минимальное значение не может быть пустым");
            }
            try {
                Long.parseLong(arguments.get(3));
            } catch (NumberFormatException e){
                throw new NumberFormatException("Минимальное значение должно быть типа long. Попробуйте снова!");
            } if (arguments.get(4).isBlank()){
                throw new RuntimeException("Минимальные личностные качества не могут быть меньше 0 или пустым");
            }
            try {
                Float.parseFloat(arguments.get(4));
                if (Float.parseFloat(arguments.get(4)) < 0){
                    throw new RuntimeException("Минимальные личностные качества не могут быть меньше 0");
                }
            } catch (NumberFormatException e){
                throw new NumberFormatException("Минимальные личностные качества должны быть типа Float и не могут быть меньше 0. Попробуйте снова!");
            }
            try {
                Float.parseFloat(arguments.get(5));
                if (Float.parseFloat(arguments.get(5)) < 0){
                    throw new NumberFormatException("Среднее значение не может быть меньше 0");
                }
            } catch (NumberFormatException e){
                throw new NumberFormatException("Среднее значение должно быть типа float. Попробуйте снова!");
            }
            try{
                Integer.parseInt(arguments.get(6));
                if (Integer.parseInt(arguments.get(6)) != 1 && Integer.parseInt(arguments.get(6)) != 2 && Integer.parseInt(arguments.get(6)) != 3){
                    throw new RuntimeException("Нет такого уровня сложности!");}
            } catch (NumberFormatException e){
                throw new NumberFormatException("Введите уровень сложности то 1 до 3. Попробуйте снова!");
            }
            if (arguments.get(7).isBlank()){
                throw new RuntimeException("Имя дисциплины не может быть пустым");
            }
            try{
                Long.parseLong(arguments.get(8));
            } catch (NumberFormatException e){
                throw new NumberFormatException("Часы практики должны быть типа Long. Попробуйте снова!");
            }
            try{
                Long.parseLong(arguments.get(9));
            } catch (NullPointerException e){
                throw new NumberFormatException("Количество лабораторных работ должно быть типа Long. Попробуйте снова!");
            }

            HashMap<Integer, String> putByNumber = new HashMap<>();
            putByNumber.put(1, "EASY");
            putByNumber.put(2, "NORMAL");
            putByNumber.put(3, "INSANE");

                LabWork labWork = new LabWork(arguments.get(0),
                        new Coordinates(Integer.parseInt(arguments.get(1)),
                                Float.parseFloat(arguments.get(2))), LocalDate.now(),
                        Long.parseLong(arguments.get(3)),
                        Float.parseFloat(arguments.get(4)),
                        Float.parseFloat(arguments.get(5)),
                        Difficulty.valueOf(putByNumber.get(Integer.parseInt(arguments.get(6)))),
                        new Discipline(arguments.get(7),
                                Long.parseLong(arguments.get(8)),
                                Long.parseLong(arguments.get(9))));
                labWork.setId(1);
                getLabWorkVector().add(labWork);
                checkUniqueId(getLabWorkVector());

                //loadCollection();
                return labWork;
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Передано некорректное количество аргументов");

        }
    }

    public void clear(){
        labWorkCollection.clear();
    }


    @Override

    public LocalDate getLastInitTime() {
        return lastInitTime;
    }


    @Override
    public LabWork addIfMin(String args) throws Exception {
        String in = args;
        List<String> arguments = new ArrayList<>();
        while (args.contains(";")) {
            arguments.add(args.split(";")[0]);
            args = args.substring(args.split(";")[0].length() + 1, args.length());
        }
        arguments.add(args);
        try {
            Float.parseFloat(arguments.get(5));
        } catch (NumberFormatException e) {
            throw new Exception("averagePoint должен быть типа Float");
        }
        List<LabWork> Labworks = getLabWorkVector();
        Comparator choose = new ComparatorLabwork();
        Collections.sort(Labworks, choose);
        LabWork minAvgPoint = Labworks.get(0);
       // System.out.println(minAvgPoint);
        if (minAvgPoint.getAveragePoint() > Float.parseFloat(arguments.get(5))) {
            try {
                LabWork labwork = addFromInput(in);
                return labwork;
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new Exception("Значение averagePoint не является наименьшим");
        }
    }

    @Override
    public LabWork updateByIdCollection(String args) throws Exception {
            try {
                Integer.parseInt(args.split(";")[0]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Команде нужен аргумент типа int!");
            }
            int id = Integer.parseInt(args.split(";")[0]);
            args = args.substring(args.split(";")[0].length() + 1, args.length());
            try {
                if (getLabWorkVector().removeIf((labWork -> labWork.getId() == id))) {
                    LabWork updLabwork = addFromInput(args);
                    updLabwork.setId(id);
                    return updLabwork;
                }
                else {
                   throw  new Exception("Нет элемента с таким id!");
                 }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }


    }


    public void loadCollection(){
        lastInitTime = LocalDate.now();
        labWorkCollection.addAll(fileManager.readCollection());
        Comparator choose = new ComparatorLocation();
        Collections.sort(labWorkCollection, choose);
        checkUniqueId(labWorkCollection);
    }
    int lastId = 1;
    public int getId() {
            for (LabWork labWork : getLabWorkVector()) {

                while (labWork.getId() == lastId) {
                    lastId += 1;
                }
            }

        return lastId;
    }
    public boolean checkExist(int id) {
        return labWorkCollection.stream()
                .anyMatch((x) -> x.getId() == id);
    }


}



