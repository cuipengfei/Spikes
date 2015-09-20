using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace GildedRose
{
    static class TicketExtension
    {
        public static void TicketIncrease(this Item item)
        {
            item.IncreaseQuality();

            if (item.SellIn < 10)
            {
                if (item.Quality < 50)
                {
                    item.IncreaseQuality();
                }
            }

            if (item.SellIn < 5)
            {
                if (item.Quality < 50)
                {
                    item.IncreaseQuality();
                }
            }
        }

        public static void DropToZero(this Item item)
        {
            item.Quality = item.Quality - item.Quality;
        }

        public static bool IsDropToZeroItem(this Item item)
        {
            return item.Name == "Backstage passes to a TAFKAL80ETC concert";
        }
    }
}
