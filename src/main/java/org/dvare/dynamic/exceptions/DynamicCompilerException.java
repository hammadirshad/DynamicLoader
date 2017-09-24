/*The MIT License (MIT)

Copyright (c) 2016 Muhammad Hammad

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Sogiftware.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.*/


package org.dvare.dynamic.exceptions;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.*;


public class DynamicCompilerException extends Exception {

    private List<Diagnostic<? extends JavaFileObject>> diagnostics;
    private int line;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public DynamicCompilerException(String message, List<Diagnostic<? extends JavaFileObject>> diagnostics) {
        super(message);
        this.diagnostics = diagnostics;
        line = -1;
    }

    public DynamicCompilerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DynamicCompilerException(Throwable cause) {
        super(cause);
    }

    private List<Map<String, Object>> getErrorList() {
        List<Map<String, Object>> list = new ArrayList<>();

        if (diagnostics != null) {
            for (Diagnostic diag : diagnostics) {
                Map<String, Object> diagnostic = new HashMap<>();
                diagnostic.put("kind", diag.getKind());
                diagnostic.put("line", diag.getLineNumber() - line + 1);
                diagnostic.put("message", diag.getMessage(Locale.US));

                list.add(diagnostic);
            }

        }
        return list;
    }


    private String getErrors() {
        StringBuilder errors = new StringBuilder();

        for (Map<String, Object> entry : getErrorList()) {


            for (String key : entry.keySet()) {

                Object value = entry.get(key);
                if (value != null && !value.toString().isEmpty()) {
                    errors.append(key);
                    errors.append(": ");
                    errors.append(value);
                }
                errors.append(" , ");
            }

            errors.append("\n");
        }


        return errors.toString();

    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\n" + getErrors();
    }


}