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

        public static void TryDecreaseOne(this Item item)
        {
            if (item.Quality > 0)
            {
                item.DecreaseOneQuality();
            }
        }

        public static bool IsTimeLimited(this Item item)
        {
            return item.Name == "Backstage passes to a TAFKAL80ETC concert";
        }

        public static void TryIncreaseOne(this Item item)
        {
            if (item.Quality < 50)
            {
                item.IncreaseOneQuality();
            }
        }

        public static bool IsValueAdding(this Item item)
        {
            return item.Name == "Aged Brie";
        }
    }
}