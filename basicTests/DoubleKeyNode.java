public class DoubleKeyNode extends Node{
    private String secondKey;

    public DoubleKeyNode(Node succ){
        super(succ);
    }

    @Override
    public String getKey(){
        return super.getKey() + secondKey;
    }
}