import React from "react";
import { render } from "react-dom";

import ParseWindow from "./parseWindow/App.jsx";
import "../css/parse.css";

// Parse data coming from the search substring.
const inputStr = decodeURIComponent(window.location.search.substring(1));

try {
  const input = JSON.parse(inputStr);
  render(
    <ParseWindow
      trees={input.tree}
      poscount={input.poscount}
      sentences={input.sentences}
    />,
    window.document.getElementById("parse-root")
  );
} catch (e) {
  alert("Could not parse input received from the server.");
  console.log(e.message);
}
