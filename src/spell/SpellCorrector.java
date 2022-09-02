package spell;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector{
    private Trie dictionary = new Trie();
    private Trie similarWords = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        //make a file, scanner, loop
        File file = new File(dictionaryFileName);
        Scanner sc = new Scanner(file);
        while (sc.hasNext()){
            dictionary.add(sc.next());
        }

        //System.out.println(dictionary.getWordCount());
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return null;
    }
}
