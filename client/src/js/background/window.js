export function createParseWindow(output, customOptions) {
  const options = {
    type: "popup",
    url: "parse.html?" + encodeURIComponent(JSON.stringify(output)),
  };
  chrome.windows.create(options, (win) => {
    chrome.windows.update(win.id, { focused: true, ...customOptions });
  });
}
