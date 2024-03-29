package bot.message;


public class Help {
    public final static String START = "%s, давай я немного расскажу о себе, выбери, что тебе интересно:";
    public final static String INFO_QUESTION = "Это очень сложный вопрос, уточни, что конкретно тебя интересует?";
    public final static String ERROR = "Извини, но я не понял, что именно ты хочешь узнать?";
    public final static String INFO_BOT = """
            Я - телеграм бот, разработанный на Java.
            В своей основе я использую библиотеку org.telegram.telegrambots v 6.5.0.
            Я использую две базы данных для себя: 

            Redis - для хранения состояний пользователей.

            SQLite - для хранения информации пользователей.
            """;
    public final static String INFO_SAVE = """
            Да, я храню информацию о тебе.
            Не переживай, все что я знаю о тебе, это только общедоступная информация Телеграм.
            Как я храню информацию:

            1. Я храню информацию в своей памяти, чтобы понимать, какими моими возможностями ты пользуешься. 
            Кстати, эта информация удаляется спустя 10 минут, если ты со мной не общаешься. 

            2. Я храню всю информацию, что ты попросил меня записать.
            Эта информация обезличина (привязывается к твоему Телеграм ID).
            Доступ к ней имеет только мой разработчик (Не переживай, ему не интересно сколько денег ты тратишь или зарабатываешь).
            """;
    public final static String SAVE = """
            Я сохраняю данные, когда ты нажимаешь кнопку "Сохранить".
            Если ты ввел информацию, но не нажал конпку "Сохранить", то я ничего не запишу.
            """;
    public final static String DELETE = """
            К сожалению, пока я не умею удалять информацию, которую ты попросил меня записать.
            Возможно, в будущем появится такая возможность, но пока она не доступна.
            """;
    public final static String WRITE = """
            Рекомендую вводить только сумму одним числом, так меньше шансов моей ошибки,
            но я могу выполнять простейшие математические операции, к примеру:
            
            Если ты напишешь в сообщении: 100-50+70-25+19,75
            То я предложу тебе сохранить 114,75.

            Если ты напишешь в сообщении: 100-10*2+300/5
            То я предложу тебе сохранить 140

            Так же я могу складывать числа из нескольких твоих сообщений, если ты не нажмешь на кнопки "Сохранить" или "Удалить", например: 

            Ты: 250
            Я: Мне записать 250?
            Ты: 100
            Я: Мне записать 350?

            Аналогично я могу выполнять и другие математические операции, если ты укажешь их, например: 

            Ты: 250
            Я: Мне записать 250?
            Ты: -100
            Я: Мне записать 150?

            Или:

            Ты: 250
            Я: Мне записать 250?
            Ты: /5
            Я: Мне записать 50?
            Ты: *2+4
            Я: Мне записать 104?

            """;
}