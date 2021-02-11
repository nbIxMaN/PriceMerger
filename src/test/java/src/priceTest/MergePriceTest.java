package src.priceTest;

import priceUtils.PriceMergeUtils;
import org.junit.Assert;
import org.junit.Test;
import priceDAO.Price;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class MergePriceTest {

    @Test
    public void leftCrossingListEmptyTest() {
        List<Price> prices1 = new ArrayList<>();
        List<Price> prices2 = new ArrayList<>();
        prices1.add(new Price(1,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 15, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 31, 23, 59, 59),
                11000));
        prices2.add(new Price(1,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 1, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 17, 23, 59, 59),
                11000));
        List<Price> correctPrices = new ArrayList<>();
        correctPrices.add(new Price(0,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 1, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 31, 23, 59, 59),
                11000));
        Collection<Price> finalPrices = PriceMergeUtils.mergeCrossingPrices(prices1, prices2);
        Assert.assertEquals(correctPrices, finalPrices);
    }

    @Test
    public void rightCrossingListEmptyTest() {
        List<Price> prices1 = new ArrayList<>();
        List<Price> prices2 = new ArrayList<>();
        prices1.add(new Price(1,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 1, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 17, 23, 59, 59),
                11000));
        prices2.add(new Price(1,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 15, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 31, 23, 59, 59),
                11000));
        List<Price> correctPrices = new ArrayList<>();
        correctPrices.add(new Price(0,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 1, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 31, 23, 59, 59),
                11000));
        Collection<Price> finalPrices = PriceMergeUtils.mergeCrossingPrices(prices1, prices2);
        Assert.assertEquals(correctPrices, finalPrices);
    }

    @Test
    public void innerCrossingListEmptyTest() {
        List<Price> prices1 = new ArrayList<>();
        List<Price> prices2 = new ArrayList<>();
        prices1.add(new Price(1,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 1, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 31, 23, 59, 59),
                11000));
        prices2.add(new Price(1,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 15, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 20, 23, 59, 59),
                11000));
        List<Price> correctPrices = new ArrayList<>();
        correctPrices.add(new Price(0,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 1, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 31, 23, 59, 59),
                11000));
        Collection<Price> finalPrices = PriceMergeUtils.mergeCrossingPrices(prices1, prices2);
        Assert.assertEquals(correctPrices, finalPrices);
    }

    @Test
    public void outerCrossingListEmptyTest() {
        List<Price> prices1 = new ArrayList<>();
        List<Price> prices2 = new ArrayList<>();
        prices1.add(new Price(1,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 15, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 20, 23, 59, 59),
                11000));
        prices2.add(new Price(1,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 1, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 31, 23, 59, 59),
                11000));
        List<Price> correctPrices = new ArrayList<>();
        correctPrices.add(new Price(0,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 1, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 31, 23, 59, 59),
                11000));
        Collection<Price> finalPrices = PriceMergeUtils.mergeCrossingPrices(prices1, prices2);
        Assert.assertEquals(correctPrices, finalPrices);
    }

    @Test
    public void doubleCrossingWithFirstListEmptyTest() {
        List<Price> prices1 = new ArrayList<>();
        List<Price> prices2 = new ArrayList<>();
        prices1.add(new Price(1,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 1, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 10, 23, 59, 59),
                11000));
        prices1.add(new Price(1,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 20, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 31, 23, 59, 59),
                11000));
        prices2.add(new Price(1,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 10, 23, 59, 59),
                new Date(113, Calendar.JANUARY, 20, 0, 0, 0),
                11000));
        List<Price> correctPrices = new ArrayList<>();
        correctPrices.add(new Price(0,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 1, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 31, 23, 59, 59),
                11000));
        Collection<Price> finalPrices = PriceMergeUtils.mergeCrossingPrices(prices1, prices2);
        Assert.assertEquals(correctPrices, finalPrices);
    }

    @Test
    public void doubleCrossingWithSecondListEmptyTest() {
        List<Price> prices1 = new ArrayList<>();
        List<Price> prices2 = new ArrayList<>();
        prices1.add(new Price(1,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 10, 23, 59, 59),
                new Date(113, Calendar.JANUARY, 20, 0, 0, 0),
                11000));
        prices2.add(new Price(1,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 1, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 10, 23, 59, 59),
                11000));
        prices2.add(new Price(1,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 20, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 31, 23, 59, 59),
                11000));
        List<Price> correctPrices = new ArrayList<>();
        correctPrices.add(new Price(0,
                "122856",
                1,
                1,
                new Date(113, Calendar.JANUARY, 1, 0, 0, 0),
                new Date(113, Calendar.JANUARY, 31, 23, 59, 59),
                11000));
        Collection<Price> finalPrices = PriceMergeUtils.mergeCrossingPrices(prices1, prices2);
        Assert.assertEquals(correctPrices, finalPrices);
    }
}
