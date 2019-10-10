package com.github.antoinejt.jasc;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public enum ManifestInfos {
    LAST_UPDATE("Last-Update"),
    VERSION("Version");

    private String value;

    ManifestInfos(String name) {
        Attributes attributes = null;
        try {
            Enumeration<URL> resources = getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                Manifest manifest = new Manifest(resources.nextElement().openStream());
                attributes = manifest.getMainAttributes();
            }
        } catch (IOException ignored) {
        }
        value = attributes != null ? attributes.getValue(name) : null;
    }

    @Override
    public String toString() {
        return value;
    }
}
