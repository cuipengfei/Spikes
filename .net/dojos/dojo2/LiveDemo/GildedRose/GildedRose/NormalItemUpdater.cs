using System;

namespace GildedRose
{
    class NormalItemUpdater : ItemUpdater
    {
        public override bool CanUpdate(Item item)
        {
            return !ValueAddingItemUpdater.IsValueAddingItem(item) && !TimeLimitedItemUpdater.IsTimeLimitedItem(item);
        }

        public override void Update(Item item)
        {
            TryDecreaseOneQuality(item);
            if (item.SellIn < 0)
            {
                TryDecreaseOneQuality(item);
            }
        }
    }
}