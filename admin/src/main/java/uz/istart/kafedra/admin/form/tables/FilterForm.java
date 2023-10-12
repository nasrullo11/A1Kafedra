package uz.istart.kafedra.admin.form.tables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterForm implements Serializable {
    private static final long serialVersionUID = -1183975305038088044L;

    private int draw;
    private int start;
    private int length;
    private List<ColumnsForm> columns;
    private List<OrderForm> order;
    private SearchFrom search;

    private Map<String,String> customDataMap = new HashMap<>();

    public String getSearchTxt(){
        if(search!=null)
            return search.getValue();
        return null;
    }
}
