using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace GildedRose
{
    static class NormalItemExtension
    {
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
    }
}
