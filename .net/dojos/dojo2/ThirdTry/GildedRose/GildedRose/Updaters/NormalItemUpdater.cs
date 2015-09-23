namespace GildedRose.Updaters
{
    internal class NormalItemUpdater : ITemUpdater
    {
        public bool CanUpdate(Item item)
        {
            return !item.IsValueAdding() && !item.IsTimeLimited();
        }

        public void Update(Item item)
        {
            item.TryDecreaseOne();
            if (item.SellIn < 0)
            {
                item.TryDecreaseOne();
            }
        }
    }
}