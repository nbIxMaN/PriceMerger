package priceUtils;

import priceDAO.Price;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

public abstract class PriceMergeUtils {

    /**
     * Merge prices from two collections
     * Second collection prices have priority
     * @param prices1
     * @param prices2
     * @return List with merged prices sorted by begin date
     */
    public static Collection<Price> mergeCrossingPrices(Collection<Price> prices1, Collection<Price> prices2) {
        Map<PriceKey, List<Price>> priceMap = initPricesMap(prices1);
        mergeOldPricesWithNewPrices(priceMap, prices2);
        List<Price> mergedPrices = new ArrayList<>();
        priceMap.values().forEach(mergedPrices::addAll);
        Collections.sort(mergedPrices);
        return mergedPrices;
    }

    /**
     * Init map with prices
     * map keys {@link PriceKey}
     * map values list wit uncrossing prices
     * @param prices
     * @return
     */
    private static Map<PriceKey, List<Price>> initPricesMap(Collection<Price> prices) {
        Map<PriceKey, List<Price>> priceMap = new HashMap<PriceKey, List<Price>>();
        prices.forEach(price -> {
            PriceKey priceKey = new PriceKey(price.getProductCode(), price.getNumber(), price.getDepart());
            if (priceMap.containsKey(priceKey)) {
                priceMap.get(priceKey).add(clonePrice(price));
            } else {
                List<Price> priceList = new ArrayList<>();
                priceList.add(clonePrice(price));
                priceMap.put(priceKey, priceList);
            }
        });
        return priceMap;
    }

    /**
     * Merge prices from map with values from second prices list
     * @param priceMap
     * @param prices
     */
    private static void mergeOldPricesWithNewPrices(Map<PriceKey, List<Price>> priceMap, Collection<Price> prices) {
        prices.forEach(price -> {
            PriceKey priceKey = new PriceKey(price.getProductCode(), price.getNumber(), price.getDepart());
            if (priceMap.containsKey(priceKey)) {
                priceMap.put(priceKey, mergeCrossingPrices(priceMap.get(priceKey), clonePrice(price)));
            } else {
                List<Price> priceList = new ArrayList<>();
                priceList.add(price);
                priceMap.put(priceKey, priceList);
            }
        });
    }

    /**
     * Merge values from from price list with price for same {@link PriceKey} with second list
     * @param prices
     * @param price
     * @return
     */
    private static List<Price> mergeCrossingPrices(List<Price> prices, Price price) {
        List<Price> newPrices = new ArrayList<>();
        prices.stream().filter(savedPrice -> !getCrossingType(savedPrice, price).equals(CrossingType.ABSENT)).
                forEach(savedPrice -> {
                    newPrices.addAll(getPricesWithNewTimes(savedPrice, price, getCrossingType(savedPrice, price)));
                });
        List<Price> newPrices2 = getFullNewPricesList(newPrices, price);
        newPrices2.addAll(
                prices.stream().filter(savedPrice -> getCrossingType(savedPrice, price).equals(CrossingType.ABSENT)).
                        collect(Collectors.toList()));
        return newPrices2;
    }

    /**
     * Unite crossing prices with same value
     * @param prices
     * @param price
     * @return
     */
    private static List<Price> getFullNewPricesList(Collection<Price> prices, Price price) {
        List<Price> mergedPrices = new ArrayList<>();
        for (Price p : prices) {
            if (p.getValue() == price.getValue()) {
                price = mergePricesWithEqualsValue(p, price);
            } else {
                mergedPrices.add(p);
            }
        }
        mergedPrices.add(price);
        return mergedPrices;
    }

    private static Price mergePricesWithEqualsValue(Price price1, Price price2) {
        return clonePrice(price2,
                    new Date(Math.min(price1.getBegin().getTime(), price2.getBegin().getTime())),
                    new Date(Math.max(price1.getEnd().getTime(), price2.getEnd().getTime())));
    }

    private static List<Price> getPricesWithNewTimes(Price price1, Price price2, CrossingType crossingType) {
        List<Price> newPrices = new ArrayList<Price>();
        switch (crossingType) {
            case INNER:
                newPrices.add(clonePrice(price1, price1.getBegin(), price2.getBegin()));
                newPrices.add(clonePrice(price1, price2.getEnd(), price1.getEnd()));
                break;
            case OUTER:
                break;
            case LEFT:
                newPrices.add(clonePrice(price1, price2.getEnd(), price1.getEnd()));
                break;
            case RIGHT:
                newPrices.add(clonePrice(price1, price1.getBegin(), price2.getBegin()));
                break;
        }
        return newPrices;
    }

    private static CrossingType getCrossingType(Price price1, Price price2) {
        if (beforeOrEquals(price1.getBegin(), price2.getBegin()) && afterOrEquals(price1.getEnd(), price2.getEnd())) {
            return CrossingType.INNER;
        }
        if (afterOrEquals(price1.getBegin(), price2.getBegin()) && beforeOrEquals(price1.getEnd(), price2.getEnd())) {
            return CrossingType.OUTER;
        }
        if (afterOrEquals(price1.getBegin(), price2.getBegin()) && beforeOrEquals(price1.getBegin(), price2.getEnd())) {
            return CrossingType.LEFT;
        }
        if (afterOrEquals(price1.getEnd(), price2.getBegin()) && beforeOrEquals(price1.getEnd(), price2.getEnd())) {
            return CrossingType.RIGHT;
        }
        return CrossingType.ABSENT;
    }

    private static Price clonePrice(Price price) {
        return clonePrice(price, price.getBegin(), price.getEnd());
    }

    private static Price clonePrice(Price price, Date dateBegin, Date dateEnd) {
        return Price.builder().
                productCode(price.getProductCode()).
                number(price.getNumber()).
                depart(price.getDepart()).
                begin(dateBegin).
                end(dateEnd).
                value(price.getValue()).
                build();
    }

    private static boolean beforeOrEquals(Date date1, Date date2){
        return date1.before(date2) || date1.equals(date2);
    }

    private static boolean afterOrEquals(Date date1, Date date2){
        return date1.after(date2) || date1.equals(date2);
    }
}
