package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an alternative option in a decision tree.
 * It holds information about its label, output text, and relationships with other alternative.
 *
 * @author <Author/s Name>
 */
public class Alt implements Serializable {

    //Instance variables
    private List<Alt> parents;
    private List<Alt> children = new ArrayList<>();
    private String altLabelText, outputText;
    private boolean chosen = false;


    // Default constructor, might be needed in later implementation!
    public Alt() {
        this(new ArrayList<>(), "Placeholder", "Placeholder text.");
    }

    /**
     * Constructor to create an Alt object with the provided label text and output text.
     *
     * @author <Author/s Name>
     * @param altLabelText The label text for the alternative.
     * @param outputText   The output text for the alternative.
     */
    public Alt(String altLabelText, String outputText) {
        this.parents = new ArrayList<>();
        this.altLabelText = altLabelText;
        this.outputText = outputText;
    }

    /**
     * Constructor to create an Alt object with the provided parent alternatives, label text,
     * and output text.
     *
     * @author <Author/s Name>
     * @param parents      The parent alternatives of this alternative.
     * @param altLabelText The label text for the alternative.
     * @param outputText   The output text for the alternative.
     */
    public Alt(List<Alt> parents, String altLabelText, String outputText) {
        this.parents = parents;
        this.altLabelText = altLabelText;
        this.outputText = outputText;
    }

    /**
     * Gets the label text of this alternative.
     *
     * @author <Author/s Name>
     * @return Label text.
     */
    public String getAltLabelText() {
        return altLabelText;
    }

    //might be needed in later implementation!
    /**
     * Sets the label text of this alternative.
     *
     * @author <Author/s Name>
     * @param text Label text of alternative.
     */
    public void setAltLabelText(String text) {
        altLabelText = text;
    }

    /**
     * Gets the output text of this alternative.
     *
     * @author <Author/s Name>
     * @return Output text.
     */
    public String getOutputText() {
        return outputText;
    }

    //might be needed in later implementation!
    /**
     * Sets the output text of this alternative.
     *
     * @author <Author/s Name>
     * @return Output text.
     */
    public void setOutputText(String text) {
        outputText = text;
    }

    /**
     * Gets the value of "chosen" (if the alternative has been chosen).
     *
     * @author <Author/s Name>
     * @return True if chosen, false if not.
     */
    public boolean isChosen() {
        return chosen;
    }

    /**
     * Sets whether this alternative has been chosen.
     *
     * @author <Author/s Name>
     * @param b True if chosen, false otherwise.
     */
    public void setChosen(boolean b) {
        chosen = b;
    }

    /*
    // Child methods, might be needed in later implementation!
    public void addChild(Alt child){
        children.add(child);
    }

    public List<Alt> getAllChildren(){
        return children;
    }

    public Alt getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    public int getChildCount() {
        return children.size();
    }
     */

    //Parent methods
    /**
     * Adds a parent alternative to this alternative.
     *
     * @author <Author/s Name>
     * @param parent The parent alternative to add.
     */
    public void addParent(Alt parent) {
        parents.add(parent);
    }

    //might be needed in later implementation!
    /**
     * Gets the chosen parent alternative of this alternative if it's not a root alternative.
     *
     * @author <Author/s Name>
     * @return The chosen parent alternative, or null if root Alt.
     */
    public Alt getParent() {
        for (Alt parent : parents) {
            if (parent.isChosen()) {
                return parent;
            }
        }
        return null; // probably root Alt. Make sure it cannot be undone.
    }

    /**
     * Gets all parent alternatives of this alternative.
     *
     * @author <Author/s Name>
     * @return A list of parent alternatives.
     */
    public List<Alt> getAllParents() {
        return parents;
    }
}
