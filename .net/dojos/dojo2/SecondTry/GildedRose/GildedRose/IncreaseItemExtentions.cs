namespace GildedRose
{
    internal static class IncreaseItemExtentions
    {
        public static void UpdateIncreaseItemQuality(this Item item)
        {
            item.TryIncreaseOne();

            if (item.SellIn < 0)
            {
                item.TryIncreaseOne();
            }
        }

        public static void TryIncreaseOne(this Item item)
        {
            if (item.Quality < 50)
            {
                item.IncreaseQualityByOne();
            }
        }

        public static bool IsIncreaseItem(this Item item)
        {
            return item.Name == "Aged Brie";
        }
    }
}