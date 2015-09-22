namespace GildedRose
{
    internal static class ValueAddingItemExtensions
    {
        public static void UpdateValueAddingItem(this Item item)
        {
            item.TryIncreaseOne();
            if (item.SellIn < 0)
            {
                item.TryIncreaseOne();
            }
        }

        public static bool IsValueAdding(this Item item)
        {
            return item.Name == "Aged Brie";
        }
    }
}