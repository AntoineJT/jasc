package com.github.antoinejt.jasc;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public enum ManifestInfos {
    LAST_UPDATE("Last-Update"),
    VERSION("Version");

    private final String value;

    ManifestInfos(String name) {
        Attributes attributes = null;
        try {
            attributes = getAttributesFromManifest();
        } catch (IOException ignored) {
        }
        value = (attributes != null) ? attributes.getValue(name) : null;
    }

    private Attributes getAttributesFromManifest() throws IOException {
        URL resource = getClass().getClassLoader().getResource("META-INF/MANIFEST.MF");
        Manifest manifest = new Manifest(Objects.requireNonNull(resource, "MANIFEST.MF doesn't exists!").openStream());
        return manifest.getMainAttributes();
    }

    @Override
    public String toString() {
        return value;
    }
}
