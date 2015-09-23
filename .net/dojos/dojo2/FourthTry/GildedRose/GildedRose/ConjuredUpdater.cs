namespace GildedRose
{
    internal class ConjuredUpdater : Updater
    {
        public override bool CanUpdate(Item item)
        {
            return IsConjured(item);
        }

        public static bool IsConjured(Item item)
        {
            return item.Name == "Conjured Mana Cake";
        }

        public override void Update(Item item)
        {
            TryDecreaseOne(item);
            TryDecreaseOne(item);
        }
    }
}