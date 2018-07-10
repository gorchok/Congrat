package ru.pochivalin.congrat.services;

public interface EmailService {

    void sendSimpleMessage(final String from, final String to,
                                  final String subject, final String text);

    void sendMessageWithAttach(final String from, final String to,
                               final String subject, final String text,
                               final String filename, final String pathToAttachment);
}
