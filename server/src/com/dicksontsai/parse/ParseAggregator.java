package com.dicksontsai.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

import edu.stanford.nlp.trees.Tree;

/**
 * ParseAggregator aggregates metrics from parse trees that are fed
 * incrementally to it.
 * 
 * Current schema:
 * 
 * 1. "tree": list of parse trees strings.
 * 
 * 2. "poscount": map of part-of-speech (POS) to word counts (map of words to
 * their counts).
 */
public class ParseAggregator {
    private List<String> trees = new ArrayList<>();
    private Map<String, Map<String, Integer>> posWordCounts = new HashMap<>();

    public void addTree(Tree tree) {
        trees.add(tree.pennString());
        List<Tree> leaves = tree.getLeaves();
        for (Tree leaf : leaves) {
            String pos = leaf.parent(tree).label().value();
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
        json.put("tree", new JSONArray(this.trees));

        Map<String, JSONObject> posWordCountsJSON = new HashMap<>();
        for (Map.Entry<String, Map<String, Integer>> entry : posWordCounts.entrySet()) {
            posWordCountsJSON.put(entry.getKey(), new JSONObject(entry.getValue()));
        }
        json.put("poscount", new JSONObject(posWordCountsJSON));
        return json;
    }
}