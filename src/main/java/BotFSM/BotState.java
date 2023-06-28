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
    Help,
    HelpMenu,
    WaitCallbackFinance,
    WaitCallbackReport,
    WaitCallbackHelp;

}