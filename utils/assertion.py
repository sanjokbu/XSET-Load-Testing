from config.config import cfg, logger


def check_response(response, value) -> bool:
    result = None

    try:
        assert value in response.text

    except AssertionError as err:
        response.failure(f'Assertion error: the response body does not contain "{value}"')
        logger.warning(f'Assertion error: the response body does not contain "{value}"') # Записываем в логи в виде ошибки
        result = False

    else: 
        result = True

    finally: 
        return result

