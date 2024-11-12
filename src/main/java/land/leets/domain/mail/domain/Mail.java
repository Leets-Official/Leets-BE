package land.leets.domain.mail.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mail {

	private final String title;
	private final String[] to;
	private final String body;
}
