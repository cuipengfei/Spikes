package org.openlmis;

import java.util.ArrayList;
import java.util.List;

public class Right {

    private RightType rightType;
    private List<Right> attachments = new ArrayList<Right>();

    private Right(RightType rightType) {
        this.rightType = rightType;
    }

    public static Right ofType(RightType rightType) {
        return new Right(rightType);
    }

    public void attach(Right attachment) {
        if (attachment.rightType == rightType) {
            attachments.add(attachment);
        }
    }

    public List<Right> getAttachments() {
        return attachments;
    }
}
