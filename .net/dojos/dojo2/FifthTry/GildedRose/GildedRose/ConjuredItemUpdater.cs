namespace GildedRose
{
    class ConjuredItemUpdater : ItemUpdater
    {
        public override bool CanUpdate(Item item)
        {
            return IsConjuredItem(item);
        }

        public static bool IsConjuredItem(Item item)
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