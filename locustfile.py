from config.config import cfg, logger
from custom_shape.custom_load_shapes import CustomLoadShape

""" 
    Точка входа в тест, 
    описание логики импорта сценариев
"""

if cfg.scenario_buy_ticket.included:
    from user_scenario.uc03_buy_ticket import ScenarioBuyTicket
    ScenarioBuyTicket.weight = cfg.scenario_buy_ticket.weight
    
    logger.info("Imported ScenarioBuyTicket")

if cfg.scenario_cancel_ticket.included:
    from user_scenario.uc05_cancel_ticket import ScenarioCancelTicket
    ScenarioCancelTicket.weight = cfg.scenario_cancel_ticket.weight
    
    logger.info("Imported ScenarioCancelTicket")