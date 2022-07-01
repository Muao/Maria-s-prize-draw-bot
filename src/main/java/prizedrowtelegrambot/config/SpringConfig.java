package prizedrowtelegrambot.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import prizedrowtelegrambot.services.ScheduleAppService;
import prizedrowtelegrambot.services.UserMessageService;
import prizedrowtelegrambot.telegram.Bot;
import prizedrowtelegrambot.telegram.handlers.CallbackQueryHandler;
import prizedrowtelegrambot.telegram.handlers.MessageHandler;


@Configuration
//@EnableScheduling
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SpringConfig {
    TelegramConfig telegramConfig;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(telegramConfig.getWebhookPath()).build();
    }

    @Bean
    public Bot springWebhookBot(SetWebhook setWebhook,
                                MessageHandler messageHandler,
                                CallbackQueryHandler callbackQueryHandler,
                                ScheduleAppService scheduleAppService,
                                UserMessageService userMessageService) {
        Bot bot = new Bot(setWebhook, messageHandler, callbackQueryHandler, scheduleAppService, userMessageService);

        bot.setBotPath(telegramConfig.getWebhookPath());
        bot.setBotUsername(telegramConfig.getBotName());
        bot.setBotToken(telegramConfig.getBotToken());

        return bot;
    }
}
