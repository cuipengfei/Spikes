using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace GildedRose
{
    static class ValueAddingItemExtensions
    {
        public static void UpdateValueAddingItem(this Item item)
        {
            item.TryIncreaseOne();
            if (item.SellIn < 0)
            {
                if (item.IsValueAdding())
                {
                    item.TryIncreaseOne();
                }
            }
        }
    }
}
