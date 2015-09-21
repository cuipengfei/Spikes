namespace GildedRose
{
    internal static class DropToZeroItemExtensions
    {
        public static void UpdateDropToZeroItemQuality(this Item item)
        {
            item.TryIncreaseOne();

            if (item.IsDropToZeroItem())
            {
                if (item.SellIn < 10)
                {
                    item.TryIncreaseOne();
                }

                if (item.SellIn < 5)
                {
                    item.TryIncreaseOne();
                }
            }

            if (item.SellIn < 0)
            {
                item.DropToZero();
            }
        }

        public static void DropToZero(this Item item)
        {
            item.Quality = item.Quality - item.Quality;
        }
    }
}