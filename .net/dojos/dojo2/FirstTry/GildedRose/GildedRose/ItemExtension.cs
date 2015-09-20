namespace GildedRose
{
    internal static class ItemExtension
    {
        public static void DecreaseQuality(this Item item)
        {
            item.Quality = item.Quality - 1;
        }

        public static void IncreaseQuality(this Item item)
        {
            item.Quality = item.Quality + 1;
        }

        public static void DecreaseSellIn(this Item item)
        {
            item.SellIn = item.SellIn - 1;
        }

        public static bool IsValueIncreaseItem(this Item item)
        {
            return item.Name == "Aged Brie" || item.Name == "Backstage passes to a TAFKAL80ETC concert";
        }

        public static void Increase(this Item item)
        {
            if (item.Quality < 50)
            {
                if (item.Name == "Aged Brie")
                {
                    item.BrieIncrease();
                }

                if (item.Name == "Backstage passes to a TAFKAL80ETC concert")
                {
                    item.TicketIncrease();
                }
            }
        }

        public static bool ShouldNeverChange(this Item item)
        {
            return item.Name == "Sulfuras, Hand of Ragnaros";
        }

        public static void DecreaseDate(this Item item)
        {
            if (!item.ShouldNeverChange())
            {
                item.DecreaseSellIn();
            }
        }
    }
}