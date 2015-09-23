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
import java.util.HashMap;
import java.util.Map;

/**
 * Simple server socket that displays the HTTP request header received from a
 * web browser.
 */
public class Exercice3 {
    public static void main(String[] args) throws IOException {
        System.out.println("Listening on port 8080...");
        try (ServerSocket server = new ServerSocket(8080)) {
            while (true) {
                try (Socket socket = server.accept()) {                  
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    
                    // To store header keys/values
                    Map<String, String> headerMap = new HashMap<>();
                    
                    while (true) {
                        String line = input.readLine();
                        
                        if(line.isEmpty()) {
                            break; // we have read all the header, leave the reading loop
                        }
                        
                        String[] parts = line.split(": ");
                        if(parts.length != 2) {
                            // The line contains no ": " or more than
                            // one, this is weird, skip it!
                            continue;
                        }
                        headerMap.put(parts[0], parts[1]);
                    }
                    
                    StringBuilder response = new StringBuilder();
                    response.append("<dl>");
                    for(Map.Entry<String, String> entry: headerMap.entrySet()) {
                        response.append("<dt>");
                        response.append(entry.getKey());
                        response.append("</dt>");
                        response.append("<dd>");
                        response.append(entry.getValue());
                        response.append("</dd>");
                    }
                    response.append("</dl>");
                    
                    System.out.println(response);
                    
                    // Send HTTP response with HTML
                    out.print("HTTP/1.1 200 OK\r\n");
                    out.print("Content-Type: text/html\r\n");
                    out.print("Content-Length: "+ response.length() +"\r\n");
                    out.print("\r\n");
                    out.print(response);
                    out.flush();
                }
            }
        }
    }
}