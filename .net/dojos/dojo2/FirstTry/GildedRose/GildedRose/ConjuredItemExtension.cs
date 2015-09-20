namespace GildedRose
{
    internal static class ConjuredItemExtension
    {
        public static bool IsConjuredItem(this Item item)
        {
            return item.Name == "Conjured Mana Cake";
        }

        public static void DoubleDecrease(this Item item)
        {
            item.DecreaseIfNotZero();
            item.DecreaseIfNotZero();
        }
    }
}