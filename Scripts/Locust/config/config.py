import logging
from pathlib import Path
from pydantic_settings import BaseSettings
from pydantic import BaseModel, Field



"""
    Родительский конфиг для конфигурации сценариев (юзер-классов)
    На основе данного класса описываются параметры сценариев
"""
class ScenarioConfig(BaseModel):
    included: bool
    weight: int



""" 
    Здесь описаны классы конфигурации сценариев 
"""
class BuyScenarioConfig(ScenarioConfig):
    ...


class CancelScenarioConfig(ScenarioConfig):    
    ...



"""
    Данный класс является основным классом конфига, 
    в него должны быть переданы все описанные классы конфига
"""
class Config (BaseSettings): 
    locust_locustfile: str = Field("./locustfile.py", env = "LOCUST_LOCUSTFILE")
    url: str = Field('http://localhost:1080', evn = "URL")
    pacing: int = Field(1, evn = "PACING")
    loadshape_type: str = Field('baseline', evn = "LOADSHAPE_TYPE")

    buy_scenario: BuyScenarioConfig
    cancel_scenario: CancelScenarioConfig

 

"""
    Класс LogConfig описывает логгер, с помощью которого имеется возможность логировать любые события
    в произвольный .log-файл (в данном случе это будет test_logs.log)
"""
class LogConfig():
    logger = logging.getLogger('demo_logger')
    logger.setLevel('DEBUG')
    file = logging.FileHandler(filename = 'test_logs.log')
    file.setFormatter(logging.Formatter('%(asctime)s %(levelname)s: %(message)s'))
    logger.addHandler(file)
    logger.propagate = False



"""
    указываем файл env_file, из которого будут взяты переменные, в том случае, 
    если система не нашла данные параметры в Переменных среды системы
"""
env_file = Path(__file__).resolve().parent.parent / ".env"

# инициализация конфига
cfg = Config(_env_file = (env_file if env_file.exists() else None), _env_nested_delimiter = "__") 

# инициализация логгера
logger = LogConfig().logger 