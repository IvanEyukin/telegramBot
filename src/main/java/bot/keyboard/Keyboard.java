package bot.keyboard;

import bot.keyboard.reply.MenuKeyboard;
import bot.keyboard.inline.FinanceKeyboard;
import bot.keyboard.inline.HelpKeyboard;
import bot.keyboard.inline.ReportKeyboard;
import bot.keyboard.inline.SettingKeyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.List;


public class Keyboard {
    public final static MenuKeyboard replyKeyboard = new MenuKeyboard();
    public final static List<InlineKeyboardButton> finance = FinanceKeyboard.FINANCE;
    public final static List<InlineKeyboardButton> report = ReportKeyboard.REPORT;
    public final static List<InlineKeyboardButton> help = HelpKeyboard.HELP;
    public final static List<InlineKeyboardButton> setting = SettingKeyboard.NOTIFICATION;
}