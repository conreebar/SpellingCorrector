package spell;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector{
    private Trie dictionary = new Trie();
    private StringBuilder wordToAdd = new StringBuilder();

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
    ///////////////////
    ///////////////////
    //the four methods
    Set<String> deletionSet(String str, Set<String> words){ //delete one letter and add each to words
        for(int iter = 0; iter < str.length(); iter++){
            wordToAdd.delete(0, wordToAdd.length());
            wordToAdd.append(str);
            wordToAdd.deleteCharAt(iter);
            words.add(wordToAdd.toString());
        }
        return words;
    }
    Set<String> insertionSet(String str, Set<String> words){
        for(int iter = 0; iter < str.length(); iter++){
            wordToAdd.delete(0, wordToAdd.length());
            wordToAdd.append(str.substring(0, iter));
            for(int iter2 = 0; iter2 < 26; iter2++){
                wordToAdd.append((char) (iter2 + 'a'));//add a letter
                wordToAdd.append(str.substring(iter));//the rest of the word (iter)
                words.add(wordToAdd.toString());//add the word
                wordToAdd.delete(iter, str.length() + 1); //delete back to beginning of iter + 1
            }
            words.add(wordToAdd.toString());
        }
        return words;
    }
    Set<String> alterationSet(String str, Set<String> words){
        for(int iter = 0; iter < str.length(); iter++){
            wordToAdd.delete(0, wordToAdd.length());
            wordToAdd.append(str.substring(0, iter));
            for(int iter2 = 0; iter2 < 26; iter2++){
                //cant reuse word
                if(str.charAt(iter) != (char)('a' + iter2)){
                    wordToAdd.append((char) (iter2 + 'a'));//add a letter
                    wordToAdd.append(str.substring(iter + 1));//the rest of the word
                    words.add(wordToAdd.toString());//add the word
                    wordToAdd.delete(iter, str.length()); //delete back to beginning of iter
                }
            }
            words.add(wordToAdd.toString());
        }
        return words;
    }
    Set<String> transSet(String str, Set<String> words){
        for(int iter = 0; iter < str.length() - 1; iter++){
            //delete wordstoadd. refresh
            wordToAdd.delete(0, wordToAdd.length());
            wordToAdd.append(str);
            //store first position
            String ch = wordToAdd.substring(iter, iter + 1);
            //swap 2nd to 1st
            wordToAdd.replace(iter, iter + 1, wordToAdd.substring(iter+1,iter+2));
            //swap ch to 2nd
            wordToAdd.replace(iter + 1, iter + 2, ch);
            words.add(wordToAdd.toString());
        }
        return words;
    }

    @Override
    public String suggestSimilarWord(String inputWord) {

        Set<String> distance1 = new TreeSet<>();
        Set<String> distance2 = new TreeSet<>();
        Set<String> goodWords = new TreeSet<>();

        if (inputWord == null || inputWord == ""){
            return null;
        }
        inputWord = inputWord.toLowerCase();
        //make sure the word isn't already in the dictionary
        if(dictionary.find(inputWord) != null){
            return inputWord;
        }

        //begin altering
        distance1 = deletionSet(inputWord,distance1);
        distance1 = alterationSet(inputWord,distance1);
        distance1 = transSet(inputWord, distance1);
        distance1 = insertionSet(inputWord, distance1);

        //check for good words
        for(String ob: distance1){
            if(dictionary.find(ob) != null){
                if(dictionary.find(ob).getValue() > 0){
                    goodWords.add(ob);
                }
            }
        }

        //if good words is empty then you need to try again
        if(goodWords.isEmpty()){
            for(String ob: distance1){
                distance2 = deletionSet(ob, distance2);
                distance2 = insertionSet(ob, distance2);
                distance2 = transSet(ob, distance2);
                distance2 = alterationSet(ob, distance2);
            }
            //check these for good words
            for(String ob: distance2){
                if(dictionary.find(ob) != null){
                    if(dictionary.find(ob).getValue() > 0){
                        goodWords.add(ob);
                    }
                }
            }
        }
        if(goodWords.isEmpty()){
            return null;
        }

        String bestWord = (String) goodWords.toArray()[0];
        for(String ob: goodWords){
            if(dictionary.find(bestWord).getValue() < dictionary.find(ob).getValue()){
                bestWord = ob;
            }
        }
        return bestWord.toLowerCase();
    }
}
