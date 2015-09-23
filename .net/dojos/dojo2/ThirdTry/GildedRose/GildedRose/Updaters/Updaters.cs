using System.Collections.Generic;
using System.Linq;

namespace GildedRose.Updaters
{
    public static class Updaters
    {
        private static readonly List<ITemUpdater> updaters = new List<ITemUpdater>
        {
            new ConjuredItemUpdater(),
            new NormalItemUpdater(),
            new TimeLimitedItemUpdater(),
            new ValueAddingItemUpdater()
        };

        public static ITemUpdater FindUpdater(Item item)
        {
            return updaters.FirstOrDefault(updater => updater.CanUpdate(item));
        }
    }
}