import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by zcabez on 10/07/2017.
 */
public class FullScan {
    private BufferedReader webFileReader;
    private PrintWriter printWriterFail;
    private PrintWriter printWriterSuccess;
    private static URL url;
    private static HttpURLConnection connection;

    private static ArrayList<Data> datas;

    private String readedLine;

    public void initScan() throws IOException {
        readResource();
        createFileOutput();
        skipALine();

        extractData();
        startScan();

        System.out.println("");
        System.out.println("Scan finished");
    }

    private void startScan() throws IOException {
        int index = 1;
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
                printWriterFail.print(data.getDomain() + " | "
                        + data.getReseller() + " | "
                        + "Socket error");
            }

            System.out.println( index + " / " + datas.size());
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
        datas = new ArrayList<>();

        while (readedLine != null) {
            datas.add(new Data(readedLine.split(",")));
            readedLine = webFileReader.readLine();
        }
    }

    private void skipALine() throws IOException {
        webFileReader.readLine();
    }

    private void readResource() throws FileNotFoundException {
        webFileReader = new BufferedReader(new FileReader("webFile.txt"));
    }

    private void createFileOutput() throws FileNotFoundException, UnsupportedEncodingException {
        printWriterFail = new PrintWriter("full-error.1-selesai.txt", "UTF-8");
        printWriterSuccess = new PrintWriter("full-success.1-selesai.txt", "UTF-8");
    }
}
