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
            if (item.Name != "Sulfuras, Hand of Ragnaros")
            {
                if (item.Quality > 0)
                {
                        item.DecreaseQuality();
                        if (item.SellIn < 0)
                        {
                            if (item.Quality > 0)
                            {
                                item.DecreaseQuality();
                            }
                        }
                }
            }
        }
    }
}
