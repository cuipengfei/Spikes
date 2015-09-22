namespace GildedRose
{
    internal static class ConjuredItemExtensions
    {
        public static bool IsConjured(this Item item)
        {
            return item.Name == "Conjured Mana Cake";
        }

        public static void UpdateConjuredItem(this Item item)
        {
            item.TryDecreaseOne();
            item.TryDecreaseOne();
        }
    }
}