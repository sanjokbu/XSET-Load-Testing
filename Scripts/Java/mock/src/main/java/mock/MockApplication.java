package mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MockApplication {

//	Необходимо разработать заглушку, обрабатывающие следующие запросы со стороны псевдо-тестируемой системы.
//
//	1. Get localhost:8080/app/v1/getRequest?id={id}&name={name}, где id > 10 и длина name > 5.
//		В случае если какое условие не выполняется вернуть InternalServerError и напечатать причину ошибки
//		Вернуть тело ответа из текстового файла getAnswer.txt и подставив в него поле name
//
//	2.Post localhost:8080/app/v1/postRequest
//		body : {“name”: “{name}”, “surname”: “{surname}”, ”age”:{age}}
//		где {name}, {surname}, {age} должны быть не пустыми, в противном случае вернуть InternalServerError
//		Вернуть ответ из приложенного файла – postAnswer.txt, подставив в него данные из тела запроса
//
//	3. Для Get запроса реализовать в случае id > 10 and id < 50 время задержи = 1000мс,
//		во всех остальных случаях 500мс

	public static void main(String[] args) {
		SpringApplication.run(MockApplication.class, args);
	}

}
