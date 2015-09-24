namespace GildedRose
{
    class RegularItemUpdater : ItemUpdater
    {
        public override bool CanUpdate(Item item)
        {
            return IsRegularItem(item);
        }

        public override void Update(Item item)
        {
            UpdateRegularItem(item);
        }

        public static bool IsRegularItem(Item item)
        {
            return !ValueAddingItemUpdater.IsValueAddingItem(item) && !TimeLimitedItemUpdater.IsTimeLimitedItem(item);
        }

        public static void UpdateRegularItem(Item item)
        {
            Program.TryDecreaseOneQuality(item);
            if (item.SellIn < 0)
            {
                Program.TryDecreaseOneQuality(item);
            }
        }
    }
}