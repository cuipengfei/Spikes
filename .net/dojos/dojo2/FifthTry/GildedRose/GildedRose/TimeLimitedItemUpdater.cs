namespace GildedRose
{
    internal class TimeLimitedItemUpdater : ItemUpdater
    {
        public override void Update(Item item)
        {
            TryIncreaseOneQuality(item);

            if (item.SellIn < 10)
            {
                TryIncreaseOneQuality(item);
            }

            if (item.SellIn < 5)
            {
                TryIncreaseOneQuality(item);
            }

            if (item.SellIn < 0)
            {
                ToZero(item);
            }
        }

        public override bool CanUpdate(Item item)
        {
            return IsTimeLimitedItem(item);
        }

        public static bool IsTimeLimitedItem(Item item)
        {
            return item.Name == "Backstage passes to a TAFKAL80ETC concert";
        }

        private static void ToZero(Item item)
        {
            item.Quality = item.Quality - item.Quality;
        }
    }
}