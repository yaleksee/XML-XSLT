package magnit.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;


@XmlRootElement(name = "entries")
public class TargetEntries implements Serializable {

    private ArrayList<TargetEntry> entries;

    public ArrayList<TargetEntry> getEntries() {
        return entries;
    }

    @XmlElement(name = "entry")
    public void setEntries(ArrayList<TargetEntry> entries) {
        this.entries = entries;
    }
}
