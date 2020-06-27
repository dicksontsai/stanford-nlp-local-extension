# Stanform NLP Local Extension

A Chrome extension that lets you study sentences on web pages using Stanford
NLP tools.

How it works: The extension sends content to a localhost server that can execute
Stanford NLP jar files. You will need to set up the
[Stanford Parser Server](https://github.com/dicksontsai/stanford_parser_server)
and have it running locally.

This extension is currently limited to returning a Penn parse tree for a
highlighted sentence. Soon, there will be more features, such as counting all
words by part of speech for a webpage. My ultimate goal is to help users study
how other people write!

![Extension demo](https://github.com/dicksontsai/stanford-nlp-local-extension/blob/master/extension-demo.gif)

## Working with Webpack

`yarn run start` autoloads but introduces files that Google Chrome doesn't understand. To load an extension in Chrome:

1. Run `yarn run build`.
1. Go to `chrome://extensions` and click `Load Unpacked`.
1. Select the `build` directory produced by `yarn run build`.

## Acknowledgement

This repo is cloned from [Chrome Extension Webpack Boilerplate](https://github.com/samuelsimoes/chrome-extension-webpack-boilerplate) by
[@samuelsimoes](https://twitter.com/samuelsimoes).
