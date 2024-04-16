package Model;


import java.io.Serial;
import java.io.Serializable;
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


    //Admin tools................

    public void addAlt(int level, Alt alt) {
        altTree.get(level).add(alt);
    }
}

