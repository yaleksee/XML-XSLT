package magnit.xml;

import magnit.pojo.Entry;
import magnit.pojo.TargetEntries;
import magnit.pojo.TargetEntry;

import java.util.ArrayList;

public class XMLResult {
    private DAOresImpl daoService;
    private XMLTransform xmlService;

    public XMLResult() {
        this.daoService = new DAOresImpl();
        this.xmlService = new XMLTransform();
    }

    public void execute() {
        daoService.updateFieldsInDatabase();
        ArrayList<Entry> entries = daoService.selectFieldsFromDatabase();
        xmlService.createFirstXmlFile(entries);
        xmlService.transformToSecondXml();
        TargetEntries targetEntries = xmlService.getEntriesFromSecondXml();
        long sumFields = 0;
        if (targetEntries != null) {
            for (TargetEntry entry : targetEntries.getEntries()) {
                sumFields += entry.getField();
            }
        }
        System.out.println("Сумма данных из таблицы test для введенного числа N составила = " + sumFields);
    }
}
