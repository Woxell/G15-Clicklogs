package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents an alternative option in a decision tree.
 * It holds information about its label, output text, and relationships with other alternative.
 *
 * @author Andre, Robert and Mohamad
 */
public class Alt implements Serializable {

    //Instance variables
    private List<Alt> parents;
    private List<Alt> children = new ArrayList<>();
    private String altLabelText, outputText;
    private boolean chosen = false;
    private AtomicInteger counter = new AtomicInteger(0);

    /**
     * Constructor to create an Alt object with the provided label text and output text.
     *
     * @param altLabelText The label text for the alternative.
     * @param outputText   The output text for the alternative.
     * @author Andre
     */
    public Alt(String altLabelText, String outputText) {
        this.parents = new ArrayList<>();
        this.altLabelText = altLabelText;
        this.outputText = outputText;
    }

    /**
     * Gets the label text of this alternative.
     *
     * @return Label text.
     * @author Andre
     */
    public String getAltLabelText() {
        return altLabelText;
    }

    /**
     * Gets the output text of this alternative.
     *
     * @return Output text.
     * @author Andre
     */
    public String getOutputText() {
        return outputText;
    }

    /**
     * Gets the value of "chosen" (if the alternative has been chosen).
     *
     * @return True if chosen, false if not.
     * @author Andre
     */
    public boolean isChosen() {
        return chosen;
    }

    /**
     * Sets whether this alternative has been chosen.
     *
     * @param b True if chosen, false otherwise.
     * @author Andre
     */
    public void setChosen(boolean b) {
        chosen = b;
    }

    //Parent methods
    /**
     * Adds a parent alternative to this alternative.
     *
     * @param parent The parent alternative to add.
     * @author Andre
     */
    public void addParent(Alt parent) {
        parents.add(parent);
    }

    /**
     * Gets all parent alternatives of this alternative.
     *
     * @return A list of parent alternatives.
     * @author Andre
     */
    public List<Alt> getAllParents() {
        return parents;
    }

    /**
     * Safely increases counter by 1 in case of Multithreading
     *
     * @author Robert
     */
    public void increaseCounter() {
        counter.incrementAndGet();
    }

    /**
     * Get the current value of counter
     *
     * @return Counter, amount of times this alt has been chosen
     * @author Robert
     */
    public int getCounter() {
        return counter.get();
    }
}
