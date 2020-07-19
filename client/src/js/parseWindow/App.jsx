import React from "react";
import Lengths from "./Lengths.jsx";
import Tree from "./Tree.jsx";

// Taken from https://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html
const posTagToNameMap = {
  CC: "Coordinating conjunction",
  CD: "Cardinal number",
  DT: "Determiner",
  EX: "Existential there",
  FW: "Foreign word",
  IN: "Preposition or subordinating conjunction",
  JJ: "Adjective",
  JJR: "Adjective, comparative",
  JJS: "Adjective, superlative",
  LS: "List item marker",
  MD: "Modal",
  NN: "Noun, singular or mass",
  NNS: "Noun, plural",
  NNP: "Proper noun, singular",
  NNPS: "Proper noun, plural",
  PDT: "Predeterminer",
  POS: "Possessive ending",
  PRP: "Personal pronoun",
  PRP$: "Possessive pronoun",
  RB: "Adverb",
  RBR: "Adverb, comparative",
  RBS: "Adverb, superlative",
  RP: "Particle",
  SYM: "Symbol",
  TO: "to",
  UH: "Interjection",
  VB: "Verb, base form",
  VBD: "Verb, past tense",
  VBG: "Verb, gerund or present participle",
  VBN: "Verb, past participle",
  VBP: "Verb, non-3rd person singular present",
  VBZ: "Verb, 3rd person singular present",
  WDT: "Wh-determiner",
  WP: "Wh-pronoun",
  WP$: "Possessive wh-pronoun",
  WRB: "Wh-adverb",
};

function posTagToName(tag) {
  return posTagToNameMap[tag] || tag;
}

function displayPosTag(tag) {
  return `${tag} (${posTagToName(tag)})`;
}

// countsToOrderedList converts a JSON object of counts into an array of
// entries sorted by decreasing order.
// Example: {"foo": 5, "bar": 10} -> [["bar", 10], ["foo", 5]]
function countsToOrderedList(counts) {
  return Object.entries(counts).sort((a, b) => {
    return b[1] - a[1];
  });
}

function PosTable(props) {
  const { poscount } = props;
  const sorted = Object.entries(poscount)
    .map((entry) => [entry[0], countsToOrderedList(entry[1])])
    .sort((a, b) => {
      return a[0].localeCompare(b[0]);
    });
  return (
    <div className="pos-section">
      <h1>Word Choice by Part of Speech</h1>
      <table>
        <thead>
          <tr>
            <th>Part of Speech</th>
            <th>Word counts</th>
          </tr>
        </thead>
        <tbody>
          {sorted.map((entry) => (
            <tr key={entry[0]}>
              <td>{displayPosTag(entry[0])}</td>
              <td>
                <pre>
                  {entry[1].map((wordCount) => wordCount.join(": ")).join("\n")}
                </pre>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function TreesTable(props) {
  return (
    <div className="trees-section">
      <h1>Parse Trees</h1>
      <table>
        <tbody>
          {props.sentences.map((sentence, i) => {
            return (
              <tr key={`tree-${i}`}>
                <td>
                  <div>{sentence.sentence}</div>
                  <Tree tree={sentence.tree} />
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}

// trees is an array of strings. Each string is a printed tree.
// poscount is a JSON object mapping pos to word counts.
export default function ParseWindow(props) {
  const { sentences, poscount } = props;
  return (
    <div className="container">
      <div>
        <PosTable poscount={poscount} />
        <Lengths sentences={sentences} />
      </div>
      <TreesTable sentences={sentences} />
    </div>
  );
}
