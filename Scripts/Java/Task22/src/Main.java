import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) throws IOException {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Введите путь к файлу с текстом который необходимо изменить:");
		String text = Files.readString(Path.of(scanner.nextLine()));

		String vowels = "[АаОоУуЭэЫыЯяЁёЮюЕеИи]";

		System.out.println("Количество гласных в тексте:" + getCountOccurrences(vowels, text));
		text = getNewString(vowels, text, "а");

		String consonants = "[БбВвГгДдЖжЗзЙйКкЛлМмНнПпРрСсТтФфХхЦцЧчШшЩщ]";

		System.out.println("Количество согласных в тексте: " + getCountOccurrences (consonants, text));
		text = getNewString(consonants, text, "м");

		System.out.println("Введите путь куда сохранить файл с измененным текстом:");
		Files.writeString(Path.of(scanner.nextLine()), text);

		scanner.close();
	}

	public static String getNewString (String regex, String text, String symbolReplace) {

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		text = matcher.replaceAll(symbolReplace);

		return text;
	}

	public static int getCountOccurrences (String regex, String text) {

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);

		int count = 0;

		while (matcher.find()) {
			count++;
		}

		return count;
	}
}
