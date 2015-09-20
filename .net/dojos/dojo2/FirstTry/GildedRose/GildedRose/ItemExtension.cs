using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace GildedRose
{
    static class ItemExtension
    {
        public static void DecreaseQuality(this Item item)
        {
            item.Quality = item.Quality - 1;
        }

        public static void IncreaseQuality(this Item item)
        {
            item.Quality = item.Quality + 1;
        }

        public static void DropToZero(this Item item)
        {
            item.Quality = item.Quality - item.Quality;
        }

        public static void DecreaseSellIn(this Item item)
        {
            item.SellIn = item.SellIn - 1;
        }

        public static bool IsValueIncreaseItem(this Item item)
        {
            return item.Name == "Aged Brie" || item.Name == "Backstage passes to a TAFKAL80ETC concert";
        }

        public static void Increase(this Item item)
        {
            if (item.Quality < 50)
            {
                item.IncreaseQuality();

                if (item.Name == "Backstage passes to a TAFKAL80ETC concert")
                {
                    if (item.SellIn < 11)
                    {
                        if (item.Quality < 50)
                        {
                            item.IncreaseQuality();
                        }
                    }

                    if (item.SellIn < 6)
                    {
                        if (item.Quality < 50)
                        {
                            item.IncreaseQuality();
                        }
                    }
                }
            }
        }

        public static void Decrease(this Item item)
        {
            if (item.Quality > 0)
            {
                if (item.Name != "Sulfuras, Hand of Ragnaros")
                {
                    item.DecreaseQuality();
                }
            }
        }

        public static bool ShouldNeverChange(this Item item)
        {
            return item.Name == "Sulfuras, Hand of Ragnaros";
        }

        public static bool IsDropToZeroItem(this Item item)
        {
            return item.Name == "Backstage passes to a TAFKAL80ETC concert";
        }
    }
}
