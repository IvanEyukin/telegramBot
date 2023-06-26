FROM openjdk:20
ARG JAR_FILE=telegramBot.jar  
COPY . ./project/telegrambot
WORKDIR /project/telegrambot
ARG name=Имя вашего бота
ENV name="${name}"
ARG token=Токен вашего бота
ENV token="${token}"
ARG creatorId=Ваш UserId телеграм
ENV creatorId="${creatorId}"
ARG dbReportPath=Путь до отчетной базы
ENV dbReportPath="${dbReportPath}"
ARG dbSessionHost=Хост базы Redis
ENV dbSessionHost="${dbSessionHost}"
ARG dbSessionPort=Порт базы Redis
ENV dbSessionPort="${dbSessionPort}"
ARG sessionTimeToLive=Время жизни сессий в базе Redis
ENV sessionTimeToLive="${sessionTimeToLive}"
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"] 