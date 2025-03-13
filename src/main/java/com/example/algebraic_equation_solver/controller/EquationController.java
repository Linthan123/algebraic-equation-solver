package com.example.algebraic_equation_solver.controller;

import com.example.algebraic_equation_solver.model.Equation;
import com.example.algebraic_equation_solver.service.EquationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/equations")
public class EquationController {

    private static final Logger logger = LoggerFactory.getLogger(EquationController.class);
    private final EquationService equationService;

    public EquationController(EquationService equationService) {
        this.equationService = equationService;
    }

    // 🔹 1️⃣ Store an Algebraic Equation
    @PostMapping("/store")
    public ResponseEntity<Map<String, Object>> storeEquation(@RequestBody Map<String, String> request) {
        String equation = request.get("equation");

        if (equation == null || equation.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Equation cannot be empty"));
        }

        try {
            String equationId = equationService.storeEquation(equation);
            return ResponseEntity.ok(Map.of(
                    "message", "Equation stored successfully",
                    "equationId", equationId));
        } catch (IllegalArgumentException e) {
            logger.error("Invalid equation provided: {}", equation, e);
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Failed to store equation: {}", equation, e);
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to store equation"));
        }
    }

    // 🔹 2️⃣ Retrieve All Stored Equations
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEquations() {
        List<Equation> equations = equationService.getAllEquations();

        if (equations.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "No equations found"));
        }

        return ResponseEntity.ok(Map.of("equations", equations));
    }

    // 🔹 3️⃣ Evaluate a Specific Equation
    @PostMapping("/{equationId}/evaluate")
    public ResponseEntity<Map<String, Object>> evaluateEquation(
            @PathVariable("equationId") String equationId, // ✅ Explicitly specifying parameter name
            @RequestBody Map<String, Map<String, Integer>> request) {

        // Get variables from the request body
        Map<String, Integer> variables = request.get("variables");

        // Check if the variables are provided in the request
        if (variables == null || variables.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Variable values are required"));
        }

        // Validate the equation ID and format
        if (!isValidEquationId(equationId)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid equation format"));
        }

        try {
            // Call the service to evaluate the equation
            int result = equationService.evaluateEquation(equationId, variables);
            return ResponseEntity.ok(Map.of(
                    "equationId", equationId,
                    "variables", variables,
                    "result", result));
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid evaluation request for equation {}: {}", equationId, e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Failed to evaluate equation: {}", equationId, e);
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to evaluate equation"));
        }
    }

    // Helper method to validate the equation format (this is an example, adjust
    // based on your requirements)
    private boolean isValidEquationId(String equationId) {
        // Simple regex to validate the equation format, you can expand this as needed
        return equationId.matches("^[a-zA-Z0-9^+=\\-*/()]+$");
    }

}
