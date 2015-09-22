namespace GildedRose
{
    internal static class ItemExtensions
    {
        public static void DecreaseOneQuality(this Item item)
        {
            item.Quality = item.Quality - 1;
        }

        public static bool IsLegendary(this Item item)
        {
            return item.Name == "Sulfuras, Hand of Ragnaros";
        }

        public static void DecreaseOneDay(this Item item)
        {
            item.SellIn = item.SellIn - 1;
        }

        public static void IncreaseOneQuality(this Item item)
        {
            item.Quality = item.Quality + 1;
        }
    }
}