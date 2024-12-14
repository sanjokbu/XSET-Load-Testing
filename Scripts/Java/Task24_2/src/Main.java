import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

        Map<String , String[]> mapRequest = getMapRequestsFromFile(setRequest);


        System.out.println("\n====================================================\n");

        System.out.println("Готово!");
        long finishTime = System.currentTimeMillis();

        long timer = finishTime - startTime ;

        System.out.println("Время выполнения составило: " + timer / 1000 + " сек");

        System.out.println("\n====================================================");

        for (String s : mapRequest.keySet()){
            String[] arrayData = mapRequest.get(s);
            double rps = Double.parseDouble(arrayData[0]) / 60.0;
            System.out.printf("Время: %s | Запрос: %s | RPM: %s | RPS: %f\n",   arrayData[1], s, arrayData[0], rps);
        }

        System.out.print("====================================================");
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
//		System.out.println(setRequest);

        return setRequest;
    }


    public static Map<String , String[]> getMapRequestsFromFile (Set<String> setRequest) throws IOException {

		System.out.println("Считаем количество каждого запроса в минуту и ищем максимум для каждого запроса!");
		System.out.println("Ищу, пожалуйста ждите...");

        Map<String , String[]> mapRequest = new HashMap<>();

        for (String request : setRequest) {

            BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
            String line;
            String lineNext;

            String nowDateTime = "";

            int maxCount = 0;
            int count = 0;

            String regexDateTime = "....-..-..T..:..:";
            Pattern patternDateTime = Pattern.compile(regexDateTime);
            Matcher matcherDateTime;

            while ((line = reader.readLine()) != null) {

                matcherDateTime = patternDateTime.matcher(line);

                if (matcherDateTime.find()) {
                    nowDateTime = line.substring(matcherDateTime.start(), matcherDateTime.end());
                    break;
                }
            }

            reader.close();

            if (nowDateTime.isEmpty()) {
                System.out.println("не найден заданный формат даты!");
                System.exit(0);
            }

            reader = new BufferedReader(new FileReader(inputFileName));
            line = reader.readLine();

            while (line != null) {
                lineNext = reader.readLine();

                String  regexRequest = nowDateTime + ".*" + request;
                Pattern pattern = Pattern.compile(regexRequest);
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    count++;
                }

                if (lineNext != null) {
                    Pattern pattern2 = Pattern.compile(nowDateTime);
                    Matcher matcher2 = pattern2.matcher(lineNext);

                    if (!matcher2.find()) {

                        if (count > maxCount) {
                            maxCount = count;
                            String[] arrayData = {String.valueOf(count), nowDateTime};
                            mapRequest.put(request, arrayData);
                        }

//						if (count != 0) {
//							System.out.println("Запрос: " + request + " отработал " + count + " раз в " + nowDateTime);
//						}

                        matcherDateTime = patternDateTime.matcher(lineNext);
                        if (matcherDateTime.find()) {
                            nowDateTime = line.substring(matcherDateTime.start(), matcherDateTime.end());
                        }

                        count = 0;
                    }

                } else {

                    if (count > maxCount) {
                        maxCount = count;
                        String[] arrayData = {String.valueOf(count), nowDateTime};
                        mapRequest.put(request, arrayData);
                    }

//					if (count != 0) {
//						System.out.println("Запрос: " + request + " отработал " + count + " раз в " + nowDateTime);
//					}
                }

                line = lineNext;
            }

            reader.close();
        }

        return mapRequest;
    }

}
