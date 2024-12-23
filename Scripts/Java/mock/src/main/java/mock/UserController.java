package mock;

import jakarta.annotation.PostConstruct;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping (value = "/app")
public class UserController {

	public String getAnswer;
	public String postAnswer;

	@PostConstruct
	public void init() throws IOException {
		getAnswer = Files.readString(Path.of("getAnswer.txt"));
		postAnswer = Files.readString(Path.of("postAnswer.txt"));
	}

	@GetMapping(value = "/v1/getRequest")
	public ResponseEntity getRequest(@RequestParam Map<String, String> param) throws InterruptedException {

		if (Integer.parseInt(param.get("id")) > 10 && Integer.parseInt(param.get("id")) < 50) {
			Thread.sleep(1000);
		} else {
			Thread.sleep(500);
		}

		if (Integer.parseInt(param.get("id")) <= 10) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("500 INTERNAL SERVER ERROR\nid должен быть больше 10");
		} else if (param.get("name").length() <= 5) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("500 INTERNAL SERVER ERROR\nДлина name должна быть больше 5");
		} else {
			StringSubstitutor sub = new StringSubstitutor(param);
			return ResponseEntity.ok(sub.replace(getAnswer));
		}
	}

//	2.Post localhost:8080/app/v1/postRequest
//		body : {“name”: “{name}”, “surname”: “{surname}”, ”age”:{age}}
//		где {name}, {surname}, {age} должны быть не пустыми, в противном случае вернуть InternalServerError
//		Вернуть ответ из приложенного файла – postAnswer.txt, подставив в него данные из тела запроса


	@PostMapping(value = "/v1/postRequest")
	public ResponseEntity postRequest (@RequestBody Map<String, String> dataPerson) throws InterruptedException {

		if (dataPerson.get("name").isEmpty() || dataPerson.get("surname").isEmpty() || dataPerson.get("age").isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("500 INTERNAL SERVER ERROR");
		}

		String regex = "\\{name\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(postAnswer);

		String string = matcher.replaceAll(dataPerson.get("name"));

		regex = "\\{surname\\}";
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(string);

		string = matcher.replaceAll(dataPerson.get("surname"));

		regex = "\\{age\\}\\s";
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(string);

		string = matcher.replaceAll(dataPerson.get("age")+"\n");

		regex = "\\{age\\}\\*2";
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(string);

		string = matcher.replaceAll(String.valueOf(Integer.parseInt(dataPerson.get("age")) * 2));

		return ResponseEntity.ok(string);


	}

}
