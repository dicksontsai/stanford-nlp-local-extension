import React from "react";
import TreeView from "@material-ui/lab/TreeView";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import ChevronRightIcon from "@material-ui/icons/ChevronRight";
import TreeItem from "@material-ui/lab/TreeItem";

export default function Tree(props) {
  let counter = 0;

  const renderTree = (node) => {
    const id = counter++;
    return (
      <TreeItem key={String(id)} nodeId={String(id)} label={node.v}>
        {Array.isArray(node.c)
          ? node.c.map((child) => renderTree(child))
          : null}
      </TreeItem>
    );
  };

  const trees = renderTree(props.tree);

  // Note: This must come after renderTree.
  const allIDs = [];
  for (let i = 0; i < counter; i++) {
    allIDs.push(String(i));
  }

  return (
    <TreeView
      defaultCollapseIcon={<ExpandMoreIcon />}
      defaultExpanded={allIDs}
      defaultExpandIcon={<ChevronRightIcon />}
    >
      {trees}
    </TreeView>
  );
}
