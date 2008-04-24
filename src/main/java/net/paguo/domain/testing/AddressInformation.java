package net.paguo.domain.testing;

import net.paguo.visual.InterfaceField;
import net.paguo.visual.EditorEnum;
import org.hibernate.validator.NotNull;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * User: sreentenko
 * Date: 20.04.2008
 * Time: 1:19:11
 * Адрес точки подключения
 * (текстовое, 25 символов, заполняется во вспомогательной форме:
 * населенный пункт (необязательное, 10 симв., ручной ввод);
 * Улица (из списка без возможности ручного ввода, 15 симв);
 * Дом (3 симв, ручной ввод);
 * Корп(Лит)(3 симв, необязательно, ручной ввод);
 * кв. (оф)(4 симв, необязательно, ручной ввод).
 */
@Embeddable
public class AddressInformation implements Serializable {

    @InterfaceField
    private String city;

    @InterfaceField(editor = EditorEnum.STREET) @NotNull
    private String street;

    @InterfaceField @NotNull
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

    public String toString(){
        StringBuilder result = new StringBuilder();
        if (StringUtils.isNotEmpty(city)){
            result.append(city).append(", ");
        }
        result.append(street).append(", ").
                append(houseNo);
        if (StringUtils.isNotEmpty(corp)){
            result.append("/").append(corp);
        }
        if (StringUtils.isNotEmpty(apt)){
            result.append("-").append(apt);
        }
        return result.toString();
    }
}
