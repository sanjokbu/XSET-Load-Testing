from random import randrange
from datetime import datetime, timedelta
import csv

def get_random_date(): # Метод возвращает рандомную дату отправления и вылета
    
    # Дата отправления
    start = datetime.now()  
    end = start + timedelta(days=120)
    delta = end - start

    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = randrange(int_delta)

    random_date = start + timedelta(seconds=random_second)

    depart_date = datetime.strftime(random_date, '%m/%d/%Y')

    # Дата возвращения
    start = datetime.strptime(depart_date, '%m/%d/%Y')
    end = start + timedelta(days=30)
    delta = end - start

    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = randrange(int_delta)

    random_date = start + timedelta(seconds=random_second)

    return_date = datetime.strftime(random_date, '%m/%d/%Y')

    return [depart_date, return_date]



def open_csv_file(filepath = str):
    with open(filepath, 'r') as file:
        reader = csv.DictReader(file)
        return list(reader)



def get_body (flightID_list): # Создает тело для запроса удаления билета
    
    count = 1
    body: str = '1=on'
    cgifields: str = ""

    for flightID in flightID_list:
        body = body + '&flightID=' + flightID
        cgifields = cgifields + '&.cgifields=' + str(count)
        count += 1

    return body + '&removeFlights.x=45&removeFlights.y=11' + cgifields


    
class UserData:
    count: int = 0

    def get_user_data(user_data_list): 
        
        if UserData.count == len(user_data_list):
            UserData.count = 0

        user_data = user_data_list[UserData.count]
        
        UserData.count += 1

        return user_data


