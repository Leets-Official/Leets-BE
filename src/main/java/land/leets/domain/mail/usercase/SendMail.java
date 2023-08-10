package land.leets.domain.mail.usercase;

import land.leets.domain.application.type.Result;

public interface SendMail {
    void execute(String to, String name, Result result);
}