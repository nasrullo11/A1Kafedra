package uz.istart.kafedra.admin.form.tables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchFrom implements Serializable {
    private static final long serialVersionUID = -3029620531741939122L;

    private String value;
    private boolean regex;
}
