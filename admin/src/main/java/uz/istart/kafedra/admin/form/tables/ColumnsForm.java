package uz.istart.kafedra.admin.form.tables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnsForm implements Serializable {
    private static final long serialVersionUID = 3979421031085816979L;

    private String data;
    private String name;
    private boolean searchable;
    private boolean orderable;
    private SearchFrom search;
}
