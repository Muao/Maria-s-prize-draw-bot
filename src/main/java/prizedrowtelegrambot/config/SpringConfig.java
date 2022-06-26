package prizedrowtelegrambot.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import prizedrowtelegrambot.telegram.Bot;
import prizedrowtelegrambot.telegram.handlers.CallbackQueryHandler;
import prizedrowtelegrambot.telegram.handlers.MessageHandler;


@Configuration
@AllArgsConstructor
public class SpringConfig {
    private final TelegramConfig telegramConfig;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(telegramConfig.getWebhookPath()).build();
    }

    @Bean
    public Bot springWebhookBot(SetWebhook setWebhook,
                                MessageHandler messageHandler,
                                CallbackQueryHandler callbackQueryHandler) {
        Bot bot = new Bot(setWebhook, messageHandler, callbackQueryHandler);

        bot.setBotPath(telegramConfig.getWebhookPath());
        bot.setBotUsername(telegramConfig.getBotName());
        bot.setBotToken(telegramConfig.getBotToken());

        return bot;
    }
}
