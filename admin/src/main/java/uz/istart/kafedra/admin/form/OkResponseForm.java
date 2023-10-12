package uz.istart.kafedra.admin.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OkResponseForm implements Serializable {
    private boolean ok = true;

    public static OkResponseForm build() {
        return new OkResponseForm();
    }

    public static OkResponseForm ok(boolean ok) {
        OkResponseForm responseForm = build();
        responseForm.setOk(ok);
        return responseForm;
    }
}
