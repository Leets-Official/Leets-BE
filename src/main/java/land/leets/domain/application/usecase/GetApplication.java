package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;

import java.util.List;

public interface GetApplication {
    List<Application> execute(String position);
}
