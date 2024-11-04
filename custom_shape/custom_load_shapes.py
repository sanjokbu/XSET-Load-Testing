from locust import LoadTestShape
from config.config import cfg, logger


class CustomLoadShape(LoadTestShape):
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

    match cfg.loadshape_type:
        case "baseline":
            stages = [ {"duration": cfg.duration_per_step, "users": cfg.num_users_per_step, "spawn_rate": cfg.spawn_rate_user_per_step} ]
            logger.info("Used loadshape type: baseline")
        case "fixedload":
            stages = [ {"duration": cfg.duration_per_step, "users": cfg.num_users_per_step, "spawn_rate": cfg.spawn_rate_user_per_step} ]
            logger.info("Used loadshape type: fixedload")
        case "stages":
            stages = [ 
                {"duration": cfg.duration_per_step, "users": cfg.num_users_per_step, "spawn_rate": cfg.spawn_rate_user_per_step},
                {"duration": cfg.duration_per_step * 2, "users": cfg.num_users_per_step * 2, "spawn_rate": cfg.spawn_rate_user_per_step},
                {"duration": cfg.duration_per_step * 3, "users": cfg.num_users_per_step * 3, "spawn_rate": cfg.spawn_rate_user_per_step},
                {"duration": cfg.duration_per_step * 4, "users": cfg.num_users_per_step * 4, "spawn_rate": cfg.spawn_rate_user_per_step},
                {"duration": cfg.duration_per_step * 5, "users": cfg.num_users_per_step * 5, "spawn_rate": cfg.spawn_rate_user_per_step},
                {"duration": cfg.duration_per_step * 6, "users": cfg.num_users_per_step, "spawn_rate": cfg.spawn_rate_user_per_step}
                ]
            logger.info("Used loadshape type: stages")


    # стандартная функция локаста, взятая из документации, для работы с кастомными "Лоад-Шейпами"
    def tick(self): 
        run_time = self.get_run_time()

        for stage in self.stages:
            if run_time < stage["duration"]:
                tick_data = (stage["users"], stage["spawn_rate"])
                return tick_data

        return None