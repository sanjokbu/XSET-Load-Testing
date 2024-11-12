from random import randrange
from datetime import datetime, timedelta
import csv
from config.config import cfg, logger


def get_load_shapes():
        
    """
        Здесь описаны типы нагрузки с помощью stages
            
        A simply load test shape class that has different user and spawn_rate at
        different stages.
        Keyword arguments:
            stages -- список dicts, каждый из которых представляет собой следующий этап:
            duration -- Сколько секунд до перехода к следующему этапу
            users -- Общее количество пользователей
            spawn_rate -- Количество пользователей, запускаемых/останавливаемых в секунду
            stop -- A boolean that can stop that test at a specific stage
            stop_at_end -- Can be set to stop once all stages have run.
    """
    stages = list()    
    
    for i in range( 1, cfg.num_steps + 1):
        stages.append(
            {
            "duration": cfg.duration_per_step * i, 
            "users": cfg.num_users_per_step * i, 
            "spawn_rate": cfg.spawn_rate_user_per_step
            }
             )
    
    logger.info(f"Loadshape created type: num of steps = {cfg.num_steps} / duration of steps = {cfg.duration_per_step} / users of steps = {cfg.num_users_per_step} / spawn rate user of steps: cfg.spawn_rate_user_per_step")
        
    return stages

def get_random_date():
    
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



def get_body (flightID_list):
    
    count = 1
    body: str = '1=on'
    cgifields: str = ""
    
    for i in range( 2, randrange(0, len(flightID_list)) ):
        body = body + "&" + str(i) + "=on"

    for flightID in flightID_list:
        body = body + '&flightID=' + flightID
        cgifields = cgifields + '&.cgifields=' + str(count)
        count += 1

    return body + '&removeFlights.x=45&removeFlights.y=11' + cgifields


    
class UserDataUC03:
    count: int = 0

    def get_user_data(user_data_list): 
        
        if UserDataUC03.count == len(user_data_list):
            UserDataUC03.count = 0

        user_data = user_data_list[UserDataUC03.count]
        
        UserDataUC03.count += 1

        return user_data

class UserDataUC05:
    count: int = 0

    def get_user_data(user_data_list): 
        
        if UserDataUC05.count == len(user_data_list):
            UserDataUC05.count = 0

        user_data = user_data_list[UserDataUC05.count]
        
        UserDataUC05.count += 1

        return user_data
    

    

