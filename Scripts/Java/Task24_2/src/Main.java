import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static String inputFileName;

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите путь к файлу:");
        inputFileName = scanner.nextLine();
//        inputFileName = "production_log.csv";
        scanner.close();

        long startTime = System.currentTimeMillis();

        String regexRequest = "/api/[^/\\s\"?]*";

        Set<String> setRequest = getSetRequestsFromFile(regexRequest);

        LocalDateTime startDateTime = LocalDateTime.of(2024,6,18, 18,25,0);
        LocalDateTime endDateTime =  LocalDateTime.of(2024,6,18, 21,31,0);

        Map<String , Integer[]> mapRequest = getMapRequestsFromFile(setRequest, startDateTime, endDateTime);


        System.out.println("\n====================================================\n");

        System.out.println("Готово!");
        long finishTime = System.currentTimeMillis();

        long timer = finishTime - startTime ;
        System.out.println("Время выполнения составило: " + timer/1000 + " cек");

        System.out.println("\n====================================================\n");

        for (String s : mapRequest.keySet()){
            Integer[] arrayData = mapRequest.get(s);
            double rps = Double.parseDouble(String.valueOf(arrayData[0])) / 60.0;
            System.out.printf("Время: %s:%s | Запрос: %s | RPS: %f\n",   arrayData[1], arrayData[2], s, rps);
        }
    }


    public static Set<String> getSetRequestsFromFile (String regexRequest) throws IOException {

        System.out.println("Начали поиск уникальных запросов!");

        Set<String> setRequest = new HashSet<>();

        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        String line;

        while ((line = reader.readLine()) != null) {

            Pattern pattern = Pattern.compile(regexRequest);
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                String request = line.substring(matcher.start(), matcher.end());
                setRequest.add(request);
//                System.out.println(request);
            }

        }

        reader.close();

        System.out.println("Все уникальные запросы найдены!");
        System.out.println(setRequest);

        return setRequest;
    }


    public static Map<String , Integer[]> getMapRequestsFromFile (Set<String> setRequest, LocalDateTime startDateTime, LocalDateTime endDateTime) throws IOException {

        System.out.println("Считаем количество каждого запроса в минуту и ищем максимум для каждого запроса!");
        System.out.print("Ищу, пожалуйста ждите");

        Map<String , Integer[]> mapRequest = new HashMap<>();

        for (String request : setRequest) {


            int maxCount = 0;

            for (LocalDateTime nowDateTime = startDateTime; nowDateTime.isBefore(endDateTime); nowDateTime = nowDateTime.plusMinutes(1)){

                int count = 0;
                LocalDateTime nextDateTime = nowDateTime.plusMinutes(1);

                BufferedReader reader = new BufferedReader(new FileReader(inputFileName));

                String line;

                while ((line = reader.readLine()) != null) {

                    String  regexRequest = nowDateTime + ".*" + request;
                    Pattern pattern = Pattern.compile(regexRequest);
                    Matcher matcher = pattern.matcher(line);

                    if (matcher.find()) {
                        count++;
                    }

                    String  regexRequest2 = nextDateTime + ".*";
                    Pattern pattern2 = Pattern.compile(regexRequest2);
                    Matcher matcher2 = pattern2.matcher(line);

                    if (matcher2.find()) {
                        break;
                    }
                }

                if (count > maxCount){
                    maxCount = count;
                    Integer[] arrayData = {count, nowDateTime.getHour(), nowDateTime.getMinute()};
                    mapRequest.put(request, arrayData);
                }

                if (count != 0){
                    System.out.println("Запрос: "  + request + " отработал " +  count + " раз в " + nowDateTime.getHour() + ":" + nowDateTime.getMinute());
                }

                reader.close();
            }

        }

        return mapRequest;
    }

}
