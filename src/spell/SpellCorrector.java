package spell;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector{
    private Trie dictionary = new Trie();
    private Trie similarWords = new Trie();

    //public void addLetter(){}

    //public void switchLetter(){}

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        //make a file, scanner, loop
        File file = new File(dictionaryFileName);
        Scanner sc = new Scanner(file);
        while (sc.hasNext()){
            dictionary.add(sc.next());
        }

        //test
        //System.out.println(dictionary.toString());
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        //4 adjustments

        //add a letter in between each
        //ab
        //aab, bab, cab...
        //aab, abb, acb...
        //aba, abb, abc, abd...



        return null;
    }
}
