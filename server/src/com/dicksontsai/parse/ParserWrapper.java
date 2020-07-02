package com.dicksontsai.parse;

import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

/**
 * ParserWrapper loads an English parser and offers basic methods involving that
 * parser.
 */
class ParserWrapper {
    private LexicalizedParser lp;

    ParserWrapper() {
        this.lp = LexicalizedParser.loadModel();
    }

    /**
     * Return the Penn syntax tree for a sentence.
     */
    public Tree parseSentence(List<HasWord> sentence) {
        return lp.apply(sentence);
    }
}
