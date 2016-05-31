package hello.pgexample.services;

import java.util.Map;

public abstract class ExtensionModel {
    private Map<String, String> extensionFields;

    public Map<String, String> getExtensionFields() {
        return extensionFields;
    }

    public void setExtensionFields(Map<String, String> extensionFields) {
        this.extensionFields = extensionFields;
    }
}
