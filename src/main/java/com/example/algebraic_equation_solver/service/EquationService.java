package com.example.algebraic_equation_solver.service;

import com.example.algebraic_equation_solver.model.Equation;
import org.apache.commons.jexl3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EquationService {
    private static final Logger logger = LoggerFactory.getLogger(EquationService.class);
    private final Map<String, Equation> equationStore = new HashMap<>();

    // Store equation and return an ID
    public String storeEquation(String equation) {
        String equationId = UUID.randomUUID().toString();
        equationStore.put(equationId, new Equation(equation));
        logger.info("Stored equation with ID: {}", equationId); // Log the stored equation ID
        return equationId;
    }

    // Retrieve all equations
    public List<Equation> getAllEquations() {
        return new ArrayList<>(equationStore.values());
    }

    // Evaluate a specific equation
    public int evaluateEquation(String equationId, Map<String, Integer> variables) {
        // Log the equationId being used to fetch the equation
        logger.info("Evaluating equation with ID: {}", equationId);

        // Retrieve the equation from the store using the equationId
        Equation equation = equationStore.get(equationId);

        // If the equation is not found, throw an exception with a meaningful message
        if (equation == null) {
            logger.error("Equation with ID '{}' not found", equationId); // Log the error when equation is not found
            throw new IllegalArgumentException("Equation not found");
        }

        // Proceed with evaluating the equation if it's found
        return evaluateExpression(equation.getEquation(), variables);
    }

    // Expression evaluation logic using JEXL
    private int evaluateExpression(String equation, Map<String, Integer> variables) {
        try {
            // Log the equation being evaluated for debugging purposes
            logger.info("Evaluating equation: {}", equation);

            // Creating a JEXL engine to evaluate the equation
            JexlEngine jexl = new JexlBuilder().create();
            JexlExpression jexlExpression = jexl.createExpression(equation);
            JexlContext context = new MapContext();

            // Adding the variable values to the context for evaluation
            for (Map.Entry<String, Integer> entry : variables.entrySet()) {
                context.set(entry.getKey(), entry.getValue());
            }

            // Evaluating the equation and returning the result as an integer
            Object result = jexlExpression.evaluate(context);
            return result instanceof Number ? ((Number) result).intValue() : 0;
        } catch (Exception e) {
            // Log the error for debugging purposes
            logger.error("Error evaluating equation: {}", equation, e);

            // If an error occurs during evaluation, throw an exception with a specific
            // message
            throw new IllegalArgumentException("Invalid equation format: " + e.getMessage(), e);
        }
    }
}
