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

import java.util.HashMap;
import java.util.Map;

/**
 * A HTTP request representation used for Exercice4.
 */
public class CalculationRequest {
    
    // HTTP method used (like GET or POST)
    private String method;
    
    // URL requested
    private String resource;
    
    // HTTP protocol
    private String protocol;
    
    // Target service without parameters (like /service)
    private String service;
    
    // Params extracted from `this.resource`
    private Map<String, String>  params = new HashMap<>();

    // Init self from the first line of a HTTP request (like GET /resource HTTP/1.1)
    public CalculationRequest(String line) {
        // Parse request like "GET /resource HTTP/1.1"
        String[] parts = line.split(" ");
        method = parts[0];
        resource = parts[1];
        protocol = parts[2];

        // Parse resource like "/target?params"
        parts = resource.split("\\?");
        service = parts[0];
        if(parts.length > 1) {
            String paramString = parts[1];
            for(String part : paramString.split("&")) {
                parts = part.split("=");
                if(parts.length > 1) {
                    params.put(parts[0], parts[1]);
                } else {
                    params.put(parts[0], "");
                }
            }
        }
        
        for(Map.Entry<String, String> entry : params.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
    
    // Check parameters
    //
    // The request must contain params `a` and `b` where both are integers.
    public CalculationOperation parseOperation() throws CalculationException, NumberFormatException {
        int a = 0;
        if(params.containsKey("a")) {
            a = Integer.parseInt(params.get("a"));
        } else {
            throw new CalculationException(403, "Missing parameter");
        }
        int b = 0;
        if(params.containsKey("b")) {
            b = Integer.parseInt(params.get("b"));
        } else {
            throw new CalculationException(403, "Missing parameter");
        }
        return new CalculationOperation(service, a, b);
    }

    public String getMethod() {
        return method;
    }

    public String getResource() {
        return resource;
    }

    public String getProtocol() {
        return protocol;
    }
    
    public String getService() {
        return service;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
