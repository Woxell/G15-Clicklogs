package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Alt implements Serializable {

    //Instance variables
    private List<Alt> parents;
    private List<Alt> children = new ArrayList<>();
    private String altLabelText, outputText;
    private boolean chosen = false;

    //Default constructor
    public Alt() {
        this(new ArrayList<>(), "Placeholder", "Placeholder text.");
    }

    public Alt(String altLabelText, String outputText) {
        this.parents = new ArrayList<>();
        this.altLabelText = altLabelText;
        this.outputText = outputText;
    }
    //Parametrized constructor
    public Alt(List<Alt> parents, String altLabelText, String outputText) {
        this.parents = parents;
        this.altLabelText = altLabelText;
        this.outputText = outputText;
    }

    //Self methods..................
    public String getAltLabelText() {
        return altLabelText;
    }

    public void setAltLabelText(String text){
        altLabelText = text;
    }

    public String getOutputText() {
        return outputText;
    }

    public void setOutputText(String text){
        outputText = text;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean b){
        chosen = b;
    }

    @Override
    public String toString() {
        return getOutputText();
    } //Probably won't be used


    //Child methods................
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

    /**
     * Returns the index of <code>node</code> in the receivers children.
     * If the receiver does not contain <code>node</code>, -1 will be
     * returned.
     *
     * @param node node to be looked for
     * @return index of specified node
     */
    public int getIndexOfChild(Alt node) { //Probably won't be used
        for (Alt child : children) {
            if (child == node) {
                return children.indexOf(node);
            }
        }
        return -1;
    }

    //Parent methods.............
    public void addParent(Alt parent){
        parents.add(parent);
    }


    public Alt getParent() {
        for (Alt parent : parents) {
            if (parent.isChosen()) {
                System.out.println("PARENT WAS RETURNED AS: " + parent.getAltLabelText());
                return parent;
            }
        }
        System.out.println("NULL WAS RETURNED");
        return null; // probably root Alt. Make sure it cannot be undone.
    }
    public List<Alt> getParents() {
        return parents;
    }
}
