const responsePre = document.createElement("div");
const containerDiv = document.getElementById("container");
const output = decodeURI(window.location.search.substring(1)).replace(
  /\\n/g,
  "\n"
);
responsePre.innerHTML = output;
containerDiv.appendChild(responsePre);
