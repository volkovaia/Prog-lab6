package src.utility;

import data.LabWork;

import java.time.LocalDate;
import java.util.Vector;

public interface CollectionManagerInterface {

    public Vector<LabWork> getLabWorkVector();

    public LocalDate getLastInitTime();
    public LabWork addFromInput(String args);

    LabWork addIfMin(String args) throws Exception;

    LabWork updateByIdCollection(String args) throws Exception;

    public void loadCollection();
    public int getId();
    public void clear();
    public boolean checkExist(int id);



}