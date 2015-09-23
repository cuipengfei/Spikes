namespace GildedRose
{
    class ValueAddingUpdater : Updater
    {
        public override bool CanUpdate(Item item)
        {
            return IsValueAddingItem(item);
        }

        public override void Update(Item item)
        {
            UpdateValueAddingItem(item);
        }

        public static bool IsValueAddingItem(Item item)
        {
            return item.Name == "Aged Brie";
        }

        public static void UpdateValueAddingItem(Item item)
        {
            TryIncreaseOne(item);
            if (item.SellIn < 0)
            {
                TryIncreaseOne(item);
            }
        }
    }
}