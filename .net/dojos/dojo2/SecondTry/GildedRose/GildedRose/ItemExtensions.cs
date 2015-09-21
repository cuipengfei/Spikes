using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace GildedRose
{
    static class ItemExtensions
    {
        public static void DecreaseQualityByOne(this Item item)
        {
            item.Quality = item.Quality - 1;
        }

        public static void IncreaseQualityByOne(this Item item)
        {
            item.Quality = item.Quality + 1;
        }

        public static void DecreaseDate(this Item item)
        {
            item.SellIn = item.SellIn - 1;
        }

        public static bool IsNeverChangeItem(this Item item)
        {
            return item.Name == "Sulfuras, Hand of Ragnaros";
        }

        public static bool IsDropToZeroItem(this Item item)
        {
            return item.Name == "Backstage passes to a TAFKAL80ETC concert";
        }

        public static bool IsIncreaseItem(this Item item)
        {
            return item.Name == "Aged Brie";
        }
    }
}
