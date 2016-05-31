package hello.pgexample.services;

import java.util.Map;

public abstract class ExtendableModel {
    private Map<String, String> extentionFields;

    public Map<String, String> getExtentionFields() {
        return extentionFields;
    }

    public void setExtentionFields(Map<String, String> extentionFields) {
        this.extentionFields = extentionFields;
    }
}
