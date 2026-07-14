package com.example.bank.filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class XssRequestWrapper extends HttpServletRequestWrapper {

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return clearXss(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }

        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = clearXss(values[i]);
        }
        return encodedValues;
    }

    private String clearXss(String value) {
        if (value == null) {
            return null;
        }
        
        return value.replaceAll("&", "&amp;")
                    .replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("\\(", "&#40;")
                    .replaceAll("\\)", "&#41;")
                    .replaceAll("'", "&#39;")
                    .replaceAll("\"", "&quot;");
    }
}