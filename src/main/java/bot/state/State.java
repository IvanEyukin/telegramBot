package bot.state;


public enum State {
    Start,
    ExpensesMenu,
    IncomeMenu,
    ReportMenu,
    HelpMenu,
    SettingMenu,
    AdminMenu,
// ExpensesMenu and IncomeMenu subState
    WaitingSum,
    WaitingComment,
    WaitCallbackSaveOrDelete,
// ReportMenu subState
    PeriodSelection,
// HelpMenu subState
    InformationRetentionQuestionsSelection,
// SettingMenu subState
    ReminderOptionsSelection,
// AdminMenu subState
    WaitingMessageMailings;
} 