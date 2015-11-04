using System;
using System.Threading;
using YAXLib;

namespace SerializerSpike
{
    [YAXSerializableType(FieldsToSerialize = YAXSerializationFields.AllFields)]
    public class Parameters
    {
        private ISomeIterface c1;
        private  int a = 1;
        protected int b = 2;
        public int c = 3;
        public int A { get; private set; }
        private  Action<int> xxx;
//
        public event Action<int> XXX;

        private int B { get; set; }

        protected int C { get; set; }

        public Parameters()
        {
            c1=new Class1();
        }

        public void SetValues()
        {
            a = 8;
            b = 9;
            c = 123;
            A = 999;
            B = 64;
            C = 38;

            xxx=(i)=> { };

            XXX = xxx;
        }
    }
}