public class DoubleLinkedQueue extends Queue{
    private Node tail;

    public DoubleLinkedQueue(Node head, Node tail){
        super(head);
        this.tail = tail;
    }


}