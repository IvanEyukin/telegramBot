package BotFSM;


public enum BotState {

    Start,
    mainMenu,
    ExpensesMenu,
    IncomeMenu,
    ReportMenu,
    EnterTypeReport,
    EnterComment,
    EnterSum,
    WaitCallbackFinance,
    WaitCallbackReport,
    Help;

}