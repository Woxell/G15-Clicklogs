package Model;

public class Alt {
    private String altLabelText;
    private String outputText;
    private int tier;

    public Alt(){
        this("Placeholder", "Placeholder text.");
    }
    public Alt(String altLabelText, String outputText){
        this.altLabelText = altLabelText;
        this.outputText = outputText;
    }

    public void setTier(int tier){
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }

    public String getAltLabelText() {
        return altLabelText;
    }

    public String getOutputText() {
        return outputText;
    }

    @Override
    public String toString(){
        return getOutputText();
    }
}
