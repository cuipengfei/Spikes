namespace GildedRose
{
    class TimeLimitedItemUpdater : ItemUpdater
    {
        public override void Update(Item item)
        {
            UpdateTimeLimitedItem(item);
        }

        public override bool CanUpdate(Item item)
        {
            return IsTimeLimitedItem(item);
        }

        public static bool IsTimeLimitedItem(Item item)
        {
            return item.Name == "Backstage passes to a TAFKAL80ETC concert";
        }

        public static void UpdateTimeLimitedItem(Item item)
        {
            Program.TryIncreaseOneQuality(item);

            if (TimeLimitedItemUpdater.IsTimeLimitedItem(item))
            {
                if (item.SellIn < 10)
                {
                    Program.TryIncreaseOneQuality(item);
                }

                if (item.SellIn < 5)
                {
                    Program.TryIncreaseOneQuality(item);
                }
                if (item.SellIn < 0)
                {
                    Program.ToZero(item);
                }
            }
        }
    }
}