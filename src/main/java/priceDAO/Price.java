package priceDAO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Price implements Comparable<Price>{
    long id; // идентификатор в БД
    String productCode; // код товара
    int number; // номер цены
    int depart; // номер отдела
    Date begin; // начало действия
    Date end; // конец действия
    long value; // значение цены в копейках

    @Override
    public int compareTo(Price price) {
        return this.getBegin().compareTo(price.getBegin());
    }
}
