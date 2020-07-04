import axios from "axios";

import { createParseWindow } from "./background/window";

chrome.contextMenus.create({
  id: "parse",
  title: "Parse highlighted sentence",
  contexts: ["selection"],
  onclick: onParse,
});

function onParse(obj) {
  axios({
    method: "post",
    url: "http://localhost:8080/parser/parse",
    data: "text=" + encodeURIComponent(obj.selectionText),
    headers: {
      "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
    },
  })
    .then(function (response) {
      createParseWindow(response.data);
    })
    .catch(function (error) {
      console.log(error);
    });
}
