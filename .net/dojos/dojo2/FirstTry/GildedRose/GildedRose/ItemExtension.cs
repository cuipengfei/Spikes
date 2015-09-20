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
    }
}
