package net.paguo.domain.testing;

/**
 * Статус (текстовое, 8 симв, устанавливается автоматически
 * по мере прохождения этапов обработки заявки:открыта, в план Т,
 * на тест, на оформ, в план П, на подкл, экспл, в план Д, закрыта.
 */
public enum ProcessStage {
    OPENED,
    SCHEDULETESTING,
    BEFORETESTING,
    BEFOREENABLING,
    ENABLINGPLANNED,
    ENABLINGQUEUED,
    WORKING,
    DISABLINGQUEUED,
    CLOSED,
    CLIENTCANCEL,
    TECHCANCEL
}
