FROM openjdk:20
ARG JAR_FILE=telegramBot.jar  
COPY . ./project/telegrambot
WORKDIR /project/telegrambot
ARG name=Имя вашего бота
ENV name="${name}"
ARG token=Токен вашего бота
ENV token="${token}"
ARG creatorId=Ваш userId в телеграме
ENV creatorId="${creatorId}"
ARG dbPath=Путь до базы данных (Еще не реализовано)
ENV dbPath="${dbPath}"
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"] 