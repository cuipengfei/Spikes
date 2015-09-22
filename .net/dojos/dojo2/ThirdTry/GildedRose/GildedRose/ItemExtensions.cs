using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace GildedRose
{
    static class ItemExtensions
    {
        public static void DecreaseOneQuality(this Item item)
        {
            item.Quality = item.Quality - 1;
        }

        public static bool IsLegendary(this Item item)
        {
            return item.Name == "Sulfuras, Hand of Ragnaros";
        }

        public static bool IsTimeLimited(this Item item)
        {
            return item.Name == "Backstage passes to a TAFKAL80ETC concert";
        }

        public static bool IsValueAdding(this Item item)
        {
            return item.Name == "Aged Brie";
        }

        public static void DecreaseOneDay(this Item item)
        {
            item.SellIn = item.SellIn - 1;
        }

        public static void IncreaseOneQuality(this Item item)
        {
            item.Quality = item.Quality + 1;
        }
    }
}
