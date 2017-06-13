package magnit.Initial;

import java.io.File;

public class Variables {
    public static final int PACKAGE_SIZE = 10000;
    public static final int POOL_SIZE = 50;
    public static final String WORK_DIR = System.getProperty("user.dir") + File.separator + "magnit";
    public static final String FIELD = "field";
    public static final String FIRST_XML_FILE = WORK_DIR + File.separator + "1.xml";
    public static final String SECOND_XML_FILE = WORK_DIR + File.separator + "2.xml";
    public static final String PATTERN_XLST_TO_XML = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"entries\"><entries><xsl:apply-templates/></entries></xsl:template><xsl:template match=\"entry\"><entry field=\"{field}\"/></xsl:template></xsl:stylesheet>";
    public static final String PATTERN_XLST_FILE = WORK_DIR + File.separator + "transfer.xlst";
}
