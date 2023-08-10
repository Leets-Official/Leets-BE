package land.leets.global.mail.dto;

import lombok.Builder;

@Builder
public record MailDto(String title, String[] to, String body) {
}