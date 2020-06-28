# Stanford NLP Local Extension

A Chrome extension that lets you study sentences on web pages using Stanford
NLP tools. Motivation: Learn how others write by studying their grammar and
word choice with the help of NLP!

How it works: The extension sends content to a localhost server that can execute
Stanford NLP jar files. You will need to set up the
[Stanford Parser Server](https://github.com/dicksontsai/stanford_parser_server)
and have it running locally.

This extension current returns Penn parse trees and a map of POS (part of
speech) to word counts.

![Extension demo](https://github.com/dicksontsai/stanford-nlp-local-extension/blob/master/extension-demo.gif)

Below is a screenshot showing the contents more clearly.

![Extension screenshot](https://github.com/dicksontsai/stanford-nlp-local-extension/blob/master/extension-screenshot.png)

Over time, I aim to add more features, such as storing a history of POS counts
and tree patterns by website/author that you can accumulate over time.

## Working with Webpack

`yarn run start` autoloads but introduces files that Google Chrome doesn't understand. To load an extension in Chrome:

1. Run `yarn run build`.
1. Go to `chrome://extensions` and click `Load Unpacked`.
1. Select the `build` directory produced by `yarn run build`.

## Acknowledgement

This repo is cloned from [Chrome Extension Webpack Boilerplate](https://github.com/samuelsimoes/chrome-extension-webpack-boilerplate) by
[@samuelsimoes](https://twitter.com/samuelsimoes).
