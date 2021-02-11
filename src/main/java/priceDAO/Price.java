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
    private long id; // идентификатор в БД
    private String productCode; // код товара
    private int number; // номер цены
    private int depart; // номер отдела
    private Date begin; // начало действия
    private Date end; // конец действия
    private long value; // значение цены в копейках

    @Override
    public int compareTo(Price price) {
        return this.getBegin().compareTo(price.getBegin());
    }
}
