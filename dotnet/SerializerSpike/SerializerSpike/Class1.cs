using YAXLib;

namespace SerializerSpike
{
    public interface ISomeIterface{}
    [YAXSerializableType(FieldsToSerialize = YAXSerializationFields.AllFields)]    
    public class Class1:ISomeIterface
    {
        private Class1 fuck;
        private readonly int xxx=123; 
        private  readonly string yyy="fffff";

        public Class1()
        {
            fuck = this;
        }
    }
}