public class Node extends NodeAbs{

    private Node next;
    private String key;

    public Node(Node succ){
        next = succ;
    }

    public Node(){}

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

    public boolean hasNext(){
        return next != null;
    }

    public void expressionTester(boolean param1, String param2, Node param3){
        int a = 3 + 5;
        int b = 6 - 2;
        int c = a * b;
        int d = a / b;

        Node n = new Node();
        n.setNext(param3);

        boolean boo;
        boo = param1;
        boo = true;
        boo = a < b;
        boo = a > b;
        boo = a <= b;
        boo = a >= b;
        boo = a == b;
        boo = a != b;

        String s = "foo";
        s = param2;
    }

    public void statementTester(int z){
        int a = z;
        if(a<3){
            a = 4;
        } else if (a>3) {
            a = 5;
        }else{
            a = 6;
        }

        a = 7;

        int e = helper(1, 2);
        Node n = getNext();
    }

    public int helper(int a, int b){
        return a + b;
    }

    public void setKey(java.lang.String key) {
        this.key = key;
    }
}