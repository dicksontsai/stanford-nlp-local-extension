package com.dicksontsai.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.stanford.nlp.trees.Tree;

class Sentence {
    private String text;
    private Tree tree;
    private int length;

    Sentence(String text, Tree tree, int length) {
        this.text = text;
        this.tree = tree;
        this.length = length;
    }

    Tree getTree() {
        return this.tree;
    }

    private static JSONObject treeToJSON(Tree tree) {
        JSONObject json = new JSONObject();
        json.put("v", tree.label());
        ArrayList<JSONObject> children = new ArrayList<>();
        for (Tree child : tree.children()) {
            children.add(Sentence.treeToJSON(child));
        }
        json.put("c", new JSONArray(children));
        return json;
    }

    /**
     * 
     * Current schema:
     * 
     * * sentence - text
     * 
     * * tree - parse tree
     * 
     * * length - length of sentence
     */
    JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("sentence", this.text);
        json.put("tree", Sentence.treeToJSON(this.tree));
        json.put("length", this.length);
        return json;
    }

}

/**
 * ParseAggregator aggregates metrics from parse trees that are fed
 * incrementally to it.
 * 
 * Current schema:
 * 
 * 1. "sentences": list of sentence objects.
 * 
 * 2. "poscount": map of part-of-speech (POS) to word counts (map of words to
 * their counts).
 */
public class ParseAggregator {
    private List<Sentence> sentences = new ArrayList<>();
    private Map<String, Map<String, Integer>> posWordCounts = new HashMap<>();

    public void add(Sentence sentence) {
        sentences.add(sentence);
        List<Tree> leaves = sentence.getTree().getLeaves();
        for (Tree leaf : leaves) {
            String pos = leaf.parent(sentence.getTree()).label().value();
            String word = leaf.label().value().toLowerCase();
            Map<String, Integer> posWordCount = posWordCounts.get(pos);
            if (posWordCount == null) {
                posWordCount = new HashMap<>();
                posWordCounts.put(pos, posWordCount);
            }
            Integer wordCount = posWordCount.get(word);
            if (wordCount == null) {
                wordCount = 0;
            }
            posWordCount.put(word, wordCount + 1);
        }
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        List<JSONObject> sentenceJSON = this.sentences.stream().map(s -> s.toJSON()).collect(Collectors.toList());
        json.put("sentences", new JSONArray(sentenceJSON));

        Map<String, JSONObject> posWordCountsJSON = new HashMap<>();
        for (Map.Entry<String, Map<String, Integer>> entry : posWordCounts.entrySet()) {
            posWordCountsJSON.put(entry.getKey(), new JSONObject(entry.getValue()));
        }
        json.put("poscount", new JSONObject(posWordCountsJSON));
        return json;
    }
}