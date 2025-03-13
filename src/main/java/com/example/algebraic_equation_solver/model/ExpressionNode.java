package com.example.algebraic_equation_solver.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor // ✅ Default constructor for empty node
@AllArgsConstructor // ✅ Constructor to accept value and child nodes
public class ExpressionNode {
    private String value;
    private ExpressionNode left;
    private ExpressionNode right;

    // Constructor
    public ExpressionNode(String value, ExpressionNode left, ExpressionNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    // Getter methods
    public String getValue() {
        return value;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getRight() {
        return right;
    }
}
