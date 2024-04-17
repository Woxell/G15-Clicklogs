package Model;


import javax.swing.*;
import java.io.*;
import java.util.*;

public class AltTree implements Serializable {
    private int maxLevels;
    private int currentLevel = 0; // move to controller
    private Map<Integer, List<Alt>> altTree;

    public AltTree(int maxLevels) {
        this.maxLevels = maxLevels;
        altTree = new HashMap<>();
        for(int i = 0; i < maxLevels; i++) {
            altTree.put(i, new ArrayList<>());
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void levelUp() {
        currentLevel++;
    }

    public void levelDown() {
        currentLevel--; //For use in undo-cases maybe?
    }

    public List<Alt> getAltsAtLevel(Integer level) {
        return altTree.get(level);
    }

    public int getMaxLevels(){
        return this.maxLevels;
    }

    public static AltTree readAltTree(String filePath){
        boolean readFailed = true;
        AltTree tree = null;
        do {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                tree = (AltTree) ois.readObject();
                readFailed = false;
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error reading from " + filePath + "\nTry again?");
                System.err.println(e);
            }
        } while (readFailed);

        return tree;
    }

    //Admin tools................

    public void addAlt(int level, Alt alt) {
        altTree.get(level).add(alt);
    }
}

