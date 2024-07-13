package land.leets.global.mail.dto;

public record MailDto(
	String title,
	String[] to,
	String body
) {
}
