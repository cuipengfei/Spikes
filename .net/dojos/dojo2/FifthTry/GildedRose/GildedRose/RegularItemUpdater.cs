using static GildedRose.TimeLimitedItemUpdater;
using static GildedRose.ValueAddingItemUpdater;

namespace GildedRose
{
    internal class RegularItemUpdater : ItemUpdater
    {
        public override bool CanUpdate(Item item)
        {
            return !IsValueAddingItem(item) && !IsTimeLimitedItem(item);
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