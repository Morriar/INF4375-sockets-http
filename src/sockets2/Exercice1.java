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
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple server socket that displays the HTTP request header received from a
 * web browser.
 */
public class Exercice1 {
    public static void main(String[] args) throws IOException {
        System.out.println("Listening on port 8080...");
        try (ServerSocket server = new ServerSocket(8080)) {
            while (true) {
                try (Socket socket = server.accept()) {                  
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                        
                    while (true) {
                        // Read the HTTP request header until the blank line
                        // delimitating the body.
                        String line = input.readLine();
                        if(line.isEmpty()) {
                            return;
                        }
                        System.out.println(line);
                    }
                }
            }
        }
    }
}
