package magnit;

import magnit.Initial.DataBaseVariables;
import magnit.Initial.Variables;
import magnit.xml.XMLResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Solution {
    public static void main(String[] args) throws ClassNotFoundException {
        createWorkDir();
        DataBaseVariables dataBaseVariables = new DataBaseVariables();
        dataBaseVariables.enterData();
        XMLResult mainService = new XMLResult();
        mainService.execute();
    }

    private static void createWorkDir() {
        Path workDir = Paths.get(Variables.WORK_DIR);

        if (Files.notExists(workDir)) {
            try {
                Files.createDirectory(workDir);
                File file = new File(Variables.PATTERN_XLST_FILE);
                Files.write(Paths.get(file.toURI()), Variables.PATTERN_XLST_TO_XML.getBytes("utf-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                System.err.println("не удалось создать папаку для хранения результата");
            }
        }
    }

}
