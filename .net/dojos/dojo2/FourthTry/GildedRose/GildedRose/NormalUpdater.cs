using static GildedRose.UpdateSuddenDropItemStrategy;
using static GildedRose.ValueAddingUpdater;

namespace GildedRose
{
    internal class NormalUpdater : Updater
    {
        public override bool CanUpdate(Item item)
        {
            return !IsValueAddingItem(item) && !IsSuddenDropItem(item);
        }

        public override void Update(Item item)
        {
            TryDecreaseOne(item);
            if (item.SellIn < 0)
            {
                TryDecreaseOne(item);
            }
        }
    }
}