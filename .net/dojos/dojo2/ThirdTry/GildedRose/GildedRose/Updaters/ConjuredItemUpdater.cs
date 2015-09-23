namespace GildedRose.Updaters
{
    internal class ConjuredItemUpdater : ITemUpdater
    {
        public bool CanUpdate(Item item)
        {
            return item.Name == "Conjured Mana Cake";
        }

        public void Update(Item item)
        {
            item.TryDecreaseOne();
            item.TryDecreaseOne();
        }
    }
}