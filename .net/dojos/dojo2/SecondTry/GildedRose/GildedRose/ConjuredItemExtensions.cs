namespace GildedRose
{
    internal static class ConjuredItemExtensions
    {
        public static void UpdateConjuredItemQuality(this Item item)
        {
            item.TryDecreaseOne();
            item.TryDecreaseOne();
        }

        public static bool IsConjuredItem(this Item item)
        {
            return item.Name == "Conjured Mana Cake";
        }
    }
}