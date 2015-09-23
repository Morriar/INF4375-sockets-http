/*
 * Copyright 2015 Alexandre Terrasa <alexandre@moz-code.org>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sockets2.exercice4;

/**
 * A calculation done by the server.
 */
public class CalculationOperation {
    
    // Operation
    String operation;
    
    // First param
    int a;
    
    // Second param
    int b;
    
    public CalculationOperation(String operation, int a, int b) {
        this.operation = operation;
        this.a = a;
        this.b = b;
    }
    
    // Do the calculation and return a String containing the operation and the result
    public String doCalculation() throws CalculationException {
        // Do calculation
        switch(operation) {
            case "/add":
                return a + " + " + b + " = " + (a + b);
            case "/sub":
                return a + " - " + b + " = " + (a - b);
            case "/mult":
                return a + " * " + b + " = " + (a * b);
            case "/div":
                if(b == 0) {
                    throw new CalculationException(403, "Division by 0");
                }
                return a + " / " + b + " = " + (a / b);
            default:
                throw new CalculationException(403, "Unknown operation " + operation);
        }
    }
}
