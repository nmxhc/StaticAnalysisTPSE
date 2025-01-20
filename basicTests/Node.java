public class Node extends NodeAbs{

    private Node next;
    private String key;

    public Node(Node succ){
        next = succ;
    }

    @Override
    public Node getNext(){
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public java.lang.String getKey() {
        return key;
    }

    public void setKey(java.lang.String key) {
        this.key = key;
    }
}