using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AbstractFactory
{
    internal class Program
    {
        static void Main(string[] args)
        {
            PacknDeliveryFactory sf = new StandardFactory();
            Client standard = new Client(sf);
            Console.WriteLine(standard.DeliveryDocument.GetType().ToString());
            Console.WriteLine(standard.Packaging.GetType().ToString());

            PacknDeliveryFactory df = new DelicateFactory();
            Client delicate = new Client(sf);
            Console.WriteLine(delicate.DeliveryDocument.GetType().ToString());
            Console.WriteLine(delicate.Packaging.GetType().ToString());
            Console.ReadLine();
        }
    }
    public abstract class PacknDeliveryFactory
    {
        public abstract Packaging CreatePackaging();
        public abstract DeliveryDocument CreateDeliveryDocument();
    }
    public class Client
    {
        private Packaging _Packaging;
        private DeliveryDocument _DeliveryDocument;
        public Client(PacknDeliveryFactory _PacknDeliveryFactory)
        {
            _Packaging = _PacknDeliveryFactory.CreatePackaging();
            _DeliveryDocument = _PacknDeliveryFactory.CreateDeliveryDocument();
        }
        public Packaging Packaging { get { return _Packaging; } }
        public DeliveryDocument DeliveryDocument { get { return _DeliveryDocument; } }
    }
    public class StandardFactory : PacknDeliveryFactory
    {
        public override DeliveryDocument CreateDeliveryDocument()
        {
            return new Postal();
        }

        public override Packaging CreatePackaging()
        {
            return new StanderedPackaging();
        }
    }
    public class DelicateFactory : PacknDeliveryFactory
    {
        public override DeliveryDocument CreateDeliveryDocument()
        {
            return new Courier();
        }

        public override Packaging CreatePackaging()
        {
            return new ShockProofPackaging();
        }
    }

    public abstract class Packaging { }
    public class StanderedPackaging : Packaging { }
    public class ShockProofPackaging : Packaging { }

    public abstract class DeliveryDocument { }
    public class Postal : DeliveryDocument { }
    public class Courier : DeliveryDocument { }
}
