package com.monkeyzi.mboot.common.core.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNode {
    protected Integer id;
    protected Integer parentId;
    protected boolean hasChild;

    protected List<TreeNode> children = new ArrayList<TreeNode>();

    public void add(TreeNode node) {
        children.add(node);
    }
}
