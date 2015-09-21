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

        public static void DropToZero(this Item item)
        {
            item.Quality = item.Quality - item.Quality;
        }

        public static void DecreaseDate(this Item item)
        {
            item.SellIn = item.SellIn - 1;
        }
    }
}
