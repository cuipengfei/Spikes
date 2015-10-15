namespace GildedRose
{
    internal abstract class ItemUpdater
    {
        public abstract bool CanUpdate(Item item);
        public abstract void Update(Item item);

        protected static void TryIncreaseOneQuality(Item item)
        {
            if (item.Quality < 50)
            {
                item.Quality = item.Quality + 1;
            }
        }

        protected static void TryDecreaseOneQuality(Item item)
        {
            if (item.Quality > 0)
            {
                item.Quality = item.Quality - 1;
            }
        }
    }

    internal class ConjuredItemUpdater : ItemUpdater
    {
        public override bool CanUpdate(Item item)
        {
            return item.Name == "Conjured Mana Cake";
        }

        public override void Update(Item item)
        {
            TryDecreaseOneQuality(item);
            TryDecreaseOneQuality(item);
        }
    }
}