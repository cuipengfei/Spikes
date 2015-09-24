using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace GildedRose
{
    abstract class ItemUpdater
    {
        public abstract bool CanUpdate(Item item);
        public abstract void Update(Item item);
    }
}
