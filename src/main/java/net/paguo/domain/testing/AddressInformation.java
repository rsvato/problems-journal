package net.paguo.domain.testing;

import net.paguo.visual.InterfaceField;
import net.paguo.visual.EditorEnum;
import org.hibernate.validator.NotNull;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * User: sreentenko
 * Date: 20.04.2008
 * Time: 1:19:11
 * ����� ����� �����������
 * (���������, 25 ��������, ����������� �� ��������������� �����:
 * ���������� ����� (��������������, 10 ����., ������ ����);
 * ����� (�� ������ ��� ����������� ������� �����, 15 ����);
 * ��� (3 ����, ������ ����);
 * ����(���)(3 ����, �������������, ������ ����);
 * ��. (��)(4 ����, �������������, ������ ����).
 */
@Embeddable
public class AddressInformation implements Serializable {

    @InterfaceField
    private String city;

    @InterfaceField(editor = EditorEnum.STREET)
    private String street;

    @InterfaceField
    private String houseNo;

    @InterfaceField
    private String corp;

    @InterfaceField
    private String apt;
    private static final long serialVersionUID = 1644003841491916264L;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotNull
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @NotNull
    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getApt() {
        return apt;
    }

    public void setApt(String apt) {
        this.apt = apt;
    }
}
