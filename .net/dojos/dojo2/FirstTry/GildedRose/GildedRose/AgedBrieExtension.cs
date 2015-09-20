using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace GildedRose
{
    static class AgedBrieExtension
    {
        public static void BrieIncrease(this Item item)
        {
            if (item.SellIn > 0)
            {
                item.IncreaseQuality();
            }
            else
            {
                item.IncreaseQuality();
                item.IncreaseQuality();
            }
        }
    }
}
