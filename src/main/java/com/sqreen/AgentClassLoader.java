package com.sqreen;

import org.apache.catalina.loader.ParallelWebappClassLoader;

public class AgentClassLoader extends ParallelWebappClassLoader {

    public AgentClassLoader() {
        super();
    }

    public AgentClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.equals("org.apache.jasper.servlet.JspServlet")) {
            return super.loadClass("com.sqreen.JspServletWrapper", resolve);
        }
        return super.loadClass(name, resolve);
    }
}
