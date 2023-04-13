package com.example.test_work_4.listener;

import com.example.test_work_4.service.PasteService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PasteListener {
    private PasteService pasteService;
    @RabbitListener(queues = "${queue.name}")
    public void receiveMessage(PasteMessage pasteMessage) {
        // удаляем пасту из базы данных
        pasteService.deletePasteById(pasteMessage.getPasteId());
    }
}

