namespace GildedRose
{
    public class UpdateSuddenDropItemStrategy : Updater
    {
        public override bool CanUpdate(Item item)
        {
            return IsSuddenDropItem(item);
        }

        public override void Update(Item item)
        {
            UpdateSuddenDropItem(item);
        }

        private static bool IsSuddenDropItem(Item item)
        {
            return item.Name == "Backstage passes to a TAFKAL80ETC concert";
        }

        public static void ToZero(Item item)
        {
            item.Quality = item.Quality - item.Quality;
        }

        public static void UpdateSuddenDropItem(Item item)
        {
            UpdateSuddenDropItemStrategy.TryIncreaseOne(item);
            if (item.SellIn < 10)
            {
                UpdateSuddenDropItemStrategy.TryIncreaseOne(item);
            }
            if (item.SellIn < 5)
            {
                UpdateSuddenDropItemStrategy.TryIncreaseOne(item);
            }
            if (item.SellIn < 0)
            {
                UpdateSuddenDropItemStrategy.ToZero(item);
            }
        }
    }
}