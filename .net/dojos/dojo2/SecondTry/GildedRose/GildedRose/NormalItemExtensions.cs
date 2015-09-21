using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace GildedRose
{
    static class NormalItemExtensions
    {
        public static void NormalItemDecrease(this Item item)
        {
            if (!item.IsNeverChangeItem())
            {
                TryDecrease(item);
                if (item.SellIn < 0)
                {
                    item.TryDecrease();
                }
            }
        }

        public static void TryDecrease(this Item item)
        {
            if (item.Quality > 0)
            {
                item.DecreaseQualityByOne();
            }
        }
    }
}
