using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace GildedRose
{
    static class TimeLimitedItemExtensions
    {
        public static void UpdateTimeLimitedItem(this Item item)
        {
            item.TryIncreaseOne();

            if (item.SellIn < 10)
            {
                item.TryIncreaseOne();
            }

            if (item.SellIn < 5)
            {
                item.TryIncreaseOne();
            }
            if (item.SellIn < 0)
            {
                if (item.IsTimeLimited())
                {
                    item.ClearQuality();
                }
            }
        }

        public static void TryIncreaseOne(this Item item)
        {
            if (item.Quality < 50)
            {
                item.IncreaseOneQuality();
            }
        }

        public static void ClearQuality(this Item item)
        {
            item.Quality = item.Quality - item.Quality;
        }
    }
}
