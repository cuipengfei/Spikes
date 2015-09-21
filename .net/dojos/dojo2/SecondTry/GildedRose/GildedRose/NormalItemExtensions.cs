namespace GildedRose
{
    internal static class NormalItemExtensions
    {
        public static void UpdateNormalItemQuality(this Item item)
        {
            TryDecreaseOne(item);
            if (item.SellIn < 0)
            {
                item.TryDecreaseOne();
            }
        }

        public static void TryDecreaseOne(this Item item)
        {
            if (item.Quality > 0)
            {
                item.DecreaseQualityByOne();
            }
        }

        public static bool IsNormalItem(this Item item)
        {
            return !item.IsConjuredItem() && !item.IsDropToZeroItem() && !item.IsIncreaseItem();
        }
    }
}