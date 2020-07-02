# Stanford NLP Local Extension

A Chrome extension that lets you study sentences on web pages using Stanford
NLP tools. Motivation: Learn how others write by studying their grammar and
word choice with the help of NLP!

How it works: The extension sends content to a localhost
[server](server/README.md) that can execute Stanford NLP jar files.

This extension current returns Penn parse trees and a map of POS (part of
speech) to word counts.

![Extension demo](https://github.com/dicksontsai/stanford-nlp-local-extension/blob/master/extension-demo.gif)

Below is a screenshot showing the contents more clearly.

![Extension screenshot](https://github.com/dicksontsai/stanford-nlp-local-extension/blob/master/extension-screenshot.png)

Over time, I aim to add more features, such as storing a history of POS counts
and tree patterns by website/author that you can accumulate over time.

## Acknowledgement

[Stanford NLP](https://nlp.stanford.edu), for making their software available.

The client directory is cloned from
[Chrome Extension Webpack Boilerplate](https://github.com/samuelsimoes/chrome-extension-webpack-boilerplate)
by [@samuelsimoes](https://twitter.com/samuelsimoes).
