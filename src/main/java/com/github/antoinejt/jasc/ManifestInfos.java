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

    private static Attributes attributes = null;

    static {
        URL resource = ManifestInfos.class.getClassLoader().getResource("META-INF/MANIFEST.MF");
        try {
            InputStream inputStream = Objects.requireNonNull(resource, "MANIFEST.MF doesn't exists!").openStream();
            Manifest manifest = new Manifest(inputStream);
            ManifestInfos.attributes = manifest.getMainAttributes();
        } catch (IOException ignored) {
        }
    }

    private final String valueName;

    ManifestInfos(String valueName) {
        this.valueName = valueName;
    }

    @Override
    public String toString() {
        return ManifestInfos.attributes.getValue(valueName);
    }
}
