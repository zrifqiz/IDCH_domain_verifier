import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by zcabez on 10/07/2017.
 */
public class PartialScan {
    private BufferedReader webFileReader;
    private PrintWriter printWriterFail;
    private PrintWriter printWriterSuccess;
    private static URL url;
    private static HttpURLConnection connection;

    private static ArrayList<Data> datas;

    private int start;
    private int end;
    String uniqueID;

    private String readedLine;

    public PartialScan(int start, int end){
        this.start = start;
        this.end = end;
        uniqueID = UUID.randomUUID().toString();
    }

    public void initScan() throws IOException {
        readResource();
        createFileOutput();
        skipLine();

        extractData();
        startScan();
        System.out.println("");
        System.out.println("Scan finished");
    }

    private void startScan() throws IOException {
        int index = start;
        for (Data data : datas) {
            try {
                System.out.print(data.getDomain() + " | ");

                url = new URL("http://" + data.getDomain());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);

                connection.connect();
                System.out.println(connection.getResponseCode());

                printWriterSuccess.println(data.getDomain() + " | "
                        + data.getReseller() + " | "
                        + connection.getResponseCode());

            } catch (UnknownHostException e) {
                System.out.println("Unresolved host");
                printWriterFail.println(data.getDomain() + " | "
                        + data.getReseller() + " | "
                        + "Un resolved host");
            } catch (SocketException e) {
                System.out.println("Socket error");
                printWriterFail.println(data.getDomain() + " | "
                        + data.getReseller() + " | "
                        + "Socket error");
            }

            System.out.println( "----Scanning at index " + index + " of " + end + "----");
            index++;
        }

        if (printWriterFail != null) {
            printWriterFail.close();
        }
        if (printWriterSuccess != null) {
            printWriterSuccess.close();
        }
    }

    private void extractData() throws IOException {
        readedLine = webFileReader.readLine();
        int toScan = end - start + 1;
        datas = new ArrayList<>();

        while (readedLine != null && toScan > 0) {
            datas.add(new Data(readedLine.split(",")));
            readedLine = webFileReader.readLine();
            toScan--;
        }
    }

    private void skipLine() throws IOException {
        webFileReader.readLine();

        int skipper = start;
        while (skipper > 1 && webFileReader.readLine() != null){
            skipper--;
        }
    }

    private void readResource() throws FileNotFoundException {
        webFileReader = new BufferedReader(new FileReader("webFile.txt"));
    }

    private void createFileOutput() throws FileNotFoundException, UnsupportedEncodingException {
        printWriterFail = new PrintWriter(uniqueID + "-error." + start +
                "-" + end + ".txt", "UTF-8");
        printWriterSuccess = new PrintWriter(uniqueID + "-success." + start +
                "-" + end + ".txt", "UTF-8");
    }
}
