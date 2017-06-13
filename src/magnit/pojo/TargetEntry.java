package magnit.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

public class TargetEntry implements Serializable {

    private Integer field;

    public Integer getField() {
        return field;
    }

    @XmlAttribute(name = "field")
    public void setField(Integer field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "field=" + field;
    }

    @Override
    public int hashCode() {
        return getField() != null ? getField().hashCode() : 0;
    }
}
