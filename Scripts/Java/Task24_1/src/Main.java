import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) throws IOException {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Введите путь к файлу:");
		String text = Files.readString(Path.of(scanner.nextLine()));

		Set<String> words = new HashSet<>();

		String regex = "[A-z']+[^,.\\s]";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			words.add(text.substring(matcher.start(), matcher.end()).toLowerCase());
		}

		List<String> wordsList = new ArrayList<>(words);
		Collections.sort(wordsList);

		System.out.println("Список слов отсортированных по порядку:");
		System.out.println(wordsList);

		Map<String, Integer> wordsMap = new HashMap<>();
		int maxCount = 0;

		for (String word : wordsList){
			int count = 0;

			regex = "\\b" + word + "\\b[,.\\s]";
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(text.toLowerCase());


			while (matcher.find()) {
				count += 1;
			}

			wordsMap.put(word, count);
			System.out.println("Слово \"" + word + "\" встречается в тексте " + count + " раз(а)");

			if (count > maxCount) {
				maxCount = count;
			}
		}

		System.out.println("\nСлова, встречающиеся максимальное число раз:");

		for (String key : wordsMap.keySet()){
			if (wordsMap.get(key) == maxCount) {
				System.out.println("Слово: " + key + " - " + maxCount + " раз(а)");
			}
		}
		
	}
}