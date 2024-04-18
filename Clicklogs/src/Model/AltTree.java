package Model;


import javax.swing.*;
import java.io.*;
import java.util.*;

public class AltTree implements Serializable {
    private int maxLevels;
    private Map<Integer, List<Alt>> altTree;

    public AltTree(int maxLevels) {
        this.maxLevels = maxLevels;
        altTree = new HashMap<>();
        for (int i = 0; i < maxLevels; i++) {
            altTree.put(i, new ArrayList<>());
        }
    }

    public List<Alt> getAltsAtLevel(Integer level) {
        return altTree.get(level);
    }

    public int getMaxLevels() {
        return this.maxLevels;
    }

    public static AltTree readAltTree(String filePath) {
        boolean readFailed = true;
        AltTree tree = null;
        do {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                tree = (AltTree) ois.readObject();
                readFailed = false;
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e);
                int choice = JOptionPane.showConfirmDialog(null,
                        "Error reading from " + filePath + "\nTry again?", "Error", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        } while (readFailed);

        return tree;
    }

    public void saveAltTreeToFile(String filePath) {
        boolean saveFailed = true;
        do {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(this);
                saveFailed = false;
            } catch (IOException e) {
                System.err.println(e);
                int choice = JOptionPane.showConfirmDialog(null,
                        "Error saving to " + filePath + "\nTry again?", "Error", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        } while (saveFailed);
        System.out.println("File saved successfully to " + filePath);
    }
    //Admin tools................

    public void addAlt(int level, Alt alt) {
        altTree.get(level).add(alt);
    }
}

