using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace GildedRose
{
    public abstract class UpdateStrategy
    {
        public abstract bool CanUpdate(Item item);
        public abstract void Update(Item item);

        public static void TryDecreaseOne(Item item)
        {
            if (item.Quality > 0)
            {
                if (item.Name != "Sulfuras, Hand of Ragnaros")
                {
                    item.Quality = item.Quality - 1;
                }
            }
        }

        public static void TryIncreaseOne(Item item)
        {
            if (item.Quality < 50)
            {
                item.Quality = item.Quality + 1;
            }
        }
    }
}