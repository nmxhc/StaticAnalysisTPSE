package SootAPI;

public class Attribute {

    protected String type;
    protected String name;

    /**
     * New Attribute
     * @param type of attribute
     * @param name of attribute
     */
    public Attribute(String type, String name){
        this.type = type;
        this.name = name;
    }

    /**
     * @return type of attribute
     */
    public String getType() {
        return type;
    }

    /**
     * @return name of attribute
     */
    public String getName() {
        return name;
    }
}
