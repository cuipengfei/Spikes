using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace GildedRose
{
    internal static class NormalItemExtensions
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
                item.DecreaseOneQuality();
            }
        }

        public static bool IsNormalItem(this Item item)
        {
            return !item.IsValueAdding() && !item.IsTimeLimited();
        }
    }
}