package com.example.algebraic_equation_solver.service;

import com.example.algebraic_equation_solver.model.ExpressionNode;

import java.util.*;

public class ExpressionTree {
    public static String toPostfix(String infix) {
        Stack<Character> stack = new Stack<>();
        StringBuilder postfix = new StringBuilder();
        Map<Character, Integer> precedence = Map.of(
                '+', 1, '-', 1, '*', 2, '/', 2, '^', 3);

        for (char ch : infix.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                postfix.append(ch);
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedence.getOrDefault(stack.peek(), 0) >= precedence.getOrDefault(ch, 0)) {
                    postfix.append(stack.pop());
                }
                stack.push(ch);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    public static ExpressionNode buildTree(String postfix) {
        Stack<ExpressionNode> stack = new Stack<>();

        for (char ch : postfix.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                stack.push(new ExpressionNode(String.valueOf(ch), null, null));
            } else {
                ExpressionNode right = stack.pop();
                ExpressionNode left = stack.pop();
                stack.push(new ExpressionNode(String.valueOf(ch), left, right));
            }
        }
        return stack.pop();
    }

    public static int evaluate(ExpressionNode root, Map<Character, Integer> values) {
        if (root.getLeft() == null && root.getRight() == null) {
            return values.getOrDefault(root.getValue().charAt(0), 0);
        }

        int leftVal = evaluate(root.getLeft(), values);
        int rightVal = evaluate(root.getRight(), values);

        return switch (root.getValue()) {
            case "+" -> leftVal + rightVal;
            case "-" -> leftVal - rightVal;
            case "*" -> leftVal * rightVal;
            case "/" -> leftVal / rightVal;
            default -> 0;
        };
    }
}
