package com.dicksontsai.parse;

/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.process.WordToSentenceProcessor;
import edu.stanford.nlp.trees.Tree;

public class ParserServer extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ParserWrapper parser = null;

    @Override
    public void init() throws ServletException {
        super.init();
        this.parser = new ParserWrapper();
    }

    /**
     * doPost parses text, which it expects to find in the "text" parameter.
     * 
     * Make sure that the content-type header matches: "Content-Type:
     * application/x-www-form-urlencoded;charset=utf-8". Then, the form data should
     * be "text=...".
     * 
     * It is ok to provide text possibly from multiple sentences. CoreNLP will
     * attempt to convert the stream of words into sentences by identifying where
     * the punctuation is.
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (this.parser == null) {
            throw new ServletException("Parser did not load");
        }

        String text = request.getParameter("text");
        if (text == null) {
            throw new ServletException("Text not provided");
        }

        text = text.replaceAll("[\\u2018\\u2019]", "'").replaceAll("[\\u201C\\u201D]", "\"");

        TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
        Tokenizer<CoreLabel> tok = tokenizerFactory.getTokenizer(new StringReader(text));
        List<CoreLabel> rawWords = tok.tokenize();
        WordToSentenceProcessor<HasWord> sentencer = new WordToSentenceProcessor<>();
        ParseAggregator agg = new ParseAggregator();
        for (List<HasWord> sentence : sentencer.process(rawWords)) {
            Tree parse = this.parser.parseSentence(sentence);
            StringBuilder sentenceStr = new StringBuilder();
            for (HasWord word : sentence) {
                if (word.word() != null) {
                    sentenceStr.append(word.word() + " ");
                }
            }
            agg.addTree(sentenceStr.toString(), parse);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        out.print(agg.toJSON().toString());
        out.flush();
    }
}