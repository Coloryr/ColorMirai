package coloryr.colormirai.plugin.mirai_http_api.obj.contact;

import net.mamoe.mirai.contact.*;

public class ComplexSubjectDTO extends ContactDTO {
    public String kind;

    public ComplexSubjectDTO(Contact contact) {
        this.id = contact.getId();
        if (contact instanceof Stranger) {
            this.kind = "Friend";
        } else if (contact instanceof Friend) {
            this.kind = "Friend";
        } else if (contact instanceof Group) {
            this.kind = "Group";
        } else if (contact instanceof OtherClient) {
            this.kind = "Friend";
        } else {
            this.kind = "Contact type " + contact.getClass().getSimpleName() + " not supported";
        }
    }
}
