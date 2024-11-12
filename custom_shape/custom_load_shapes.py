from locust import LoadTestShape
from config.config import cfg, logger
from utils.non_test_methods import get_load_shapes


class CustomLoadShape(LoadTestShape):

    stages = get_load_shapes()

    # стандартная функция локаста, взятая из документации, для работы с кастомными "Лоад-Шейпами"
    def tick(self): 
        run_time = self.get_run_time()

        for stage in self.stages:
            if run_time < stage["duration"]:
                tick_data = (stage["users"], stage["spawn_rate"])
                return tick_data

        return None