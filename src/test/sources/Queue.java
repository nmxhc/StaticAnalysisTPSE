public class Queue implements QueueInt{

    private Node head;

    public Queue(Node head){
        this.head = head;
    }

    public Node getHead() {
        return head;
    }

    public int size(){
        Node node = head;
        int size = 0;
        while (node.getNext() != null){
            size++;
            node = node.getNext();
        }
        return size;
    }

    public Node getNode(String key){
        return getNodeRecursive(key, head);
    }

    public Node getNodeRecursive(String key, Node node){
//        if(node.getNext() == null){
//            throw new IndexOutOfBoundsException();
//        }
        // this had to be commented out, as exceptions aren't in scope for the project
        if(node.getKey() == key){
            return node;
        }else{
            return getNodeRecursive(key, node.getNext());
        }
    }

    public Node getNodeIterative(String key, Node node){
        for (int i = 0; i < this.size(); i++) {
            if(node.getKey() == key) return node;
            else node = node.getNext();
        }
        return null;
    }

    public void forEach(String key, Node node){
        for (int i = 0; i < this.size(); i++) {
            node.setKey("a");
            node = node.getNext();
        }
    }

}