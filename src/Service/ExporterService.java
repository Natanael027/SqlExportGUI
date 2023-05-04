package Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExporterService {

    List<Integer> tsel = Arrays.asList(62811, 62812, 62813, 62821, 62822, 62823, 62851, 62852, 62853);
    List<Integer> isat = Arrays.asList(62814, 62815, 62816, 62855, 62856, 62857, 62858);
    List<Integer> xl = Arrays.asList(62817, 62818, 62819, 62859, 62877, 62878, 62879);
    List<Integer> axis = Arrays.asList(62831, 62832, 62833, 62834, 62835, 62836, 62837, 62838, 62839);

//    String insert = "INSERT INTO `messagemt_operator` (`messageid`, `campaignid`, `userid`, `original`, `sendto`, `messagetype`, `message`, `ston`, `snpi`, `dton`, `dnpi`, `dcs`, `udhl`, `destport`, `origport`, `refnum`, `segseq`, `totseg`, `ispush`, `replyto`, `keyword`, `receivedate`, `sentdate`, `sentstatus`, `delivereddate`, `deliveredstatus`, `status`, `priority`, `startdate`, `enddate`, `oprid`, `fromcp`, `fromchannel`, `tochannel`, `thirdid`, `jenis`, `smsprice`, `prefix_code`, `date_10`, `date_20`, `date_30`, `date_90`) VALUES ";
    String name = "BATCH-0205-";

    public ExporterService() {
    }

    private String table(String q, String operator){
        if (operator == null || operator.isEmpty()) {
            operator = "operator";
        }
        String insert = "";
        switch (q){
            case "CCA" :
                insert = "INSERT INTO `messagemt_tm_cn` (`messageid`, `campaignid`, `userid`, `original`, `sendto`, `messagetype`, `message`, `ston`, `snpi`, `dton`, `dnpi`, `dcs`, `udhl`, `destport`, `origport`, `refnum`, `segseq`, `totseg`, `ispush`, `replyto`, `keyword`, `receivedate`, `sentdate`, `sentstatus`, `delivereddate`, `deliveredstatus`, `status`, `priority`, `startdate`, `enddate`, `oprid`, `fromcp`, `fromchannel`, `tochannel`, `thirdid`, `jenis`, `smsprice`, `prefix_code`, `date_10`, `date_20`, `date_30`, `date_90`) VALUES ";
                break;

            case "CSA" :
                insert = "INSERT INTO `messagemt_"+operator+"` (`messageid`, `campaignid`, `userid`, `original`, `sendto`, `messagetype`, `message`, `ston`, `snpi`, `dton`, `dnpi`, `dcs`, `udhl`, `destport`, `origport`, `refnum`, `segseq`, `totseg`, `ispush`, `replyto`, `keyword`, `receivedate`, `sentdate`, `sentstatus`, `delivereddate`, `deliveredstatus`, `status`, `priority`, `startdate`, `enddate`, `oprid`, `fromcp`, `fromchannel`, `tochannel`, `thirdid`, `jenis`, `smsprice`, `prefix_code`, `date_10`, `date_20`, `date_30`, `date_90`) VALUES ";
                break;

            case "TR" :
                insert = "INSERT INTO `messagemt_cn_tr` (`messageid`, `campaignid`, `userid`, `original`, `sendto`, `messagetype`, `message`, `ston`, `snpi`, `dton`, `dnpi`, `dcs`, `udhl`, `destport`, `origport`, `refnum`, `segseq`, `totseg`, `ispush`, `replyto`, `keyword`, `receivedate`, `sentdate`, `sentstatus`, `delivereddate`, `deliveredstatus`, `status`, `priority`, `startdate`, `enddate`, `oprid`, `fromcp`, `fromchannel`, `tochannel`, `thirdid`, `jenis`, `smsprice`, `prefix_code`, `date_10`, `date_20`, `date_30`, `date_90`) VALUES ";
                break;
        }
        return insert;
    }

    private String prefix(String q){
        String prefix = "";
        switch (q){
            case "CCA" :
                prefix = "operator";
                break;

            case "CSA" :
                prefix = "operator";
                break;

            case "TR" :
                prefix = "ALL";
                break;
        }
        return prefix;
    }

    public void genFileNew(List<String> number, String type) throws IOException {
        HashMap<Integer, String> prefixToNetworkCode = new HashMap<>();
        for (int i = 0; i < isat.size(); i++) {
            prefixToNetworkCode.put(isat.get(i), "isat");
        }

        for (int i = 0; i < xl.size(); i++) {
            prefixToNetworkCode.put(xl.get(i), "xl");
        }

        for (int i = 0; i < tsel.size(); i++) {
            prefixToNetworkCode.put(tsel.get(i), "tsel");
        }

        for (int i = 0; i < axis.size(); i++) {
            prefixToNetworkCode.put(axis.get(i), "axis");
        }

        File fileSql = null;
        FileWriter writer = null;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());


        fileSql = new File( "SqlExport_"+timestamp+".sql");
        writer = new FileWriter(fileSql);

        for (int i = 0; i < number.size(); i++) {
            Random r = new Random();
            int n = r.nextInt();
            String Hexadecimal = Integer.toHexString(n);

            String nol = "('";
            String dua = number.get(i);
            String prefix = prefixToNetworkCode.get(Integer.valueOf(dua.substring(0, 5)));

            String prepare = name + "_" + dua + new Date().getDate() + "-" + prefix + "-" + Hexadecimal;
            String satu = "',\t0,\t'checkNumber',\t'whatsapp',\t'";
            String tiga = "',\t0,\t'Selamat Siang',\t0,\t0,\t0,\t0,\t0,\t0,\t0,\t0,\t0,\t0,\t1,\t1,\tNULL,\tNULL,\tNOW(),\tNULL,\tNULL,\tNULL,\tNULL,\t0,\t5,\tNOW(),\tNOW(),\t0,\t0,\t'',\t'',\tNULL,\t4,\t0,\t'";

            String op = prefixToNetworkCode.get(Integer.valueOf(dua.substring(0, 5)));

            String empat = prefix(type);
            String insert = table(type,op);

            String lima = "',\tNULL,\tNULL,\tNULL,\tNULL)";

            String query = nol + prepare + satu + dua + tiga + empat + lima;
            //String query = "('" + dua + "')";
            System.out.println(query);
            if (i % 2000 == 0) {
                if (i > 0) {
                    writer.append(";");
                    writer.close();
                    fileSql = new File(  name + i + ".sql");
                    writer = new FileWriter(fileSql);
                }
                writer.write(insert);
                writer.write(query);

            } else {
                writer.append(",");
                writer.write(query);
            }
        }
        writer.append(";");
        writer.close();
    }

}
