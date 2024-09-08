package land.leets.domain.interview.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import land.leets.domain.application.domain.Application;
import land.leets.domain.interview.type.HasInterview;
import land.leets.domain.shared.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "interviews")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Interview extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private Application application;

	@Builder.Default
	@Column(columnDefinition = "char(10)")
	@Enumerated(EnumType.STRING)
	private HasInterview hasInterview = HasInterview.PENDING;

	@Column(nullable = false)
	private LocalDateTime fixedInterviewDate;

	@Column(nullable = false)
	private String place;
}
