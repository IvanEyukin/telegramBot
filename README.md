<h1 align="center">Телеграм Бот для учета финансов</h1>
<h3 align="center">Описание</h3>

Используется следующие технологии:
1. Java 20
2. org.telegram.telegrambots - сторонняя библиотека для telegram написсанная на Java
3. SQLite - легковесная БД для хранение отчетной информации.
4. Redis - хэш БД для хранения сотояний бота при общении с пользователями.

<h3 align="center">Deploy</h3>

Перед деплоем установите Redis командой: 
`docker pull redis/redis-stack`

После установки и запуска контейнера с Redis пропишите необходимые атрибуты в DockerFile
Затем соберите докер образ проекта командой:
`docker build -t telegram . `
 
Для запуска докер образов необходимо сделать следующие действия: 
1. Создать сеть Docker:
  `docker network create telegram`
2. Запустить Redis в докер сети:
   `docker run --name redis--network telegram -d redis`
3. Запустить TelegramBot в докер сети:
   `docker run --name telegramBot --network telegram -d telegram`

<h3 align="center">Минимальные системные требования</h3>

1. Процессор: 1 ядро.
2. Оперативная память: 512 MiB
3. Размер диска: 3 GiB
