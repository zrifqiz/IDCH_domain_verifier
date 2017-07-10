/**
 * Created by zcabez on 10/07/2017.
 */
public class Data {
    private String[] data;

    public Data(String[] inlineData){
        data = inlineData;
    }

    public String getDomain(){
        return data[0];
    }

    public String getReseller() {
        return data[10];
    }
}
