package priceUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class PriceKey {
    String productCode; // код товара
    int number; // номер цены
    int depart; // номер отдела
}
