import React from "react";

// props: {
//   sentences: Array<sentence>;
// }

export default function Lengths(props) {
  const data = props.sentences.map((s) => [
    s.sentence.substring(0, 20),
    s.length,
  ]);
  data.sort((x, y) => x[1] - y[1]);
  return (
    <div>
      <h1>Sentence Lengths</h1>
      <table>
        <thead>
          <tr>
            <th>Sentence</th>
            <th>Length</th>
          </tr>
        </thead>
        <tbody>
          {data.map((d, i) => (
            <tr key={`${i}`}>
              <td>{d[0]}</td>
              <td>{d[1]}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
