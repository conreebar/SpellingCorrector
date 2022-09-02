package spell;

public class Node implements INode{
    private int value = 0;
    private Node[] children;

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void incrementValue() {
        value++;
    }

    @Override
    public Node[] getChildren() {
        if(children == null){
            createChildren();
        }
        return children;
    }
    public void createChildren(){
        children = new Node[26];
    }
}
