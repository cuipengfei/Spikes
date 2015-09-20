namespace GildedRose
{
    internal static class NormalItemExtension
    {
        public static void Decrease(this Item item)
        {
            if (item.Name != "Sulfuras, Hand of Ragnaros")
            {
                DecreaseIfNotZero(item);
                if (item.SellIn < 0)
                {
                    DecreaseIfNotZero(item);
                }
            }
        }

        private static void DecreaseIfNotZero(Item item)
        {
            if (item.Quality > 0)
            {
                item.DecreaseQuality();
            }
        }
    }
}