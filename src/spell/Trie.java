package spell;

import java.util.Objects;
import java.util.Scanner;

public class Trie implements ITrie{
    private Node root;
    private int wordCount;
    private int nodeCount;
    private StringBuilder words;
    private StringBuilder letters;
    private boolean bool;

    //int index = (letter) - 'a' -> returns the correct index
    //char letter = (char)('a' + index) -> converts letter to char

    public Trie(){
        wordCount = 0;
        nodeCount = 1;
        words = new StringBuilder();
        letters = new StringBuilder();
        root = new Node();
    }

    @Override
    public void add(String word) {
        //sets node
        Node[] node = root.getChildren();

        //loops through each letter and adds a node / increments the node
        int index = 0;
        for(int iter = 0; iter < word.length()-1; iter++){
            //gets index of node to move to next
            index = word.charAt(iter) - 'a';
            if(node[index] == null){node[index] = new Node(); nodeCount++;}
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
            if(node[index].getChildren() == null){return null;}
            node = node[index].getChildren();
        }
        //one last, dont care about children
        index = word.charAt(word.length() -1) - 'a';
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

    public String toString(){
        //delete stringBuilders words and letter so that they're fresh
        words.delete(0, words.length());
        letters.delete(0, letters.length());

        toStringHelper(root);
        return words.toString();
    }
    private void toStringHelper(Node node){
        //check for word to be added
        if(node.getValue() > 0){ //if its value /= 0 then its a word to add
            words.append(letters);//add the letter
            words.append("\n");//add a new line
        }
        for(int iter = 0; iter < 26; iter++){
            if(node.getChildren()[iter] != null){ //do nothing on null otherwise
                letters.append((char)(iter + 'a'));
                toStringHelper(node.getChildren()[iter]);
                letters.deleteCharAt(letters.length() - 1);
            }
        }
    }
    @Override
    public boolean equals(Object o) {
        //check correct class
        if (o == null || getClass() != o.getClass()) return false;
        Trie trie = (Trie) o;
        //check size of wordcount and nodecount
        if(nodeCount != trie.nodeCount){return false;}
        if(wordCount != trie.wordCount){return false;}
        //check each child
        Node node1 = root;
        Node node2 = trie.root;
        bool = true;
        equalsHelper(node1, node2);
        return bool;
    }
    private void equalsHelper(Node n1, Node n2){
        if(!bool){return;}
        if(n1.getValue() != n2.getValue()){
            bool = false;
            return;
        }

        //check each letter
        for(int iter = 0; iter < 26; iter++){
            //case both null
            if(n1.getChildren()[iter] == null && n2.getChildren()[iter] == null){
            }
            //case one null or the other but not both
            if((n1.getChildren()[iter] == null && n2.getChildren()[iter] != null)
                    || (n1.getChildren()[iter] != null && n2.getChildren()[iter] == null)){
                bool = false;
            }
            //otherwise keep checking
            if(n1.getChildren()[iter] != null && n2.getChildren()[iter] != null){
                equalsHelper(n1.getChildren()[iter], n2.getChildren()[iter]);
            }
        }
    }

    @Override
    public int hashCode() {
        //in order to randomize the hashcode, I'll check whether the end of the alphabet is used and where it is
        int num = 1;
        for(int iter = 0; iter < 26; iter++){
            if (root.getChildren()[25 - iter] != null){
                num = iter + 1;
            }
        }
        return (num * wordCount * nodeCount);
    }
}
