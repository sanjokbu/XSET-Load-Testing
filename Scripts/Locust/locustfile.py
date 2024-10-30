from config.config import cfg, logger
from custom_shape.custom_load_shapes import CustomLoadShape

""" 
    Точка входа в тест, 
    описание логики импорта сценариев
"""

if cfg.buy_scenario.included:
    from user_classes.buy_scenario import BuyUserClass
    BuyUserClass.weight = cfg.buy_scenario.weight
    
    logger.info("Imported BuyUserClass")

if cfg.cancel_scenario.included:
    pass