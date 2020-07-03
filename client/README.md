# Stanford NLP Local Extension Client Code

## Building the Extension

To load an extension in Chrome:

1. [Install yarn](https://classic.yarnpkg.com/en/docs/install).
1. Run `yarn install` (if you haven't already) to download dependencies.
1. Run `yarn run build`.
1. Go to `chrome://extensions` and click `Load Unpacked`.
   Note: You may need to enable `Developer mode` first.
1. Select the `build` directory produced by `yarn run build`.

NOTE: `yarn run start` autoloads but introduces files that Google Chrome doesn't understand. I'd avoid using it.
