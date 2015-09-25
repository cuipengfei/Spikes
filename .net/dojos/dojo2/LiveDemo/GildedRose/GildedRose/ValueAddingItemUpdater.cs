using System;

namespace GildedRose
{
    class ValueAddingItemUpdater : ItemUpdater
    {
        public override bool CanUpdate(Item item)
        {
            return IsValueAddingItem(item);
        }

        public override void Update(Item item)
        {
            TryIncreaseOneQuality(item);
            if (item.SellIn < 0)
            {
                TryIncreaseOneQuality(item);
            }
        }

        public static bool IsValueAddingItem(Item item)
        {
            return item.Name == "Aged Brie";
        }
    }
}