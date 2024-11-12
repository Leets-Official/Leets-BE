package land.leets.domain.mail.domain;

public record Mail(
	String title,
	String[] to,
	String body
) {
}
