namespace GildedRose.Updaters
{
    internal class TimeLimitedItemUpdater : ITemUpdater
    {
        public bool CanUpdate(Item item)
        {
            return item.IsTimeLimited();
        }

        public void Update(Item item)
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
            if (item.SellIn < 0)
            {
                ClearQuality(item);
            }
        }

        public static void ClearQuality(Item item)
        {
            item.Quality = item.Quality - item.Quality;
        }
    }
}