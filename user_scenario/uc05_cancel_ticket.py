from locust import task, SequentialTaskSet, HttpUser, FastHttpUser, constant_pacing, constant_throughput, events
from config.config import cfg, logger
from utils.assertion import check_response
import sys, re, random
from utils.non_test_methods import open_csv_file, UserData, get_body

# Класс с запросами (содержит основной сценарий)
class ScenarioTaskSet(SequentialTaskSet):

    def on_start(self):
        
        self.user_data = UserData.get_user_data(open_csv_file(cfg.path_user_data))

        # тут получаем логин и пароль из csv файла 

        self.username = self.user_data['username']
        self.password = self.user_data['password']
        logger.info(f'Username: {self.username}')
        logger.info(f'Password: {self.password}')

        @task() # тут получаем user_session
        def uc05_01_getHomePage(self) -> None:
            
            headers_1 = {
                'Sec-Fetch-Mode': 'navigate'
            }

            self.client.get(
                "/WebTours/",
                name = "uc05_01_WebTours/_1", # Имя запроса
                headers = headers_1, # передаем Headers

                allow_redirects = False, # Отключаем редиректы для измерения только основного запроса без учета запроса редиректа
                
                # debug_stream = sys.stderr # дебаггер для просмотра REQUEST и RESPONSE запускать через TERMINAL: locust --headless    
            )


            with self.client.get(
                "/cgi-bin/welcome.pl?signOff=true",
                name = "uc05_01_welcome.pl?signOff_2",

                allow_redirects = False,

                catch_response = True

                # debug_stream = sys.stderr
            ) as response_2:
                check_response(response_2, "A Session ID has been created")


            with self.client.get(
                "/cgi-bin/nav.pl?in=home",
                name = "uc05_01_nav.pl?in=home_3",

                allow_redirects = False,

                catch_response = True

                # debug_stream = sys.stderr
            ) as response_3:
                check_response(response_3, 'name="userSession"')
                self.user_session = re.search(r'name="userSession" value="(.*)"/>', response_3.text).group(1)
                logger.info(f'User session: {self.user_session}')


        @task() 
        def uc05_02_loginAction(self) -> None:

            headers_1 = {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
            
            body_1 = f'userSession={self.user_session}&username={self.username}&password={self.password}&login.x=0&login.y=0&JSFormSubmit=off'
            
            with self.client.post(
                "/cgi-bin/login.pl",
                name = "uc05_02_login.pl_1",
                headers = headers_1,
                data = body_1,

                allow_redirects = False,

                catch_response = True,

                # debug_stream = sys.stderr
            ) as response_1:
                check_response(response_1, "User password was correct")
            

            self.client.get(
                "/cgi-bin/nav.pl?page=menu&in=home",
                name = "uc05_02_nav.pl?page=menu&in=home_2",

                allow_redirects = False,

                # debug_stream = sys.stderr
            ) 


            self.client.get(
                "/cgi-bin/login.pl?intro=true",
                name = "uc05_02_login.pl?intro=true_3",

                allow_redirects = False,

                # debug_stream = sys.stderr
            ) 

        uc05_01_getHomePage(self)
        uc05_02_loginAction(self)


    @task()
    def uc05_03_getFlightsListPage(self) -> None:

        with self.client.get(
            "/cgi-bin/welcome.pl?page=itinerary",
            name = "uc05_03_welcome.pl?page=itinerary_1",

            allow_redirects = False,

            catch_response = True,

            # debug_stream = sys.stderr
        ) as response_1:
            check_response(response_1, 'User wants the intineraries.  Since user has already logged') 


        self.client.get(
            "/cgi-bin/nav.pl?page=menu&in=itinerary",
            name = "uc05_03_nav.pl?page=menu&in=itinerary_2",

            allow_redirects = False,

            # debug_stream = sys.stderr
        ) 


        with self.client.get(
            "/cgi-bin/itinerary.pl",
            name = "uc05_03_itinerary.pl_3",

            allow_redirects = False,

            catch_response = True,

            # debug_stream = sys.stderr
        ) as response_3:
            self.flightID_list = re.findall(r"name=\"flightID\" value=\"(.*)\"  />", response_3.text)
            logger.info(f'Flight ID list: {self.flightID_list}')

    
    @task()
    def uc05_04_cancelTicket(self) -> None:
        
        headers_1 = {
            # 'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7',
            # 'Accept-Encoding': 'gzip, deflate, br, zstd',
            # 'Accept-Language': 'ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7',
            # 'Cache-Control': 'max-age=0',
            # 'Connection': 'keep-alive',
            # 'Content-Length': '236',
            'Content-Type': 'application/x-www-form-urlencoded',
            # 'Sec-Fetch-Dest': 'frame',
            # 'Sec-Fetch-Mode': 'navigate',
            # 'Sec-Fetch-Site': 'same-origin',
            # 'Sec-Fetch-User': '?1',
            # 'Upgrade-Insecure-Requests': '1',
            # 'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36',
            # 'sec-ch-ua': '"Chromium";v="130", "Google Chrome";v="130", "Not?A_Brand";v="99"',
            # 'sec-ch-ua-mobile': '?0',
            # 'sec-ch-ua-platform': '"Windows"'
        }

        body_1 = get_body(self.flightID_list)
        logger.info(f'Body: {body_1}')
        
        with self.client.post(
            "/cgi-bin/itinerary.pl",
            name = "uc05_04_itinerary.pl_1",
            headers = headers_1,
            data = body_1,

            allow_redirects = False,

            catch_response = True,

            # debug_stream = sys.stderr
        ) as response_1:
            check_response(response_1, not f'name="flightID" value="{self.flightID_list[0]}"')
 

    def on_stop(self):
        
        @task()
        def uc05_05_logoutAction(self) -> None:
                
            headers_1 = {
                'Sec-Fetch-Mode': 'navigate'
            }

            self.client.get(
                "/cgi-bin/nav.pl?in=home",
                name = "uc05_05_nav.pl?in=home_1",
                headers = headers_1,

                allow_redirects = False,

                # debug_stream = sys.stderr 
            )


            with self.client.get(
                "/cgi-bin/welcome.pl?signOff=1",
                name = "uc05_05_welcome.pl?signOff=1_2",

                allow_redirects = False,

                catch_response = True,

                # debug_stream = sys.stderr
            ) as response_2:
                check_response(response_2, "A Session ID has been created")


            self.client.get(
                "/WebTours/home.html",
                name = "uc05_05_home.html_3",

                allow_redirects = False,

                catch_response = True,

                # debug_stream = sys.stderr
            )
        
        uc05_05_logoutAction(self)

    


# класс, принимающий в себя основные параметры теста
class ScenarioCancelTicket(HttpUser): 
    wait_time = constant_pacing(cfg.scenario_cancel_ticket.pacing)
    host = cfg.url
    logger.info(f"ScenarioCancelTicket started. Host: {host}")
    tasks = [ScenarioTaskSet]
