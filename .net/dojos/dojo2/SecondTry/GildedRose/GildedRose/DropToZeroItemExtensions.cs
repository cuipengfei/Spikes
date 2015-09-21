namespace GildedRose
{
    internal static class DropToZeroItemExtensions
    {
        public static void UpdateDropToZeroItemQuality(this Item item)
        {
            if (item.SellIn >= 0)
            {
                item.DropToZeroItemIncrease();
            }
            else
            {
                item.DropToZero();
            }
        }

        private static void DropToZeroItemIncrease(this Item item)
        {
            item.TryIncreaseOne();

            if (item.SellIn < 10)
            {
                item.TryIncreaseOne();
            }

            if (item.SellIn < 5)
            {
                item.TryIncreaseOne();
            }
        }

        public static void DropToZero(this Item item)
        {
            item.Quality = item.Quality - item.Quality;
        }

        public static bool IsDropToZeroItem(this Item item)
        {
            return item.Name == "Backstage passes to a TAFKAL80ETC concert";
        }
    }
}