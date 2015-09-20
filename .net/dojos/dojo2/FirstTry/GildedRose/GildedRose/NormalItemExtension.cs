namespace GildedRose
{
    internal static class NormalItemExtension
    {
        public static void NormalDecrease(this Item item)
        {
            item.DecreaseIfNotZero();
            if (item.SellIn < 0)
            {
                item.DecreaseIfNotZero();
            }
        }

        public static void DecreaseIfNotZero(this Item item)
        {
            if (item.Quality > 0)
            {
                item.DecreaseQuality();
            }
        }
    }
}