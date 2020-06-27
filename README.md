# Stanform NLP Local Extension

A Chrome extension that lets you send sentences to a localhost server running
Stanford NLP tools. See https://github.com/dicksontsai/stanford_parser_server
for the server code.

This repo is cloned from [Chrome Extension Webpack Boilerplate](https://github.com/samuelsimoes/chrome-extension-webpack-boilerplate) by
[@samuelsimoes](https://twitter.com/samuelsimoes).

## Working with Webpack

`yarn run start` autoloads but introduces files that Google Chrome doesn't understand. To load an extension in Chrome:

1. Run `yarn run build`.
1. Go to `chrome://extensions` and click `Load Unpacked`.
1. Select the `build` directory produced by `yarn run build`.
