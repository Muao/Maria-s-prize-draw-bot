package prizedrowtelegrambot.services;

import lombok.Getter;
import org.springframework.stereotype.Service;
import prizedrowtelegrambot.enums.ScheduledStopAction;

@Service
public class ScheduleAppService {
    @Getter private ScheduledStopAction scheduledStopAction = ScheduledStopAction.NONE;

//    @Scheduled(cron = "${cron.stop.taking.donates.date}", zone = "${cron.zone}")
    public void setStopTakingDonates() {
        scheduledStopAction = ScheduledStopAction.STOP_GETTING_DONATES;
    }

//    @Scheduled(cron = "${cron.stop.draw.date}", zone = "${cron.zone}")
    public void setStopDraw() {
        scheduledStopAction = ScheduledStopAction.STOP_DRAW;
    }
}
