    // Java Program to Extract Content from a PDF

// Importing java input/output classes
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// Importing Apache POI classes
//import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

    // Class
    public class ReadThePDF {

        // Main driver method
        public static void main(String[] args) throws Exception
        {

            String pathToPDF = args[0];
            String pathForSaveCsv = args[1];

      /*      String pathToPDF = "c:\\tmp\\kart\\Wyciag_z_rachunku_karty_kredytowej_22052022.pdf";
            String pathForSaveCsv = "C:/tmp/fileJ.txt";*/

            BodyContentHandler contenthandler = getBodyPDFContentHandler(pathToPDF);


            printDocumentToFileorConsole(contenthandler,pathForSaveCsv, false);

            extractTransactionsFormCitiPdf(contenthandler,false);


        }

        private static void extractTransactionsFormCitiPdf(BodyContentHandler contenthandler, boolean lineNumbers) {
            String extractPatternString = "\\d+\\.\\d+\\.\\d\\d\\d\\d.*";
            Pattern extractOnlyThisLines = Pattern.compile(extractPatternString);


            int i = 0;
            List<String> list2 = new ArrayList<String>();
            for(String text: contenthandler.toString().split("\\n")) {
                Matcher matcher = extractOnlyThisLines.matcher(text);
                boolean matches = matcher.matches();

                if (matches) {

                    list2.add(text);
                    if (lineNumbers) {
                        if(i>1) {
                        System.out.printf(++i + " " + text + System.lineSeparator()); }
                    } else
                    {  ++i;
                        if(i>1)
                        System.out.printf(Parser.parsLine(text) + System.lineSeparator());
                    }

                }
            }
        }

        private static void printDocumentToFileorConsole(BodyContentHandler contenthandler, String filePath, boolean printall) throws FileNotFoundException {

            if (!filePath.equals("")) {
                File file = new File(filePath) ; //"C:/tmp/fileJ.txt");
                FileOutputStream fos = new FileOutputStream(file);

                // Create new print stream for file.
                PrintStream ps = new PrintStream(fos);

                // Set file print stream.
                System.setOut(ps);
            }
            if (printall)
            System.out.println(contenthandler);
            //new PrintStream(new BufferedOutputStream(new FileOutputStream("file.txt")), true);
        }

        private static BodyContentHandler getBodyPDFContentHandler(String pdfFile) throws IOException, SAXException, TikaException {
            // Create a content handler
            BodyContentHandler contenthandler
                    = new BodyContentHandler();

            // Create a file in local directory
            File f = new File(pdfFile);

            // Create a file input stream
            // on specified path with the created file
            FileInputStream fstream = new FileInputStream(f);

            // Create an object of type Metadata to use
            Metadata data = new Metadata();

            // Create a context parser for the pdf document
            ParseContext context = new ParseContext();

            // PDF document can be parsed using the PDFparser
            // class
            PDFParser pdfparser = new PDFParser();

            // Method parse invoked on PDFParser class
            pdfparser.parse(fstream, contenthandler, data,
                    context);

            // Printing the contents of the pdf document
            // using toString() method in java
        //    System.out.println("Extracting contents :"                    + contenthandler);
            return contenthandler;
        }


 /*       public static String parsLine(String line) {
            String parsed_line = line;
            String[] array = parsed_line.split(" ");
            String newText = "";
            int i = 0;
            for (String txt : array
            ) {
                i++;
                if (i == 1 || i == 2) {
                    newText = newText + txt + ";";
                } else
                    newText = newText + txt + " ";
            }


            return newText.replace("zł","");
        }
*/
    }



