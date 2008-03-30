package net.paguo.domain.testing;

/**
 * Статус (текстовое, 8 симв, устанавливается автоматически
 * по мере прохождения этапов обработки заявки:открыта, в план Т,
 * на тест, на оформ, в план П, на подкл, экспл, в план Д, закрыта.
 */
public enum ProcessStage {
    OPENED,
    SCHEDULE_TESTING,
    BEFORE_TESTING,
    ENABLING_PLANNED,
    ENABLING_QUEUED,
    WORKING,
    DISABLING_QUEUED,
    CLOSED
}
