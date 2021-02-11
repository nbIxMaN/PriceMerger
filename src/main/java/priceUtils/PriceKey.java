package priceUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class PriceKey {
    private String productCode; // код товара
    private int number; // номер цены
    private int depart; // номер отдела
}
