package net.paguo.domain.testing;

/**
 * ������ (���������, 8 ����, ��������������� �������������
 * �� ���� ����������� ������ ��������� ������:�������, � ���� �,
 * �� ����, �� �����, � ���� �, �� �����, �����, � ���� �, �������.
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
