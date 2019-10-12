package com.github.antoinejt.jasc;

import java.io.IOException;
import java.io.InputStream;
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

    // TODO Can improve performance by making this static (load MANIFEST.MF just once is better than loading it everytime)
    private Attributes getAttributesFromManifest() throws IOException {
        URL resource = getClass().getClassLoader().getResource("META-INF/MANIFEST.MF");
        InputStream inputStream = Objects.requireNonNull(resource, "MANIFEST.MF doesn't exists!").openStream();
        Manifest manifest = new Manifest(inputStream);

        return manifest.getMainAttributes();
    }

    @Override
    public String toString() {
        return value;
    }
}
