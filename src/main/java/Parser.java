import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;

public class Parser {


    public static void main(String[] args) {
        System.out.println(parsLine("22.04.2022 35,00 złSUNFLOWER R.NAPORA KRAKÓW PL"));

    }

    public static String changeDateFormat(String txt) {
        String changedDateFormat = "wrong date!";
        String [] array = txt.split("\\.");
        if (array.length == 3)
        changedDateFormat = array[2] + "-" + array[1] + "-" + array[0];

        return changedDateFormat;
    }


    public static String parsLine(String line) {
        String parsed_line = line;
        String[] array = parsed_line.split(" ");
        String newText = "";
        int columnNumberSplitedString = 0;
        for (String txt : array
        ) {
            if (columnNumberSplitedString == 0) {
                txt = changeDateFormat(txt);
            }

            columnNumberSplitedString++;
            if (columnNumberSplitedString == 1 || columnNumberSplitedString == 2) {

                newText = newText + txt + ";";
            } else
                newText = newText + txt + " ";
        }


        return newText.replace("zł","").replace(",", ".");
    }

    public void setOutputHandler () throws FileNotFoundException {
        File file = new File("C:/tmp/fileJ.txt");
        FileOutputStream fos = new FileOutputStream(file);

        // Create new print stream for file.
        PrintStream ps = new PrintStream(fos);

        // Set file print stream.
        System.setOut(ps);

        //new PrintStream(new BufferedOutputStream(new FileOutputStream("file.txt")), true);
    }

}
