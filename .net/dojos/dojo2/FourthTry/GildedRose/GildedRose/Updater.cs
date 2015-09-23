namespace GildedRose
{
    public abstract class Updater
    {
        public abstract bool CanUpdate(Item item);
        public abstract void Update(Item item);

        protected static void TryDecreaseOne(Item item)
        {
            if (item.Quality > 0)
            {
                item.Quality = item.Quality - 1;
            }
        }

        protected static void TryIncreaseOne(Item item)
        {
            if (item.Quality < 50)
            {
                item.Quality = item.Quality + 1;
            }
        }
    }
}