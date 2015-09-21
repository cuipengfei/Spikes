namespace GildedRose
{
    internal static class ConjuredItemExtensions
    {
        public static void UpdateConjuredItemQuality(this Item item)
        {
            item.TryDecreaseOne();
            item.TryDecreaseOne();
        }
    }
}