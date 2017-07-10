import java.io.*;
import java.net.*;

/**
 * Created by zcabez on 7/10/17.
 */
public class Main {
    static BufferedReader webFileReader;
    static String line;
    static URL url;
    static HttpURLConnection connection;
    static PrintWriter printWriterFail;
    static PrintWriter printWriterSuccess;
    static String websiteDomain;
    static String reseller;

    static int startingPoint = 1;
    static String endPoint = "selesai";
    static int skipper = 1;

    public static void main(String[] args) {


        try {
            webFileReader = new BufferedReader(new FileReader("webFile.txt"));
            printWriterFail = new PrintWriter("unresolvedDNS." + startingPoint +
                    "-" + endPoint + ".txt", "UTF-8");

            printWriterSuccess = new PrintWriter("success." + startingPoint +
                    "-" + endPoint + ".txt", "UTF-8");
            line = webFileReader.readLine();
            line = webFileReader.readLine();
        } catch (UnknownHostException e) {
            System.out.print("Un resolved host");
        } catch (FileNotFoundException e) {
            System.out.println("error reading file");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (skipper < startingPoint){
            try {
                line = webFileReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            skipper++;
        }


        while (line != null) {

            try {
                String[] array = line.split(",");
                websiteDomain = array[0];
                reseller = array[10];
                System.out.print(websiteDomain + " ");

                url = new URL("http://" + array[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(10000);

                connection.connect();
                System.out.println(connection.getResponseCode() + "");

                printWriterSuccess.append(websiteDomain + " | " + reseller + " | " + connection.getResponseCode() +
                        "\n");

            } catch (UnknownHostException e) {
                System.out.println("Un resolved host");
                printWriterFail.append(websiteDomain + " | " + reseller + " | " + "Un resolved host\n");
            } catch (FileNotFoundException e) {
                System.out.println("error reading file");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                line = webFileReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            startingPoint++;
        }

        if (printWriterFail != null) {
            printWriterFail.close();
        }
        if (printWriterSuccess != null) {
            printWriterSuccess.close();
        }


//        try {
//            URL url = new URL("http://sndksjdfasdbfasdf.com/");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//
//            connection.connect();
//            System.out.print(connection.getResponseCode() + "");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (UnknownHostException e) {
//            System.out.print("Un resolved host");
//            e.printStackTrace();
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
