from locust import task, SequentialTaskSet, HttpUser, constant_pacing, events
from config.config import cfg, logger


# класс с запросами (содержит основной сценарий)
class BuyFlightTicket(SequentialTaskSet): 
    @task()
    def uc03_01_getHomePage(self) -> None:
        
        uc03_01_headers_1 = {
            'Sec-Fetch-Mode': 'navigate'
        }

        uc03_01_reaponse_1 = self.client.get(
            "/WebTours/",
            name = "uc03_01_WebTours/-1",
            allow_redirects = False,
            headers = uc03_01_headers_1
        ) 

        print(uc03_01_reaponse_1.status_code)
        print(uc03_01_reaponse_1.text)

# юзер-класс, принимающий в себя основные параметры теста
class BuyUserClass(HttpUser): 
    wait_time = constant_pacing(cfg.pacing)
    host = cfg.url
    logger.info(f"BuyUserClass started. Host: {host}")
    tasks = [BuyFlightTicket]
