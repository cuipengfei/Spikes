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
            TryIncreaseOne(item);
            if (item.SellIn < 10)
            {
                TryIncreaseOne(item);
            }
            if (item.SellIn < 5)
            {
                TryIncreaseOne(item);
            }
            if (item.SellIn < 0)
            {
                ToZero(item);
            }
        }

        public static bool IsSuddenDropItem(Item item)
        {
            return item.Name == "Backstage passes to a TAFKAL80ETC concert";
        }

        private static void ToZero(Item item)
        {
            item.Quality = item.Quality - item.Quality;
        }
    }
}