Телеграм Бот для учета финансов. 
Используется: SQLite, Redis

Перед запуской установите Redis командой: 
docker pull redis/redis-stack

После установки и запуска контейнера с Redis пропишите необходимые атрибуты в DockerFile
Затем соберите докер образ проекта командой:
docker build -t telegram .  

Для запуска докер образов необходимо сделать следующие действия: 
1. Создать сеть Docker:
  docker create network telegram
2. Запустить Redis в докер сети:
  docker run --name RedisProd --network telegram -d redis
3. Запустить TelegramBot в докер сети:
  docker run --name TelegramBotProd --network telegram -d telegram
