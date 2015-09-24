using System;
using System.Collections.Generic;

namespace GildedRose
{
    public class Program
    {
        public IList<Item> Items;

        private static void Main(string[] args)
        {
            Console.WriteLine("OMGHAI!");

            var app = InitApp();

            app.UpdateQuality();

            Console.ReadKey();
        }

        public static Program InitApp()
        {
            var app = new Program
            {
                Items = new List<Item>
                {
                    new Item {Name = "+5 Dexterity Vest", SellIn = 10, Quality = 20},
                    new Item {Name = "Aged Brie", SellIn = 2, Quality = 0},
                    new Item {Name = "Elixir of the Mongoose", SellIn = 5, Quality = 7},
                    new Item {Name = "Sulfuras, Hand of Ragnaros", SellIn = 0, Quality = 80},
                    new Item
                    {
                        Name = "Backstage passes to a TAFKAL80ETC concert",
                        SellIn = 15,
                        Quality = 20
                    },
                    new Item {Name = "Conjured Mana Cake", SellIn = 3, Quality = 6}
                }
            };
            return app;
        }

        public void UpdateQuality()
        {
            foreach (Item item in Items)
            {
                if (IsNotLegendaryItem(item))
                {
                    PassOneDay(item);

                    if (!IsValueAddingItem(item) && !IsTimeLimitedItem(item))
                    {
                        UpdateRegularItem(item);
                    }
                    else if (IsTimeLimitedItem(item))
                    {
                        UpdateTimeLimitedItem(item);
                    }
                    else if (IsValueAddingItem(item))
                    {
                        UpdateValueAddingItem(item);
                    }
                }
            }
        }

        private static void UpdateValueAddingItem(Item item)
        {
            TryIncreaseOneQuality(item);
            if (item.SellIn < 0)
            {
                TryIncreaseOneQuality(item);
            }
        }

        private static void UpdateTimeLimitedItem(Item item)
        {
            TryIncreaseOneQuality(item);

            if (IsTimeLimitedItem(item))
            {
                if (item.SellIn < 10)
                {
                    TryIncreaseOneQuality(item);
                }

                if (item.SellIn < 5)
                {
                    TryIncreaseOneQuality(item);
                }
                if (item.SellIn < 0)
                {
                    ToZero(item);
                }
            }
        }

        private static void UpdateRegularItem(Item item)
        {
            TryDecreaseOneQuality(item);
            if (item.SellIn < 0)
            {
                TryDecreaseOneQuality(item);
            }
        }

        private static bool IsNotLegendaryItem(Item item)
        {
            return item.Name != "Sulfuras, Hand of Ragnaros";
        }

        private static bool IsTimeLimitedItem(Item item)
        {
            return item.Name == "Backstage passes to a TAFKAL80ETC concert";
        }

        private static bool IsValueAddingItem(Item item)
        {
            return item.Name == "Aged Brie";
        }

        private static void PassOneDay(Item item)
        {
            item.SellIn = item.SellIn - 1;
        }

        private static void ToZero(Item item)
        {
            item.Quality = item.Quality - item.Quality;
        }

        private static void TryIncreaseOneQuality(Item item)
        {
            if (item.Quality < 50)
            {
                item.Quality = item.Quality + 1;
            }
        }

        private static void TryDecreaseOneQuality(Item item)
        {
            if (item.Quality > 0)
            {
                if (IsNotLegendaryItem(item))
                {
                    item.Quality = item.Quality - 1;
                }
            }
        }
    }

    public class Item
    {
        public string Name { get; set; }

        public int SellIn { get; set; }

        public int Quality { get; set; }
    }
}