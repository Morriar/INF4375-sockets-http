
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
package sockets2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import sockets2.exercice4.CalculationException;
import sockets2.exercice4.CalculationOperation;
import sockets2.exercice4.CalculationRequest;

/**
 * Simple server socket that displays the HTTP request header received from a
 * web browser.
 */
public class Exercice4 {
    public static void main(String[] args) throws IOException {
        System.out.println("Listening on port 8080...");
        try (ServerSocket server = new ServerSocket(8080)) {
            while (true) {
                try (Socket socket = server.accept()) {                  
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    
                    String line = input.readLine();
                    if(line == null || line.isEmpty()) {
                        continue;
                    }

                    CalculationRequest request = new CalculationRequest(line);
                    
                    CalculationOperation operation;
                    try {
                        operation = request.parseOperation();
                    } catch(CalculationException e) {
                        sendResponse(out, e.getCode(), e.getMessage(), e.getMessage());
                        continue;
                    } catch(NumberFormatException e) {
                        sendResponse(out, 403, "Bad parameters", "Parameters are not integers");
                        continue;
                    }
                    
                    String response;
                    try {
                        response = operation.doCalculation();
                    } catch (CalculationException e) {
                        sendResponse(out, 403, e.getMessage(), e.getMessage());
                        continue;
                    }
                    
                    sendResponse(out, 200, "OK", response);
                }
            }
        }
    }
    
    public static void sendResponse(PrintWriter out, int code, String reason, String body) {
        // Send HTTP response with HTML
        out.print("HTTP/1.1 " + code + " " + reason + "\r\n");
        out.print("Content-Type: text/html\r\n");
        out.print("Content-Length: "+ body.length() +"\r\n");
        out.print("\r\n");
        out.print(body);
        out.flush();
    }
}