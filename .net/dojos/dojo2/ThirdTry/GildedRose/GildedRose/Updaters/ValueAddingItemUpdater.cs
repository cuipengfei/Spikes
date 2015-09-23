namespace GildedRose.Updaters
{
    internal class ValueAddingItemUpdater : ITemUpdater
    {
        public bool CanUpdate(Item item)
        {
            return item.IsValueAdding();
        }

        public void Update(Item item)
        {
            item.TryIncreaseOne();
            if (item.SellIn < 0)
            {
                item.TryIncreaseOne();
            }
        }
    }
}