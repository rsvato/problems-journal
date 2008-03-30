package net.paguo.domain.testing;

/**
 * ������ (���������, 8 ����, ��������������� �������������
 * �� ���� ����������� ������ ��������� ������:�������, � ���� �,
 * �� ����, �� �����, � ���� �, �� �����, �����, � ���� �, �������.
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
