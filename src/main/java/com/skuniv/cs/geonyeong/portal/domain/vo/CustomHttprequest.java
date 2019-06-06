package com.skuniv.cs.geonyeong.portal.domain.vo;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CustomHttprequest extends HttpServletRequestWrapper {

    private final Map<String, String> header;

    public CustomHttprequest(HttpServletRequest request) {
        super(request);
        this.header = new HashMap<String, String>();
    }

    public void putHeader(String name, String value) {
        this.header.put(name, value);
    }

    public String getHeader(String name) {
        String headerValue = header.get(name);

        if (headerValue != null) {
            return headerValue;
        }
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    public Enumeration<String> getHeaderNames() {
        Set<String> set = new HashSet<String>(header.keySet());

        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            String n = e.nextElement();
            set.add(n);
        }
        return Collections.enumeration(set);
    }
}
