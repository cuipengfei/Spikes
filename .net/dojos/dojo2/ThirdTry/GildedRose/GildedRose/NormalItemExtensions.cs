using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace GildedRose
{
    static class NormalItemExtensions
    {
        public static void UpdateNormalItem(this Item item)
        {
            TryDecreaseOne(item);
            if (item.SellIn < 0)
            {
                TryDecreaseOne(item);
            }
        }

        public static void TryDecreaseOne(this Item item)
        {
            if (item.Quality > 0)
            {
                if (!item.IsLegendary())
                {
                    item.DecreaseOneQuality();
                }
            }
        }
    }
}
