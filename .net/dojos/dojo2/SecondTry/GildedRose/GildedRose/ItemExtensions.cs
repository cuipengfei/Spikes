namespace GildedRose
{
    internal static class ItemExtensions
    {
        public static void DecreaseQualityByOne(this Item item)
        {
            item.Quality = item.Quality - 1;
        }

        public static void IncreaseQualityByOne(this Item item)
        {
            item.Quality = item.Quality + 1;
        }

        public static void DecreaseDate(this Item item)
        {
            item.SellIn = item.SellIn - 1;
        }

        public static bool IsNeverChangeItem(this Item item)
        {
            return item.Name == "Sulfuras, Hand of Ragnaros";
        }

        public static void Update(this Item item)
        {
            item.DecreaseDate();
            if (item.IsIncreaseItem())
            {
                item.UpdateIncreaseItemQuality();
            }
            if (item.IsDropToZeroItem())
            {
                item.UpdateDropToZeroItemQuality();
            }
            if (item.IsConjuredItem())
            {
                item.UpdateConjuredItemQuality();
            }
            if (item.IsNormalItem())
            {
                item.UpdateNormalItemQuality();
            }
        }
    }
}