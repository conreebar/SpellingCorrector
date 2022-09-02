package spell;

import java.util.Objects;

public class Trie implements ITrie{
    private Node root = new Node();
    private int wordCount;
    private int nodeCount;
    //int index = (letter) - 'a' -> returns the correct index
    //char letter = (char)('a' + index) -> converts letter to char
    @Override
    public void add(String word) {
        //sets node
        if(root.getChildren() == null){root.createChildren(); nodeCount++;}
        Node[] node = root.getChildren();

        //loops through each letter and adds a node / increments the node
        int index = 0;
        for(int iter = 0; iter < word.length()-1; iter++){
            //gets index of node to move to next
            index = word.charAt(iter) - 'a';
            if(node[index] == null){node[index] = new Node(); nodeCount++;}
            if(node[index].getChildren() == null){node[index].createChildren();}
            node = node[index].getChildren();
        }
        //last letter
        index = word.charAt(word.length()-1) - 'a';
        if(node[index] == null){node[index] = new Node(); nodeCount++;}
        node[index].incrementValue();
        //only increment if the word is the first addition
        if(node[index].getValue() == 1){
            wordCount++;
        }
    }

    @Override
    public INode find(String word) {
        Node[] node = root.getChildren();
        int index = 0;
        for(int iter = 0; iter < word.length()-1; iter++){
            index = word.charAt(iter) - 'a';
            if(node[index] == null){return null;}

            if(node[index].getChildren() == null){;return null;}
            node = node[index].getChildren();
        }
        //one last, dont care about children
        index = word.charAt(word.length()-1) - 'a';
        if(node[index] == null){return null;}
        if(node[index].getValue() > 0){
            return node[index];
        }
        return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }
    //need to do!!!
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trie trie = (Trie) o;
        Node[] node = root.getChildren();
        //Node[] otherNode = o.get
        for(int iter = 0; iter < 26; iter++){
            if
        }

        return wordCount == trie.wordCount && nodeCount == trie.nodeCount && Objects.equals(root, trie.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root, wordCount, nodeCount);
    }
}
