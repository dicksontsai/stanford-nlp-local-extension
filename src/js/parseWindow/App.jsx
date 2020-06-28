import React from "react";

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
    .map(entry => [entry[0], countsToOrderedList(entry[1])])
    .sort((a, b) => {
      return a[0].localeCompare(b[0]);
    });
  return (
    <table>
      <thead>
        <tr>
          <th>Part of Speech</th>
          <th>Word counts</th>
        </tr>
      </thead>
      <tbody>
        {sorted.map(entry => (
          <tr key={entry[0]}>
            <td>{entry[0]}</td>
            <td>
              <pre>{entry[1].join("\n")}</pre>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

function TreesTable(props) {
  return (
    <table>
      <tbody>
        {props.trees.map((tree, i) => {
          return (
            <tr key={`tree-${i}`}>
              <td>
                <div className="tree">{tree.replace(/\\n/g, "\n")}</div>
              </td>
            </tr>
          );
        })}
      </tbody>
    </table>
  );
}

// trees is an array of strings. Each string is a printed tree.
// poscount is a JSON object mapping pos to word counts.
export default function ParseWindow(props) {
  const { trees, poscount } = props;
  return (
    <div>
      <PosTable poscount={poscount} />
      <TreesTable trees={trees} />
    </div>
  );
}
