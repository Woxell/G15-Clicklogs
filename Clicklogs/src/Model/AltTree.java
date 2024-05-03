package Model;


import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * Den klass representerar en alternativ trädstruktur för att lagra alternative på olika nivåer.
 * varje nivå kan innehålla en lista med alternativa objekt.
 * @ author Andre
 */
public class AltTree implements Serializable {
    private int maxLevels; //MAximal anatal nivåer tillåtna i trädet.
    private Map<Integer, List<Alt>> altTree; // kartläggning av nivåer till listor med alternativ

    /**
     * Konstruktur för klassen  AltTree med det angivna maximala antalet nivåer.
     *
     * @param maxLevels Det maximala antalet nivåer tillåtna i AltTree.
     * @author Andre
     */
    public AltTree(int maxLevels) {
        this.maxLevels = maxLevels;
        altTree = new HashMap<>();
        for (int i = 0; i < maxLevels; i++) {
            altTree.put(i, new ArrayList<>());
        }
    }

    /**
     * Hämtar listan med alternativ på den angivna nivån.
     *
     * @param level Nivån från vilken alternativen ska hämtas.
     * @return Listan med alternativ på den angivna nivån.
     * @author Andre
     */
    public List<Alt> getAltsAtLevel(Integer level) {
        return altTree.get(level);
    }

    /**
     * Hämtar det maximala antalet nivåer tillåtna i detta AltTree.
     *
     * @return Det maximala antalet tillåtna nivåer.
     * @author Andre
     */
    public int getMaxLevels() {
        return this.maxLevels;
    }

    /**
     * Läser in ett AltTree-objekt från den angivna filvägen.
     *
     * @param filePath Sökvägen till filen från vilken AltTree-objektet ska läsas in.
     * @return AltTree-objektet som läses in från filen.
     * @author Andre
     */
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

    /**
     * Sparar detta AltTree-objekt till den angivna filvägen.
     *
     * @param filePath Sökvägen till filen där AltTree-objektet ska sparas.
     * @author Andre
     */
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

    /**
     * Lägger till ett alternativobjekt på den angivna nivån i AltTree.
     * @param level Nivån där alternativet ska läggas till.
     * @param alt   Alternativobjektet som ska läggas till.
     * @author Andre
     */
    public void addAlt(int level, Alt alt) {
        altTree.get(level).add(alt);
    }
}

