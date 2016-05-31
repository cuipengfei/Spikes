package hello.pgexample.services;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Map;

@MappedSuperclass
public class ExtensionModel {
    @Transient
    private Map<String, String> extensionFields;

    @Column(name = "extensionId")
    private String extensionId;

    public ExtensionModel() {
    }

    public Map<String, String> getExtensionFields() {
        return extensionFields;
    }

    public void setExtensionFields(Map<String, String> extensionFields) {
        this.extensionFields = extensionFields;
    }

    public String getExtensionId() {
        return extensionId;
    }

    public void setExtensionId(String extensionId) {
        this.extensionId = extensionId;
    }
}
