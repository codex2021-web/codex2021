package cloud.thoughtspotstaging.champagne.testing;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadFile {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = null;
        try {
            /*File file = new File("/Users/srikanth.jandhyala/Downloads/PublicAPI/PublicEndpoints.txt");
            inputStream = new FileInputStream(file);
            System.out.println(readFromInputStream(inputStream));*/

            /*String campeignFile = "/Users/srikanth.jandhyala/Downloads/PublicAPI/app_ib_dev.txt";
            String restRefFile = "/Users/srikanth.jandhyala/Downloads/PublicAPI/RESTAPIReference.txt";
            String publicRefFile = "/Users/srikanth.jandhyala/Downloads/PublicAPI/PublicAPIReference.txt";
            compareChampeignWithRestApiRef(campeignFile, restRefFile, publicRefFile);*/

            String restApiPart4File = "/Users/srikanth.jandhyala/Downloads/PublicAPI/RestPart4Items.txt";
            compareChampeignWithRestApiRef(restApiPart4File);
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static String readFromInputStream(InputStream inputStream)
            throws IOException {


        StringBuilder resultStringBuilder = new StringBuilder();
        String trace = "";
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean flg = false;
            boolean flg1 = false;
            while ((line = br.readLine()) != null) {

                line = line.replaceAll("\\s+", " ").replace("*", "").replace("\u00a0"," ");
                if(line.trim().equals("") || line.trim().contains("Show/Hide")
                        || line.trim().contains("List Operations") || line.trim().contains("Expand Operations")) continue;

                if( line.trim().contains(":") ){
                    trace = line.trim().split(":")[1];
                    System.out.print(trace+",");
                    flg1 = true;
                    continue;
                }
                if( line.trim().contains("GET") || line.trim().contains("POST") || line.trim().contains("PUT") || line.trim().contains("DELETE") ){

                    String s[] = line.trim().split(" ");
                    if( flg1 ){
                        System.out.print( s[0] + "," + s[1]);
                    }else {
                        System.out.print(trace +","+ s[0] + "," + s[1]);
                    }
                    flg = true;
                    continue;
                }
                if(flg){
                    System.out.print(","+line);

                    flg1 = false;
                    flg=false;
                    //System.out.println();
                    continue;
                }

                resultStringBuilder.append(line).append("\n");
            }
        }catch (Exception e){
            System.out.println(trace);
            e.printStackTrace();
        }
        return resultStringBuilder.toString();
    }

    static void compareChampeignWithRestApiRef(String campeignFile, String restRefFile, String publicRefFile)
            throws IOException {
        List<String> campeignList;
        try (Stream<String> lines = Files.lines(Paths.get(campeignFile))) {
            campeignList = lines.collect(Collectors.toList());
        }

        List<String> restRefList;
        try (Stream<String> lines = Files.lines(Paths.get(restRefFile))) {
            restRefList = lines.collect(Collectors.toList());
        }

        List<String> publicRefList;
        try (Stream<String> lines = Files.lines(Paths.get(publicRefFile))) {
            publicRefList = lines.collect(Collectors.toList());
        }

        for( String str : campeignList ){
            if (str.trim().equals("")) {
                System.out.println();
                continue;
            }
            String tokens[] = str.trim().replaceAll("\\s+", " ").split(" ");
            String campeignStr = tokens[1]+" "+tokens[0];
            boolean flg = false;
            /*for( String restStr : restRefList ){
                if( campeignStr.equals(restStr.trim().replaceAll("\\s+", " ")) ){
                    System.out.println("Yes");
                    flg = true;
                    break;
                }
            }*/
            for( String restStr : publicRefList ){
                if( campeignStr.equals(restStr.trim().replaceAll("\\s+", " ")) ){
                    System.out.println("Yes");
                    flg = true;
                    break;
                }
            }
            if( !flg ) System.out.println("No");
        }
    }


    static void compareChampeignWithRestApiRef(String restApiPart4File )
            throws IOException {
        List<String> restApiPart4List;
        try (Stream<String> lines = Files.lines(Paths.get(restApiPart4File))) {
            restApiPart4List = lines.collect(Collectors.toList());
        }

        for( String str : restApiPart4List ){
            if (str.contains("/")  && !str.contains("Resolved")) {
                String api = str.substring(str.indexOf("/"));
                System.out.println(api.substring(api.indexOf("/"), api.indexOf(" ")));
            }
        }
    }
}
