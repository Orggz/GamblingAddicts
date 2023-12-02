package app;

import api.SportDataIODB;
import interface_adapter.ViewManagerModel;
import schedule.entity.EventFactory;
import schedule.entity.SportEventFactory;
import schedule.service.refresh.RefreshInputBoundary;
import schedule.service.refresh.RefreshInteractor;
import schedule.service.refresh.RefreshOutputBoundary;
import schedule.service.refresh.RefreshScheduleDataAccessInterface;
import schedule.service.refresh.interface_adapter.RefreshController;
import schedule.service.refresh.interface_adapter.RefreshPresenter;
import schedule.service.refresh.interface_adapter.ScheduleViewModel;
import view.ScheduleView;

public class ScheduleUseCaseFactory {

    ScheduleUseCaseFactory() {}

    public static ScheduleView create(ViewManagerModel viewManagerModel, ScheduleViewModel scheduleViewModel, RefreshScheduleDataAccessInterface scheduleDataAccessObject) {

        RefreshController refreshController = createScheduleUseCase(viewManagerModel, scheduleViewModel, scheduleDataAccessObject);
        return new ScheduleView(scheduleViewModel, refreshController);
    }

    private static RefreshController createScheduleUseCase(ViewManagerModel viewManagerModel, ScheduleViewModel scheduleViewModel, RefreshScheduleDataAccessInterface scheduleDataAccessObject) {
        RefreshOutputBoundary refreshOutputBoundary = new RefreshPresenter(scheduleViewModel, viewManagerModel);

        EventFactory eventFactory = new SportEventFactory();

        RefreshInputBoundary refreshInteractor = new RefreshInteractor(new SportDataIODB(), scheduleDataAccessObject, refreshOutputBoundary, eventFactory);

        return new RefreshController(refreshInteractor);
    }
}
