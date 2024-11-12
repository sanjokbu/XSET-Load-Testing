from locust import task, SequentialTaskSet, HttpUser, FastHttpUser, constant_pacing, constant_throughput, events
from config.config import cfg, logger
from utils.assertion import check_response
import sys, re, random
from utils.non_test_methods import open_csv_file, UserDataUC03, get_random_date

# Класс с запросами (содержит основной сценарий)
class ScenarioTaskSet(SequentialTaskSet):

    def on_start(self):
        
        self.user_data = UserDataUC03.get_user_data(open_csv_file(cfg.path_user_data))

        # тут получаем логин и пароль из csv файла 

        self.username = self.user_data['username']
        self.password = self.user_data['password']
        logger.info(f'Username: {self.username}')
        logger.info(f'Password: {self.password}')
        logger.info(f'User {self.username} used to buy ticket')

        @task() # тут получаем user_session
        def uc03_01_getHomePage(self) -> None:
            
            headers_1 = {
                'Sec-Fetch-Mode': 'navigate'
            }

            self.client.get(
                "/WebTours/",
                name = "uc03_01_WebTours/_1", # Имя запроса
                headers = headers_1, # передаем Headers

                allow_redirects = False, # Отключаем редиректы для измерения только основного запроса без учета запроса редиректа
                
                # debug_stream = sys.stderr # дебаггер для просмотра REQUEST и RESPONSE запускать через TERMINAL: locust --headless    
            )


            with self.client.get(
                "/cgi-bin/welcome.pl?signOff=true",
                name = "uc03_01_welcome.pl?signOff_2",

                allow_redirects = False,

                catch_response = True

                # debug_stream = sys.stderr
            ) as response_2:
                check_response(response_2, "A Session ID has been created")


            with self.client.get(
                "/cgi-bin/nav.pl?in=home",
                name = "uc03_01_nav.pl?in=home_3",

                allow_redirects = False,

                catch_response = True

                # debug_stream = sys.stderr
            ) as response_3:
                check_response(response_3, 'name="userSession"')
                self.user_session = re.search(r'name="userSession" value="(.*)"/>', response_3.text).group(1)
                logger.info(f'User session: {self.user_session}')


        @task() 
        def uc03_02_loginAction(self) -> None:

            headers_1 = {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
            
            body_1 = f'userSession={self.user_session}&username={self.username}&password={self.password}&login.x=0&login.y=0&JSFormSubmit=off'
            
            with self.client.post(
                "/cgi-bin/login.pl",
                name = "uc03_02_login.pl_1",
                headers = headers_1,
                data = body_1,

                allow_redirects = False,

                catch_response = True,

                # debug_stream = sys.stderr
            ) as response_1:
                check_response(response_1, "User password was correct")
            

            self.client.get(
                "/cgi-bin/nav.pl?page=menu&in=home",
                name = "uc03_02_nav.pl?page=menu&in=home_2",

                allow_redirects = False,

                # debug_stream = sys.stderr
            ) 

            self.client.get(
                "/cgi-bin/login.pl?intro=true",
                name = "uc03_02_login.pl?intro=true_3",

                allow_redirects = False,

                # debug_stream = sys.stderr
            ) 

        uc03_01_getHomePage(self)
        uc03_02_loginAction(self)


    @task() # тут получаем city_list, seatPerf_list, seatType_list, depart, arrive, seatPref, seatType
    def uc03_03_findFlightPage(self) -> None:

        with self.client.get(
            "/cgi-bin/welcome.pl?page=search",
            name = "uc03_03_welcome.pl?page=search_1",

            allow_redirects = False,

            catch_response = True,

            # debug_stream = sys.stderr
        ) as response_1:
            check_response(response_1, 'User has returned to the search page.') 

        self.client.get(
            "/cgi-bin/nav.pl?page=menu&in=flights",
            name = "uc03_03_nav.pl?page=menu&in=flights_2",

            allow_redirects = False,

            # debug_stream = sys.stderr
        ) 

        with self.client.get(
            "/cgi-bin/reservations.pl?page=welcome",
            name = "uc03_03_reservations.pl?page=welcome_3",

            allow_redirects = False,

            catch_response = True,

            # debug_stream = sys.stderr
        ) as response_3:
            check_response(response_3, 'align="left">Departure City :</td> <td><select name="depart"')

            city_list = re.findall(r">(.*)</option>", response_3.text)
            seatPerf_list = re.findall(r"name=\"seatPref\" value=\"([a-zA-Z]+)\"", response_3.text)
            seatType_list = re.findall(r"name=\"seatType\" value=\"([a-zA-Z]+)\"", response_3.text)

            self.depart = random.choice(city_list).replace(' ', '+')
            self.arrive = random.choice(city_list).replace(' ', '+')
            self.seatPref = random.choice(seatPerf_list)
            self.seatType = random.choice(seatType_list)

            logger.info(f'City of departure: {self.depart}')
            logger.info(f'City of arrival: {self.arrive}')
            logger.info(f'Seat pref: {self.seatPref}')
            logger.info(f'Seat type: {self.seatType}')


    @task() # тут получаем departDate, returnDate, outboundFlight
    def uc03_04_inputDepartureData(self) -> None:

        random_date = get_random_date()

        departDate = random_date[0].replace('/', '%2F')
        returnDate = random_date[1].replace('/', '%2F')

        headers_1 = {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
        
        body_1 = f'advanceDiscount=0&depart={self.depart}&departDate={departDate}&arrive={self.arrive}&returnDate={returnDate}&numPassengers=1&seatPref={self.seatPref}&seatType={self.seatType}&findFlights.x=43&findFlights.y=12&.cgifields=roundtrip&.cgifields=seatType&.cgifields=seatPref'
        
        with self.client.post(
            "/cgi-bin/reservations.pl",
            name = "uc03_04_reservations.pl_1",
            headers = headers_1,
            data = body_1,

            allow_redirects = False,

            catch_response = True,

            # debug_stream = sys.stderr
        ) as response_1:
            check_response(response_1, f'Flight departing from <B>{self.depart.replace('+', ' ')}</B> to <B>{self.arrive.replace('+', ' ')}</B>')
            
            outbound_flight = re.search(r'name="outboundFlight" value="(.*)" checked="checked"', response_1.text).group(1)
            logger.info(f'Outbound flight: {outbound_flight}')

            self.outboundFlight = outbound_flight.translate(outbound_flight.maketrans({';': '%3B', '/': '%2F'}))

            
    @task()
    def uc03_05_selectionsFlight(self) -> None:

        headers_1 = {
            'Content-Type': 'application/x-www-form-urlencoded'
        }

        body_1 = f'outboundFlight={self.outboundFlight}&numPassengers=1&advanceDiscount=0&seatType={self.seatType}&seatPref={self.seatPref}&reserveFlights.x=35&reserveFlights.y=2'
        
        with self.client.post(
            "/cgi-bin/reservations.pl",
            name = "uc03_05_reservations.pl_1",
            headers = headers_1,
            data = body_1,

            allow_redirects = False,

            catch_response = True,

            # debug_stream = sys.stderr
        ) as response_1:
            check_response(response_1, 'Payment Details')
    

    @task() # тут получаем firstName, lastName, pass1, address1, address2, creditCard, expDate из csv файла
    def uc03_06_inputPaymentData(self) -> None:

        headers_1 = {
            'Content-Type': 'application/x-www-form-urlencoded'
        }

        firstName = self.user_data['firstName']
        lastName = self.user_data['lastName']
        pass1 = firstName + '+' + lastName

        address1 = self.user_data['address1']
        address2 = self.user_data['address2']
        
        creditCard = self.user_data['creditCard']
        expDate = self.user_data['expDate']

        body_1 = f'firstName={firstName}&lastName={lastName}&address1={address1}&address2={address2}&pass1={pass1}&creditCard={creditCard}&expDate={expDate}&oldCCOption=&numPassengers=1&seatType={self.seatType}&seatPref={self.seatPref}&outboundFlight={self.outboundFlight}&advanceDiscount=0&returnFlight=&JSFormSubmit=off&buyFlights.x=66&buyFlights.y=4&.cgifields=saveCC'
        
        with self.client.post(
            "/cgi-bin/reservations.pl",
            name = "uc03_06_reservations.pl_1",
            headers = headers_1,
            data = body_1,

            allow_redirects = False,

            catch_response = True,

            # debug_stream = sys.stderr
        ) as response_1:
            check_response(response_1, 'Thank you for booking through Web Tours')
            
            logger.info(f'User {self.username} bought a ticket')


    def on_stop(self):
        
        @task()
        def uc03_07_logoutAction(self) -> None:
                
            headers_1 = {
                'Sec-Fetch-Mode': 'navigate'
            }

            self.client.get(
                "/cgi-bin/nav.pl?in=home",
                name = "uc03_07_nav.pl?in=home_1",
                headers = headers_1,

                allow_redirects = False,

                # debug_stream = sys.stderr 
            )


            with self.client.get(
                "/cgi-bin/welcome.pl?signOff=1",
                name = "uc03_07_welcome.pl?signOff=1_2",

                allow_redirects = False,

                catch_response = True,

                # debug_stream = sys.stderr
            ) as response_2:
                check_response(response_2, "A Session ID has been created")


            self.client.get(
                "/WebTours/home.html",
                name = "uc03_07_home.html_3",

                allow_redirects = False,

                catch_response = True,

                # debug_stream = sys.stderr
            )
        
        uc03_07_logoutAction(self)



# класс, принимающий в себя основные параметры теста
class ScenarioBuyTicket(HttpUser): 
    wait_time = constant_pacing(cfg.scenario_buy_ticket.pacing)
    host = cfg.url
    logger.info(f"ScenarioBuyTicket started. Host: {host}")
    tasks = [ScenarioTaskSet]
