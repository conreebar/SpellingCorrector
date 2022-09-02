package spell;

public class Trie implements ITrie{
    private Node root = new Node();

    //int index = (letter) - 'a' -> returns the correct index
    //char letter = (char)('a' + index) -> converts letter to char
    @Override
    public void add(String word) {
        //sets node
        Node[] node = root.getChildren();

        //loops through each letter and adds a node / increments the node
        for(int iter = 0; iter < word.length(); iter++){
            //gets index of node to move to next
            int index = word.charAt(iter) - 'a';
            node = node[index].getChildren();
        }

    }

    @Override
    public INode find(String word) {
        return null;
    }

    @Override
    public int getWordCount() {
        return 0;
    }

    @Override
    public int getNodeCount() {
        return 0;
    }
}
