package magnit.xml;

import magnit.pojo.Entry;
import magnit.pojo.TargetEntries;
import magnit.Initial.Variables;
import magnit.pojo.Entries;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class XMLTransform {
    /**
     * Marshal Java class to XML
     *
     * @param entriesList - ArrayList<Entry>
     */

    /**
     * Создание файла 1.xml
     */
    public void createFirstXmlFile(ArrayList<Entry> entriesList) {
        try {
            /*
            *класс JAXBContext обеспечивает точку входа клиента для API JAXB.
            * Это обеспечивает абстракцию для того, чтобы она управляла информацией о привязке XML/Java,
            * необходимой, чтобы реализовать JAXB обязательные операции платформы: неупорядочьте,
            * упорядочьте и проверьте.
            * Клиентское приложение обычно получает новые экземпляры этого класса,
            * используя один из этих двух стилей для newInstance методов.
             */
            JAXBContext jaxbContext = JAXBContext.newInstance(Entries.class);
            /*
            Marshaller класс обеспечивает клиентское приложение возможность
            преобразовать дерево контента Java назад в данные XML.
             */
            Marshaller m = jaxbContext.createMarshaller();
            Entries entries = new Entries();
            entries.setEntries(entriesList);
            OutputStream os = new FileOutputStream(Variables.FIRST_XML_FILE);
            m.marshal(entries, os);
        } catch (JAXBException | FileNotFoundException e) {
            System.err.println("не удалось создать файл 1.xml, " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Преобразование с ипользованием "XLST"
     */
    public void transformToSecondXml() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            Source xslt = new StreamSource(new File(Variables.PATTERN_XLST_FILE));
            transformer = transformerFactory.newTransformer(xslt);
            Source source = new StreamSource(new File(Variables.FIRST_XML_FILE));
            transformer.transform(source, new StreamResult(new File(Variables.SECOND_XML_FILE)));
        } catch (TransformerException e) {
            System.err.println("не удалось преобразовать в xml файл, " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Преобразование в java кдасс
     */
    public TargetEntries getEntriesFromSecondXml() {
        TargetEntries entries = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TargetEntries.class);
            //Unmarshaller класс обеспечивает клиентское приложение возможность преобразовать данные XML
            // в дерево объектов контента Java.
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            entries = (TargetEntries) unmarshaller.unmarshal(new File(Variables.SECOND_XML_FILE));
        } catch (JAXBException e) {
            System.err.println("не удалось распокавать во второй xml файл, " + e.getMessage());
            System.exit(0);
        }
        return entries;
    }
}
