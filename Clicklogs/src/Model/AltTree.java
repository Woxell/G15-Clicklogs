package Model;

import javax.swing.*;
import java.io.*;
import java.util.*;

/**
<<<<<<< Updated upstream
 * Represents an alternate tree structure to store alternatives at different levels.
 * Each level can contain a list of alternative objects.
 *
 * @author Andre
=======
 * Den klass representerar en alternativ trädstruktur för att lagra alternative på olika nivåer.
 * varje nivå kan innehålla en lista med alternativa objekt.
 * @ author Andre
>>>>>>> Stashed changes
 */
public class AltTree implements Serializable {
    private int maxLevels; //MAximal anatal nivåer tillåtna i trädet.
    private Map<Integer, List<Alt>> altTree; // kartläggning av nivåer till listor med alternativ


    /**
     * Constructs an AltTree with the specified maximum levels.
     *
     * @param maxLevels The maximum levels allowed in the AltTree.
     * @author Andre
     */

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
<<<<<<< Updated upstream
     * Retrieves the list of alternatives at the specified level.
     *
     * @param level The level from which to retrieve the alternatives.
     * @return The list of alternatives at the specified level.
=======
     * Hämtar listan med alternativ på den angivna nivån.
     *
     * @param level Nivån från vilken alternativen ska hämtas.
     * @return Listan med alternativ på den angivna nivån.
>>>>>>> Stashed changes
     * @author Andre
     */
    public List<Alt> getAltsAtLevel(Integer level) {
        return altTree.get(level);
    }

    /**
<<<<<<< Updated upstream
     * Retrieves the maximum levels allowed in this AltTree.
     *
     * @return The maximum levels allowed.
=======
     * Hämtar det maximala antalet nivåer tillåtna i detta AltTree.
     *
     * @return Det maximala antalet tillåtna nivåer.
     * @author Andre
>>>>>>> Stashed changes
     */
    public int getMaxLevels() {
        return this.maxLevels;
    }

    /**
<<<<<<< Updated upstream
     * Reads an AltTree object from the specified file path.
     *
     * @param filePath The path of the file from which to read the AltTree object.
     * @return The AltTree object read from the file.
=======
     * Läser in ett AltTree-objekt från den angivna filvägen.
     *
     * @param filePath Sökvägen till filen från vilken AltTree-objektet ska läsas in.
     * @return AltTree-objektet som läses in från filen.
>>>>>>> Stashed changes
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
                int choice = JOptionPane.showConfirmDialog(null, "Error reading from " + filePath + "\nTry again?", "Error", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        } while (readFailed);
        return tree;
    }

    /**
<<<<<<< Updated upstream
     * Saves this AltTree object to the specified file path.
     *
     * @param filePath The path of the file to which to save the AltTree object.
=======
     * Sparar detta AltTree-objekt till den angivna filvägen.
     *
     * @param filePath Sökvägen till filen där AltTree-objektet ska sparas.
>>>>>>> Stashed changes
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
                int choice = JOptionPane.showConfirmDialog(null, "Error saving to " + filePath + "\nTry again?", "Error", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        } while (saveFailed);
        System.out.println("File saved successfully to " + filePath);
    }


    //Admin tools
    /**
     * Adds an alternative object to the specified level in the AltTree.
     *
     * @param level The level at which to add the alternative.
     * @param alt   The alternative object to add.
=======

    //Admin tools................

    /**
     * Lägger till ett alternativobjekt på den angivna nivån i AltTree.
     * @param level Nivån där alternativet ska läggas till.
     * @param alt   Alternativobjektet som ska läggas till.
>>>>>>> Stashed changes
     * @author Andre
     */
    public void addAlt(int level, Alt alt) {
        altTree.get(level).add(alt);
    }
}